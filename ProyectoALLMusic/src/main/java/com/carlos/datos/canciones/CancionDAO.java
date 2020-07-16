package com.carlos.datos.canciones;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.carlos.datos.albumes.Album;

@Repository
public interface CancionDAO extends CrudRepository<Cancion, Integer> {

	// Saca las canciones de un album
    List<Cancion> findByAlbum(Album album);
}
