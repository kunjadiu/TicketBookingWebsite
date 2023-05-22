package ca.sheridancollege.kunjadiu.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.kunjadiu.Beans.Ticket;
import ca.sheridancollege.kunjadiu.Beans.User;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor

public class SecurityRepository {
	
	private NamedParameterJdbcTemplate jdbc;
	
	public User findUserFindAccount(String userName)
	{
		String query="SELECT * FROM SEC_USER WHERE USERNAME=:name";
		MapSqlParameterSource parameters= new MapSqlParameterSource();
		parameters.addValue("name", userName);
		
		ArrayList <User> users=
				(ArrayList<User>) jdbc.query(query, parameters, new BeanPropertyRowMapper
				<User>(User.class));
		return (users.size()>0?users.get(0):null);
	}
	public List<String> getRolesById(long userId)
	{
		ArrayList<String> roles=new ArrayList<String >();
		MapSqlParameterSource parameters= new MapSqlParameterSource();
		
		String query="SELECT user_role.userId, sec_role.roleName FROM user_role, "
				+ "sec_role WHERE user_role.roleId=sec_role.roleId and userId=:userId";
		
		parameters.addValue("userId",userId);
		
		List <Map<String,Object>> rows =jdbc.queryForList(query,parameters);
		for (Map<String,Object> row : rows) {
			roles.add((String)row.get("roleName"));
			}
			return roles;
	}
	public ArrayList<User> getAllGuestUser()
	{
		MapSqlParameterSource parameters= new MapSqlParameterSource();
//		String query= "SELECT SEC_USER.userName FROM SEC_USER,USER_role Where user_Role.roleId=1";
		String query="SELECT USERNAME FROM SEC_USER WHERE USERID in(SELECT USERID FROM USER_ROLE WHERE roleId=1) order by username;";
		ArrayList <User> users=(ArrayList<User>) jdbc.query(query, parameters, new BeanPropertyRowMapper<User>(User.class));
		
		return users;
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	public void addUser(String userName,String password){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query="INSERT INTO sec_user "
				+ "(userName,encryptedPassword,ENABLED) VAlUES "
				+ "(:name,:pass,1)";
		parameters.addValue("name",userName);
		parameters.addValue("pass",passwordEncoder().encode(password));
		jdbc.update(query,parameters);
	}
	public void addUserRole(long userId,long roleId)
	{
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query="INSERT INTO user_role "
				+ "(userId,roleId) VAlUES "
				+ "(:uid,:rid)";
		parameters.addValue("uid",userId);
		parameters.addValue("rid",roleId);
		jdbc.update(query,parameters);
	}
}
