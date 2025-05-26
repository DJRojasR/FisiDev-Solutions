import React, { useState } from 'react'
import "./Navbar.css"
import { assets } from '../../assets/assets'

const Navbar = () => {

    const [menu, setMenu] = useState("menu"); 

  return (
    <div className='navbar'>
        <img src={assets.logo_juliaFish} alt='' className='logo'/>
        <ul className='navbar-menu'>
            
            <li onClick={()=>setMenu("home")}
            className={menu==="home"?"active":""}
            >
                inicio
            </li>

            <li onClick={()=>setMenu("menu")}
            className={menu==="menu"?"active":""}
            >
                menu
            </li>

            <li onClick={()=>setMenu("mobile-app")}
            className={menu==="mobile-app"?"active":""}
            >
                app mobil
            </li>

            <li onClick={()=>setMenu("contact-us")}
            className={menu==="contact-us"?"active":""}
            >
                contactanos
            </li>

        </ul>

        <div className='navbar-right'>
            <img src={assets.search_icon} alt=''/>
            <div className='navbar-search-icon'>
                <img src={assets.basket_icon} alt='' /> 
                <div className='dot'></div>
            </div>
        </div>
        <button>Iniciar sesion</button>
    </div>
  )
}

export default Navbar
