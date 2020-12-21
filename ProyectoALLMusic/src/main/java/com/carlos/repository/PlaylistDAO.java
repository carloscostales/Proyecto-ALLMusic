package com.carlos.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.carlos.model.Playlist;
import com.carlos.model.Usuario;

@Repository
public interface PlaylistDAO extends CrudRepository<Playlist, Integer> {

	// Saca las playlists de un usuario
    List<Playlist> findByUsuario(Usuario usuario);
    
    @Query(value="SELECT * FROM playlist WHERE usuario_nombre_usuario=:usuario ORDER BY id DESC LIMIT 6", nativeQuery=true)
    List<Playlist> findLast6(@Param("usuario") String usuario);
    
    @Query(value="DELETE FROM playlist WHERE id=:id", nativeQuery=true)
	@Transactional
	@Modifying
    void borrarPlaylist(@Param("id") Integer id);
}
