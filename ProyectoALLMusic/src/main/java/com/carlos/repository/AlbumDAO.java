package com.carlos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.carlos.model.Album;
import com.carlos.model.Artista;

@Repository
public interface AlbumDAO extends CrudRepository<Album, Integer> {

	// Saca los álbumes de un artista
    List<Album> findByArtista(Artista artista);
    
    // Saca los últimos 6 álbumes agregados a la base de datos
    @Query(value="SELECT * FROM album ORDER BY id DESC LIMIT 6", nativeQuery = true)
    List<Album> findLast6();
    // Saca los albumes de un artista y tipo en concreto
    @Query(value="SELECT * FROM album WHERE artista_id=:id AND tipo_album=:tipo", nativeQuery=true)
    List<Album> findAllByTipo_album(@Param("id") Integer id, @Param("tipo") String tipo);
    
    // Saca todos los albumes de un artista excepto uno, el contenido por el id
    @Query(value="SELECT* FROM album WHERE artista_id=:artista_id AND id!=:id", nativeQuery=true)
    List<Album> findTodosMenosUnoArtista(@Param("artista_id") Integer artista_id, @Param("id") Integer id);

}
