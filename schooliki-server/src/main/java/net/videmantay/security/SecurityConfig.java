package net.videmantay.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import net.videmantay.shared.UserRoles;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

		
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  http.authorizeRequests()
	   // .antMatchers("/login").permitAll()
		//.antMatchers("/admin/**").access("hasRole('"+UserRoles.ADMIN.toString()+"')")
		.antMatchers("/teacher/**").access("hasRole('"+UserRoles.TEACHER.toString()+"')")
		.and().logout().logoutUrl("/logout").logoutSuccessUrl("/login").invalidateHttpSession(true);
	}

}
