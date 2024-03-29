package com.carlos.controller;

import java.util.List;
import java.util.Optional;

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

import com.carlos.model.Artista;
import com.carlos.model.Playlist;
import com.carlos.model.Rol;
import com.carlos.model.Usuario;
import com.carlos.service.ArtistaService;
import com.carlos.service.PlaylistService;
import com.carlos.service.RolService;
import com.carlos.service.UsuarioService;


@Controller
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private RolService rolService;
	
	@Autowired
	private PlaylistService playlistService;
	
	@Autowired
	private ArtistaService artistaService;
	
	
	@GetMapping("/usuarios")
	private ModelAndView rutaUsuario(Authentication auth) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("usuarios/usuarios");
		
		List<Usuario> listaUsuarios = (List<Usuario>) usuarioService.listaUsuarios();
		mav.addObject("usuarios", listaUsuarios);
		
		if(auth != null) {
			Usuario usuario = (Usuario) auth.getPrincipal();
			mav.addObject("usuario", usuario);
		}
		
		return mav;
		
	}
	
	@GetMapping("/registrarse")
	private ModelAndView nuevoUsuario() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("usuarios/nuevoUsuario");
		mav.addObject("usuario", new Usuario());
		
		List<Rol> listaRoles = (List<Rol>)rolService.listaRoles();
		mav.addObject("roles",listaRoles);
		
		return mav;
	}
	
	@PostMapping("/addUser")
	private ModelAndView rutaAnadir(@Valid @ModelAttribute Usuario usuario, BindingResult bindingResult) {
		
		ModelAndView mav = new ModelAndView();

		if(bindingResult.hasErrors()) {
			mav.setViewName("usuarios/nuevoUsuario");

			List<Rol> listaRoles = (List<Rol>)rolService.listaRoles();
			mav.addObject("roles",listaRoles);

			return mav;
		}
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		usuario.setContrasena(passwordEncoder.encode(usuario.getPassword()));
		
		usuarioService.add(usuario);
		
		mav.setViewName("redirect:/");
		
		return mav;
		
	}
	
	@GetMapping("/usuarios/{usuario}")
	private ModelAndView rutaUsuario(@PathVariable Usuario usuario) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("usuarios/mostrarUsuario");
		mav.addObject("usuario", usuario);

		List<Playlist> listaPlaylists = (List<Playlist>)playlistService.buscarPorUsuario(usuario);
		mav.addObject("listaPlaylists", listaPlaylists);
		
		mav.addObject("genero_preferido", usuarioService.generoPreferidoUsuario(usuario.getNombreUsuario()));
		
		Artista artista = null;
		if(usuarioService.artistaPreferidoUsuario(usuario.getNombreUsuario()) != null) {
			int artista_id_preferido = usuarioService.artistaPreferidoUsuario(usuario.getNombreUsuario());
			Optional<Artista> artista_optional = artistaService.buscarPorId(artista_id_preferido);
			artista = artista_optional.get();
		}
		
		mav.addObject("artista_preferido", artista);
		return mav;
		
	}

	@GetMapping("/usuarios/editar/{usuario}")
	private ModelAndView editarUsuario(@PathVariable Usuario usuario) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("usuarios/editarUsuario");
		mav.addObject("usuario", usuario);
		
		List<Rol> listaRoles = (List<Rol>)rolService.listaRoles();
		mav.addObject("roles",listaRoles);
		
		return mav;
		
	}
	
	@PostMapping("/updateUsuario")
	public ModelAndView updateUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult bindingResult) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/usuarios/" + usuario.getNombreUsuario());
		
		usuarioService.add(usuario);
		
		return mav;
		
	}
	
	@GetMapping("/borrarUsuario/{usuario}")
	public String rutaEliminar(@PathVariable String usuario) {
		
		usuarioService.deleteById(usuario);
		
		return "redirect:/usuarios";

	}
	
}
