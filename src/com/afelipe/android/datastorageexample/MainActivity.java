package com.afelipe.android.datastorageexample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.afelipe.android.datastorageexample.adapter.ListViewAdapter;
import com.afelipe.android.datastorageexample.db.DepartamentosDBDataSource;
import com.afelipe.android.datastorageexample.model.Departamento;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	ListView listaListView;
	List<Departamento> listaDepartamentos = new ArrayList<Departamento>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//INICIA EJEMPLOS de ALMACENAMIENTO
		/*
		//Shared preferences
		SharedPreferences preferencias 
		= getSharedPreferences("configuracion", 0);
		//recuperar una cadena 
		String cadena = preferencias.getString("nombre", "Anonimo");
		int valorX = preferencias.getInt("valorX", 0);

		Toast.makeText(getApplicationContext(), 
				"Los valores recuperados. \n\ncadena: " + cadena
				+"\n\nint: " + valorX, Toast.LENGTH_SHORT).show();
		
		//guardar preferences
		SharedPreferences.Editor editor = preferencias.edit();
		editor.putString("nombre", "Pablito");
		editor.putInt("valorX", 416);
		editor.commit();
		
		//Almacenamiento Interno
		String contenido = "Este es el contenido a guardar en el archivo";
		try {
			FileOutputStream fos 
			= openFileOutput("MiArchivo", Context.MODE_PRIVATE);
			fos.write(contenido.getBytes());
			fos.close();
			
			//lectura del contenido
			FileInputStream fin = openFileInput("MiArchivo");
			byte[] buffer = new byte[128];
			fin.read(buffer);
			
			Toast.makeText(getApplicationContext(), 
					buffer.toString(), 
					Toast.LENGTH_SHORT).show();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//carpeta privada
		Toast.makeText(getApplicationContext(), 
				"La carpeta privada se encuentra en: \n" 
		+ getFilesDir(), Toast.LENGTH_SHORT).show();
		
		//Almacenamiento Externo
		//crear carpeta
		File file = new File(Environment.getExternalStorageDirectory()+"/MiCarpeta");
		if(!file.exists()){
			if(file.mkdir())
			{
				Toast.makeText(getApplicationContext(), 
						"La carpeta se ha creado en: " 
				+ file.getAbsolutePath(), 
				Toast.LENGTH_SHORT).show();
			}
		}else{
			Toast.makeText(getApplicationContext(), 
					"La carpeta ya existe en: " 
			+ file.getAbsolutePath(), 
			Toast.LENGTH_SHORT).show();
		}
		
		//Ver ejemplo en https://github.com/afelipelc/Android-crear-carpeta 
		
		
		//FINALIZA EJEMPLOS de ALMACENAMIENTO
		//Disponible en https://github.com/afelipelc/Android-Data-Storage-Example
		*/
		
		
		listaListView = (ListView) findViewById(R.id.listadepartamentos);
		
		//Insertar objetos en la BD
		//solo ejecutar una vez
		for(int i=5; i<=8; i++)
		{
			Departamento item = new Departamento();
			item.setIdDepartamento(i);
			item.setNombre("Departamento U" + i);
			item.setResponsable("Resp " + i);
			item.setCargoResponsable("Cargo " + i);
			item.setTelefono("0 00 00");
			item.setEmail("item"+i+"@example.com");
			
			//DepartamentosDBDataSource.registrarDepartamento(this, 
			//		item);
			listaDepartamentos.add(item);
		}
//>>>		//registrar todos los departamentos
		//esto solo una vez, de lo contrario cada vez que inicie la 
		//app se insertaran nuevos departamentos
		//>>>DepartamentosDBDataSource.registrarDepartamentos(this, listaDepartamentos);

		//>> Ya funcionando la aplicación no sería conveniente llamar al metodo
		//registrar cada vez que se obtengan datos del WS, ya que la probabilidad 
		//de tener muchos departamentos nuevos es menor después de la primera ejecución
		//por lo que lo más viable sería obtener la lista del WS
		//e intentar actualizarlos en nuestr DB local, aquellos deptos que no
		//estén registrados se registrarán automáticamente.
		
		//y lo más conveniente sería utilizar un AsyncTask para evitar carga al proceso principal
		//cuando se reciban desde el WS
		DepartamentosDBDataSource.actualizarDepartamentos(this, listaDepartamentos);
		
		//recuperar la lista de departamentos desde la BD
		this.listaDepartamentos = DepartamentosDBDataSource.listaDepartamentos(this);
		//si listaDepartamentos es null, inicializarlo en vacio para evitar un error
		if(listaDepartamentos == null)
		{
			listaDepartamentos = new ArrayList<Departamento>();
			Toast.makeText(this, "No se obtuvo ningún registro de la BD", Toast.LENGTH_SHORT).show();
		}
		Log.d("Departamentos encontrados", listaDepartamentos.size()+"");
		//pasar la lista de departamentos al Adapter y asignarlo al ListView
		this.listaListView.setAdapter(new ListViewAdapter(this,listaDepartamentos));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
