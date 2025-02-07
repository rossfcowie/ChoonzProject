package com.qa.choonz.rest.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class UserDTOTest {

	@Test
	void testEquals() {
		EqualsVerifier.simple().forClass(UserDTO.class).verify();
	}

	@Test
	void ConstructorTest() {

		UserDTO user = new UserDTO("Cowiejr", "password");
		Assertions.assertEquals(user.toString(), ("UserDTO [id=" + user.getId() + ", username=" + user.getUsername()
				+ ", password=" + user.getPassword() + "]"));

	}
}
