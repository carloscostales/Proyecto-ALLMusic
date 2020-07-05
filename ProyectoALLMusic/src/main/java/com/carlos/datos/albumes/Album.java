package com.carlos.datos.albumes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.carlos.datos.artistas.Artista;

@Entity
public class Album {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String titulo;

	@Column
	private String fecha_salida;
	
	@ManyToOne
	private Artista artista = new Artista();
	
	
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

	public Artista getArtista() {
		return artista;
	}

	public void setArtista(Artista artista) {
		this.artista = artista;
	}

	
	@Override
	public String toString() {
		return "Album [id=" + id + ", titulo=" + titulo + ", fecha_salida=" + fecha_salida + ", artista=" + artista + "]";
	}
	
}
