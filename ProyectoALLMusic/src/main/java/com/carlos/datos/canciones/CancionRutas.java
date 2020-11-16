package com.carlos.datos.canciones;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CancionRutas {
	
	@Autowired
	private CancionDAO cancionDAO;

	
	/* El formulario para una nueva cancion se encuentra en la vista de mostrarAlbum.html*/
	
	
	@PostMapping("/addCancion")
	private ModelAndView addCancion(@ModelAttribute Cancion cancion, HttpServletRequest request, BindingResult bindingResult) {
		ModelAndView mav = new ModelAndView();
		
		if(bindingResult.hasErrors()) {
			mav.setViewName("albumes/mostrarAlbum");

			List<Cancion> listaCanciones = (List<Cancion>) cancionDAO.findByAlbum(cancion.getAlbum());
			// Ordenar las canciones por su numero en el album
			Collections.sort(listaCanciones, (a, b) -> a.getNumero() < b.getNumero() ? -1 : a.getNumero() == b.getNumero() ? 0 : 1);
			mav.addObject("canciones", listaCanciones);
			int contador = listaCanciones.size();
			mav.addObject("numero_canciones", contador);
			
			return mav;
		}
		
		cancionDAO.save(cancion);
		
		String referer = request.getHeader("Referer");
		mav.setViewName("redirect:" + referer);
		return mav;
	}
}
