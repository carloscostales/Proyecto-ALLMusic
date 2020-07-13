package com.carlos.datos.albumes;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.carlos.datos.artistas.Artista;
import com.carlos.datos.canciones.Cancion;

@Entity
public class Album {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String titulo;

	@Column
	private String fecha_salida;
	
	@Column
	private String portada;

	@ManyToOne
	private Artista artista = new Artista();

	@OneToMany(fetch=FetchType.EAGER, mappedBy = "album", cascade=CascadeType.ALL)
	private List<Cancion> canciones = new ArrayList<Cancion>();
	
	public void addAlbum(Cancion cancion) {

		if(!canciones.contains(cancion)) {
			
			canciones.add(cancion);
		}
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getFecha_salida() {
		return fecha_salida;
	}

	public void setFecha_salida(String fecha_salida) {
		this.fecha_salida = fecha_salida;
	}
	
	public String getPortada() {
		return portada;
	}

	public void setPortada(String portada) {
		this.portada = portada;
	}

	public Artista getArtista() {
		return artista;
	}

	public void setArtista(Artista artista) {
		this.artista = artista;
	}

	public List<Cancion> getCanciones() {
		return canciones;
	}

	public void setCanciones(List<Cancion> canciones) {
		this.canciones = canciones;
	}
	
	@Override
	public String toString() {
		return "Album [id=" + id + ", titulo=" + titulo + ", fecha_salida=" + fecha_salida + ", artista=" + artista + "]";
	}
	
}
