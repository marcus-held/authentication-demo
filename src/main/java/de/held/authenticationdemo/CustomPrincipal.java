package de.held.authenticationdemo;

import java.security.Principal;
import lombok.ToString;
import lombok.Value;

@Value
@ToString
public class CustomPrincipal implements Principal {

	private String name;

	private String password;

}
