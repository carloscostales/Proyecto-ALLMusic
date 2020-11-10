package com.carlos.datos.albumes;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.carlos.datos.artistas.Artista;

@Repository
public interface AlbumDAO extends CrudRepository<Album, Integer> {

	// Saca los álbumes de un artista
    List<Album> findByArtista(Artista artista);
    
    // Saca los últimos 6 álbumes agregados a la base de datos
    @Query(value="SELECT* FROM album ORDER BY id DESC LIMIT 6", nativeQuery = true)
    List<Album> findLast6();
}
