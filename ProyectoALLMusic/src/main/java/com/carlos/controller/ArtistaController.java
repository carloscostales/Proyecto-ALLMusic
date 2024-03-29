package com.carlos.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.util.Collections;
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
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.carlos.enums.RutaArchivos;
import com.carlos.enums.TipoAlbumModel;
import com.carlos.model.Album;
import com.carlos.model.Artista;
import com.carlos.model.Genero;
import com.carlos.model.Usuario;
import com.carlos.service.AlbumService;
import com.carlos.service.ArtistaService;
import com.carlos.service.CancionService;
import com.carlos.service.ComparatorFechaISO;
import com.carlos.service.ComparatorFechaPersonalizada;
import com.carlos.service.GeneroService;

@Controller
public class ArtistaController {

	@Autowired
	private ArtistaService artistaService;
	
	@Autowired
	private GeneroService generoService;
	
	@Autowired
	private AlbumService albumService;
	
	@Autowired
	private CancionService cancionService;
	
	
	@GetMapping("/artistas")
	private ModelAndView rutaArtistas(@RequestParam Map<String, Object> params, Authentication auth) {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("artistas/artistas");
		mav.addObject("artista", new Artista());


		// Obtenemos el parametro que tiene la página.Si es diferente de null entonces hace lo siguiente.
		int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;

		// Pagina que vamos a buscar y cuantos registros cargamos por página.
		PageRequest pageRequest = PageRequest.of(page, 18);

		// Realizamos la consulta con los parametros de la pagina y el tamaño de ella.
		Page<Artista> pageArtista = artistaService.listaArtistasCompleta(pageRequest);

		// Total de páginas.
		int totalPage = pageArtista.getTotalPages();

		// Crea un stream del 1 al total de páginas. Lo convertimos en una lista(.boxed().collect(Collectors.toList())
		if (totalPage > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			mav.addObject("paginas", pages);
		}

		mav.addObject("listaArtistas", pageArtista.getContent());
		mav.addObject("current", page+1);
		mav.addObject("next", page+2);
		mav.addObject("prev", page);
		mav.addObject("last", totalPage);
		
		mav.addObject("busqueda", new Artista());
		
		if (auth != null) {
			Usuario usuario = (Usuario) auth.getPrincipal();
			mav.addObject("usuario", usuario);
		}

		return mav;

	}
	
	@GetMapping("/artistas/buscar")
	private ModelAndView buscarPorArtista(@RequestParam Map<String, Object> params, @RequestParam String nombre, @ModelAttribute("busqueda") Artista busqueda, Authentication auth) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("artistas/busquedaArtistas");
		mav.addObject("busqueda", busqueda);
		

		// Obtenemos el parametro que tiene la página.Si es diferente de null entonces hace lo siguiente.
		int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;

		// Pagina que vamos a buscar y cuantos registros cargamos por página.
		PageRequest pageRequest = PageRequest.of(page, 12);

		// Realizamos la consulta con los parametros de la pagina y el tamaño de ella.
		Page<Artista> pageArtista = artistaService.buscarPorNombreEmpiezaCon(pageRequest, nombre);

		// Total de páginas.
		int totalPage = pageArtista.getTotalPages();

		// Crea un stream del 1 al total de páginas. Lo convertimos en una lista(.boxed().collect(Collectors.toList())
		if (totalPage > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			mav.addObject("paginas", pages);
		}

		mav.addObject("listaArtistas", pageArtista.getContent());
		mav.addObject("current", page+1);
		mav.addObject("next", page+2);
		mav.addObject("prev", page);
		mav.addObject("last", totalPage);
		
