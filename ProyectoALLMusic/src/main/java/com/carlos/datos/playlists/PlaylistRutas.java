package com.carlos.datos.playlists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PlaylistRutas {
	
	@Autowired
	private PlaylistDAO playlistDAO;

	@GetMapping("/playlist/{playlist}")
	private ModelAndView rutaVerPlaylist(@PathVariable Playlist playlist) {
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("playlists/mostrarPlaylist");
		
		return mav;
	}
}
