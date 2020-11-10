package com.carlos.datos.artistas;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.carlos.datos.albumes.Album;
import com.carlos.datos.albumes.AlbumDAO;
import com.carlos.datos.generos.Genero;
import com.carlos.datos.generos.GeneroDAO;
import com.carlos.datos.usuarios.Usuario;
import com.carlos.paginacion.ArtistaServiceAPI;
import com.carlos.servicios.ComparatorFecha;

@Controller
public class ArtistaRutas {

	@Autowired
	private ArtistaDAO artistaDAO;
	
	@Autowired
	private GeneroDAO generoDAO;
	
	@Autowired
	private AlbumDAO albumDAO;
	
	@Autowired
	private ArtistaServiceAPI artistaAPI;

	@GetMapping("/artistas")
	private ModelAndView rutaArtistas(@RequestParam Map<String, Object> params, Authentication auth) {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("artistas/artistas");
		mav.addObject("artista", new Artista());

		List<Artista> listaArtistas = (List<Artista>) artistaDAO.findAll();
		mav.addObject("artistas", listaArtistas);

		// Obtenemos el parametro que tiene la página.Si es diferente de null entonces hace lo siguiente.
		int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;

		// Pagina que vamos a buscar y cuantos registros cargamos por página.
		PageRequest pageRequest = PageRequest.of(page, 10);

		// Realizamos la consulta con los parametros de la pagina y el tamaño de ella.
		Page<Artista> pageArtista = artistaAPI.getAll(pageRequest);

		// Total de páginas.
		int totalPage = pageArtista.getTotalPages();

		// Crea un stream del 1 al total de páginas. Lo convertimos en una lista(.boxed().collect(Collectors.toList())
		if (totalPage > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			mav.addObject("paginas", pages);
		}

		mav.addObject("lista", pageArtista.getContent());
		mav.addObject("current", page+1);
		mav.addObject("next", page+2);
		mav.addObject("prev", page);
		mav.addObject("last", totalPage);
		
		
		if (auth != null) {
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
	private ModelAndView rutaAnadirArtista(@Valid @ModelAttribute Artista artista, BindingResult bindingResult, Authentication auth) {
		
		ModelAndView mav = new ModelAndView();
		
		if(bindingResult.hasErrors()) {
			mav.setViewName("artistas/nuevoArtista");

			List<Genero> listaGeneros = (List<Genero>)generoDAO.findAll();
			mav.addObject("generos",listaGeneros);
			Usuario usuario = (Usuario) auth.getPrincipal();
			mav.addObject("usuario", usuario);
			
			return mav;
		}
		
		artistaDAO.save(artista);
		
		mav.setViewName("redirect:/");
		
		return mav;
		
	}
	
	@GetMapping("/artista/editar/{artista}")
	public ModelAndView editarArtista(@PathVariable Artista artista, Authentication auth) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/artistas/editarArtista");
		mav.addObject("artista", artista);
		
		List<Genero> listaGeneros = (List<Genero>)generoDAO.findAll();
		mav.addObject("generos",listaGeneros);
		
		if(auth != null) {
			System.out.println("nombre: " + auth.getName());
			Usuario usuario = (Usuario) auth.getPrincipal();
			mav.addObject("usuario", usuario);
		}
		
		return mav;
	}
	
	@PostMapping("/updateArtista")
	private ModelAndView editarArtistaPost(@Valid @ModelAttribute Artista artista, BindingResult bindingResult) {
		
		ModelAndView mav = new ModelAndView();
		
		if(bindingResult.hasErrors()) {
			mav.setViewName("artistas/nuevoArtista");

			List<Genero> listaGeneros = (List<Genero>)generoDAO.findAll();
			mav.addObject("generos",listaGeneros);
			
			return mav;
		}
		
		artistaDAO.save(artista);
		
		mav.setViewName("redirect:/artistas/" + artista.getId());
		
		return mav;
		
	}
	
}
