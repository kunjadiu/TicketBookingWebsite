package ca.sheridancollege.kunjadiu.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
@Component
public class LoginAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		Authentication auth=SecurityContextHolder.getContext()
				.getAuthentication();
		
		if(auth!=null)
		{
			System.out.println(auth.getName()
					+ " Was trying to access resource: " 
					+ request.getRequestURL());
			
		}
		
		// handle "redirect:/accessDenied"
		response.sendRedirect(request.getContextPath()+ "/accessDenied");
	}

}
