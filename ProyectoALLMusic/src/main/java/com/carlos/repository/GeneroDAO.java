package com.carlos.repository;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import com.carlos.model.Genero;

@Repository
public interface GeneroDAO extends CrudRepository<Genero, String> {

}
