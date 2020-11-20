package com.carlos.service;

import java.util.List;

import com.carlos.model.Artista;
import com.carlos.model.Genero;

public interface ArtistaService {
	
	public void add(Artista artista);
	
	public void update(Artista artista);
	
	public void delete(Artista artista);
	
	public List<Artista> listaArtistasCompleta();
	
	public List<Artista> buscarUltimosSeis();
	
	public List<Artista> buscarPorGenero(Genero genero);
	
	public List<Artista> buscarArtistasDelMismoGenero(Integer id, String genero);
	
	public List<Artista> buscarPorNombreEmpiezaCon(String nombre);
	
}
