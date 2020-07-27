package com.carlos.datos.playlist_cancion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.carlos.datos.canciones.Cancion;
import com.carlos.datos.playlists.Playlist;

@Controller
public class PlaylistCancionRutas {
	
	@Autowired
	private PlaylistCancionDAO pcDAO;

	@GetMapping("/playlist/{playlist}")
	public ModelAndView playlistCancion(@PathVariable Playlist playlist) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("prueba");
		
		mav.addObject("playlist", playlist);
		
		List<PlaylistCancion> lista = (List<PlaylistCancion>) pcDAO.findByPlaylist(playlist);
		mav.addObject("lista", lista);
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" + lista);

		
		return mav;
	}
	
}
