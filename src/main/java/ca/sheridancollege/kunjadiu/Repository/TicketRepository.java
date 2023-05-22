package ca.sheridancollege.kunjadiu.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.*;
import ca.sheridancollege.kunjadiu.Beans.Ticket;

@Repository
@AllArgsConstructor

public class TicketRepository {
	
	private NamedParameterJdbcTemplate jdbc;
	
	public void addTicket(Ticket ticket)
	{
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query= "INSERT INTO TICKET (showName,userName,firstName,lastName,email,age,price, quantity) "
				+ "			VALUES(:sName ,:uName, :fName, :lName, :email, :age, :price, :quantity)";
		parameters.addValue("sName", ticket.getShowName() );
		parameters.addValue("uName", ticket.getUserName() );
		parameters.addValue("fName", ticket.getFirstName() );
		parameters.addValue("lName", ticket.getLastName() );
		parameters.addValue("email", ticket.getEmail() );
		parameters.addValue("price", ticket.getPrice() );
		parameters.addValue("age", ticket.getAge() );
		parameters.addValue("quantity", ticket.getQuantity() );
		
		jdbc.update(query,parameters);
	}
	public ArrayList<Ticket> getAllTicket()
	{
		MapSqlParameterSource parameters= new MapSqlParameterSource();
		String query= "SELECT * FROM TICKET";
		ArrayList <Ticket> tickets=(ArrayList<Ticket>) jdbc.query(query, parameters, new BeanPropertyRowMapper<Ticket>(Ticket.class));
		return tickets;
	}
	public ArrayList<Ticket> getTicketByUserName(String currentUser)
	{
		MapSqlParameterSource parameters= new MapSqlParameterSource();
		String query= "SELECT * FROM TICKET where userName=:currentUser";
		parameters.addValue("currentUser", currentUser);
		ArrayList <Ticket> tickets=(ArrayList<Ticket>) jdbc.query(query, parameters, new BeanPropertyRowMapper<Ticket>(Ticket.class));
		return tickets;
	}
	public Ticket getDrinksById(int id)
	{
		ArrayList <Ticket> tickets=new ArrayList<Ticket>();
		
		MapSqlParameterSource parameters= new MapSqlParameterSource();
		String query= "SELECT * FROM ticket WHERE id=:meow";
		
		parameters.addValue("meow", id);
		List <Map<String,Object>> rows = jdbc.queryForList(query,parameters);
		for(Map<String,Object> row : rows)
		{
			Ticket t= new Ticket();
			t.setId((Integer)row.get("id"));
			t.setShowName((String)row.get("showName"));
			t.setFirstName((String)row.get("firstName"));
			t.setLastName((String)row.get("lastName"));
			t.setEmail((String)row.get("email"));
			t.setAge((Integer)row.get("age"));
			
			tickets.add(t); 
		}
		if(tickets.size()>0)
		{
			return tickets.get(0);
		}else{
			return null;
		}
	}
	public void editDrink(Ticket ticket)
	{
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		String query = "UPDATE ticket SET  showName=:sName,firstName=:fName,lastName=:lName ,email=:email, age=:age , price=:price, quantity=:quantity Where id=:id"; 
		parameters.addValue("id",ticket.getId());
		parameters.addValue("sName", ticket.getShowName() );
		parameters.addValue("fName", ticket.getFirstName() );
		parameters.addValue("lName", ticket.getLastName() );
		parameters.addValue("email", ticket.getEmail() );
		parameters.addValue("age", ticket.getAge() );	
		parameters.addValue("price", ticket.getPrice() );
		parameters.addValue("quantity", ticket.getQuantity() );
		jdbc.update(query,parameters);
	}
	public void deleteDrink(Ticket ticket)
	{
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		String query = "Delete from ticket Where id=:id"; 
		
		parameters.addValue("id",ticket.getId());
		jdbc.update(query,parameters);
	}
}
