package urlshortener2015.common.repository;

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
			return new Click(rs.getString("name"), rs.getString("type"));
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
	public User findByName(String name) {
		try {
			return jdbc.query("SELECT * FROM user WHERE name=?",
					new Object[] { name }, rowMapper);
		} catch (Exception e) {
			log.debug("When select for name " + name, e);
			return null;
		}
	}

	@Override
	public User save(final User user) {
		try {
			KeyHolder holder = new GeneratedKeyHolder();
			jdbc.update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement ps = conn
							.prepareStatement(
									"INSERT INTO USER VALUES (?, ?)",
									Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, user.getName());
					ps.setString(2, user.getType());
				}
			}, holder);
			new DirectFieldAccessor(user).setPropertyValue("id", holder.getKey()
					.longValue());
		} catch (DuplicateKeyException e) {
			log.debug("When insert for user with user " + user.getName(), e);
			return user;
		} catch (Exception e) {
			log.debug("When insert a user", e);
			return null;
		}
		return user;
	}
	
	@Override
	public void update(User user) {
		log.info("Name: "+user.getName()+" - Type: "+ user.getType());
		try {
			jdbc.update("update User set name=?, type=?",
				     user.getName(), user.getType());
			
		} catch (Exception e) {
			log.info("When update for user " + user.getName(), e);
		}
	}

	@Override
	public void delete(String name) {
		try {
			jdbc.update("delete from User where id=?", name);
		} catch (Exception e) {
			log.debug("When delete for name " + name, e);
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
