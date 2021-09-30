package com.example.crud_sqlite_kotlin_hddr

import  java.util.*

class PersonaModel (

    var id: Int = getAutoId(),
    var nombres: String = "",
    var apellidos: String = "",
    var dni: String = "",
    var telefono: String = "",
    var correo: String = "",
    var direccion: String = ""
){
    companion object{

        fun getAutoId(): Int{
            val randon = Random();
            return randon.nextInt(100);
        }
    }


}