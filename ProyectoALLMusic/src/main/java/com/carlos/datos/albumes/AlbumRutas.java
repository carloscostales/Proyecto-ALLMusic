package com.carlos.datos.albumes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.carlos.datos.artistas.Artista;
import com.carlos.datos.artistas.ArtistaDAO;
import com.carlos.datos.generos.Genero;

@Controller
public class AlbumRutas {
	
	@Autowired
	private AlbumDAO albumDAO;
	
	@Autowired
	private ArtistaDAO artistaDAO;

	@GetMapping("/albumes")
	private ModelAndView rutaAlbumes() {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("albumes/albumes");
		mav.addObject("album", new Album());
		
		List<Album> listaAlbumes = (List<Album>) albumDAO.findAll();
		mav.addObject("albumes", listaAlbumes);
		
		return mav;
		
	}
	
	@GetMapping("/nuevoAlbum")
	private ModelAndView nuevoAlbum() {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("albumes/nuevoAlbum");
		mav.addObject("album", new Album());
		
		List<Artista> listaArtistas = (List<Artista>)artistaDAO.findAll();
		mav.addObject("artistas",listaArtistas);
		
		return mav;
		
	}
	
	@PostMapping("/addAlbum")
	private String rutaAnadirAlbum(@ModelAttribute Album album) {
		
		albumDAO.save(album);
		
		return "redirect:/albumes";
		
	}
}
