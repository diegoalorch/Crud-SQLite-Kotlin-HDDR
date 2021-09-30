package com.example.crud_sqlite_kotlin_hddr


import android.annotation.SuppressLint
import android.app.Person
import android.content.ContentValues
import android.content.Context;
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.lang.Exception;

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        companion object{

            private const val DATABASE_VERSION = 1;
            private const val DATABASE_NAME = "persona.db";
            private const val PERSONA = "persona";
            private const val ID = "id";
            private const val NOMBRES = "nombres";
            private const val APELLIDOS = "apellidos";
            private const val DNI = "dni";
            private const val TELEFONO = "telefono";
            private const val CORREO = "correo";
            private const val DIRECCION = "direccion";
        }

    override fun onCreate(db : SQLiteDatabase?){

        val createTblPersona = ("CREATE TABLE " + PERSONA + "(" + ID + " INTEGER PRIMARY KEY," + NOMBRES + " TEXT," +
                                APELLIDOS + " TEXT," + DNI + " TEXT," + TELEFONO + " TEXT," + CORREO + " TEXT," +
                                DIRECCION + " TEXT" + ")");
        db?.execSQL(createTblPersona)
    }

    override fun onUpgrade(db : SQLiteDatabase?, oldVersion: Int, newVersion: Int){

        db!!.execSQL("DROP TABLE IF EXISTS $PERSONA");
        onCreate(db);
    }

    fun insertPersona(std: PersonaModel): Long {

        val db = this.writableDatabase;
        val contentValues = ContentValues();
        contentValues.put(ID, std.id);
        contentValues.put(NOMBRES, std.nombres);
        contentValues.put(APELLIDOS, std.apellidos);
        contentValues.put(DNI, std.dni);
        contentValues.put(TELEFONO, std.telefono);
        contentValues.put(CORREO, std.correo);
        contentValues.put(DIRECCION, std.direccion);

        val success = db.insert(PERSONA, null, contentValues);
        db.close();
        return success;
    }


    @SuppressLint("Range")
    fun getAllPersona(): ArrayList<PersonaModel>{

        val stdList : ArrayList<PersonaModel> = ArrayList();
        val selectQuery = "SELECT * FROM $PERSONA";
        val db = this.readableDatabase;

        val cursor: Cursor?;

        try {
            cursor = db.rawQuery(selectQuery, null);
        }catch (e: Exception){
            e.printStackTrace();
            db.execSQL(selectQuery);
            return ArrayList();
        }

        var id: Int;
        var nombre: String;
        var apellidos: String;
        var dni: String;
        var telefono: String;
        var correo: String;
        var direccion: String;

        if (cursor.moveToFirst()){
            do {

                id = cursor.getInt(cursor.getColumnIndex("id"));
                nombre = cursor.getString(cursor.getColumnIndex("nombres"));
                apellidos = cursor.getString(cursor.getColumnIndex("apellidos"));
                dni = cursor.getString(cursor.getColumnIndex("dni"));
                telefono = cursor.getString(cursor.getColumnIndex("telefono"));
                correo = cursor.getString(cursor.getColumnIndex("correo"));
                direccion = cursor.getString(cursor.getColumnIndex("direccion"));

                val std = PersonaModel(id = id, nombres = nombre,
                                        apellidos = apellidos, dni = dni,
                                        telefono = telefono, correo = correo,
                                        direccion = direccion);
                stdList.add(std);

            }while (cursor.moveToNext())
        }
        return stdList;
    }

    fun updatePersona(std: PersonaModel): Int{

        val db = this.writableDatabase;

        val contentValues = ContentValues();

        contentValues.put(ID, std.id);
        contentValues.put(NOMBRES, std.nombres);
        contentValues.put(APELLIDOS, std.apellidos);
        contentValues.put(DNI, std.dni);
        contentValues.put(TELEFONO, std.telefono);
        contentValues.put(CORREO, std.correo);
        contentValues.put(DIRECCION, std.direccion);

        val success = db.update(PERSONA, contentValues, "id=" + std.id, null);
        db.close();
        return success;
    }

    fun deletePersonaById(id : Int) : Int{

        val db = this.writableDatabase;

        val  contentValues = ContentValues();
        contentValues.put(ID, id);

        val success = db.delete(PERSONA, "id=$id", null);
        db.close();
        return success;
    }
}