package com.carlos.datos.playlist_cancion;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.carlos.datos.playlists.Playlist;

public interface PlaylistCancionDAO extends CrudRepository<PlaylistCancion, Integer>	{

	 List<PlaylistCancion> findByPlaylist(Playlist playlist);
	
}
