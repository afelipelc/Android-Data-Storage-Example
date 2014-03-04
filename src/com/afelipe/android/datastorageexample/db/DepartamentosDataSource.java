package com.afelipe.android.datastorageexample.db;

import com.afelipe.android.datastorageexample.model.Departamento;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DepartamentosDataSource {
	//necesitamos los objetos: Helper y SQLiteDB
	private MySQLiteHelper myHelper;
	private SQLiteDatabase database;
	
	//constructor
	public DepartamentosDataSource(Context context){
		//inicializamos el Helper
		myHelper = new MySQLiteHelper(context);
	}
	
	//métodos para abrir y cerrar la base de datos
	public void openDB(){
		database = myHelper.getWritableDatabase();
	}
	public void closeDB()
	{
		database.close();
	}
	
	//crear los metodos para manipular la base de datos
	public Departamento registrarDepartamento(Departamento depto){
		//necesitamos crear un objeto ContentValues
		// para proporcionar los valores de los campos
		ContentValues valores = new ContentValues();
		valores.put("id", depto.getIdDepartamento());
		valores.put("nombre", depto.getNombre());
		//completar el resto de los campos
		
		//insertar el departamento en la BD
		database.insert("departamentosTbl", null, valores);
		return depto;
	}
	
	//actualizar departamento
	public boolean actualizarDepartamento(Departamento depto){
		ContentValues valores = new ContentValues();
		valores.put("id", depto.getIdDepartamento());
		valores.put("nombre", depto.getNombre());
		//completar el resto de los campos
		
		String[] argsV = {depto.getIdDepartamento()+""};
		int res = database.update("departamentosTbl", 
				valores, "id=?", argsV);
		return res == 1 ? true : false;
	}
}
