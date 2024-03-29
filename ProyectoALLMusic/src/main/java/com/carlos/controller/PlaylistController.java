package com.carlos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.carlos.model.Playlist;
import com.carlos.model.Usuario;
import com.carlos.service.CancionService;
import com.carlos.service.PlaylistService;

@Controller
public class PlaylistController {
	
	@Autowired
	private PlaylistService playlistService;
	
	@Autowired
	private CancionService cancionService;
	
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
		
		playlistService.add(playlist);
		
		return mav;
	}
	
	@GetMapping("/playlists/borrar/{playlist}")
	private String borrarPlaylist(@PathVariable Playlist playlist, Authentication auth) {
		
		cancionService.borrarCancionesDePlaylist(playlist.getId());
		playlistService.borrarPlaylist(playlist.getId());
		return "redirect:/usuarios/" + playlist.getUsuario().getNombreUsuario();
		
	}
}
