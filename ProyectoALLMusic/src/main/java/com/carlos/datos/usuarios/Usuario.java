package com.carlos.datos.usuarios;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.carlos.roles.Rol;


@Entity
public class Usuario implements UserDetails {

	@Id
	private String nombreUsuario;
	
	@Column
	private String contrasena;
	
	@Column
	@NotNull(message="No puedes dejar esto vacio")
	@Size(min=3)
	@Size(max=10, message="Nombre no puede ser tan largo. Maximo 10 caracteres")
	private String nombre;
	
	@Column
	private String apellidos;
	
	@Column
	@Pattern(regexp="^(.+)@(.+)$", message="Email invalido")
	private String email;
	
	@Column
	private String telefono;
	
	@Column
	private Integer edad;

	@ManyToOne
	private Rol rol = new Rol();	
	

	public Rol getRol() {
		return rol;
	}

	public void setRoles(Rol rol) {
		this.rol = rol;
	}
	
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}


	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
	    grantedAuthorities.add(new SimpleGrantedAuthority(rol.getNombre()));
	    	    
	    return grantedAuthorities;
	}

	@Override
	public String getUsername() {

		return this.nombreUsuario;
	}
	
	@Override
	public String getPassword() {

		return this.contrasena;
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}

	@Override
	public String toString() {
		return "[nombreUsuario=" + nombreUsuario + ", edad=" + edad + "]";
	}

}
