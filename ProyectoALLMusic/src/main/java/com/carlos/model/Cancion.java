package com.carlos.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;

@Entity
public class Cancion {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	@Min(value=1, message="El numero debe ser mayor de 0.")  
	private Integer numero;
	
	@Column
	private String titulo;
	
	@Column
	private String duracion;
	
	@ManyToOne
	private Album album = new Album();
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy = "cancion", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<PlaylistCancion> playlist_cancion= new HashSet<>();


	public Set<PlaylistCancion> getPlaylist_cancion() {
		return playlist_cancion;
	}

	public void setPlaylist_cancion(Set<PlaylistCancion> playlist_cancion) {
		this.playlist_cancion = playlist_cancion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}
	
	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	@Override
	public String toString() {
		return "Cancion [id=" + id + ", numero=" + numero + ", titulo=" + titulo + ", duracion=" + duracion + ", playlist_cancion=" + playlist_cancion + "]";
	}

	
	
	
}
