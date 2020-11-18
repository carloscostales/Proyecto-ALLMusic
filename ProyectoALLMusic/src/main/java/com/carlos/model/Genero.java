package com.carlos.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Genero {

	@Id
	private String nombre;

	@OneToMany(fetch=FetchType.EAGER, mappedBy = "genero")
	private List<Artista> artistas = new ArrayList<Artista>();	
	
	public void addArtista(Artista artista) {

		if(!artistas.contains(artista)) {
			
			artistas.add(artista);
		}
	}	
	
	
	public List<Artista> getArtistas() {
		return artistas;
	}

	public void setArtistas(List<Artista> artistas) {
		this.artistas = artistas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Genero [nombre=" + nombre + "]";
	}
	
}
