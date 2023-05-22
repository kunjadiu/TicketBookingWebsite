package ca.sheridancollege.kunjadiu.Beans;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ticket {
	private int id;
	private String showName;
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private int age;
	private String[] shows= {"Navatri Festival",
							 "Toronto International Snowmobile, ATV & Powersports Show"};
	private double price;
	private int quantity;
	
}
