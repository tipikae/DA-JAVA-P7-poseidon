/**
 * 
 */
package com.nnk.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration for SpringSecurity.
 * @author tipikae
 * @version 1.0
 *
 */
@EnableWebSecurity
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.headers()
			.frameOptions().sameOrigin();
		http.authorizeRequests()
			.antMatchers("/bidList/*", "/curvePoint/*", "/rating/*", "/ruleName/*", "/trade/*", "/error")
			.hasAnyRole("USER", "ADMIN");
		http.authorizeRequests()
			.antMatchers("/admin/home", "/user/*")
			.hasRole("ADMIN");
		http.authorizeRequests()
			.antMatchers("/")
			.permitAll();
		http.authorizeRequests()
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			//.loginPage("/login")
            .successHandler(myAuthenticationSuccessHandler())
            .and()
            .oauth2Login();
		http.exceptionHandling().accessDeniedPage("/error");
	}

	/**
	 * Password encoder bean.
	 * @return PasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public MySimpleUrlAuthSuccessHandler myAuthenticationSuccessHandler() {
		return new MySimpleUrlAuthSuccessHandler();
	}
}
