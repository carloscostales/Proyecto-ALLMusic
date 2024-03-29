package com.carlos.controller;

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

import com.carlos.model.Genero;
import com.carlos.model.Usuario;
import com.carlos.service.GeneroService;

@Controller
public class GeneroController {

	@Autowired
	private GeneroService generoService;
	
	@GetMapping("/generos")
	private ModelAndView verGeneros(Authentication auth) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("generos/generos");
		
		List<Genero> listaGeneros = generoService.listaGeneros();
		mav.addObject("generos", listaGeneros);
		
		mav.addObject("genero", new Genero());
		
		if(auth != null) {
			Usuario usuario = (Usuario) auth.getPrincipal();
			mav.addObject("usuario", usuario);
		}
		
		return mav;
	}
	
	@PostMapping("/addGenero")
	private ModelAndView addGenero(@ModelAttribute Genero genero, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		
		generoService.add(genero);
		
		String referer = request.getHeader("Referer");
		mav.setViewName("redirect:" + referer);
		
		return mav;
		
	}
	
	@GetMapping("/borrarGenero/{genero}")
	public String rutaEliminar(@PathVariable Genero genero) {
		
		generoService.delete(genero);
		
		return("redirect:/generos");

	}
	
}
