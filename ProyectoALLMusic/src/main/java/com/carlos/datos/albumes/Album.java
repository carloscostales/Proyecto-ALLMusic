package com.carlos.datos.albumes;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.carlos.datos.artistas.Artista;
import com.carlos.datos.canciones.Cancion;
import com.carlos.enums.TipoAlbumModel;

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
	
	@Column
	@Enumerated(EnumType.STRING)
	private TipoAlbumModel tipo_album = TipoAlbumModel.ALBUM;

	@ManyToOne
	private Artista artista = new Artista();

	@OneToMany(fetch=FetchType.EAGER, mappedBy = "album", cascade=CascadeType.REMOVE)

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
	
	public TipoAlbumModel getTipo_album() {
		return tipo_album;
	}

	public void setTipo_album(TipoAlbumModel tipo_album) {
		this.tipo_album = tipo_album;
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
	
	@Transient
	public String getPortadaPath() {
		if (portada == null || this.id == null) return null;
			
		return "/artista-fotos/" + artista.getId() + "/album-portadas/" + portada;
	}

	
	@Override
	public String toString() {
		return "Album [id=" + id + ", titulo=" + titulo + ", fecha_salida=" + fecha_salida + ", portada=" + portada
				+ ", tipo_album=" + tipo_album + ", artista=" + artista + ", canciones=" + canciones + "]";
	}
	
}
