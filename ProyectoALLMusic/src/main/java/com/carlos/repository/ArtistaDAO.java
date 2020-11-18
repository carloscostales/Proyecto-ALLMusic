package com.carlos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.carlos.model.Artista;
import com.carlos.model.Genero;

@Repository
public interface ArtistaDAO extends JpaRepository<Artista, Integer> {

	// Saca los últimos 6 artistas agregados a la base de datos
	@Query(value="SELECT * FROM artista ORDER BY id desc LIMIT 6", nativeQuery = true)
	List<Artista> findLast6();
	
	List<Artista> findByGenero(Genero genero);
	
	@Query(value="SELECT * FROM artista WHERE id!=:id AND genero_nombre=:genero LIMIT 12", nativeQuery=true)
	List<Artista> findArtistasGenero(@Param("id") Integer id, @Param("genero") String genero);
}
