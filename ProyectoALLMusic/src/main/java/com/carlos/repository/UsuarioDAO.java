package com.carlos.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.carlos.model.Usuario;

@Repository
public interface UsuarioDAO extends CrudRepository<Usuario, String> {
	
	// Género más añadido a las playlists de un usuario
	@Query(value="SELECT g.nombre FROM playlist_cancion pc INNER JOIN playlist p ON pc.playlist_id = p.id INNER JOIN cancion ca ON pc.cancion_id = ca.id  INNER JOIN album al ON ca.album_id = al.id INNER JOIN artista ar ON al.artista_id = ar.id INNER JOIN genero g ON ar.genero_nombre = g.nombre where p.usuario_nombre_usuario = :usuario GROUP BY g.nombre ORDER BY COUNT(g.nombre) DESC LIMIT 1", nativeQuery=true)
	String mostAddedGenreUser(@Param("usuario") String usuario);
	
}
