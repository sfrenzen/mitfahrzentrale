package de.hsba.a16.bi.mitfahrzentrale.user;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	@Mock
	private UserRepository userRepository;


	@InjectMocks
	private UserService userServiceToTest;


	@Before
	public void setUp (){
		User test = new User("test", "testtest");
		Mockito.when(userRepository.findByName(test.getName()))
			.thenReturn(test);
	}

	@Test
	public void whenFindByName_thenReturnUsername() {
		// when
		String name = "test";
		User found = userServiceToTest.getUserByName(name);
		// then
		assertThat(found.getName())
			.isEqualTo(name);
	}
}
