package com.carlos.paginacion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.carlos.datos.artistas.Artista;

public interface ArtistaServiceAPI {

	Page<Artista> getAll(Pageable pageable);
}
