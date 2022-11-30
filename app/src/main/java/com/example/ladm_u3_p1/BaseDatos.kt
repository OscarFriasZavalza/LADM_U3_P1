package com.example.ladm_u3_p1

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos(
    context:Context?,
    name:String?,
    factory:SQLiteDatabase.CursorFactory?,
    version: Int
):SQLiteOpenHelper(context,name,factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table ESTUDIANTES(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NOMBRE VARCHAR(400)," +
                "APELLIDOP VARCHAR(400)," +
                "APELLIDOM VARCHAR(400)," +
                "ESCUELA VARCHAR(400)," +
                "TELEFONO INT," +
                "CARRERA1 VARCHAR(400)," +
                "CARRERA2 VARCHAR(400)," +
                "CORREO VARCHAR(400))"
        )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}