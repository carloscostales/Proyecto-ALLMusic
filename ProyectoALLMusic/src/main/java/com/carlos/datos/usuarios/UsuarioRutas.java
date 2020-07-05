package com.carlos.datos.usuarios;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.carlos.roles.Rol;
import com.carlos.roles.RolDAO;


@Controller
public class UsuarioRutas {

	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	private RolDAO rolDAO;
	
	
	@GetMapping("/usuarios")
	private ModelAndView rutaUsuario() {
		
		ModelAndView model = new ModelAndView();
		model.setViewName("usuarios/usuarios");
		model.addObject("usuario", new Usuario());
		
		List<Usuario> listaUsuarios = (List<Usuario>) usuarioDAO.findAll();
		model.addObject("usuarios", listaUsuarios);
		
		List<Rol> listaRoles = (List<Rol>)rolDAO.findAll();
		model.addObject("roles",listaRoles);
		
		return model;
		
	}
	
	@PostMapping("usuarios/anadir")
	private String rutaAnadir(@ModelAttribute Usuario usuario) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		usuario.setContrasena(passwordEncoder.encode(usuario.getPassword()));
		
		usuarioDAO.save(usuario);
		
		return "redirect:/usuarios";
		
	}

	@GetMapping("/usuarios/borrar/{usuario}")
	public String rutaEliminar(@PathVariable String usuario) {

		
		//Version 2
		usuarioDAO.deleteById(usuario);
		
		return("redirect:/usuarios");

	}
	
	@GetMapping("/usuarios/{usuario}")
	private ModelAndView rutaUsuario(@PathVariable Usuario usuario) {
		ModelAndView model = new ModelAndView();
		model.setViewName("usuarios/mostrarUsuario");
		model.addObject("usuario", usuario);

		
		return model;
		
	}

	@GetMapping("/usuarios/editar/{usuario}")
	private ModelAndView editarUsuario(@PathVariable Usuario usuario) {
		
		ModelAndView model = new ModelAndView();
		model.setViewName("editarUsuario");
		model.addObject("usuario", usuario);
		
		List<Rol> listaRoles = (List<Rol>)rolDAO.findAll();
		model.addObject("roles",listaRoles);
		
		return model;
		
	}
	
	@PostMapping("/updateUsuario")
	public ModelAndView updateUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult bindingResult) {
		
		System.out.println("bindingResult.hasErrors()" + bindingResult.hasErrors());
		
		ModelAndView model = new ModelAndView();

		if(bindingResult.hasErrors()) {
			model.setViewName("editarUsuario");

			List<Rol> listaRoles = (List<Rol>)rolDAO.findAll();
			model.addObject("roles",listaRoles);
			
			return model;
		}
		
		usuarioDAO.save(usuario);

		model.setViewName("redirect:/usuarios");

		return model;

	}
	
	@GetMapping("/consultas")
	public String consultas() {
		
		// Busqueda
		List<Usuario> resultado = usuarioDAO.findByEdad(10);
		
		List<Usuario> resultadoCompleto = (List<Usuario>) usuarioDAO.findAll();
		
		System.out.println(resultadoCompleto);
		
		
		return "redirect:/usuarios";

	}
	
}
