package com.carlos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.carlos.service.Autenticacion;

@Configuration
@EnableWebSecurity(debug=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private Autenticacion autenticacion;
	
	
	 @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        
	    	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	        provider.setPasswordEncoder(new BCryptPasswordEncoder());
	        provider.setUserDetailsService(autenticacion);
	    	
	    	auth.authenticationProvider(provider);
	    }

	 @Override
	 protected void configure(HttpSecurity http) throws Exception {
	   	http
	   		.authorizeRequests()
		        .antMatchers("/usuarios").hasAuthority("ADMIN")
		        .antMatchers("/artista/editar/**").hasAuthority("ADMIN")
		        .antMatchers("/cancion/editar/**").hasAuthority("ADMIN")
		        .antMatchers("/playlists/**").hasAnyAuthority("ADMIN", "USER")
		        .antMatchers("/nuevoArtista").hasAuthority("ADMIN")
		        .antMatchers("/nuevaPlaylist").hasAnyAuthority("ADMIN", "USER")
		        .antMatchers("/playlists/nuevaCancionPlaylist/**").hasAuthority("ADMIN")
		        .antMatchers("/usuarios/editar/**").hasAuthority("ADMIN")
		        .antMatchers("/artista/borrar/**").hasAuthority("ADMIN")
		        .antMatchers("/album/borrar/**").hasAuthority("ADMIN")
		        .antMatchers("/cancion/borrar/**").hasAuthority("ADMIN")
		        .antMatchers("/borrarGenero/**").hasAuthority("ADMIN")
		        .antMatchers("/playlists/borrar/**").hasAuthority("ADMIN")
		    .and()
	        .formLogin()
	        	.loginPage("/login")
	            .defaultSuccessUrl("/")
	            .failureUrl("/login-error")
	            .usernameParameter("username")
	            .passwordParameter("password")
	        .and()
	        .logout()
	        	.permitAll()
	            .logoutUrl("/logout")
	            .logoutSuccessUrl("/login")
	        .and()
	    .csrf().disable();
	 }
}
