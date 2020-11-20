package com.carlos.service;

import java.util.List;

import com.carlos.model.Genero;

public interface GeneroService {

	public void add(Genero genero);
	
	public void delete(Genero genero);
	
	public List<Genero> listaGeneros();

}
