package com.carlos.datos.albumes;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.carlos.datos.albumes.Album;
import com.carlos.datos.albumes.AlbumDAO;
import com.carlos.datos.canciones.Cancion;
import com.carlos.datos.canciones.CancionDAO;

@Controller
public class AlbumRutas {
	
	@Autowired
	private AlbumDAO albumDAO;
	
	@Autowired
	private CancionDAO cancionDAO;

	@GetMapping("/albumes")
	private ModelAndView rutaAlbumes() {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("albumes/albumes");
		mav.addObject("album", new Album());
		
		List<Album> listaAlbumes = (List<Album>) albumDAO.findAll();
		mav.addObject("albumes", listaAlbumes);
		
		
		return mav;
		
	}
	
	@GetMapping("/albumes/{album}")
	private ModelAndView rutaAlbumesCanciones(@PathVariable Album album) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("albumes/mostrarAlbum");
		mav.addObject("album", album);
		
		List<Cancion> listaCanciones = (List<Cancion>) cancionDAO.findByAlbum(album);
		// Ordenar las canciones por su numero en el album
		Collections.sort(listaCanciones, (a, b) -> a.getNumero() < b.getNumero() ? -1 : a.getNumero() == b.getNumero() ? 0 : 1);
		mav.addObject("canciones", listaCanciones);
		int contador = listaCanciones.size();
		mav.addObject("numero_canciones", contador);
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" + listaCanciones);
		
		return mav;
		
	}
	
	
	/* La ruta para un nuevo album se encuentra en la vista de mostrarArtista.html*/
	
	
	@PostMapping("/addAlbum")
	private String rutaAnadirAlbum(@ModelAttribute Album album) {
		
		albumDAO.save(album);
		
		return "redirect:/albumes";
		
	}
}
