import Navbar from './components/Navbar/Navbar'
import {Route, Routes} from 'react-router-dom'
import Home from './pages/Home/Home'
import Cart from './pages/Cart/Cart'
import PlaceOrder from './pages/PlaceOrder/PlaceOrder'
import Footer from './components/Footer/Footer'

function App() {  

  return (
    <div>
      <div className='app'>
        <Navbar/>
        <Routes>
            {/* Creamos una ruta con el path / y el componente Home */}
            <Route path="/" element={<Home />} />
            {/* Creamos una ruta con el path /cart y el componente Cart */}
            <Route path="/cart" element={<Cart/>} />
            {/* Creamos una ruta con el path /order y el componente PlaceOrder */}
            <Route path='/order' element={<PlaceOrder/>} />
          </Routes>
      </div>
      <Footer/>
    </div>
    
  )
}

export default App
