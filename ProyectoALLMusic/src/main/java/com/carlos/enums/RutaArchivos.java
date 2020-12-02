package com.carlos.enums;

public enum RutaArchivos {
	
	LOCALHOST("/src/main/resources/static/img/artista-fotos/"),
	
	// ROOT es el nombre del archivo .war
	DESPLIEGUE("/webapps/ROOT/WEB-INF/classes/static/img/artista-fotos/");

	public final String ruta;
	
	private RutaArchivos(String ruta) {
		this.ruta = ruta;
	}
	
	@Override
	public String toString() { 
	    return this.ruta; 
	}
	
}
