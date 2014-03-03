package com.afelipe.android.datastorageexample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	ListView listaListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//INICIA EJEMPLOS de ALMACENAMIENTO
		
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
		
		//listaListView = (ListView) findViewById(R.id.listadepartamentos);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
