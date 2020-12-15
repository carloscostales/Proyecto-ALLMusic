package com.carlos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.carlos.model.Genero;
import com.carlos.model.Rol;
import com.carlos.model.Usuario;
import com.carlos.repository.GeneroDAO;
import com.carlos.repository.RolDAO;
import com.carlos.repository.UsuarioDAO;

@Component
public class Arranque {

    @Autowired
    private RolDAO rolDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private GeneroDAO generoDAO;

    @EventListener(ContextRefreshedEvent.class)
    void contextRefreshedEvent() {
        Rol rol1 = new Rol();
        rol1.setNombre("ADMIN");
        Rol rol2 = new Rol();
        rol2.setNombre("USER");

        rolDAO.save(rol1);
        rolDAO.save(rol2);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Usuario admin = new Usuario();
        admin.setNombreUsuario("admin");
        admin.setContrasena("admin");
        admin.setNombre("admin");
        admin.setApellidos("admin");
        admin.setEmail("admin@admin.com");
        admin.setRoles(rol1);
        admin.setContrasena(passwordEncoder.encode(admin.getPassword()));

        Usuario user = new Usuario();
        user.setNombreUsuario("user");
        user.setContrasena("user");
        user.setNombre("user");
        user.setApellidos("user");
        user.setEmail("user@user.com");
        user.setRoles(rol2);
        user.setContrasena(passwordEncoder.encode(user.getPassword()));

        usuarioDAO.save(admin);
        usuarioDAO.save(user);

        Genero genero = new Genero();
        genero.setNombre("Punk");
        generoDAO.save(genero);

    }
}
