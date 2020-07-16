package com.carlos.datos.playlists;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.carlos.datos.usuarios.Usuario;

@Repository
public interface PlaylistDAO extends CrudRepository<Playlist, Integer> {

	// Saca las playlists de un usuario
    List<Playlist> findByUsuario(Usuario usuario);
}
