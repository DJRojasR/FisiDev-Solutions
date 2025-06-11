import React, { useContext } from 'react'
import './PlaceOrder.css'
import { StoreContext } from '../../context/StoreContext'

const PlaceOrder = () => {

  const {getTotalCartAmount} = useContext(StoreContext)

  return (
    <form className='place-order'>
      <div className="place-order-left">
        <p className="title">Informacion de Delivery</p>
        <div className="multi-fields">
          <input type="text" placeholder='Nombre'/>
          <input type="text" placeholder='Apellido'/>
        </div>
        <input type="email" placeholder='Email' />
        <input type="text" placeholder='Direccion' />
        <div className="multi-fields">
          <input type="text" placeholder='Distrito'/>
          <input type="text" placeholder='Ciudad'/>
        </div>
        <div className="multi-fields">
          <input type="text" placeholder='Codigo  Postal'/>
          <input type="text" placeholder='Pais'/>
        </div>
        <input type="texto" placeholder='Celular'/>
      </div>
      <div className='place-order-right'>
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
          <button >Proceder al pago</button>
        </div>
      </div>
    </form>
  )
}

export default PlaceOrder
