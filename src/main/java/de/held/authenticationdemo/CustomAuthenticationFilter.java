package de.held.authenticationdemo;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * This is our custom authentication filter that is used in the {@link SecurityConfig}.
 */
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends GenericFilterBean {

	private final InMemoryUserStore userStore;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		/*
		For our example we simply read the user and password information from the header and check if against our
		internal user store.
		IMPORTANT: THIS IS NOT A SECURE WAY TO DO AUTHENTICATION AND JUST FOR DEMONSTRATION PURPOSE!
		 */
		// First read the custom headers
		String user = request.getHeader("x-user");
		String password = request.getHeader("x-password");

		// No we check if they are existent in our internal user store
		Optional<CustomPrincipal> optionalCustomPrincipal = userStore.findByUsernameAndPassword(user, password);

		// When they are present we authenticate the user in the SecurityContextHolder
		if (optionalCustomPrincipal.isPresent()) {
			CustomAuthentication authentication = new CustomAuthentication(optionalCustomPrincipal.get());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		// In either way we continue the filter chain to also apply filters that follow after our own.
		chain.doFilter(request, response);

	}
}
