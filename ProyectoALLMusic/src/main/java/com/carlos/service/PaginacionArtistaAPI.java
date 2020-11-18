package com.carlos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.carlos.model.Artista;

public interface PaginacionArtistaAPI {

	Page<Artista> getAll(Pageable pageable);
}
