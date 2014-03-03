package com.afelipe.android.datastorageexample.adapter;

import java.util.List;

import com.afelipe.android.datastorageexample.R;
import com.afelipe.android.datastorageexample.model.Departamento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adaptador para cada elemento Departamento en el ListView del MainActivity En
 * este adaptador se controlan los eventos que contiene el cada elemento:
 * Editar, Eliminar, Ver detalles
 * 
 * @author afelipe
 * 
 */
public class ListViewAdapter extends ArrayAdapter<Departamento> {
	Context context;
	List<Departamento> departamentos;

	public ListViewAdapter(Context context, List<Departamento> deptos) {
		super(context, R.layout.elemento_lista, deptos);
		this.context = context;
		this.departamentos = deptos;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View item = inflater.inflate(R.layout.elemento_lista, null);

		TextView nombreDeptoText = (TextView) item
				.findViewById(R.id.nombreDeptoText);
		TextView responsableText = (TextView) item
				.findViewById(R.id.responsableText);
		TextView cargoResponsableText = (TextView) item
				.findViewById(R.id.cargoResponsableText);
		TextView telefonoText = (TextView) item.findViewById(R.id.telefonoText);
		TextView emailText = (TextView) item.findViewById(R.id.emailText);		
		
		ImageView imagenDeptoImg = (ImageView) item
				.findViewById(R.id.imagenDeptoImg);
		if (departamentos.get(position) != null) {
			nombreDeptoText.setText(departamentos.get(position).getNombre());
			responsableText.setText(departamentos.get(position)
					.getResponsable());
			cargoResponsableText.setText(departamentos.get(position)
					.getCargoResponsable());
			telefonoText.setText(departamentos.get(position).getTelefono());
			emailText.setText(departamentos.get(position).getEmail());
		}

		return item;
	}
}
