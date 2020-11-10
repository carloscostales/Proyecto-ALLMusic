package com.carlos.paginacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.carlos.datos.artistas.Artista;
import com.carlos.datos.artistas.ArtistaDAO;

@Service
public class ArtistaServiceImpl implements ArtistaServiceAPI {
	
	@Autowired
	private ArtistaDAO artistaDAO;

	@Override
	public Page<Artista> getAll(Pageable pageable) {

		return artistaDAO.findAll(pageable);
	}

}
