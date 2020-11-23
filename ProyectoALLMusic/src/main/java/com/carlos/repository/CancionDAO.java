package com.carlos.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.carlos.model.Album;
import com.carlos.model.Cancion;

@Repository
public interface CancionDAO extends CrudRepository<Cancion, Integer> {

	// Saca las canciones de un album
    List<Cancion> findByAlbum(Album album);

    // Borra todas las canciones de los álbumes de un artista
	@Query(value="DELETE c FROM cancion c INNER JOIN album alb ON c.album_id = alb.id WHERE alb.artista_id = :id", nativeQuery=true)
	@Transactional
	@Modifying
    void borrarCancionesDeArtista(@Param("id") Integer id);
	
	// Borra todas las canciones de un álbum
	@Query(value="DELETE FROM cancion WHERE album_id = :id", nativeQuery=true)
	@Transactional
	@Modifying
    void borrarCancionesDeAlbum(@Param("id") Integer id);
	
	// Borra una canción
	@Query(value="DELETE FROM cancion WHERE id = :id", nativeQuery=true)
	@Transactional
	@Modifying
    void borrarCancion(@Param("id") Integer id);
    
}
