import React, { useContext } from 'react'
import './Cart.css'
import { StoreContext } from '../../context/StoreContext'

const Cart = () => {

  const {cartItems, food_list, removeFromCart} = useContext(StoreContext)

  return (
    <div className='cart'>
      <div className="cart-items">
        <div className="cart-items-title">
          <p>Items</p>
          <p>Titulo</p>
          <p>Precio</p>
          <p>Cantidad</p>
          <p>Total</p>
          <p>Quitar</p>
        </div>
        <br />
        <hr />
        {food_list.map((item, index)=>{
          if(cartItems[item._id]>0){
            return(
              <div className="cart-items-title cart-items-item">
                <p>{item.name}</p>
              </div>
            )
          }
        })}
      </div>
    </div>
  )
}

export default Cart
