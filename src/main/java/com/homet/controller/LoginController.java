package com.homet.controller;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession; 

import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.homet.model.Mealkit;
import com.homet.model.User;
import com.homet.service.UserService;

@Controller
@SessionAttributes("user")
public class LoginController {

	private final UserService service;
	
	public LoginController(UserService service) {
		this.service=service;
	}
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(String alert,Model model) {
		if(alert !=null && alert.equals("y")) {
			model.addAttribute("message","로그인이 필요합니다." );  
			model.addAttribute("url","login");
			return "alertLogin";
		}else {
		
			return "login";    // 로그인 버튼 => login.jsp(뷰) -> 로그인정보입력후 버튼(사용자) -> 
		}
	}
	// -> 로그인 정보를 Model 객체로 전달받습니다.
	@RequestMapping(value = "login", method = RequestMethod.POST)
	//메소드 매개변수 (Customer customer 일때, @ModelAttribute("customer") Customer customer 에서 생략된 상태
	public String loginOk(String email, String password ,Model model,HttpSession session) {  //customer 가 모델객체입니다.(로그인정보 저장된상태)
		//로그인 정보 일치하는지 확인.	//홈에서 패스워드 지우는 방법2
		 User result = service.login(User.builder().email(email).password(password).build());
		//customer.setPassword(""); //홈에서 패스워드 지우는 방법1
		if(result != null) {
			//로그인 성공- session에 result 저장합니다.
			model.addAttribute("user", result);//위에서 customer를 세션애튜리뷰트 설정함...
			session.setAttribute("user", result);
			List<HashMap<String,Object>> cart = new ArrayList<HashMap<String,Object>>();// 추가 : 빈 장바구니 생성
			List<HashMap<String,Object>> cart_set = new ArrayList<HashMap<String,Object>>();// 추가 : 빈 장바구니 생성
			session.setAttribute("cart", cart);	// 세션에 장바구니 저장, 장바구니 넣을때마다 갱신하기 위함!
			session.setAttribute("cart_set", cart_set);	// 세션에 장바구니 저장, 장바구니 넣을때마다 갱신하기 위함!
			System.out.println(session.getAttribute("user"));
			System.out.println(model);
			return "home";   //정상 로그인 후 -> home.jsp(뷰)
		}else { 
			//로그인 실패
			String message="로그인 정보가 틀립니다.";
			model.addAttribute("message",message );  
			model.addAttribute("url","login");
			return "alertLogin";
		}
	}
	@RequestMapping(value = "logout")
	//public String logout(HttpSession session) {   
	public String logout(SessionStatus status) {   //@SessionAttributes로 설정된것은 SessionStatus 로 지운다.
		status.setComplete();   //@SessionAttributes 로 설정된 애트리뷰트를 clear 한다.
	//	session.invalidate();
		return "redirect:/";
	}
	  @PostMapping("/klogin")
	   @ResponseBody
	public void test(@RequestParam("name") String name,@RequestParam("email") String email,@RequestParam(
	"gender") String gender, Model model,HttpSession session) {
	 
			System.out.println(name);
	        System.out.println(email);
	        System.out.println(gender);
	        
	        User user= User.builder().name(name).email(email).gender(gender).nickname(name).build();
	      session.setAttribute("user",user);
	       model.addAttribute("user",user);
			System.out.println(user);
			System.out.println(session.getAttribute("user"));
			System.out.println(model);
		
	}
	   @RequestMapping(value="home")
	   	public String home(@SessionAttribute("user") User user, Model model){
	    	model.addAttribute("user",user);
	    	return "home";
	   	}
	   @RequestMapping(value="findId")
	   public String findId(String email) {
		   
		   return "findId";
	   }
	   @RequestMapping(value="findId",method=RequestMethod.POST)
	   public String find(@RequestParam("name") String name,@RequestParam("nickname") String nickname, Model model) {
		   User user = User.builder().name(name).nickname(nickname).build();
		   String message=service.findId(user);
			model.addAttribute("message",message );  
			model.addAttribute("url","login");
			return "alertLogin";
	   }
	   @RequestMapping(value="password" ,method=RequestMethod.POST)
	   public String passwordSave(@RequestParam("email") String email,@RequestParam("password") String password) {
		   User user = User.builder().email(email).password(password).build();
		   service.password(user);
		   return "login";
	   }
	   @RequestMapping(value="password" )
	   public String password() {
		   return "password";
	   }
	
	
//status.setComplete();   - JSESSIONID 는 변하지 않고 @SessionAttributes 로 설정된 애트리뷰트 값을 clear 한다.
//						  - HttpSession의 removeAttribute() 메소드 동작과 유사
//session.invalidate();   - 서버가 JSESSIONID는 새로 부여해주지만 @SesstionAttributes 로 설정된 값은 남아있다.
}