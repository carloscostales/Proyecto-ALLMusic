package com.carlos.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.carlos.model.Album;
import com.carlos.model.Cancion;
import com.carlos.model.Usuario;
import com.carlos.service.AlbumService;
import com.carlos.service.CancionService;

@Controller
public class AlbumController {
	
	@Autowired
	private AlbumService albumService;
	
	@Autowired
	private CancionService cancionService;
	
	
	@GetMapping("/albumes")
	private ModelAndView rutaAlbumes(Authentication auth) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("albumes/albumes");
		mav.addObject("album", new Album());
		
		List<Album> listaAlbumes = albumService.listaAlbumesCompleta();
		mav.addObject("albumes", listaAlbumes);
		
		if(auth != null) {
			Usuario usuario = (Usuario) auth.getPrincipal();
			mav.addObject("usuario", usuario);
		}
		
		return mav;
		
	}
	
	@GetMapping("/albumes/{album}")
	private ModelAndView rutaAlbumesCanciones(@PathVariable Album album, Authentication auth) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("albumes/mostrarAlbum");
		mav.addObject("album", album);
		
		List<Cancion> listaCanciones = (List<Cancion>) cancionService.buscarPorAlbum(album);
		// Ordenar las canciones por su numero en el album
		Collections.sort(listaCanciones, (a, b) -> a.getNumero() < b.getNumero() ? -1 : a.getNumero() == b.getNumero() ? 0 : 1);
		mav.addObject("canciones", listaCanciones);
		int contador = listaCanciones.size();
		mav.addObject("numero_canciones", contador);
		
		// Lista de albumes que aparece al final de la vista
		List<Album> listaAlbumes = null;
		List<Album> listaAlbumesCorta = null;
		if(album != null) {
			listaAlbumes = albumService.buscarTodosMenosUnoArtista(album.getArtista().getId(), album.getId());
			listaAlbumesCorta = null;
			Collections.shuffle(listaAlbumes);
			
			if(listaAlbumes.size() < 6 ) {
				listaAlbumesCorta = listaAlbumes.subList(0, listaAlbumes.size());
			} else {
				listaAlbumesCorta = listaAlbumes.subList(0, 6);
			}
		}
		
		mav.addObject("listaAlbumes", listaAlbumesCorta);
		
		mav.addObject("cancion", new Cancion());
		
		if(auth != null) {
			Usuario usuario = (Usuario) auth.getPrincipal();
			mav.addObject("usuario", usuario);
		}
		
		return mav;
		
	}
	
	
	/* El formulario para un nuevo album se encuentra en la vista de mostrarArtista.html*/
	
	
	@PostMapping("/addAlbum")
	private String rutaAnadirAlbum(@ModelAttribute Album album, BindingResult bindingResult, HttpServletRequest request, @RequestParam("portada")  MultipartFile multipartFile) throws IOException {
		
		// Utilizado para volver a la pagina en la que esta el modal
		String referer = request.getHeader("Referer");
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		
		album.setPortada(fileName);
		
		albumService.add(album);
		
		Album savedAlbum = album; 
		
		String uploadDir = "." + RutaArchivos.LOCALHOST.toString() + savedAlbum.getArtista().getId() + "/album-portadas/";
		
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
		
		return "redirect:" + referer;
		
	}
	
	@PostMapping("/updateAlbum")
	private String rutaActualizarAlbum(@ModelAttribute Album album, BindingResult bindingResult) {
		
		albumService.add(album);
		
		return "redirect:/albumes/" + album.getId();
		
	}
	
	@PostMapping("/updatePortadaAlbum")
	private String rutaActualizarPortadaAlbum(@ModelAttribute Album album, BindingResult bindingResult, @RequestParam("portada")  MultipartFile multipartFile) throws IOException {
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		
		album.setPortada(fileName);
		
		albumService.add(album);
		
		Album savedAlbum = album;
		
		String uploadDir = "." + RutaArchivos.LOCALHOST.toString() + savedAlbum.getArtista().getId() + "/album-portadas/";
		
		Path uploadPath = Paths.get(uploadDir);
		
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		try (InputStream inputStream = multipartFile.getInputStream()){
			Path filePath = uploadPath.resolve(fileName);
			System.out.println("FILEPATH -> " + filePath.toFile().getAbsolutePath());
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);	
		} catch (IOException e) {
			
		}
		
		return "redirect:/albumes/" + album.getId();
		
	}
	
	@GetMapping("/album/borrar/{album}")
	private String rutaBorrarAlbum(@PathVariable Album album)  {
		
		cancionService.borrarCancionesDeAlbum(album.getId());
		albumService.borrarAlbum(album.getId());
		
		return "redirect:/artistas/" + album.getArtista().getId();
		
	}
	
}
