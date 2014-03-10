package com.afelipe.android.datastorageexample.db;

import java.util.ArrayList;
import java.util.List;

import com.afelipe.android.datastorageexample.model.Departamento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

/**
 * Clase para administrar la BD Se ha creado como final static para no tener que
 * crear objetos al momento de realizar la manipulación. de este modo el
 * aplicación reduce el consumo de memoria
 * 
 * En cada método se requiere un objeto Context para poder realizar la apertura
 * y cierre de la BD.
 * 
 * Para más información sobre SQLite en Android, referirse a
 * https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html
 * 
 * @author afelipe
 * 
 */
public final class DepartamentosDBDataSource {

	/**
	 * Método que devuelve los datos de un departamento
	 * 
	 * @param context
	 * @param idDepto
	 *            a localizar
	 * @return Departamento
	 */
	public final static Departamento datosDepartamento(Context context,
			int idDepto) {
		SQLiteDatabase db = new MySQLiteHelper(context).getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"Select * from departamentosTbl where id = ?",
				new String[] { idDepto + "" });
		// movemos el cursor a la primera fila
		if (cursor != null && cursor.moveToFirst()) {
			Departamento item = CursorToDepartamento(cursor);
			db.close();
			return item;
		} else
			return null;
	}

	/**
	 * Método que devuelve la lista de todos los departamentos de la BD
	 * 
	 * @param context
	 * @return List<Departamento> encontrados
	 */
	public final static List<Departamento> listaDepartamentos(Context context) {
		return executeSQLtoList(context, "Select * from departamentosTbl",
				new String[] {});
	}

	/**
	 * Método que devuelve la lista de departamentos que conincidan con el
	 * criterio
	 * 
	 * @param context
	 * @param q
	 * @return List<Departamento>
	 */
	public final static List<Departamento> buscarDepartamentos(Context context,
			String q) {
		return executeSQLtoList(
				context,
				"Select * from departamentosTbl where nombre like '%?%' or responsable like '%?%'",
				new String[] { q, q });
	}

	/**
	 * Método privado para procesar un SQL que devuelve una lista de
	 * Departamentos
	 * 
	 * @param context
	 * @param sql
	 * @param selectArgs
	 * @return List<Departamento>
	 */
	private final static List<Departamento> executeSQLtoList(Context context,
			String sql, String[] selectArgs) {
		SQLiteDatabase db = new MySQLiteHelper(context).getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, selectArgs);

		if (cursor != null && cursor.getCount() > 0) {
			List<Departamento> listaDepartamentos = new ArrayList<Departamento>();
			while (cursor.moveToNext()) {
				listaDepartamentos.add(CursorToDepartamento(cursor));
			}
			db.close();
			return listaDepartamentos;
		} else {
			db.close();
			return null;
		}
	}

	/**
	 * Método que registra un nuevo departamento
	 * 
	 * @param context
	 * @param depto
	 *            Departamento a registrar
	 * @return Departamento
	 */
	public final static Departamento registrarDepartamento(Context context,
			Departamento depto) {
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

		SQLiteDatabase db = new MySQLiteHelper(context)
		.getWritableDatabase();
		// insertar el departamento en la BD
		try {
			
			db.insert("departamentosTbl", null, valores);
			db.close();
			return depto;
		} catch (SQLException e) {
			db.close();
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Método que se encarga de registrar una lista de departamentos
	 * 
	 * @param context
	 * @param deptos
	 * @return boolean
	 */
	public final static boolean registrarDepartamentos(Context context,
			List<Departamento> deptos) {
		SQLiteDatabase db = new MySQLiteHelper(context).getWritableDatabase();
		//Log.d("DB is Open",
		//		db.isOpen() + ", deptos a insertar: " + deptos.size());

		for (Departamento item : deptos) {
			ContentValues valores = new ContentValues();
			valores.put("id", item.getIdDepartamento());
			valores.put("nombre", item.getNombre());
			valores.put("responsable", item.getResponsable());
			valores.put("cargoResponsable", item.getCargoResponsable());
			valores.put("telefono", item.getTelefono());
			valores.put("email", item.getEmail());
			valores.put("foto", item.getFoto());
			valores.put("informacion", item.getInformacion());

			// insertar el departamento en la BD
			// controlar el error cuando se tenga una Clave primaria duplicada
			try {
				long res = db.insert("departamentosTbl", null, valores);
				//Log.d("DB inserted", db.isOpen() + " id: " + res);

				//si devuelve -1 puede deberse a que el Id ya existe
				//entonces podría pensarse en actualizar el registro
				/*
				 * if (res < 0) { Log.i("Insertar Departamento",
				 * "Ocurrio un error al insertar el departamento: " +
				 * item.getNombre()); db.close(); return false; }
				 */
				if(res == -1)
					actualizarDepartamento(context, item);
				
			} catch (SQLiteConstraintException e) {
				Log.i("Registrar departamento", "El departamento con el id "
						+ item.getIdDepartamento() + " : " + item.getNombre()
						+ ". Ya se encuentra registrado");
				Toast.makeText(
						context,
						"El departamento con el id " + item.getIdDepartamento()
								+ " : " + item.getNombre()
								+ ". Ya se encuentra registrado",
						Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				//e.printStackTrace();
				Log.i("Registrar departamento", "Error al intentar registrar:  "
						+ item.getIdDepartamento() + " : " + item.getNombre()
						+ ". " + e.getMessage());
			}
		}
		db.close();
		return true;
	}
	
	/**
	 * Método que actualiza una lista de departamentos en la BD
	 * si el departamento no se encuentra, entonces se registra
	 * @param context
	 * @param deptos
	 * @return boolean
	 */
	public final static boolean actualizarDepartamentos(Context context,
			List<Departamento> deptos) {
		SQLiteDatabase db = new MySQLiteHelper(context).getWritableDatabase();

		for (Departamento item : deptos) {
			ContentValues valores = new ContentValues();
			valores.put("id", item.getIdDepartamento());
			valores.put("nombre", item.getNombre());
			valores.put("responsable", item.getResponsable());
			valores.put("cargoResponsable", item.getCargoResponsable());
			valores.put("telefono", item.getTelefono());
			valores.put("email", item.getEmail());
			valores.put("foto", item.getFoto());
			valores.put("informacion", item.getInformacion());

			// actualizar el departamento en la BD
			try {
				long res = db.update("departamentosTbl", valores,"id=?",new String[]{item.getIdDepartamento()+""});
				if(res == 0)
					registrarDepartamento(context, item);
				
			} catch (Exception e) {
				//e.printStackTrace();
				Log.i("Actualizar departamento", "Error al intentar actualizar:  "
						+ item.getIdDepartamento() + " : " + item.getNombre()
						+ ". " + e.getMessage());
			}
		}
		db.close();
		return true;
	}

	/**
	 * Método que actualiza un departamento
	 * 
	 * @param context
	 * @param depto
	 *            a actualizar
	 * @return boolean
	 */
	public final static boolean actualizarDepartamento(Context context,
			Departamento depto) {
		ContentValues valores = new ContentValues();
		valores.put("id", depto.getIdDepartamento());
		valores.put("nombre", depto.getNombre());
		// completar el resto de los campos

		SQLiteDatabase db = new MySQLiteHelper(context).getWritableDatabase();
		int res = db.update("departamentosTbl", valores, "id=?",
				new String[] { depto.getIdDepartamento() + "" });
		db.close();
		// si el número de filas afectadas es 1, es que se actualizó
		return res == 1;
	}

	/**
	 * Método que elimina un departamento
	 * 
	 * @param context
	 * @param idDepto
	 *            a eliminar
	 * @return boolean
	 */
	public final static boolean eliminarDepartamento(Context context,
			int idDepto) {
		SQLiteDatabase db = new MySQLiteHelper(context).getWritableDatabase();
		int res = db.delete("departamentosTbl", "id=?", new String[] { idDepto
				+ "" });
		db.close();
		return res == 1;
	}

	/**
	 * Método que convierte una fila de Cursor a objeto Departamento
	 * 
	 * @param cursor
	 * @return Departamento
	 */
	private final static Departamento CursorToDepartamento(Cursor cursor) {
		Departamento item = new Departamento();
		item.setIdDepartamento(cursor.getInt(cursor.getColumnIndex("id")));
		item.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
		item.setResponsable(cursor.getString(cursor
				.getColumnIndex("responsable")));
		item.setCargoResponsable(cursor.getString(cursor
				.getColumnIndex("cargoResponsable")));
		item.setTelefono(cursor.getString(cursor.getColumnIndex("telefono")));
		item.setEmail(cursor.getString(cursor.getColumnIndex("email")));
		item.setFoto(cursor.getString(cursor.getColumnIndex("foto")));
		item.setInformacion(cursor.getString(cursor
				.getColumnIndex("informacion")));
		return item;
	}
}
