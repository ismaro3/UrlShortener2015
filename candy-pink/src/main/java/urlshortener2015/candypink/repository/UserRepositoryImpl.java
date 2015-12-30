package urlshortener2015.candypink.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
					rs.getBoolean("enabled"), rs.getString("email"), rs.getString("authority"));
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
	public List<User> getAllUsers() {
		try {
			return jdbc.query(
					 "SELECT u.username, u.password, u.enabled, u.email, a.authority"
				       + " FROM USERS u, AUTHORITIES a"
				       + " WHERE u.username=a.username", rowMapper);
		} catch (Exception e) {
			log.debug("When select for all users", e);
			return Collections.emptyList();
		}
	}

	@Override
	public User findByUsernameOrEmail(String id) {
		try {
			return jdbc.queryForObject("SELECT u.username, u.password, u.enabled, u.email, a.authority" 
			                            +" FROM USERS u, AUTHORITIES a "
			                            +"WHERE u.username=? OR u.email=? AND u.username=a.username",
         					    rowMapper, id, id);
		} catch (Exception e) {
			log.debug("When select for id " + id, e);
			return null;
		}
	}

	@Override
	public User save(User user) {
		try {
			log.info("Password " + user.getPassword());
			jdbc.update("INSERT INTO USERS VALUES (?, ?, ?, ?)",
					user.getUsername(), user.getPassword(), user.getEnabled(), user.getEmail());
			jdbc.update("INSERT INTO AUTHORITIES VALUES (?, ?)",
					user.getUsername(), user.getAuthority());
			return user;
		} catch (DuplicateKeyException e) {
			log.info("When insert for user with user " + user.getUsername());
			return null;
		} catch (Exception e) {
			log.info("When insert a user " + e);
			return null;
		}
	}
	
	@Override
	public void update(User user) {
		log.info("Username: "+user.getUsername());
		try {
			jdbc.update("update Users set password=?, email=? WHERE username=?",
				     user.getPassword(), user.getEmail(), user.getUsername());
			jdbc.update("update Authorities set authority=? where userame=?",
				     user.getAuthority(), user.getUsername());
		} catch (Exception e) {
			log.info("When update for user " + user.getUsername(), e);
		}
	}

	@Override
	public void delete(String username) {
		try {
			jdbc.update("delete from Authorities where username=?", username);
			jdbc.update("delete from Users where username=?", username);
		} catch (Exception e) {
			log.debug("When delete for username " + username, e);
		}
	}

	@Override
	public void deleteAll() {
		try {
			jdbc.update("delete from Users");
			jdbc.update("delete from Authorities");
		} catch (Exception e) {
			log.debug("When delete all", e);
		}
	}

	@Override
	public Long count() {
		try {
			return jdbc.queryForObject("select count(*) from Users", Long.class);
		} catch (Exception e) {
			log.debug("When counting", e);
		}
		return -1L;
	}
}
