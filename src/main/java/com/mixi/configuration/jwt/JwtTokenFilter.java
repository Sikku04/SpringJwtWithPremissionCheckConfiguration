package com.mixi.configuration.jwt;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mixi.service.UserinfoService;

public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtils;

	@Autowired
	private UserinfoService userInfoService;

	private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.err.println("::::::::doFilterInternal:::::");
		try {
			String jwt = parseJwt(request);
			if (jwt != null && jwtUtils.validateToken(jwt)) {
				String username = jwtUtils.extractUsernameFromJwtToken(jwt);
				UserDetails userDetails = userInfoService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			logger.error("Cannot set user authentication: {}", e);
		} catch (Throwable c) {
			c.printStackTrace();
		}
		filterChain.doFilter(request, response);
	}

	private String parseJwt(HttpServletRequest request) {
		try {
			String headerAuth = request.getHeader("Authorization");
			if (headerAuth != null) {
				if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
					return headerAuth.substring(7, headerAuth.length());
				} else {
					return headerAuth;
				}
			} else {
				String token = new String(Base64.getUrlDecoder().decode(request.getParameter("token")));
				if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
					return token.substring(7, token.length());
				} else {
					return token;
				}
			}
		} catch (Exception e) {
			return e.getMessage();
		}
	}

}