package com.example.crud_sqlite_kotlin_hddr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var nombres: EditText
    private lateinit var apellidos: EditText
    private lateinit var dni: EditText
    private lateinit var telefono: EditText
    private lateinit var correo: EditText
    private lateinit var direccion: EditText

    private lateinit var agregar: Button
    private lateinit var listar: Button
    private lateinit var modificar: Button

    private lateinit var sqliteHelper: SQLiteHelper

    private lateinit var recyclerView: RecyclerView

    private var adapter: PersonaAdapter? = null
    private var std: PersonaModel? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        sqliteHelper = SQLiteHelper(this)

        agregar.setOnClickListener { addPersona() }
        listar.setOnClickListener { getPersona() }
        modificar.setOnClickListener { updatePersona() }

        adapter?.setOnClickItem {
            Toast.makeText(this, it.nombres, Toast.LENGTH_SHORT).show();

            nombres.setText(it.nombres);
            apellidos.setText(it.apellidos);
            dni.setText(it.dni);
            telefono.setText(it.telefono);
            correo.setText(it.correo);
            direccion.setText(it.direccion);

            std = it;
        }

        adapter?.setOnClickEliminarItem {
            eliminarPersona(it.id)
        }

    }

    private fun addPersona(){

        val nombres = nombres.text.toString()
        val apellidos = apellidos.text.toString()
        val dni = dni.text.toString()
        val telefono = telefono.text.toString()
        val correo = correo.text.toString()
        val direccion = direccion.text.toString()

        if (nombres.isEmpty() || apellidos.isEmpty() || dni.isEmpty() || telefono.isEmpty() || correo.isEmpty() || direccion.isEmpty()){

            Toast.makeText(this, "Por Ingrese datos en los Campos", Toast.LENGTH_SHORT).show()

        }else{

            val std = PersonaModel( nombres = nombres,
                                    apellidos = apellidos, dni = dni,
                                    telefono = telefono, correo = correo,
                                    direccion = direccion)
            val status = sqliteHelper.insertPersona(std)

            if (status > -1 ){

                Toast.makeText(this, "Persona Agregada...", Toast.LENGTH_SHORT).show()
                clearEditText()
                getPersona();
            }else{

                Toast.makeText(this, "Persona No se guardo...", Toast.LENGTH_SHORT).show()

            }

        }
    }

    private fun clearEditText(){

        nombres.setText("")
        apellidos.setText("")
        dni.setText("")
        telefono.setText("")
        correo.setText("")
        direccion.setText("")

        nombres.requestFocus()
    }

    private fun getPersona(){

        val stdList = sqliteHelper.getAllPersona()
        Log.e("pppp", "${stdList.size}")

        adapter?.addItems(stdList)
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PersonaAdapter()
        recyclerView.adapter = adapter
    }

    private fun updatePersona(){

        val nombres = nombres.text.toString();
        val apellidos = apellidos.text.toString();
        val dni = dni.text.toString();
        val telefono = telefono.text.toString();
        val correo = correo.text.toString();
        val direccion = direccion.text.toString();

        if (nombres == std?.nombres && apellidos == std?.apellidos && dni == std?.dni && telefono == std?.telefono && correo == std?.correo && direccion == std?.direccion){
            Toast.makeText(this, "Registro no cambiado...!", Toast.LENGTH_SHORT).show();
            return
        }

        if (std == null) return

        val std = PersonaModel(id = std!!.id, nombres = nombres, apellidos = apellidos, dni = dni, telefono = telefono, correo = correo, direccion = direccion);
        val status = sqliteHelper.updatePersona(std);
        if (status > -1){
            clearEditText();
            getPersona();
        }else{
            Toast.makeText(this, "Fallo al modificar...!", Toast.LENGTH_SHORT).show();
        }
    }

    private fun eliminarPersona(id : Int){

        if (id == null) return

        val builder = AlertDialog.Builder(this);
        builder.setMessage("Estas seguro de eliminar la Persona?");
        builder.setCancelable(true);
        builder.setPositiveButton("Si, Eliminar"){dialog, _ ->
            sqliteHelper.deletePersonaById(id)
            getPersona()
            dialog.dismiss()
        }
        builder.setNegativeButton("No Eliminar"){ dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create();
        alert.show();

    }

    private fun initView(){

        nombres = findViewById(R.id.nombre)
        apellidos = findViewById(R.id.apellidos)
        dni = findViewById(R.id.dni)
        telefono = findViewById(R.id.telefono)
        correo = findViewById(R.id.correo)
        direccion = findViewById(R.id.direccion)

        agregar = findViewById(R.id.agregar)
        listar = findViewById(R.id.listar)
        modificar = findViewById(R.id.modificar)

        recyclerView = findViewById(R.id.recyclerView)
    }
}