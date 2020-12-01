package com.carlos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.carlos.model.Artista;
import com.carlos.model.Genero;

public interface ArtistaService {
	
	public void add(Artista artista);
	
	public void update(Artista artista);
	
	public void delete(Artista artista);
	
	public List<Artista> listaArtistasCompleta();
	
	public Page<Artista> listaArtistasCompleta(Pageable pageable);
	
	public Optional<Artista> buscarPorId(Integer id);
	
	public List<Artista> buscarUltimosSeis();
	
	public List<Artista> buscarPorGenero(Genero genero);
	
	public List<Artista> buscarArtistasDelMismoGenero(Integer id, String genero);
	
	public List<Artista> buscarPorNombreEmpiezaCon(String nombre);
	
	public Page<Artista> buscarPorNombreEmpiezaCon(Pageable pageable, String nombre);
	
}
