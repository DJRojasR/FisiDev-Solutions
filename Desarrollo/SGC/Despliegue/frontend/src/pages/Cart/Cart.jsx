import React, { useContext } from 'react'
import './Cart.css'
import { StoreContext } from '../../context/StoreContext'
import { useNavigate } from 'react-router-dom';

const Cart = () => {

  const{cartItems,food_list,removeFromCart, getTotalCartAmount,url} = useContext(StoreContext);

  const navigate = useNavigate();

  return (
    <div className='cart'>
      <div className="cart-items">
        <div className="cart-items-title">
          <p>Comida</p>
          <p>Nombre</p>
          <p>Precio</p>
          <p>Cantidad</p>
          <p>Total</p>
          <p>Remover</p>
        </div>
        <br />
        <hr />
       {food_list.map((item,index)=>{
        if(cartItems[item._id] > 0) {
          return (
            <div key={item._id}> {/* 👈 esta línea es la solución */}
              <div className='cart-items-title cart-items-item'>
                <img src={url+"/images/"+item.image} alt=""/>
                <p>{item.name}</p>
                <p>S/{item.price}</p>
                <p>{cartItems[item._id]}</p>
                <p>S/{item.price * cartItems[item._id]}</p>
                <button 
                  onClick={() => removeFromCart(item._id)} 
                  className="remove-btn">eliminar
                </button>

              </div>
              <hr/>
            </div>
          )
        }
      })}

      </div>
      <div className='cart-bottom'>
        <div className='cart-total'>
          <h2>Total en Carro</h2>
          <div>
            <div className="cart-total-details">
              <p>Subtotal</p>
              <p>S/{getTotalCartAmount()}</p>
            </div>
            <div className="cart-total-details">
              <p>Costo Delivery</p>
              <p>S/{getTotalCartAmount()===0?0:2}</p>
            </div>
            <div className="cart-total-details">
              <b>Total</b>
              <b>S/{getTotalCartAmount()===0?0:getTotalCartAmount()+2}</b>
            </div>
          </div>
          <button onClick={()=>navigate('/order')}>Proceder con la compra</button>
        </div>
        <div className="cart-promocode">
          <div>
            <p>Si tienes codigo de promocion agregarlo aqui</p>
            <div className='cart-promocode-input'>
              <input type="text" placeholder='Codigo Promocional'/>
              <button>Enviar</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Cart
