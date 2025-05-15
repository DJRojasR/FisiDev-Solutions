import streamlit as st
from langchain.text_splitter import RecursiveCharacterTextSplitter
from langchain_community.document_loaders import WebBaseLoader, UnstructuredURLLoader
from langchain_community.vectorstores import Chroma
from langchain_openai import OpenAIEmbeddings, ChatOpenAI
from langchain.chains import LLMChain, RetrievalQA
from langchain.prompts import PromptTemplate
from langchain.memory import ConversationBufferMemory
from langchain.chains.question_answering import load_qa_chain
import tempfile
import os
import validators

# Set page config
st.set_page_config(
    page_title="Advanced Web Content Analyzer",
    page_icon="🌐",
    layout="wide"
)

# Sidebar for API key and settings
with st.sidebar:
    st.title("Settings ⚙️")
    openai_api_key = st.text_input("OpenAI API Key", type="password", help="Get your API key from https://platform.openai.com/account/api-keys")
    
    st.subheader("Processing Options")
    chunk_size = st.slider("Chunk Size", 100, 2000, 500, help="Size of text chunks for processing")
    chunk_overlap = st.slider("Chunk Overlap", 0, 200, 50, help="Overlap between text chunks")
    temperature = st.slider("Creativity Level", 0.0, 1.0, 0.2, help="Higher values = more creative, Lower values = more factual")
    
    model_options = {
        "GPT-4 Turbo": "gpt-4-turbo",
        "GPT-4": "gpt-4",
        "GPT-3.5 Turbo": "gpt-3.5-turbo"
    }
    selected_model = st.selectbox("Select Model", options=list(model_options.keys()), index=0)
    
    st.markdown("---")
    st.caption("Made with ❤️ using LangChain & Streamlit")

# Main app
st.title("Advanced Web Content Analyzer 🌐")
st.caption("Summarize, analyze, and ask questions about web page content using AI")

# Tab interface
tab1, tab2, tab3 = st.tabs(["Single URL", "Multiple URLs", "Upload Documents"])

