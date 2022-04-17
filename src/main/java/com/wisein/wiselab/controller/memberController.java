package com.wisein.wiselab.controller;

import com.wisein.wiselab.dto.MemberDTO;
import com.wisein.wiselab.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@Controller
public class memberController {

	@Autowired
	MemberService service;

	@Autowired
	BCryptPasswordEncoder passEncoder;

	@GetMapping(value="/register")
	public String getRegister () throws Exception {
		return "reg";
	}

	@PostMapping(value = "/register")
	public String postRegister (MemberDTO dto) throws Exception {

		// Password Encoding by BCryptPasswordEncoder
		String inputPw = dto.getPw();
		String passEncd = passEncoder.encode(inputPw);
		dto.setPw(passEncd);

		service.register(dto);

		return "redirect:/register";
	}

	@GetMapping(value="/login")
	public String getLogin () throws Exception {
		return "login";
	}

	@PostMapping(value = "/login")
	public String postLogin(MemberDTO dto, HttpServletRequest req, RedirectAttributes rttr) throws Exception {

		HttpSession session= req.getSession();
		MemberDTO login = service.login(dto);
		String lgFailMessage;

		if (login != null) {
			boolean passMat = passEncoder.matches(dto.getPw(), login.getPw());
			if(passMat) {
				session.setAttribute("member", login);
			} else {
				session.setAttribute("member", null);
				lgFailMessage ="로그인에 실패했습니다.";
				rttr.addFlashAttribute("msg", lgFailMessage);

				return "redirect:/login";
			}
		} else {
			lgFailMessage ="로그인에 실패했습니다.";
			rttr.addFlashAttribute("msg", lgFailMessage);

			return "redirect:/login";
		}

		return "redirect:/login";
	}

	@GetMapping(value = "/user/logout")
	public String logout(HttpSession session) throws Exception {
		service.logout(session);
		return "redirect:/login";
	}


	@GetMapping(value = "/user/update")
	public String getModifyUser() throws Exception {
		return "upd";
	}

	@PostMapping(value = "/user/update")
	public String postModifyUser(MemberDTO dto) throws Exception {

		String inputPw = dto.getPw();
		String passEncd = passEncoder.encode(inputPw);
		dto.setPw(passEncd);

		service.modify(dto);

		return "redirect:/login";
	}

	@GetMapping(value = "/user/withdraw")
	public String getWithdrawalUser() throws Exception {
		return "withdrawal";
	}

	@PostMapping(value = "/user/withdraw")
	public String withdrawal(MemberDTO dto, HttpSession session) throws Exception {
		service.withdraw(dto, session);
		return "redirect:/login";
	}

}