package de.held.authenticationdemo;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

	/**
	 * This is our demo endpoint we want to secure via our custom authentication filter. The respective configuration
	 * is done in {@link SecurityConfig}
	 */
	@GetMapping("secure")
	@ResponseBody
	public String securedEndpoint(Principal principal) {
		return principal.toString();
	}

}
