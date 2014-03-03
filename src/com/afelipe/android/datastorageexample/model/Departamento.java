package com.afelipe.android.datastorageexample.model;

public class Departamento {
	int idDepartamento;
	private String nombre, responsable, cargoResponsable, telefono, email, foto, informacion;

	public int getIdDepartamento(){
		return idDepartamento;
	}
	
	public void setIdDepartamento(int idDepto){
		this.idDepartamento = idDepto;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getCargoResponsable() {
		return cargoResponsable;
	}

	public void setCargoResponsable(String cargoResponsable) {
		this.cargoResponsable = cargoResponsable;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getInformacion() {
		return informacion;
	}

	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}
	
	public void Guardar(){
		//Llamar a al final method que realiza el almacenamiento
	}
	
	public void Eliminar(){
		//Llamar a al final method que realiza el almacenamiento
	}
}