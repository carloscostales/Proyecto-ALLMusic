package com.carlos.datos.artistas;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.carlos.datos.albumes.Album;
import com.carlos.datos.generos.Genero;

@Entity
public class Artista {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	@Size(min=2, message="El nombre debe tener como mínimo 2 caracteres.")
	@Size(max=40, message="El nombre no puede tener mas de 40 caracteres.")
	private String nombre;
	
	@Column
	@Size(min=2, message="El origen no puede estar vacio. Mínimo 2 caracteres.")
	private String origen;
	
	@Column
	@Size(min=2, message="La foto no puede estar vacía. Coloca una URL.")
	private String foto;
	
	@Column
	@Size(min=20, message="La descripcion debe tener como mínimo 20 caracteres.")
	@Size(max=4000, message="La descripcion no puede pasar de 4000 caracteres.")
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

	@Override
	public String toString() {
		return "Artista [id=" + id + ", nombre=" + nombre + ", origen=" + origen + ", genero=" + genero + "]";
	}
	
}
