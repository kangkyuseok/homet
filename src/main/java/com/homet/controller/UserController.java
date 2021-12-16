package com.homet.controller;

import java.util.Random; 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.homet.model.User;
import com.homet.service.UserService;

@Controller
@RequestMapping("/user")
@SessionAttributes(names={"join","user"})
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService service;
	
	@Autowired
	  JavaMailSender sender;
	
	@ModelAttribute(name="join")
	public User setJoin() {
		return new User();
	}
	
	@RequestMapping(value="/join",params="start=1")
	public void join() {
			
			
	}
	
    @PostMapping("/idCheck")
    @ResponseBody
    public int idCheck(@RequestParam("id") String id){
        logger.info("userIdCheck 진입");
        logger.info("전달받은 id:"+id);
        int cnt = service.idCheck(id);
        logger.info("확인 결과:"+cnt);
        return cnt;
    }
    
    @RequestMapping(value="/join",method=RequestMethod.POST)
	public String registration(@ModelAttribute("join")User join,SessionStatus status,Model model) {
		
		service.insert(join);
		logger.info("고객등록 완료 idx =" + join.getUidx());
		status.setComplete();
		return "redirect:../login";
	}
    @RequestMapping(value="update",method=RequestMethod.GET)
	public String update(@SessionAttribute("user") User user){
    	return "/user/update";
	}
    @RequestMapping(value="update",method=RequestMethod.POST	)
  	public String update2(@ModelAttribute User user, Model model){
    	service.update(user);
    	model.addAttribute("user",user);
      	return "redirect:detail";
  	}
    @RequestMapping(value="/detail")
   	public String detail(@SessionAttribute("user") User user){
    	return "/user/detail";
   	}
    @RequestMapping(value="/delete")
   	public String delete(@SessionAttribute("user") User user,SessionStatus status,Model model){
    	service.delete(user.getUidx());
    	status.setComplete();
    	 String message="지금까지 이용해 주셔서 감사합니다.";
			model.addAttribute("message",message );  
			model.addAttribute("url","../login");
			return "alertLogin";
   	}
    @RequestMapping(value="/mypage")
   	public void mypage(@SessionAttribute("user") User user){
    	
   	}
	
    @PostMapping("/CheckMail") // AJAX와 URL을 매핑시켜줌 
    @ResponseBody  //AJAX후 값을 리턴하기위해 작성
    public String SendMail(String email) {
    		Random random=new Random();  //난수 생성을 위한 랜덤 클래스
    		String key="";  //인증번호 
    		System.out.println("이메일 작성");
    		SimpleMailMessage message = new SimpleMailMessage();
    		message.setTo(email); //스크립트에서 보낸 메일을 받을 사용자 이메일 주소 
    		//입력 키를 위한 코드
    		for(int i =0; i<3;i++) {
    			int index=random.nextInt(25)+65; //A~Z까지 랜덤 알파벳 생성
    			key+=(char)index;
    		}
    		int numIndex=random.nextInt(9999)+1000; //4자리 랜덤 정수를 생성
    		key+=numIndex;
    		System.out.println("이메일 전송");
    		message.setSubject("인증번호 입력을 위한 메일 전송");
    		message.setText("인증 번호 : "+key);
    		 
    		sender.send(message);
    		 

    	
    		
            return key;
    	}
     
}