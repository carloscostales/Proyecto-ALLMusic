package com.carlos.datos.artistas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistaDAO extends JpaRepository<Artista, Integer> {

	// Saca los últimos 6 artistas agregados a la base de datos
	@Query(value="SELECT * FROM artista ORDER BY id desc LIMIT 6", nativeQuery = true)
	List<Artista> findLast6();
}
