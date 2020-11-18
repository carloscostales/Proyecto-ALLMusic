package com.carlos.repository;

import org.springframework.data.repository.CrudRepository;

import com.carlos.model.Rol;

public interface RolDAO extends CrudRepository<Rol, String> {

}
