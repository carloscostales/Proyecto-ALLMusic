package com.carlos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.carlos.model.Artista;
import com.carlos.repository.ArtistaDAO;

@Service
public class PaginacionArtistaImpl implements PaginacionArtistaAPI {
	
	@Autowired
	private ArtistaDAO artistaDAO;

	@Override
	public Page<Artista> getAll(Pageable pageable) {

		return artistaDAO.findAll(pageable);
	}

}
