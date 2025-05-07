import streamlit as st
from langchain.text_splitter import RecursiveCharacterTextSplitter
from langchain_community.document_loaders import WebBaseLoader
from langchain_community.vectorstores import Chroma
from langchain_openai import OpenAIEmbeddings, ChatOpenAI
from langchain.chains import LLMChain
from langchain.prompts import PromptTemplate

openai_api_key = "[API_KEY]"

st.title("Web Page Summarizer 🌍")
st.caption("Summarize any web page using LangChain and OpenAI's GPT-4")

webpage_url = st.text_input("Enter the URL of the web page you want to summarize", type="default", placeholder="https://example.com")

if webpage_url:
    loader = WebBaseLoader(webpage_url)
    docs = loader.load()
    text_splitter = RecursiveCharacterTextSplitter(chunk_size=500, chunk_overlap=10)
    splits = text_splitter.split_documents(docs)
    embeddings = OpenAIEmbeddings(model="text-embedding-ada-002", openai_api_key=openai_api_key)
    vectorstore = Chroma.from_documents(documents=splits, embedding=embeddings)
    promp_template = """
    Eres un asistente inteligente que responde preguntas y hace resúmenes en el contenido de unapágina web. Responde las preguntas de los usuarios {input} basándote estrictamente en el {context} proporcionado. No hagas suposiciones ni inventes información que no esté incluída en el {context}. Si no puedes encontrar la respuesta en el contexto, di "No tengo suficiente información para responder a esa pregunta".
    """
    llm = ChatOpenAI(model="gpt-4o", max_tokens=1024, openai_api_key=openai_api_key, temperature=0.2)
    qa_chain = LLMChain(llm=llm, prompt=PromptTemplate.from_template(promp_template))
    
    def combine_docs(docs):
        return "\n\n".join(doc.page_content for doc in docs)
    
    def rag_chain(pregunta):
        retrieved_docs = retrieved.invoke(pregunta)
        formatted_docs = combine_docs(retrieved_docs)
        respuesta = qa_chain.invoke({"input": pregunta, "context": formatted_docs})
        return respuesta["text"]
    
    st.success(f"URL: {webpage_url} cargada exitosamente" )
    promp = st.text_input("¿Qué quieres saber de la página web?", type="default", placeholder="Escribe tu pregunta aquí")
    
    if promp:
        result = rag_chain(promp)
        st.write(result)
        