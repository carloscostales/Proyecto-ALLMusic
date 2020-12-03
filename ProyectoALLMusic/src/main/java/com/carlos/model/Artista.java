package com.carlos.model;

import java.beans.Transient;
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

import org.hibernate.validator.constraints.Length;

@Entity
public class Artista {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String nombre;
	
	@Column
	private String origen;
	
	@Column
	private String foto;
	
	@Column
	private String foto_fondo;

	@Column
	@Length(max=10000)
	private String bio;

	@ManyToOne
	private Genero genero = new Genero();
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy = "artista", cascade=CascadeType.ALL)
	private List<Album> albumes = new ArrayList<Album>();
	
	public void addAlbum(Album album) {

		if(!albumes.contains(album)) {
			
			albumes.add(album);
		}
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}
	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	public String getFoto_fondo() {
		return foto_fondo;
	}

	public void setFoto_fondo(String foto_fondo) {
		this.foto_fondo = foto_fondo;
	}
	
	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}
	
	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}
	
	public List<Album> getAlbumes() {
		return albumes;
	}

	public void setAlbumes(List<Album> albumes) {
		this.albumes = albumes;
	}
	
	@Transient
	public String getFotoPath() {
		if (foto == null || id == null) return null;
			
		return "/img/artista-fotos/" + id + "/" + foto;
	}
	
	@Transient
	public String getFotoFondoPath() {
		if (foto_fondo == null || id == null) return null;
			
		return "/img/artista-fotos/" + id + "/" + foto_fondo;
	}
	

	@Override
	public String toString() {
		return "Artista [id=" + id + ", nombre=" + nombre + ", origen=" + origen + ", genero=" + genero + ", foto=" + getFotoPath() + ", foto_fondo=" + getFotoFondoPath() + "]";
	}
	
}
