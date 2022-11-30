package com.example.ladm_u3_p1

import android.content.ContentValues
import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var bd=BaseDatos(this,"PRACTICA1",null,1)
    var IDs=ArrayList<Int>()
    var migracion=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mostrarTodos()

        //detectar conexion internet
        Thread(Runnable {
            while (true){

                var textInicial="no tiene conexion"
                var internet=false
                val sc=getSystemService(Context.CONNECTIVITY_SERVICE)as ConnectivityManager
                var redInfo=sc.allNetworkInfo
                for(ir in redInfo){
                    if (ir.typeName.equals("WIFI",ignoreCase = true))
                        if(ir.isConnected) {
                            textInicial="CONECTADO WIFI"
                            internet=true
                        }

                }
                runOnUiThread {
                    textoConexion.setText(textInicial)
                    if (internet == true && migracion==true) {
                        migracion =false
                        AlertDialog.Builder(this)
                            .setTitle("ATENCION")
                            .setMessage("QUE DESEAS HACER CON: ?")
                            .setPositiveButton("NADA") { d, i -> }
                            .setNegativeButton("ELIMINAR") { d, i ->

                            }
                            .setNeutralButton("ACTUALIZAR") { d, i ->
                                migracion=false
                            }
                            .show()

                    }
                }

            }
        }).start()


        insertar.setOnClickListener{
            var alumnos=bd.writableDatabase
            var datos=ContentValues()
            datos.put("NOMBRE",nombre.text.toString())
            datos.put("APELLIDOP",apellidoP.text.toString())
            datos.put("APELLIDOM",apellidoM.text.toString())
            datos.put("ESCUELA",escuela.text.toString())
            datos.put("TELEFONO",telefono.text.toString().toInt())
            datos.put("CARRERA1",carrera1.text.toString())
            datos.put("CARRERA2",carrera2.text.toString())
            datos.put("CORREO",correo.text.toString())

            var resultado=alumnos.insert("ESTUDIANTES","ID",datos)

            if(resultado == -1L) {
                AlertDialog.Builder(this).setTitle("ERROR").setMessage("NO SE PUDIERON INSERTAR")
                    .show()
            }else{
                Toast.makeText(this,"se insertaron",Toast.LENGTH_LONG).show()
                limpiarCampos()
                mostrarTodos()
            }




        }
    }
    fun mostrarTodos(){
        var alumnos=bd.readableDatabase
        val list=ArrayList<String>()
        IDs.clear()

        var resultado=alumnos.query("ESTUDIANTES", arrayOf("*"),
        null,null,null,null,null)
        if (resultado.moveToFirst()) {
            do {
                val data =
                    resultado.getString(1) + "\n" + resultado.getString(2) + "\n" + resultado.getString(
                        3
                    ) + "\n" + resultado.getString(4) + "\n" + resultado.getString(5) + "\n" + resultado.getString(
                        6
                    ) + "\n" + resultado.getString(7)

                list.add(data)
                IDs.add(resultado.getInt(0))

            } while (resultado.moveToNext())

        }else{
            list.add("la tabla esta vacia")
        }
        listaAlumnos.adapter=ArrayAdapter<String>(
            this,android.R.layout.simple_list_item_1,list
        )
    }
    fun limpiarCampos(){
        nombre.setText("")
        apellidoP.setText("")
        apellidoM.setText("")
        escuela.setText("")
        telefono.setText("")
        carrera1.setText("")
        carrera2.setText("")
        correo.setText("")
    }
    fun eliminarDatosTabla(){
        val resultado= bd.writableDatabase.delete("PERSONA",null,null)
        if(resultado==0){
            AlertDialog.Builder(this).setMessage("ERROR NO SE BORRO").show()
        }else{
            Toast.makeText(this,"SE BORRO CON EXITO!",Toast.LENGTH_LONG).show()
            mostrarTodos()
        }
    }
}