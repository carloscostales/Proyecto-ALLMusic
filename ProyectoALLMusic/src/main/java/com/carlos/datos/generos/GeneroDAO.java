package com.carlos.datos.generos;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface GeneroDAO extends CrudRepository<Genero, String> {

}
