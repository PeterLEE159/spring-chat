package com.sample.chat.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sample.chat.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	JdbcTemplate template;
	
	@Override
	public List<User> getUsers() {
		return template.query("select * from tb_user", new RowMapper<User>(){
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getString("userid"));
				user.setPwd(rs.getString("userpwd"));
				user.setName(rs.getString("username"));
				return user;
			}
		});
	}
	
	@Override
	public User getUserById(String userid) {
		return template.queryForObject("select * from tb_user where userid = ?", new RowMapper<User>(){
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getString("userid"));
				user.setPwd(rs.getString("userpwd"));
				user.setName(rs.getString("username"));
				return user;
			}
		}, userid);
	}
}
