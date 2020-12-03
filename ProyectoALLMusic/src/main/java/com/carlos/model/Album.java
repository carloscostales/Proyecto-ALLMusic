package com.carlos.model;

import java.beans.Transient;
import java.text.DateFormatSymbols;
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
			
		return "/img/artista-fotos/" + artista.getId() + "/album-portadas/" + portada;
	}
	
	@Transient
	public String getFechaSalidaString() {
		DateFormatSymbols d = new DateFormatSymbols();
		
		String date = this.getFecha_salida();
		String[] parts = date.split("-");
		String year = parts[0];
		String month = parts[1];
		int month_number = Integer.parseInt(month);
		String month_string = d.getMonths()[month_number - 1];
		month_string = month_string.substring(0, 1).toUpperCase() + month_string.substring(1);
		String day = parts[2];
		
		return day + " de " + month_string + " de " + year;
	}

	
	@Override
	public String toString() {
		return "Album [id=" + id + ", titulo=" + titulo + ", fecha_salida=" + fecha_salida + ", portada=" + portada
				+ ", tipo_album=" + tipo_album + ", artista=" + artista + ", canciones=" + canciones + "]";
	}
	
}
