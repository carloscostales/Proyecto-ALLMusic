package com.carlos.datos.artistas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistaDAO extends JpaRepository<Artista, Integer> {

}
