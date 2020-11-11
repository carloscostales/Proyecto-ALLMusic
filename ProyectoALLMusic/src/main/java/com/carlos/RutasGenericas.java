package com.carlos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.carlos.datos.albumes.Album;
import com.carlos.datos.albumes.AlbumDAO;
import com.carlos.datos.artistas.Artista;
import com.carlos.datos.artistas.ArtistaDAO;
import com.carlos.datos.playlists.Playlist;
import com.carlos.datos.playlists.PlaylistDAO;
import com.carlos.datos.usuarios.Usuario;
import com.carlos.servicios.ComparatorFecha;

@Controller
public class RutasGenericas {
	
	@Autowired
	private ArtistaDAO artistaDAO;
	
	@Autowired
	private AlbumDAO albumDAO;
	
	@Autowired
	private PlaylistDAO playlistDAO;
	
	
	@GetMapping("/")
	public ModelAndView index(Authentication auth) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		
		List<Artista> ultimosArtistas = artistaDAO.findLast6();
		List<Album> albumes = (List<Album>) albumDAO.findAll();
		
		// Si no hay 6 albumes, se rellena con los que haya.
		int numero;
		if (albumes.size() < 6) {
			numero = albumes.size();
		}else {
			numero = 6;
		}
		
		albumes.sort(new ComparatorFecha());
		List<Album> ultimosAlbumes = albumes.subList(0, numero);
		
		mav.addObject("ultimosAlbumes", ultimosAlbumes);
		mav.addObject("ultimosArtistas", ultimosArtistas);
		
		if(auth != null) {
			System.out.println("nombre: " + auth.getName());
			Usuario usuario = (Usuario) auth.getPrincipal();
			mav.addObject("usuario", usuario);

			List<Playlist> ultimasPlaylists = playlistDAO.findLast6(auth.getName());
			mav.addObject("ultimasPlaylists", ultimasPlaylists);
		}
		return mav;
	}
	
	@GetMapping("/login")
	public ModelAndView login() {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
		return mav;
	}

}
