package urlshortener2015.candypink.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import urlshortener2015.candypink.domain.User;


@Repository
public class UserRepositoryImpl implements UserRepository {

	private static final Logger log = LoggerFactory
			.getLogger(UserRepositoryImpl.class);

	private static final RowMapper<User> rowMapper = new RowMapper<User>() {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new User(rs.getString("username"), rs.getString("password"),
					rs.getString("email"), rs.getString("role"),
					rs.getString("name"));
		}
	};

	@Autowired
	protected JdbcTemplate jdbc;

	public UserRepositoryImpl() {
	}

	public UserRepositoryImpl(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public User findByUsernameOrEmail(String id) {
		try {
			return jdbc.queryForObject("SELECT * FROM user WHERE username=? OR email=?",
					rowMapper, id, id);
		} catch (Exception e) {
			log.debug("When select for id " + id, e);
			return null;
		}
	}

	@Override
	public User save(final User user) {
		try {
			jdbc.update("INSERT INTO USER VALUES (?, ?, ?, ?, ?)",
					user.getUsername(), user.getPassword(), user.getEmail(),
					user.getRole(), user.getName());
		} catch (DuplicateKeyException e) {
			log.debug("When insert for user with user " + user.getUsername(), e);
			return user;
		} catch (Exception e) {
			log.debug("When insert a user", e);
			return null;
		}
		return user;
	}
	
	@Override
	public void update(User user) {
		log.info("Username: "+user.getUsername());
		try {
			jdbc.update("update User set password=?, email=?, role=?, name=? WHERE username=?",
				     user.getPassword(), user.getEmail(), user.getRole(), user.getName(), user.getUsername());
			
		} catch (Exception e) {
			log.info("When update for user " + user.getName(), e);
		}
	}

	@Override
	public void delete(String username) {
		try {
			jdbc.update("delete from User where username=?", username);
		} catch (Exception e) {
			log.debug("When delete for username " + username, e);
		}
	}

	@Override
	public void deleteAll() {
		try {
			jdbc.update("delete from User");
		} catch (Exception e) {
			log.debug("When delete all", e);
		}
	}

	@Override
	public Long count() {
		try {
			return jdbc
					.queryForObject("select count(*) from User", Long.class);
		} catch (Exception e) {
			log.debug("When counting", e);
		}
		return -1L;
	}
}
