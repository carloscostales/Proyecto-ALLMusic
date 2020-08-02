package com.carlos;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.carlos.datos.usuarios.Usuario;

@Controller
public class RutasGenericas {

	@GetMapping("/")
	public ModelAndView index(Authentication auth) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		
		if(auth != null) {
			System.out.println("nombre: " + auth.getName());
			Usuario usuario = (Usuario) auth.getPrincipal();
			mav.addObject("usuario", usuario);
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
