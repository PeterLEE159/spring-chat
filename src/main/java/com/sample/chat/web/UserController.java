package com.sample.chat.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sample.chat.form.LoginForm;
import com.sample.chat.model.User;
import com.sample.chat.service.UserService;
import com.sample.chat.websocket.ChatHandler;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value="/login.do", method=RequestMethod.GET)
	public String loginform() {
		return "login";
	}
	
	@Autowired
	ChatHandler chatHandler;
	
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public String login(LoginForm loginForm, HttpSession session) {
		
		User user = userService.getUser(loginForm.getUserid());
		if (user == null) {
			return "redirect:/login.do?error";
		}
		if (!user.getPwd().equals(loginForm.getUserpwd())) {
			return "redirect:/login.do?error";
		}
		
		session.setAttribute("LOGIN_USER", user);
		
		return "redirect:/home.do";
	}
	
	@RequestMapping(value="/logout.do")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/home.do";
	}
	
	@RequestMapping(value="/users.do")
	public String users(Model model) {
		model.addAttribute("users", userService.getAllUser());
		return "users";
	}
	
}
