package com.carlos.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.carlos.model.Playlist;
import com.carlos.model.PlaylistCancion;

public interface PlaylistCancionDAO extends CrudRepository<PlaylistCancion, Integer>	{

	 List<PlaylistCancion> findByPlaylist(Playlist playlist);
	
}
