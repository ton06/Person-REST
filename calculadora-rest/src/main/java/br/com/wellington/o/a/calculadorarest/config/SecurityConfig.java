package br.com.wellington.o.a.calculadorarest.config;

import br.com.wellington.o.a.calculadorarest.security.jwt.JwtConfigurer;
import br.com.wellington.o.a.calculadorarest.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.httpBasic().disable().csrf().disable().
				sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
				and().authorizeRequests().antMatchers("/auth/signin", "/api-docs/**", "swgger-ui.html**").
				permitAll().antMatchers("/api/**").authenticated().
				antMatchers("/user").denyAll().and().apply(new JwtConfigurer(tokenProvider));

	}
}
