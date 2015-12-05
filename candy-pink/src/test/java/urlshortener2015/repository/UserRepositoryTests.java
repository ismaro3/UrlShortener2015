package urlshortener2015.candypink.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;
import static urlshortener2015.candypink.repository.fixture.UserFixture.user1;
import static urlshortener2015.candypink.repository.fixture.UserFixture.user1Modified;
import static urlshortener2015.candypink.repository.fixture.UserFixture.user2;
import static urlshortener2015.candypink.repository.fixture.UserFixture.userPassword;
import static urlshortener2015.candypink.repository.fixture.UserFixture.userEmail;
import static urlshortener2015.candypink.repository.fixture.UserFixture.userRole;
import static urlshortener2015.candypink.repository.fixture.UserFixture.userName;
import static urlshortener2015.candypink.repository.fixture.UserFixture.badUser;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import urlshortener2015.candypink.domain.User;
import urlshortener2015.candypink.repository.UserRepository;
import urlshortener2015.candypink.repository.UserRepositoryImpl;

public class UserRepositoryTests {

	private EmbeddedDatabase db;
	private UserRepository repository;
	private JdbcTemplate jdbc;

	@Before
	public void setup() {
		db = new EmbeddedDatabaseBuilder().setType(HSQL)
				.addScript("schema-hsqldb.sql").build();
		jdbc = new JdbcTemplate(db);
		repository = new UserRepositoryImpl(jdbc);
	}

	@Test
	public void thatSavePersistsTheUser() {
		assertNotNull(repository.save(user1()));
		assertSame(jdbc.queryForObject("select count(*) from USER",
				Integer.class), 1);
	}

	@Test
	public void thatSavePassword() {
		assertNotNull(repository.save(userPassword()));
		assertSame(jdbc.queryForObject("select password from USER",
				String.class), userPassword().getPassword());
	}
	
	@Test
	public void thatSaveEmail() {
		assertNotNull(repository.save(userEmail()));
		assertSame(jdbc.queryForObject("select email from USER",
				String.class), userEmail().getEmail());
	}

	@Test
	public void thatSaveRole() {
		assertNotNull(repository.save(userRole()));
		assertSame(jdbc.queryForObject("select rol from USER",
				String.class), userRole().getRole());
	}
	
	@Test
	public void thatSaveName() {
		assertNotNull(repository.save(userName()));
		assertSame(jdbc.queryForObject("select name from USER",
				String.class), userName().getUsername());
	}

	@Test
	public void thatSaveADuplicateUserIsSafelyIgnored() {
		repository.save(user1());
		assertNotNull(repository.save(user1()));
		assertSame(jdbc.queryForObject("select count(*) from USER",
				Integer.class), 1);
	}

	@Test
	public void thatErrorsInSaveReturnsNull() {
		assertNull(repository.save(badUser()));
		assertSame(jdbc.queryForObject("select count(*) from USER",
				Integer.class), 0);
	}

	@Test
	public void thatFindByUsernameOrEmailReturnsAUser() {
		repository.save(userEmail());
		// Test find with an username
		User u = repository.findByUsernameOrEmail(userEmail().getUsername());
		assertNotNull(u);
		assertSame(u.getUsername(),userEmail().getUsername());
		// Test find with an email
		u = repository.findByUsernameOrEmail(userEmail().getEmail());
		assertNotNull(u);
		assertSame(u.getUsername(), userEmail().getEmail());
	}

	@Test
	public void thatFindByKeyReturnsNullWhenFails() {
		repository.save(user1());
		assertNull(repository.findByUsernameOrEmail(user2().getUsername()));
	}
	
	@Test
	public void thatDeleteDelete() {
		repository.save(user1());
		repository.save(user2());
		repository.delete(user1().getUsername());
		assertEquals(repository.count().intValue(), 1);
		repository.delete(user2().getUsername());
		assertEquals(repository.count().intValue(), 0);
	}

	@Test
	public void thatUpdateUpdate() {
		repository.save(user1());
		User u = repository.findByUsernameOrEmail(user1().getName());
		assertEquals(u.getName(), "Name1");
		repository.update(user1Modified());
		u = repository.findByUsernameOrEmail(user1Modified().getName());
		assertEquals(u.getName(), "Name1Update");
	}
	
	@After
	public void shutdown() {
		db.shutdown();
	}

}
