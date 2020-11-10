package com.carlos.datos.playlists;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.carlos.datos.usuarios.Usuario;

@Repository
public interface PlaylistDAO extends CrudRepository<Playlist, Integer> {

	// Saca las playlists de un usuario
    List<Playlist> findByUsuario(Usuario usuario);
    
    @Query(value="SELECT * FROM playlist WHERE usuario_nombre_usuario=:usuario ORDER BY id DESC LIMIT 6", nativeQuery=true)
    List<Playlist> findLast6(@Param("usuario") String usuario);
}
