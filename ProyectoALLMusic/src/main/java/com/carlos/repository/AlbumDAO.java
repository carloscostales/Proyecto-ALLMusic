package com.carlos.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
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
    
    // Saca los álbumes de un artista y tipo en concreto
    @Query(value="SELECT * FROM album WHERE artista_id=:id AND tipo_album=:tipo", nativeQuery=true)
    List<Album> findAllByTipo_album(@Param("id") Integer id, @Param("tipo") String tipo);
    
    // Saca todos los álbumes de un artista excepto uno, el correspondiente al id
    @Query(value="SELECT* FROM album WHERE artista_id=:artista_id AND id!=:id", nativeQuery=true)
    List<Album> findTodosMenosUnoArtista(@Param("artista_id") Integer artista_id, @Param("id") Integer id);

    // Borra todos los álbumes de un artista
    @Query(value="DELETE alb FROM album alb INNER JOIN artista art ON alb.artista_id = art.id WHERE alb.artista_id = :id", nativeQuery=true)
    @Transactional
    @Modifying
    void borrarAlbumesDeArtista(@Param("id") Integer id);
    
    // Borra un álbum
    @Query(value="DELETE FROM album  WHERE id = :id", nativeQuery=true)
    @Transactional
    @Modifying
    void borrarAlbum(@Param("id") Integer id);
}
