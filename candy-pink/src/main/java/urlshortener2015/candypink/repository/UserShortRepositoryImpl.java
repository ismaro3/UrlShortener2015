package urlshortener2015.candypink.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

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

import urlshortener2015.candypink.domain.UserShort;
import urlshortener2015.common.domain.ShortURL;

@Repository
public class UserShortRepositoryImpl implements UserShortRepository{

	private static final Logger log = LoggerFactory
			.getLogger(UserRepositoryImpl.class);

	private static final RowMapper<UserShort> rowMapper = new RowMapper<UserShort>() {
		@Override
		public UserShort mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new UserShort(rs.getString("username"), rs.getString("shortURL"));
		}
	};

	private static final RowMapper<ShortURL> rowMapper2 = new RowMapper<ShortURL>() {
		@Override
		public ShortURL mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new ShortURL(rs.getString("hash"), rs.getString("target"),
					null, rs.getString("sponsor"), rs.getDate("created"),
					rs.getString("owner"), rs.getInt("mode"),
					rs.getBoolean("safe"), rs.getString("ip"),
					rs.getString("country"));
		}
	};

	@Autowired
	protected JdbcTemplate jdbc;

	public UserShortRepositoryImpl() {
	}

	public UserShortRepositoryImpl(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public List<ShortURL> findShortURLofUser(String username) {
		try {
			return jdbc.query("SELECT s.* FROM SHORTURL s, USERSHORT u "
					+ "WHERE u.user=? AND s.hash=u.shorturl",
					new Object[]{username}, rowMapper2);
		} catch (Exception e) {
			log.debug("When select for shorturls of user: " +username, e);
			return null;
		}
	}
	
	public List<ShortURL> findShortURLofUser(String username, String dateIni, String dateFin) {
		try {
			return jdbc.query("SELECT s.* FROM SHORTURL s, USERSHORT u "
					+ "WHERE u.user=? AND s.hash=u.shorturl"
					+ "AND s.create>? AND s.create<?",
					new Object[]{username, dateIni, dateFin}, rowMapper2);
		} catch (Exception e) {
			log.debug("When select for shorturls of user: " +username, e);
			return null;
		}	
	}
	
	@Override
	public UserShort save(final UserShort us) {
		try {
			jdbc.update("INSERT INTO USERSHORT VALUES (?, ?)",
					us.getUsername(), us.getShortURL());
		} catch (DuplicateKeyException e) {
			log.debug("When insert for UserShort with username " + us.getUsername(), e);
			return us;
		} catch (Exception e) {
			log.debug("When insert a UserShort", e);
			return null;
		}
		return us;
	}

	@Override
	public Long count() {
		try {
			return jdbc.queryForObject("select count(*) from UserShort", Long.class);
		} catch (Exception e) {
			log.debug("When counting", e);
		}
		return -1L;
	}
}
