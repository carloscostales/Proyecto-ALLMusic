package com.carlos.datos.albumes;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.carlos.datos.canciones.Cancion;
import com.carlos.datos.canciones.CancionDAO;
import com.carlos.datos.usuarios.Usuario;

@Controller
public class AlbumRutas {
	
	@Autowired
	private AlbumDAO albumDAO;
	
	@Autowired
	private CancionDAO cancionDAO;
	
	
	@GetMapping("/albumes")
	private ModelAndView rutaAlbumes(Authentication auth) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("albumes/albumes");
		mav.addObject("album", new Album());
		
		List<Album> listaAlbumes = (List<Album>) albumDAO.findAll();
		mav.addObject("albumes", listaAlbumes);
		
		if(auth != null) {
			System.out.println("nombre: " + auth.getName());
			Usuario usuario = (Usuario) auth.getPrincipal();
			mav.addObject("usuario", usuario);
		}
		
		return mav;
		
	}
	
	@GetMapping("/albumes/{album}")
	private ModelAndView rutaAlbumesCanciones(@PathVariable Album album, Authentication auth) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("albumes/mostrarAlbum");
		mav.addObject("album", album);
		
		List<Cancion> listaCanciones = (List<Cancion>) cancionDAO.findByAlbum(album);
		// Ordenar las canciones por su numero en el album
		Collections.sort(listaCanciones, (a, b) -> a.getNumero() < b.getNumero() ? -1 : a.getNumero() == b.getNumero() ? 0 : 1);
		mav.addObject("canciones", listaCanciones);
		int contador = listaCanciones.size();
		mav.addObject("numero_canciones", contador);
		
		List<Album> listaAlbumes = albumDAO.findByArtista(album.getArtista());
		List<Album> listaAlbumesCorta = null;
		Collections.shuffle(listaAlbumes);
		
		if(listaAlbumes.size() < 6 ) {
			listaAlbumesCorta = listaAlbumes.subList(0, listaAlbumes.size());
		} else {
			listaAlbumesCorta = listaAlbumes.subList(0, 6);
		}
		
		
		
		
		mav.addObject("listaAlbumes", listaAlbumesCorta);
		
		
		mav.addObject("cancion", new Cancion());
		
		if(auth != null) {
			System.out.println("nombre: " + auth.getName());
			Usuario usuario = (Usuario) auth.getPrincipal();
			mav.addObject("usuario", usuario);
		}
		
		return mav;
		
	}
	
	
	/* El formulario para un nuevo album se encuentra en la vista de mostrarArtista.html*/
	
	
	@PostMapping("/addAlbum")
	private String rutaAnadirAlbum(@ModelAttribute Album album, HttpServletRequest request) {
		
		// Utilizado para volver a la pagina en la que esta el modal
		String referer = request.getHeader("Referer");
		albumDAO.save(album);
		
		return "redirect:" + referer;
		
	}
}
