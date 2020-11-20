package com.carlos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.model.Genero;
import com.carlos.repository.GeneroDAO;

@Service
public class GeneroServiceImpl implements GeneroService {
	
	@Autowired
	private GeneroDAO generoDAO;

	@Override
	public void add(Genero genero) {
		
		generoDAO.save(genero);
	}

	@Override
	public void delete(Genero genero) {
		
		if(generoDAO.existsById(genero.getNombre()))
			generoDAO.delete(genero);
	}

	@Override
	public List<Genero> listaGeneros() {
		
		return (List<Genero>) generoDAO.findAll();
	}
	
	

}
