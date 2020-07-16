package com.carlos.datos.usuarios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDAO extends CrudRepository<Usuario, String> {
	
	List<Usuario> findByEdad(Integer edad);
	List<Usuario> findByEdadLessThan(Integer edad);	
	List<Usuario> findByEdadLessThanEqual(Integer edad);	

	List<Usuario> findByEdadAndNombreUsuario(Integer edad,String usuario);
	List<Usuario> findByEdadOrNombreUsuario(Integer edad,String usuario);	

	List<Usuario> findByNombreUsuario(String cadena);
	List<Usuario> findByNombreUsuarioEndsWith(String cadena);	
	List<Usuario> findByNombreUsuarioStartsWith(String cadena);	

	List<Usuario> findTop3ByEdadGreaterThan(Integer edad);
	
}
