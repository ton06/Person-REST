package br.com.wellington.o.a.calculadorarest.security.jwt;

import br.com.wellington.o.a.calculadorarest.exception.InvalidJwtAuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {

	@Value("${security.jwt.token.secret-key:secret}")
	private String secreteKey = "secret";

	@Value("${security.jwt.token.expire-lenght:3600000}")
	private long validityInMillisenconds = 3600000;

	@Autowired
	private UserDetailsService userDetailsService;

	@PostConstruct
	public void init() {
		secreteKey = Base64.getEncoder().encodeToString(secreteKey.getBytes());
	}

	public String createToken(String userName, List<String> roles) {
		Claims claims = Jwts.claims().setSubject(userName);
		claims.put("roles", roles);

		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMillisenconds);

		return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity).
				signWith(SignatureAlgorithm.HS256, secreteKey).compact();
	}

	public Authentication getAtAuthentication(String token) {
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUserName(token));
		return new UsernamePasswordAuthenticationToken(
				userDetails, "", userDetails.getAuthorities());
	}

	private String getUserName(String token) {
		return Jwts.parser().setSigningKey(secreteKey).
				parseClaimsJws(token).getBody().getSubject();
	}

	public String resolverToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

	public boolean validadeToken(String token) {
		try {
			Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secreteKey).
					parseClaimsJws(token);

			return !claimsJws.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			throw new InvalidJwtAuthException("Token invalido ou expirado");
		}
	}
}
