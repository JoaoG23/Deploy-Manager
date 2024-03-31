package com.deploy.manager.infra.SecurityFilter;


import com.deploy.manager.entities.users.repository.UserRepository;
import com.deploy.manager.infra.TokenServices.TokenServices;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter { // Realizar um filtragem por request

	@Autowired
	private TokenServices tokenServices;
	@Autowired
	private UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		var tokenFound = getTokenRequest(request); // Realizar busca

		if (tokenFound != null) {
			var subject = tokenServices.getSubject(tokenFound); // Verifica token valido
			var user = userRepository.findByUsername(subject);
			var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()); // Busca autenticação e destribui na aplicação

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response); // Realiza a validação de todos os items da aplicação
	}

	public String getTokenRequest(HttpServletRequest request) {
		var authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null) {
			return authorizationHeader.replace("Bearer ", "");
		}
		return null;
	}
}
