package com.carlos.datos.artistas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.carlos.datos.albumes.Album;
import com.carlos.datos.albumes.AlbumDAO;
import com.carlos.datos.generos.Genero;
import com.carlos.datos.generos.GeneroDAO;
import com.carlos.datos.usuarios.Usuario;
import com.carlos.servicios.ComparatorFecha;

@Controller
public class ArtistaRutas {

	@Autowired
	private ArtistaDAO artistaDAO;
	
	@Autowired
	private GeneroDAO generoDAO;
	
	@Autowired
	private AlbumDAO albumDAO;
	
	
	@GetMapping("/artistas")
	private ModelAndView rutaArtistas(Authentication auth) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("artistas/artistas");
		mav.addObject("artista", new Artista());
		
		List<Artista> listaArtistas = (List<Artista>) artistaDAO.findAll();
		mav.addObject("artistas", listaArtistas);
		
		if(auth != null) {
			System.out.println("nombre: " + auth.getName());
			Usuario usuario = (Usuario) auth.getPrincipal();
			mav.addObject("usuario", usuario);
		}
		
		return mav;
		
	}
	
	
	@GetMapping("/artistas/{artista}")
	private ModelAndView rutaArtista(@PathVariable Artista artista, Authentication auth) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("artistas/mostrarArtista");
		mav.addObject("artista", artista);
		
		mav.addObject("album", new Album());
		
		List<Album> listaAlbumes = (List<Album>)albumDAO.findByArtista(artista);

		mav.addObject("listaAlbumes", listaAlbumes);
        listaAlbumes.sort(new ComparatorFecha());
        
        if(auth != null) {
			System.out.println("nombre: " + auth.getName());
			Usuario usuario = (Usuario) auth.getPrincipal();
			mav.addObject("usuario", usuario);
		}

		return mav;
		
	}
	
	@GetMapping("/nuevoArtista")
	public ModelAndView nuevoArtista(Authentication auth) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/artistas/nuevoArtista");
		mav.addObject("artista", new Artista());
		
		List<Genero> listaGeneros = (List<Genero>)generoDAO.findAll();
		mav.addObject("generos",listaGeneros);
		
		if(auth != null) {
			System.out.println("nombre: " + auth.getName());
			Usuario usuario = (Usuario) auth.getPrincipal();
			mav.addObject("usuario", usuario);
		}
		
		return mav;
	}
	
	@PostMapping("/addArtista")
	private String rutaAnadir(@ModelAttribute Artista artista) {
		
		artistaDAO.save(artista);
		
		return "redirect:/";
		
	}
	
}
