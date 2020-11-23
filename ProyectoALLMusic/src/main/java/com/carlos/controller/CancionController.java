package com.carlos.controller;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.carlos.model.Cancion;
import com.carlos.model.Usuario;
import com.carlos.service.CancionService;

@Controller
public class CancionController {
	
	@Autowired
	private CancionService cancionService;

	
	/* El formulario para una nueva cancion se encuentra en la vista de mostrarAlbum.html */
	
	
	@PostMapping("/addCancion")
	private ModelAndView addCancion(@ModelAttribute Cancion cancion, HttpServletRequest request, BindingResult bindingResult) {
		ModelAndView mav = new ModelAndView();
		
		if(bindingResult.hasErrors()) {
			mav.setViewName("albumes/mostrarAlbum");

			List<Cancion> listaCanciones = (List<Cancion>) cancionService.buscarPorAlbum(cancion.getAlbum());
			// Ordenar las canciones por su numero en el album
			Collections.sort(listaCanciones, (a, b) -> a.getNumero() < b.getNumero() ? -1 : a.getNumero() == b.getNumero() ? 0 : 1);
			mav.addObject("canciones", listaCanciones);
			int contador = listaCanciones.size();
			mav.addObject("numero_canciones", contador);
			
			return mav;
			
		}
		
		cancionService.add(cancion);
		
		String referer = request.getHeader("Referer");
		mav.setViewName("redirect:" + referer);
		return mav;
	}
	
	@GetMapping("/cancion/editar/{cancion}")
	public ModelAndView editarArtista(@PathVariable Cancion cancion, Authentication auth) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("canciones/editarCancion");
		mav.addObject("cancion", cancion);
		
		
		if(auth != null) {
			Usuario usuario = (Usuario) auth.getPrincipal();
			mav.addObject("usuario", usuario);
		}
		
		return mav;
		
	}
	
	@PostMapping("/updateCancion")
	private String updateCancion(@ModelAttribute Cancion cancion) {
	
		cancionService.add(cancion);
		
		return "redirect:/albumes/" + cancion.getAlbum().getId();
		
	}
	
	@GetMapping("/cancion/borrar/{cancion}")
	private String rutaBorrarCancion(@ModelAttribute Cancion cancion)  {
		
		cancionService.borrarCancion(cancion.getId());
		
		return "redirect:/albumes/" + cancion.getAlbum().getId();
		
	}
	
}
