package com.carlos.datos.playlist_cancion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.carlos.datos.playlists.Playlist;
import com.carlos.datos.usuarios.Usuario;

@Controller
public class PlaylistCancionRutas {
	
	@Autowired
	private PlaylistCancionDAO pcDAO;


	@GetMapping("/playlists/{playlist}")
	public ModelAndView playlistCancion(@PathVariable Playlist playlist, Authentication auth) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("playlists/mostrarPlaylist");
		
		mav.addObject("playlist", playlist);
		
		List<PlaylistCancion> lista = (List<PlaylistCancion>) pcDAO.findByPlaylist(playlist);
		mav.addObject("lista", lista);
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" + lista);
		
		if(auth != null) {
			System.out.println("nombre: " + auth.getName());
			Usuario usuario = (Usuario) auth.getPrincipal();
			mav.addObject("usuario", usuario);
		}
		
		return mav;
	}
	
}
