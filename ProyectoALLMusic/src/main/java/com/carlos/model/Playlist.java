package com.carlos.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Playlist {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String nombre;
	
	@ManyToOne
	private Usuario usuario = new Usuario();
	
	@OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL)
	private Set<PlaylistCancion> playlist_cancion = new HashSet<>();

	
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@Override
	public String toString() {
		return "Playlist [id=" + id + ", nombre=" + nombre + ", usuario=" + usuario + "]";
	}
	
}
