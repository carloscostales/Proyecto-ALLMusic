package com.carlos.datos.artistas;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistaDAO extends CrudRepository<Artista, Integer> {

}
