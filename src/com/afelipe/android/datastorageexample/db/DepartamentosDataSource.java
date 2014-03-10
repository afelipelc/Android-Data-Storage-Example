package com.afelipe.android.datastorageexample.db;

import java.util.ArrayList;
import java.util.List;

import com.afelipe.android.datastorageexample.model.Departamento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DepartamentosDataSource {
	// necesitamos los objetos: Helper y SQLiteDB
	private MySQLiteHelper myHelper;
	private SQLiteDatabase database;

	// constructor
	public DepartamentosDataSource(Context context) {
		// inicializamos el Helper
		myHelper = new MySQLiteHelper(context);
	}

	// métodos para abrir y cerrar la base de datos
	public void openDB() {
		database = myHelper.getWritableDatabase();
	}

	public void closeDB() {
		database.close();
	}

	// crear los metodos para manipular la base de datos
	public Departamento registrarDepartamento(Departamento depto) {
		// necesitamos crear un objeto ContentValues
		// para proporcionar los valores de los campos
		ContentValues valores = new ContentValues();
		valores.put("id", depto.getIdDepartamento());
		valores.put("nombre", depto.getNombre());
		valores.put("responsable", depto.getResponsable());
		valores.put("cargoResponsable", depto.getCargoResponsable());
		valores.put("telefono", depto.getTelefono());
		valores.put("email", depto.getEmail());
		valores.put("foto", depto.getFoto());
		valores.put("informacion", depto.getInformacion());

		// insertar el departamento en la BD
		database.insert("departamentosTbl", null, valores);
		return depto;
	}

	// actualizar departamento
	public boolean actualizarDepartamento(Departamento depto) {
		Log.d("Departamento recibido",depto.getIdDepartamento() + " - " + depto.getNombre());
		ContentValues valores = new ContentValues();
		valores.put("id", depto.getIdDepartamento());
		valores.put("nombre", depto.getNombre());
		valores.put("responsable", depto.getResponsable());
		valores.put("cargoResponsable", depto.getCargoResponsable());
		valores.put("telefono", depto.getTelefono());
		valores.put("email", depto.getEmail());
		valores.put("foto", depto.getFoto());
		valores.put("informacion", depto.getInformacion());

		// String[] argsV = {depto.getIdDepartamento()+""};
		int res = database.update("departamentosTbl", valores, "id=?",
				new String[] { depto.getIdDepartamento() + "" });
		return res == 1;
	}

	public boolean eliminarDepartamento(Departamento depto) {
		int res = database.delete("departamentosTbl", "id=?", new String[] { depto
				+ "" });
		return res==1;
	}

	/**
	 * Devuelve la lista de todos los departamentos que se encuentran en la BD
	 * 
	 * @return
	 */
	public List<Departamento> listaDepartamentos() {
		// TAREA para ahora: encontrar el método apropiado de SQLiteDatabase
		// COMO PROCESAR UN Cursor que es el resultado obtenido de la consulta

		Cursor result = database.rawQuery("SELECT * FROM departamentosTbl",
				null);
		List<Departamento> departamentos = new ArrayList<Departamento>();
		// recorrer el cursor
		while (result.moveToNext()) {
			// tomar los datos del registro actual y pasarlos al objeto
			// Departamento
			Departamento item = new Departamento();
			item.setIdDepartamento(result.getInt(result.getColumnIndex("id")));
			item.setNombre(result.getString(result.getColumnIndex("nombre")));
			item.setResponsable(result.getString(result
					.getColumnIndex("responsable")));
			item.setCargoResponsable(result.getString(result
					.getColumnIndex("cargoResponsable")));
			item.setTelefono(result.getString(result.getColumnIndex("telefono")));
			item.setEmail(result.getString(result.getColumnIndex("email")));
			item.setFoto(result.getString(result.getColumnIndex("foto")));
			item.setInformacion(result.getString(result
					.getColumnIndex("informacion")));
			
			// meter el objeto a la colección
			departamentos.add(item);
		}
		return departamentos;
	}

	public List<Departamento> Buscar(String q) {
		Cursor result = database.rawQuery("SELECT * FROM departamentosTbl "
				+ "WHERE nombre like '%?%' " + "or responsable like '%?%'",
				new String[] { q });
		// lo mismo que en el método listaDepartamentos
		List<Departamento> departamentos = new ArrayList<Departamento>();
		// recorrer el cursor
		while (result.moveToNext()) {
			// tomar los datos del registro actual y pasarlos al objeto
			// Departamento
			Departamento item = new Departamento();
			item.setIdDepartamento(result.getInt(result.getColumnIndex("id")));
			item.setNombre(result.getString(result.getColumnIndex("nombre")));
			item.setResponsable(result.getString(result
					.getColumnIndex("responsable")));
			item.setCargoResponsable(result.getString(result
					.getColumnIndex("cargoResponsable")));
			item.setTelefono(result.getString(result.getColumnIndex("telefono")));
			item.setEmail(result.getString(result.getColumnIndex("email")));
			item.setFoto(result.getString(result.getColumnIndex("foto")));
			item.setInformacion(result.getString(result
					.getColumnIndex("informacion")));
			
			// meter el objeto a la colección
			departamentos.add(item);
		}
		return departamentos;
	}

	/**
	 * Método que registra en la BD una lista de departamentos
	 * 
	 * @param departamentos
	 * @return
	 */
	public void registrarDepartamentos(List<Departamento> departamentos) {
		for (Departamento item : departamentos)
			this.registrarDepartamento(item);
	}

	public void actualizarDepartamentos(List<Departamento> departamentos) {

		for (Departamento item : departamentos)
		{
			Log.d("Actualizar departamento", item.getNombre());
			if(!this.actualizarDepartamento(item))
				this.registrarDepartamento(item);
		}
	}
	
	/**
	 * Método que devuelve los datos de UN SOLO Departamento
	 * @param id
	 * @return Departamento
	 */
	public Departamento datosDepartamento(int id){
		Departamento item = new Departamento();
		//realizar la consulta   ?
		Cursor result = database.rawQuery(
				"SELECT * from departamentosTbl where id=?",
				new String[]{id+""});
		//Procesar el resultado  ?
		if(result.moveToFirst())
		{
			item.setIdDepartamento(result.getInt(result.getColumnIndex("id")));
			item.setNombre(result.getString(result.getColumnIndex("nombre")));
			item.setResponsable(result.getString(result
					.getColumnIndex("responsable")));
			item.setCargoResponsable(result.getString(result
					.getColumnIndex("cargoResponsable")));
			item.setTelefono(result.getString(result.getColumnIndex("telefono")));
			item.setEmail(result.getString(result.getColumnIndex("email")));
			item.setFoto(result.getString(result.getColumnIndex("foto")));
			item.setInformacion(result.getString(result
					.getColumnIndex("informacion")));
		}
		
		return item;
		
	}

}