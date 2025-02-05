package com.qa.choonz.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.choonz.persistence.domain.User;
import com.qa.choonz.persistence.repository.UserRepository;
import com.qa.choonz.rest.dto.UserDTO;
import com.qa.choonz.utils.TestWatch;
import com.qa.choonz.utils.UserSecurity;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

@SpringBootTest
@ExtendWith(TestWatch.class)
public class UserServiceUnitTest {

	@Autowired
	private UserService service;

	@MockBean
	private UserRepository repo;

	@MockBean
	private UserSecurity security;

	ExtentReports report = TestWatch.report;

	static User validUser;
	static UserDTO validUserDTO;
	static UserDTO createUserDTO;

	@BeforeAll
	static void init() {

		validUser = new User(1, "CowieJr", "password");
		validUserDTO = new UserDTO(1, "CowieJr", validUser.getPassword());
		createUserDTO = new UserDTO(1, "CowieJr", "password");
	}

	@AfterAll
	static void Exit() {
		TestWatch.report.flush();
	}

	@Test
	void createTest() {
		TestWatch.test = report.startTest("Create user test - service unit");
		when(repo.save(Mockito.any(User.class))).thenReturn(validUser);
		try {
			assertThat(validUserDTO).isEqualTo(service.create(createUserDTO));
			TestWatch.test.log(LogStatus.PASS, "Ok");
			report.endTest(TestWatch.test);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			TestWatch.test.log(LogStatus.FAIL, "UserService Error");
			Assertions.fail();
		}
		verify(repo, times(1)).save(Mockito.any(User.class));

	}

	@Test
	void loginTest() {
		TestWatch.test = report.startTest("Login user test - service unit");
		when(repo.findbyName(Mockito.anyString())).thenReturn(Optional.of(validUser));
		when(security.verifyLogin(Mockito.any(User.class), Mockito.anyString())).thenReturn(true);
		assertThat(true).isEqualTo(service.login(createUserDTO));
		verify(repo, times(1)).findbyName(Mockito.anyString());
		verify(security, times(1)).verifyLogin(Mockito.any(User.class), Mockito.anyString());
		TestWatch.test.log(LogStatus.PASS, "Ok");
		report.endTest(TestWatch.test);

	}
}
