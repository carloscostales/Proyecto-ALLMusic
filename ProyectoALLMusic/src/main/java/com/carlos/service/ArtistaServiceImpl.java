package com.carlos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.model.Artista;
import com.carlos.model.Genero;
import com.carlos.repository.ArtistaDAO;

@Service
public class ArtistaServiceImpl implements ArtistaService {

	@Autowired
	private ArtistaDAO artistaDAO;
	
	
	@Override
	public void add(Artista artista) {
		
		artistaDAO.save(artista);
	}
	
	@Override
	public void update(Artista artista) {

		if(artistaDAO.existsById(artista.getId())) 
			artistaDAO.save(artista);
	}
	
	@Override
	public void delete(Artista artista) {
		
		if(artistaDAO.existsById(artista.getId()))
			artistaDAO.delete(artista);
	}
	
	@Override
	public List<Artista> listaArtistasCompleta() {
		
		return artistaDAO.findAll();
	}
	
	@Override
	public List<Artista> buscarUltimosSeis() {

		return artistaDAO.findLast6();
	}
	
	@Override
	public List<Artista> buscarPorGenero(Genero genero) {
		
		return artistaDAO.findByGenero(genero);
	}
	
	@Override
	public List<Artista> buscarArtistasDelMismoGenero(Integer id, String genero) {
		
		return artistaDAO.findArtistasGenero(id, genero);
	}
	
	@Override
	public List<Artista> buscarPorNombreEmpiezaCon(String nombre) {

		return artistaDAO.findByNombreStartsWith(nombre);
	}

}
