package net.videmantay.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


import net.videmantay.shared.UserRoles;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public SchoolikiUserDetailsService getUserDetailsService(){
		  return new SchoolikiUserDetailsService();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth, SchoolikiUserDetailsService schoolikiUserDetailService) throws Exception {
		auth.userDetailsService(schoolikiUserDetailService);
	}
	
	
	@Bean(name="schoolikiAuthenticationManager")
	public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	
	
		
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  http.authorizeRequests()
	    
	    //.antMatchers("/login").permitAll()
		//.antMatchers("/admin/**").access("hasRole('"+UserRoles.ADMIN.toString()+"')")
		.antMatchers("/teacher/**").access("hasRole('"+UserRoles.TEACHER.toString()+"')")
		.antMatchers("/student/**").access("hasRole('"+UserRoles.STUDENT.toString()+"')")
		.and().logout().logoutUrl("/logout").logoutSuccessUrl("/login").invalidateHttpSession(true)
		.and().headers().frameOptions().contentTypeOptions().disable().authorizeRequests()
		.and().csrf().disable();
	}

}
