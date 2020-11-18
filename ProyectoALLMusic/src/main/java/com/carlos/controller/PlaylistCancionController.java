package com.carlos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.carlos.model.Cancion;
import com.carlos.model.Playlist;
import com.carlos.model.PlaylistCancion;
import com.carlos.model.Usuario;
import com.carlos.repository.PlaylistCancionDAO;
import com.carlos.repository.PlaylistDAO;

@Controller
public class PlaylistCancionController {
	
	@Autowired
	private PlaylistCancionDAO pcDAO;
	
	@Autowired
	private PlaylistDAO playlistDAO;


	@GetMapping("/playlists/{playlist}")
	public ModelAndView playlistCancion(@PathVariable Playlist playlist, Authentication auth) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("playlists/mostrarPlaylist");
		
		mav.addObject("playlist", playlist);
		
		List<PlaylistCancion> canciones_playlist = pcDAO.findByPlaylist(playlist);
		mav.addObject("lista", canciones_playlist);
		Integer n_canciones = canciones_playlist.size();
		mav.addObject("numero_canciones", n_canciones);
		
		if (!canciones_playlist.isEmpty()) {
			Usuario owner = playlist.getUsuario();
			mav.addObject("owner", owner.getNombreUsuario());
		}
		
		if(auth != null) {
			Usuario usuario = (Usuario) auth.getPrincipal();
			mav.addObject("usuario", usuario);
		}
		
		return mav;
	}
	
	@GetMapping("/playlists/nuevaCancionPlaylist/{cancion}")
	public ModelAndView nuevaCancionPlaylist(@PathVariable Cancion cancion, Authentication auth) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("cancion-playlist/nuevaCancionPlaylist");
		
		mav.addObject("pc", new PlaylistCancion());
		mav.addObject("cancion", cancion);
		
		if(auth != null) {
			Usuario usuario = (Usuario) auth.getPrincipal();
			mav.addObject("usuario", usuario);
			
			List<Playlist> listaPlaylists = (List<Playlist>) playlistDAO.findByUsuario(usuario);
			mav.addObject("listaPlaylists", listaPlaylists);
			
		}
		
		return mav;
	}
	
	@PostMapping("/addCancionPlaylist")
	private ModelAndView addCancionPlaylist(@ModelAttribute PlaylistCancion pc) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/albumes/" + pc.getCancion().getAlbum().getId());
		
		pcDAO.save(pc);
		
		return mav;
	}
	
	@GetMapping("/quitarCancionPlaylist/{pc}")
	private ModelAndView quitarCancionPlaylist(@PathVariable PlaylistCancion pc, Authentication auth) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/playlists/" + pc.getPlaylist().getId());
		
		if(auth != null) {
			Usuario usuario = (Usuario) auth.getPrincipal();
			mav.addObject("usuario", usuario);
			
			if(pc.getPlaylist().getUsuario().getNombreUsuario().equals(usuario.getNombreUsuario())) {
				pcDAO.delete(pc);
			}
		}

		return mav;
	}
	
}
