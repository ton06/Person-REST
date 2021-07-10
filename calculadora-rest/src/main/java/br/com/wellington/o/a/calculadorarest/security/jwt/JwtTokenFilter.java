package br.com.wellington.o.a.calculadorarest.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtTokenFilter extends GenericFilterBean {

	private JwtTokenProvider tokenProvider;

	@Autowired
	public JwtTokenFilter(JwtTokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override
	public void doFilter(ServletRequest servletRequest,
						 ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		String token = tokenProvider.resolverToken((HttpServletRequest) servletRequest);
		if (token != null && tokenProvider.validadeToken(token)) {
			Authentication authentication = tokenProvider.getAtAuthentication(token);
			if (authentication != null) {
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}
}
