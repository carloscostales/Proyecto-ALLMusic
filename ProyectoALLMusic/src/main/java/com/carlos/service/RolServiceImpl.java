package com.carlos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.model.Rol;
import com.carlos.repository.RolDAO;

@Service
public class RolServiceImpl implements RolService {

	@Autowired
	private RolDAO rolDAO;
	
	@Override
	public List<Rol> listaRoles() {
		
		return (List<Rol>) rolDAO.findAll();
	}

}
