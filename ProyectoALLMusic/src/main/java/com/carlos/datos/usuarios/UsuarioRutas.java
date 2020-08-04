package com.carlos.datos.usuarios;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.carlos.datos.playlists.Playlist;
import com.carlos.datos.playlists.PlaylistDAO;
import com.carlos.roles.Rol;
import com.carlos.roles.RolDAO;


@Controller
public class UsuarioRutas {

	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	private RolDAO rolDAO;
	
	@Autowired
	private PlaylistDAO playlistDAO;
	
	
	@GetMapping("/usuarios")
	private ModelAndView rutaUsuario() {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("usuarios/usuarios");
		
		List<Usuario> listaUsuarios = (List<Usuario>) usuarioDAO.findAll();
		mav.addObject("usuarios", listaUsuarios);
		
		return mav;
		
	}
	
	@GetMapping("/nuevoUsuario")
	private ModelAndView nuevoUsuario(Authentication auth) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("usuarios/nuevoUsuario");
		mav.addObject("usuario", new Usuario());
		
		List<Rol> listaRoles = (List<Rol>)rolDAO.findAll();
		mav.addObject("roles",listaRoles);
		
		return mav;
	}
	
	@PostMapping("/addUser")
	private ModelAndView rutaAnadir(@Valid @ModelAttribute Usuario usuario, BindingResult bindingResult) {
		
		ModelAndView mav = new ModelAndView();

		if(bindingResult.hasErrors()) {
			mav.setViewName("usuarios/nuevoUsuario");

			List<Rol> listaRoles = (List<Rol>)rolDAO.findAll();
			mav.addObject("roles",listaRoles);

			return mav;
		}
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		usuario.setContrasena(passwordEncoder.encode(usuario.getPassword()));
		
		usuarioDAO.save(usuario);
		
		mav.setViewName("redirect:/usuarios");
		
		return mav;
		
	}

	@GetMapping("/usuarios/borrar/{usuario}")
	public String rutaEliminar(@PathVariable String usuario) {

		
		//Version 2
		usuarioDAO.deleteById(usuario);
		
		return("redirect:/usuarios");

	}
	
	@GetMapping("/usuarios/{usuario}")
	private ModelAndView rutaUsuario(@PathVariable Usuario usuario) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("usuarios/mostrarUsuario");
		mav.addObject("usuario", usuario);

		List<Playlist> listaPlaylists = (List<Playlist>)playlistDAO.findByUsuario(usuario);
		mav.addObject("listaPlaylists", listaPlaylists);
		
		return mav;
		
	}

	@GetMapping("/usuarios/editar/{usuario}")
	private ModelAndView editarUsuario(@PathVariable Usuario usuario) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("usuarios/editarUsuario");
		mav.addObject("usuario", usuario);
		
		List<Rol> listaRoles = (List<Rol>)rolDAO.findAll();
		mav.addObject("roles",listaRoles);
		
		return mav;
		
	}
	
	@PostMapping("/updateUsuario")
	public ModelAndView updateUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult bindingResult) {
		
		System.out.println("bindingResult.hasErrors()" + bindingResult.hasErrors());
		
		ModelAndView mav = new ModelAndView();

		if(bindingResult.hasErrors()) {
			mav.setViewName("usuarios/editarUsuario");

			List<Rol> listaRoles = (List<Rol>)rolDAO.findAll();
			mav.addObject("roles",listaRoles);
			
			return mav;
		}
		
		usuarioDAO.save(usuario);

		mav.setViewName("redirect:/usuarios");

		return mav;

	}
	
}
