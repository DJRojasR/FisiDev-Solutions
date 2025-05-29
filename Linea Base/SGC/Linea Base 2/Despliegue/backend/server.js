import express from "express";
import cors from "cors";
import { connectDB } from "./config/db.js";

// Creamos una instancia de Express que sirve como nuestro servidor web
const app = express()
// Definimos el puerto en el que escuchará nuestro servidor
const port = 4000

//niddleware para manejar JSON en las solicitudes
app.use(express.json())
//Utilizamos CORS para permitir solicitudes desde otros dominios
app.use(cors())

// API routes es un mensaje de bienvenida que demuestra que el servidor está funcionando
app.get("/",(req,res)=>{
    res.send("Hello world!")
})

// Escuchamos en el puerto definido y mostramos un mensaje en la consola cuando el servidor está listo
app.listen(port, () => {
    console.log(`Server is running on http://localhost:${port}`);
})

//Conectamos con la base de datos MongoDB
connectDB();


//mongodb+srv://DJRojasR:<db_password>@juliafish.ufscbao.mongodb.net/
