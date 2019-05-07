package de.held.authenticationdemo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.session.ConcurrentSessionFilter;

@EnableWebSecurity
public class SecurityConfig {

	@RequiredArgsConstructor
	@Configuration
	@Slf4j
	public static class UserWebSecurity extends WebSecurityConfigurerAdapter {

		private final InMemoryUserStore userStore;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			/*
			We need to decide where in the filter chain our filter is tested. For our example we still want to support
			 the default session functionality, so we put our own filter after the session filter. That way the
			 authentication by session is done before our own filter.
			 */
			http.addFilterAfter(new CustomAuthenticationFilter(userStore), ConcurrentSessionFilter.class);

			http.requestMatchers()
					.antMatchers("/**");

			http
					.authorizeRequests()
					.anyRequest().authenticated();
		}

	}
}