with tab1:
    st.subheader("Analyze a Single Web Page")
    webpage_url = st.text_input("Enter URL", placeholder="https://example.com", key="single_url")
    
    if webpage_url:
        if not validators.url(webpage_url):
            st.error("Please enter a valid URL")
        else:
            with st.spinner("Loading and processing web content..."):
                try:
                    # Load and process document
                    loader = WebBaseLoader(webpage_url)
                    docs = loader.load()
                    
                    # Split text
                    text_splitter = RecursiveCharacterTextSplitter(
                        chunk_size=chunk_size,
                        chunk_overlap=chunk_overlap
                    )
                    splits = text_splitter.split_documents(docs)
                    
                    # Initialize embeddings and vectorstore
                    embeddings = OpenAIEmbeddings(
                        model="text-embedding-ada-002",
                        openai_api_key=openai_api_key
                    )
                    
                    # Create persistent Chroma DB in temp directory
                    with tempfile.TemporaryDirectory() as temp_dir:
                        vectorstore = Chroma.from_documents(
                            documents=splits,
                            embedding=embeddings,
                            persist_directory=temp_dir
                        )
                        
                        # Initialize LLM
                        llm = ChatOpenAI(
                            model=model_options[selected_model],
                            max_tokens=2048,
                            openai_api_key=openai_api_key,
                            temperature=temperature
                        )
                        
                        # Define prompt templates
                        summary_template = """
                        You are an expert at summarizing web content. Create a comprehensive summary of the following content.
                        Include key points, main arguments, and important details.
                        Content: {context}
                        Summary:
                        """
                        
                        qa_template = """
                        You are an intelligent assistant that answers questions about web content.
                        Use the following context to answer the question at the end.
                        If you don't know the answer, say you don't know. Don't make up answers.
                        
                        Context: {context}
                        
                        Question: {question}
                        
                        Answer:
                        """
                        
                        SUMMARY_PROMPT = PromptTemplate(
                            template=summary_template,
                            input_variables=["context"]
                        )
                        
                        QA_PROMPT = PromptTemplate(
                            template=qa_template,
                            input_variables=["context", "question"]
                        )
                        
                        # Generate summary
                        summary_chain = load_qa_chain(
                            llm=llm,
                            chain_type="stuff",
                            prompt=SUMMARY_PROMPT
                        )
                        
                        summary = summary_chain.run(input_documents=splits[:5], question="")
                        
                        st.success("Content loaded successfully!")
                        
                        # Display summary
                        st.subheader("Page Summary")
                        st.write(summary)
                        
                        # Question answering section
                        st.subheader("Ask Questions About the Content")
                        
                        # Initialize conversation memory
                        if 'memory' not in st.session_state:
                            st.session_state.memory = ConversationBufferMemory(
                                memory_key="chat_history",
                                return_messages=True
                            )
                        
                        # Initialize QA chain
                        qa_chain = RetrievalQA.from_chain_type(
                            llm=llm,
                            chain_type="stuff",
                            retriever=vectorstore.as_retriever(),
                            chain_type_kwargs={
                                "prompt": QA_PROMPT,
                                "memory": st.session_state.memory
                            }
                        )
                        
                        # Chat interface
                        if "messages" not in st.session_state:
                            st.session_state.messages = []
                        
                        for message in st.session_state.messages:
                            with st.chat_message(message["role"]):
                                st.markdown(message["content"])
                        
                        if prompt := st.chat_input("Ask a question about the page"):
                            st.session_state.messages.append({"role": "user", "content": prompt})
                            with st.chat_message("user"):
                                st.markdown(prompt)
                            
                            with st.spinner("Thinking..."):
                                response = qa_chain({"query": prompt})
                            
                            with st.chat_message("assistant"):
                                st.markdown(response["result"])
                            
                            st.session_state.messages.append({"role": "assistant", "content": response["result"]})
                
                except Exception as e:
                    st.error(f"An error occurred: {str(e)}")

with tab2:
    st.subheader("Analyze Multiple Web Pages")
    urls = st.text_area(
        "Enter multiple URLs (one per line)",
        placeholder="https://example1.com\nhttps://example2.com",
        height=100
    )
    
    if urls:
        url_list = [url.strip() for url in urls.split('\n') if url.strip()]
        invalid_urls = [url for url in url_list if not validators.url(url)]
        
        if invalid_urls:
            st.error(f"Invalid URLs detected: {', '.join(invalid_urls)}")
        else:
            with st.spinner("Loading and processing multiple web pages..."):
                try:
                    loader = UnstructuredURLLoader(urls=url_list)
                    docs = loader.load()
                    
                    # Rest of processing similar to tab1
                    # (Would implement comparison features across multiple pages)
                    
                    st.success(f"Successfully loaded {len(url_list)} URLs")
                    
                except Exception as e:
                    st.error(f"Error loading URLs: {str(e)}")

with tab3:
    st.subheader("Upload Documents for Analysis")
    uploaded_files = st.file_uploader(
        "Upload PDF, TXT, or DOCX files",
        type=["pdf", "txt", "docx"],
        accept_multiple_files=True
    )
    
    if uploaded_files:
        st.warning("Document upload feature coming soon!")
        # Implementation would use appropriate document loaders
        # like PyPDFLoader, UnstructuredFileLoader, etc.

# Add some analytics
if webpage_url:
    with st.expander("Content Analysis Metrics"):
        col1, col2, col3 = st.columns(3)
        with col1:
            st.metric("Text Chunks", len(splits))
        with col2:
            st.metric("Estimated Words", sum(len(doc.page_content.split()) for doc in splits))
        with col3:
            st.metric("Embedding Model", "text-embedding-ada-002")


#modificado m15/05/2025        