		if (auth != null) {
			Usuario usuario = (Usuario) auth.getPrincipal();
			mav.addObject("usuario", usuario);
		}
		return mav;
	}
	
	@GetMapping("/artistas/{artista}")
	private ModelAndView rutaArtista(@PathVariable Artista artista, Authentication auth) throws ParseException {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("artistas/mostrarArtista");
		mav.addObject("artista", artista);
		
		mav.addObject("album", new Album());
		
		List<Album> listaAlbumes = (List<Album>)albumService.buscarAlbumesDeArtista(artista);
		
		
		if(!listaAlbumes.isEmpty()) {
			// Ultimo lanzamiento del artista
			listaAlbumes.sort(new ComparatorFechaISO());
			Album ultimo = listaAlbumes.get(0);
			mav.addObject("ultimoLanzamiento", ultimo);
			
			// Albumes por tipo. Ordenación por fecha
			List<Album> listaTipoAlbum = albumService.buscarPorTipoAlbum(artista.getId(), TipoAlbumModel.ALBUM.toString());
			listaTipoAlbum = albumService.cambiarFecha(listaTipoAlbum);
			listaTipoAlbum.sort(new ComparatorFechaPersonalizada());
			mav.addObject("listaTipoAlbum", listaTipoAlbum);
			
			List<Album> listaTipoSingle = albumService.buscarPorTipoAlbum(artista.getId(), TipoAlbumModel.SINGLE.toString());
			listaTipoSingle = albumService.cambiarFecha(listaTipoSingle);
			listaTipoSingle.sort(new ComparatorFechaPersonalizada());
			mav.addObject("listaTipoSingle", listaTipoSingle);
		}
		
		// Artistas con el mismo género que el artista de la ruta
		if (artista != null) {
			List<Artista> artistasPorGenero = artistaService.buscarPorGenero(artista.getGenero());
			Collections.shuffle(artistasPorGenero);
			mav.addObject("artistasRelacionados", artistasPorGenero);
			
			// Lista de artistas del mismo género excluyendo el artista de la ruta
			List<Artista> listaRelacionados = artistaService.buscarArtistasDelMismoGenero(artista.getId(), artista.getGenero().getNombre());
			mav.addObject("listaRelacionados", listaRelacionados);
		}
        if(auth != null) {
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
		
		List<Genero> listaGeneros = generoService.listaGeneros();
		mav.addObject("generos",listaGeneros);
		
		if(auth != null) {
			Usuario usuario = (Usuario) auth.getPrincipal();
			mav.addObject("usuario", usuario);
		}
		
		return mav;
	}
	
	@PostMapping("/addArtista")
	private ModelAndView rutaAnadirArtista(@ModelAttribute Artista artista, BindingResult bindingResult, Authentication auth,
			@RequestParam("foto") MultipartFile multipartFile) throws IOException {
		
		ModelAndView mav = new ModelAndView();
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		artista.setFoto(fileName);
		
		artistaService.add(artista);
		
		Artista savedArtista = artista;
		
		String uploadDir = "." + RutaArchivos.LOCALHOST.toString() + savedArtista.getId();
		
		Path uploadPath = Paths.get(uploadDir);
		
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		try (InputStream inputStream = multipartFile.getInputStream()){
			Path filePath = uploadPath.resolve(fileName);
			System.out.println("FILEPATH -------------------------------------------------------------------- " + filePath.toFile().getAbsolutePath());
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);	
		} catch (IOException e) {
			throw new IOException("No se guardo el archivo subido: " + fileName);
		}
		
		mav.setViewName("redirect:/");
		
		return mav;
		
	}
	
	@GetMapping("/artista/editar/{artista}")
	public ModelAndView editarArtista(@PathVariable Artista artista, Authentication auth) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/artistas/editarArtista");
		mav.addObject("artista", artista);
		
		List<Genero> listaGeneros = generoService.listaGeneros();
		mav.addObject("generos",listaGeneros);
		
		if(auth != null) {
			Usuario usuario = (Usuario) auth.getPrincipal();
			mav.addObject("usuario", usuario);
		}
		
		return mav;
		
	}
	
	@PostMapping("/updateArtista")
	private ModelAndView editarArtistaPost(@Valid @ModelAttribute Artista artista, BindingResult bindingResult) {
		
		ModelAndView mav = new ModelAndView();
		
		artistaService.update(artista);
		
		mav.setViewName("redirect:/artistas/" + artista.getId());
		
		return mav;
		
	}
	
	@PostMapping("/updateArtistaFoto")
	private ModelAndView editarArtistaFotoPost(@ModelAttribute Artista artista, BindingResult bindingResult, Authentication auth,
			@RequestParam("foto") MultipartFile multipartFile) throws IOException {
		
		ModelAndView mav = new ModelAndView();
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		
		artista.setFoto(fileName);
		
		artistaService.add(artista);
		
		Artista savedArtista = artista;
		
		String uploadDir = "." + RutaArchivos.LOCALHOST.toString() + savedArtista.getId();
		
		Path uploadPath = Paths.get(uploadDir);
		
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		try (InputStream inputStream = multipartFile.getInputStream()){
			Path filePath = uploadPath.resolve(fileName);
			System.out.println("FILEPATH -------------------------------------------------------------------- " + filePath.toFile().getAbsolutePath());
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);	
		} catch (IOException e) {
			
		}
		
		mav.setViewName("redirect:/artistas/" + artista.getId());
		
		return mav;
		
	}

	@PostMapping("/updateArtistaFotoFondo")
	private ModelAndView editarArtistaFotoFondoPost(@ModelAttribute Artista artista, BindingResult bindingResult, Authentication auth,
			@RequestParam("foto_fondo") MultipartFile multipartFile) throws IOException {
		
		ModelAndView mav = new ModelAndView();
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		artista.setFoto_fondo(fileName);
		
		artistaService.add(artista);
		
		Artista savedArtista = artista;
		
		String uploadDir = "." + RutaArchivos.LOCALHOST.toString() + savedArtista.getId();
		
		Path uploadPath = Paths.get(uploadDir);
		
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		try (InputStream inputStream = multipartFile.getInputStream()){
			Path filePath = uploadPath.resolve(fileName);
			System.out.println("FILEPATH -------------------------------------------------------------------- " + filePath.toFile().getAbsolutePath());
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);	
		} catch (IOException e) {
			
		}
		
		mav.setViewName("redirect:/artistas/" + artista.getId());
		
		return mav;
		
	}
	
	@GetMapping("/artista/borrar/{artista}")
	private String rutaBorrarAlbum(@PathVariable Artista artista)  {
		
		cancionService.borrarCancionesDeArtistaEnPlaylist(artista.getId());
		cancionService.borrarCancionesDeArtista(artista.getId());
		albumService.borrarAlbumesDeArtista(artista.getId());
		artistaService.delete(artista);
		
		return "redirect:/artistas";
		
	}
	
}
