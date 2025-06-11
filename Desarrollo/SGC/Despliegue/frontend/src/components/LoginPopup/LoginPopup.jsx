import React, { useContext, useEffect, useState } from "react";
import "./LoginPopup.css";
import { assets } from "../../assets/assets";

const LoginPopup = ({setShowLogin}) => {

    const[currState,setCurrState] = useState("Login")
    const[data,setData] = useState({
        name:"",
        email:"",
        password:""
    })

    const onChangeHandler = (event) => {
        const name = event.target.name;
        const value = event.target.value;
        setData(data=>({...data,[name]:value}))
    }

    useEffect(()=>{
        console.log(data);
    },[data])

    return (
        <div className='login-popup'>
            <form className="login-popup-container">
                <div classsName="login-popup-title">
                    <h2>{currState}</h2>
                    <img onClick={()=>setShowLogin(false)} 
                    src={assets.cross_icon} 
                    /* Alguien Arregle el simbolo X que esta mal espaciado */
                    alt=""
                    />
                </div>
                <div className="login-popup-inputs">
                    {currState==="Login"?<></>:<input name='name' onChange={onChangeHandler} value={data.name} type="text" placeholder='Nombre' required/>}
                    <input name='email' onChange={onChangeHandler} value={data.email} type="email" placeholder='email' required/>
                    <input name='password' onChange={onChangeHandler}  value={data.password} type="password" placeholder='contraseña' required/>
                </div>
                <button>{currState==="Sign Up"?"Create account":"Login"}</button>
                <div className="login-popup-condition">
                    <input type="checkbox" required/>
                    <p>Acepto Terminos y Condiciones</p>
                </div>
                {currState==="Login"
                ?<p>Crear Cuenta nueva? <span onClick={()=>setCurrState("Sign Up")}>Click aqui</span></p>
                :<p>Ya tienes cuenta <span onClick={()=>setCurrState("Login")}>Login aqui</span></p>
                }

            </form>
        </div>
    )
}

export default LoginPopup