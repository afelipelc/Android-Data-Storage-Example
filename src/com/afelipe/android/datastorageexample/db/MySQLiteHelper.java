package com.afelipe.android.datastorageexample.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

	//constantes de información sobre la BD
	private final static String nombreDB = "departamentos.db";
	private final static int versionDB = 1;
	private final static String departamentosTbl = 
			"create table departamentosTbl(" +
			" id integer primary key, " +
			" nombre text not null," +
			" responsable text not null," +
			" cargoResponsable text not null," +
			" telefono text not null," +
			" email text not null," +
			" foto text not null," +
			" informacion text not null);";
	//constructor
	public MySQLiteHelper(Context context) {
		super(context, nombreDB, null, versionDB);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		//mandar a crear las tablas
		db.execSQL(departamentosTbl);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//se mandan a eliminar todas las tablas
		db.execSQL("DROP table departamentosTbl");
		//se vuelve a llamar al método onCreate(db);
		onCreate(db);
		//nosotros solo lo manejamos sin actualización
	}
}
