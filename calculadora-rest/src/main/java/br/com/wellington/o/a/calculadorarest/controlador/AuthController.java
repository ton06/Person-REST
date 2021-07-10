package br.com.wellington.o.a.calculadorarest.controlador;

import br.com.wellington.o.a.calculadorarest.reposiroty.UserRepository;
import br.com.wellington.o.a.calculadorarest.security.AccountCredentialsVO;
import br.com.wellington.o.a.calculadorarest.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthController {


	AuthenticationManager authenticationManager;

	JwtTokenProvider tokenProvider;

	UserRepository userRepository;

	@Autowired
	public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, UserRepository userRepository) {
		this.authenticationManager = authenticationManager;
		this.tokenProvider = tokenProvider;
		this.userRepository = userRepository;
	}

	@PostMapping(value = "/signin", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"},
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
	public ResponseEntity signin(@RequestBody AccountCredentialsVO data) {

		try {
			var userName = data.getUserName();
			var password = data.getPasswords();

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));

			var user = userRepository.findByUserName(userName);

			var token = "";

			if (user != null) {
				token = tokenProvider.createToken(userName, user.getRoles());
			} else {
				throw new UsernameNotFoundException("usuario " + userName + " não encontrado");
			}

			Map<Object, Object> model = new HashMap<>();
			model.put("userName", userName);
			model.put("token", token);

			return ok(model);
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("usuario ou senha invalidos");
		}
	}
}
