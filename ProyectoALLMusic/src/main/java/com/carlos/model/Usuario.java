package com.carlos.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
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


@Entity
public class Usuario implements UserDetails {

	@Id
	@Size(min=2, message="El nombre del usuario debe tener como mínimo 2 caracteres.")
	private String nombreUsuario;
	
	@Column
	@NotNull
	@Size(min=3, message="La contraseña debe tener como mínimo 3 caracteres.")
	private String contrasena;
	
	@Column
	@NotNull(message="No puedes dejar esto vacio")
	@Size(min=2, message="El nombre debe tener como mínimo 2 caracteres.")
	@Size(max=20, message="El nombre no puede tener mas de 20 caracteres.")
	private String nombre;
	
	@Column
	@Size(min=2, message="El apellido debe tener como mínimo 2 caracteres.")
	@Size(max=40, message="El apellido no puede tener mas de 40 caracteres.")
	private String apellidos;
	
	@Column
	@Pattern(regexp="^(.+)@(.+)$", message="Email invalido.")
	private String email;

	@ManyToOne
	private Rol rol = new Rol();	
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy = "usuario", cascade=CascadeType.ALL)
	private List<Playlist> playlists = new ArrayList<Playlist>();
	
	public void addPlaylist(Playlist playlist) {

		if(!playlists.contains(playlist)) {
			
			playlists.add(playlist);
		}
	}
	

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
	
	public List<Playlist> getPlaylists() {
		return playlists;
	}


	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
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
		return "Usuario [nombreUsuario=" + nombreUsuario + ", contrasena=" + contrasena + ", nombre=" + nombre
				+ ", apellidos=" + apellidos + ", email=" + email + ", rol=" + rol + "]";
	}

}
