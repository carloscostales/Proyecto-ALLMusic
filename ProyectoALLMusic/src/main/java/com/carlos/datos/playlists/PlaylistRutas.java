package com.carlos.datos.playlists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.carlos.datos.usuarios.Usuario;

@Controller
public class PlaylistRutas {
	
	@Autowired
	private PlaylistDAO playlistDAO;
	
	@GetMapping(value="/nuevaPlaylist")
	private ModelAndView nuevaPlaylist(Authentication auth) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("playlists/nuevaPlaylist");
		mav.addObject("playlist", new Playlist());
		
		if (auth != null) {
			Usuario usuario = (Usuario) auth.getPrincipal();
			mav.addObject("usuario", usuario);
		}
		
		return mav;
	}
	
	@PostMapping(value="/addPlaylist")
	private ModelAndView addPlaylist(@ModelAttribute Playlist playlist, Authentication auth) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/");
		
		playlistDAO.save(playlist);
		
		return mav;
	}
}
