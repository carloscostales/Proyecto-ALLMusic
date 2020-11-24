package com.carlos.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.carlos.model.Artista;
import com.carlos.model.Genero;

@Repository
public interface ArtistaDAO extends JpaRepository<Artista, Integer> {

	// Busca los últimos 6 artistas agregados a la base de datos
	@Query(value="SELECT * FROM artista ORDER BY id desc LIMIT 6", nativeQuery = true)
	List<Artista> findLast6();
	
	// Busca todos los artistas de un mismo género
	List<Artista> findByGenero(Genero genero);
	
	// Selecciona artistas con el mismo género que el :id. Limitado a 12.
	@Query(value="SELECT * FROM artista WHERE id!=:id AND genero_nombre=:genero LIMIT 12", nativeQuery=true)
	List<Artista> findArtistasGenero(@Param("id") Integer id, @Param("genero") String genero);
	
	// Busca artistas que empiezen por el string dado.
	List<Artista> findByNombreStartsWith(String nombre);
	
	// Busca artistas que empiezen por el string dado. Hecho para paginar la búsqueda
	Page<Artista> findByNombreStartsWith(Pageable pageable, String nombre);
	
}
