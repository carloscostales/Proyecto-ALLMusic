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

import com.carlos.datos.generos.Genero;
import com.carlos.datos.generos.GeneroDAO;
import com.carlos.datos.usuarios.Usuario;

@Controller
public class ArtistaRutas {

	@Autowired
	private ArtistaDAO artistaDAO;
	
	@Autowired
	private GeneroDAO generoDAO;
	
	
	
	@GetMapping("/artistas")
	private ModelAndView rutaUsuario() {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("artistas/artistas");
		mav.addObject("artista", new Artista());
		
		List<Artista> listaArtistas = (List<Artista>) artistaDAO.findAll();
		mav.addObject("artistas", listaArtistas);
		
		return mav;
		
	}
	
	@GetMapping("/artistas/{artista}")
	private ModelAndView rutaArtistas(@PathVariable Artista artista) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("artistas/mostrarArtista");
		mav.addObject("artista", artista);

		return mav;
		
	}
	
	@GetMapping("/nuevoArtista")
	public ModelAndView nuevoArtista() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/artistas/nuevoArtista");
		mav.addObject("artista", new Artista());
		
		List<Genero> listaGeneros = (List<Genero>)generoDAO.findAll();
		mav.addObject("generos",listaGeneros);
		
		return mav;
	}
	
	@PostMapping("/addArtista")
	private String rutaAnadir(@ModelAttribute Artista artista) {
		
		artistaDAO.save(artista);
		
		return "redirect:/artistas";
		
	}
	
}