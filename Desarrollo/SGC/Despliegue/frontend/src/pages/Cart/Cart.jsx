import React, { useContext } from 'react'
import './Cart.css'
import { StoreContext } from '../../context/StoreContext'

const Cart = () => {

  const{cartItems,food_list,removeFromCart, getTotalCartAmount} = useContext(StoreContext);

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
          if(cartItems[item._id]>0)
          {
            return (
              <div>
                <div className='cart-items-title cart-items-item'>
                  <img src={item.image} alt=""/>
                  <p>{item.name}</p>
                  <p>S/{item.price}</p>
                  <p>{cartItems[item._id]}</p>
                  <p>S/{item.price*cartItems[item._id]}</p>
                  <p onClick={()=>removeFromCart(item._id)} className='cross'>x</p>
                </div>
                <hr/>
              </div>
            )
          }
        })}
      </div>
      <div className='cart-bottomn'>
        <div className='cart-total'>
          <h2>Total en Carro</h2>
          <div>
            <div className="cart-total-details">
              <p>Subtotal</p>
              <p>S/{getTotalCartAmount()}</p>
            </div>
            <div className="cart-total-details">
              <p>Costo Delivery</p>
              <p>S/{2}</p>
            </div>
            <div className="cart-total-details">
              <b>Total</b>
              <b>S/{getTotalCartAmount()+2}</b>
            </div>
          </div>
          <button>Proceder con la compra</button>
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
