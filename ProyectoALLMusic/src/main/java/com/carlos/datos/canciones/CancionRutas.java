package com.carlos.datos.canciones;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CancionRutas {
	
	@Autowired
	private CancionDAO cancionDAO;

	
	/* El formulario para una nueva cancion se encuentra en la vista de mostrarAlbum.html*/
	
	
	@PostMapping("/addCancion")
	private ModelAndView addCancion(@ModelAttribute Cancion cancion, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		
		cancionDAO.save(cancion);
		
		String referer = request.getHeader("Referer");
		mav.setViewName("redirect:" + referer);
		return mav;
	}
}
