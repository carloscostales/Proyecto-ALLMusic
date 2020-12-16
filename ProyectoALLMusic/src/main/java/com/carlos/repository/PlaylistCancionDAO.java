package com.carlos.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.carlos.model.Playlist;
import com.carlos.model.PlaylistCancion;

public interface PlaylistCancionDAO extends CrudRepository<PlaylistCancion, Integer>	{

	 List<PlaylistCancion> findByPlaylist(Playlist playlist);
	
	// Borra una canción en una playlist
	@Query(value="DELETE FROM playlist_cancion WHERE id = :id", nativeQuery=true)
	@Transactional
	@Modifying
	void borrarCancionPlaylist(@Param("id") Integer id);
}
