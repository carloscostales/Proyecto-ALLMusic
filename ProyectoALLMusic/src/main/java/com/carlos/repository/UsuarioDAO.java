package com.carlos.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.carlos.model.Usuario;

@Repository
public interface UsuarioDAO extends CrudRepository<Usuario, String> {
	
}
