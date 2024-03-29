package com.carlos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public Page<Artista> listaArtistasCompleta(Pageable pageable) {
		
		return artistaDAO.findAll(pageable);
	}
	
	@Override
	public Optional<Artista> buscarPorId(Integer id) {
		
		return artistaDAO.findById(id);
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

	@Override
	public Page<Artista> buscarPorNombreEmpiezaCon(Pageable pageable, String nombre) {
		
		return artistaDAO.findByNombreStartsWith(pageable, nombre);
	}

}
