package com.example.crud_sqlite_kotlin_hddr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PersonaAdapter : RecyclerView.Adapter<PersonaAdapter.PersonaViewHolder>() {

    private var stdList: ArrayList<PersonaModel> = ArrayList();
    private var onClickItem: ((PersonaModel) -> Unit)? = null;
    private var onClickEliminarItem: ((PersonaModel) -> Unit)? = null;

    fun addItems(items: ArrayList<PersonaModel>){
        this.stdList = items;
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (PersonaModel) -> Unit){
        this.onClickItem = callback;
    }

    fun setOnClickEliminarItem(callback: (PersonaModel) -> Unit){
        this.onClickEliminarItem = callback;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PersonaViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_std, parent, false)
    )

    override fun onBindViewHolder(holder: PersonaViewHolder, position: Int){
        val std = stdList[position];
        holder.bindView(std);
        holder.itemView.setOnClickListener(){ onClickItem?.invoke(std)}
        holder.eliminar.setOnClickListener { onClickEliminarItem?.invoke(std) }
    }

    override fun getItemCount(): Int {

        return stdList.size;

    }

    class PersonaViewHolder(var view: View): RecyclerView.ViewHolder(view){

        private var id = view.findViewById<TextView>(R.id.tvId);
        private var nombres = view.findViewById<TextView>(R.id.tvNombres);
        private var apellidos = view.findViewById<TextView>(R.id.tvApellidos);
        private var dni = view.findViewById<TextView>(R.id.tvDni);
        private var telefono = view.findViewById<TextView>(R.id.tvTelefono);
        private var correo = view.findViewById<TextView>(R.id.tvCorreo);
        private var direccion = view.findViewById<TextView>(R.id.tvDireccion);

        var eliminar = view.findViewById<Button>(R.id.btnEliminar);

        fun  bindView(std: PersonaModel){

            id.text = std.id.toString();
            nombres.text = std.nombres;
            apellidos.text = std.apellidos;
            dni.text = std.dni;
            telefono.text = std.telefono;
            correo.text = std.correo;
            direccion.text = std.direccion;

        }
    }

}