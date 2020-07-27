package com.carlos.datos.playlist_cancion;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.carlos.datos.canciones.Cancion;
import com.carlos.datos.playlists.Playlist;

@Entity
public class PlaylistCancion implements Serializable {

	@Id
	private int id;
	
    @ManyToOne
    @JoinColumn
    private Playlist playlist;
	
    @ManyToOne
    @JoinColumn
    private Cancion cancion;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Playlist getPlaylist() {
		return playlist;
	}

	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}

	public Cancion getCancion() {
		return cancion;
	}

	public void setCancion(Cancion cancion) {
		this.cancion = cancion;
	}

	@Override
	public String toString() {
		return "PlaylistCancion [id=" + id + ", playlist=" + playlist + ", cancion=" + cancion + "]";
	}
	
}
