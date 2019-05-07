package de.held.authenticationdemo;

import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

/**
 * This is a very simple in memory user store for demonstration purpose.
 */
@Service
public class InMemoryUserStore {

	private List<CustomPrincipal> store;

	@PostConstruct
	public void init() {
		store = List.of(new CustomPrincipal("Marcus Held", "SuperSecret"),
				new CustomPrincipal("Bill Gates", "Windows>MacOs"),
				new CustomPrincipal("Foo Bar", "BarFoo")
		);
	}

	public Optional<CustomPrincipal> findByUsernameAndPassword(String username, String password) {
		return store.stream()
				.filter(principal -> principal.getName().equals(username) && principal.getPassword().equals(password))
				.findFirst();
	}

}
