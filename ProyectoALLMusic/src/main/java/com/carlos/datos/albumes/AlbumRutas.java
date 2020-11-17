package com.carlos.datos.albumes;

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

import com.carlos.datos.canciones.Cancion;
import com.carlos.datos.canciones.CancionDAO;
import com.carlos.datos.usuarios.Usuario;

@Controller
public class AlbumRutas {
	
	@Autowired
	private AlbumDAO albumDAO;
	
	@Autowired
	private CancionDAO cancionDAO;
	
	
	@GetMapping("/albumes")
	private ModelAndView rutaAlbumes(Authentication auth) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("albumes/albumes");
		mav.addObject("album", new Album());
		
		List<Album> listaAlbumes = (List<Album>) albumDAO.findAll();
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
		
		List<Cancion> listaCanciones = (List<Cancion>) cancionDAO.findByAlbum(album);
		// Ordenar las canciones por su numero en el album
		Collections.sort(listaCanciones, (a, b) -> a.getNumero() < b.getNumero() ? -1 : a.getNumero() == b.getNumero() ? 0 : 1);
		mav.addObject("canciones", listaCanciones);
		int contador = listaCanciones.size();
		mav.addObject("numero_canciones", contador);
		
		List<Album> listaAlbumes = albumDAO.findByArtista(album.getArtista());
		List<Album> listaAlbumesCorta = null;
		Collections.shuffle(listaAlbumes);
		
		if(listaAlbumes.size() < 6 ) {
			listaAlbumesCorta = listaAlbumes.subList(0, listaAlbumes.size());
		} else {
			listaAlbumesCorta = listaAlbumes.subList(0, 6);
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
	private String rutaAnadirAlbum(@ModelAttribute Album album, BindingResult bindingResult, HttpServletRequest request,@RequestParam("portada")  MultipartFile multipartFile) throws IOException {
		
		// Utilizado para volver a la pagina en la que esta el modal
		String referer = request.getHeader("Referer");
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		album.setPortada(fileName);
		
		Album savedAlbum = albumDAO.save(album);
		
		String uploadDir = "./artista-fotos/" + savedAlbum.getArtista().getId() + "/album-portadas/";
		
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
	private String rutaActualizarAlbum(@ModelAttribute Album album, BindingResult bindingResult) throws IOException {
		
		albumDAO.save(album);
		
		return "redirect:/album/" + album.getId();
		
	}
	
	@PostMapping("/updateFotoAlbum")
	private String rutaActualizarFotoAlbum(@ModelAttribute Album album, BindingResult bindingResult, @RequestParam("portada")  MultipartFile multipartFile) throws IOException {
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		album.setPortada(fileName);
		
		Album savedAlbum = albumDAO.save(album);
		
		String uploadDir = "./artista-fotos/" + savedAlbum.getArtista().getId() + "/album-portadas/";
		
		Path uploadPath = Paths.get(uploadDir);
		
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		try (InputStream inputStream = multipartFile.getInputStream()){
			Path filePath = uploadPath.resolve(fileName);
			System.out.println("FILEPATH -> " + filePath.toFile().getAbsolutePath());
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);	
		} catch (IOException e) {
			throw new IOException("No se guardo el archivo subido: " + fileName);
		}
		
		return "redirect:/album/" + album.getId();
		
	}
	
}
