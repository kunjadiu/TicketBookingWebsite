package ca.sheridancollege.kunjadiu.Controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.kunjadiu.Beans.Ticket;
import ca.sheridancollege.kunjadiu.Beans.User;
import ca.sheridancollege.kunjadiu.Repository.SecurityRepository;
import ca.sheridancollege.kunjadiu.Repository.TicketRepository;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class TicketController {
		
	private TicketRepository ticketRepo;
	private SecurityRepository secuRepo;
	
	
	@GetMapping("/")
	public String goRoot()
	{
		return "root";
	}
	@GetMapping("/add")
	public String goAdd(Authentication  authentication,Model model)
	{
//		String username=authentication.getName();
//		
//		ArrayList <String> roles =new ArrayList<String>();
//		
//		for (GrantedAuthority ga:authentication.getAuthorities())
//		{
//			roles.add(ga.getAuthority());
//		}
		ArrayList <User> users=secuRepo.getAllGuestUser();
		model.addAttribute("users",users );
		model.addAttribute("ticket", new Ticket());
		return "add.html";
	}
	@PostMapping("/processAdd")
	public String goAddData(@ModelAttribute Ticket ticket,Model model)
	{
		ticketRepo.addTicket(ticket);
		return "redirect:/add";
	}
	@GetMapping("/view")
	public String goView(Authentication  authentication,Model model)
	{
		ArrayList <Ticket> tickets = null;
		String currentUser=authentication.getName();
		ArrayList <String> roles =new ArrayList<String>();
		for (GrantedAuthority ga:authentication.getAuthorities())
		{
			roles.add(ga.getAuthority());
		}
		if(roles.contains("ROLE_GUEST"))
		{
			tickets=ticketRepo.getTicketByUserName(currentUser);
			double subTotal=0;
			for(int i=0;i<tickets.size();i++)
			{
				subTotal+=tickets.get(i).getQuantity() * tickets.get(i).getPrice();
			}
			double tax=(subTotal*13)/100;
			double total=subTotal+tax;
			model.addAttribute("ticket",tickets );
			model.addAttribute("subTotal", subTotal);
			model.addAttribute("tax", tax);
			model.addAttribute("total", total);
		}
		else
		{
			tickets=ticketRepo.getAllTicket();
			model.addAttribute("ticket",tickets );
		}
		
		return "view.html";
	}
	@GetMapping("/edit/{id}")
	public String editDrink(@PathVariable int id,Model model)
	{
		// GET the particular drink based on the id
		
		Ticket ticket=ticketRepo.getDrinksById(id);
		ArrayList <User> users=secuRepo.getAllGuestUser();
		model.addAttribute("users",users );
		// send the data to edit page
		model.addAttribute("ticket", ticket);
		return "edit.html";
	}
	@PostMapping("/editTicket")
	public String processEdit(@ModelAttribute Ticket ticket)
	{
		Ticket t=ticket;
		
		// update the drink in my database
		ticketRepo.editDrink(t);
		
		//go back to another page
		return "redirect:/view";
	}
	@GetMapping("/delete/{id}")
	public String DeleteDrink(@PathVariable int id,Model model)
	{
		// GET the particular drink based on the id
		
		Ticket drink=ticketRepo.getDrinksById(id);
		
		// send the data to edit page
		model.addAttribute("drink", drink);
		Ticket d=drink;
		
		// update the drink in my database
		ticketRepo.deleteDrink(d);
		
		return "redirect:/view";
	}
	@GetMapping("/Login")
	public String goLogin()
	{
		return "login.html";
	}
	@GetMapping("/accessDenied")
	public String goaccessDenied()
	{
		return "AccessDenied.html";
	}
	@Autowired
	@Lazy
	public SecurityRepository secRepo;
	
	@GetMapping("/register")
	public String goRegister()
	{
		return "registration.html";
	}
	@PostMapping("/register")
	public String processRegister(@RequestParam String username,
										@RequestParam String password)
	{
		secRepo.addUser(username, password);
		
		long uid =secRepo.findUserFindAccount(username).getUserId();
		
		secRepo.addUserRole(uid, 1);
		return "redirect:/";
	}
}