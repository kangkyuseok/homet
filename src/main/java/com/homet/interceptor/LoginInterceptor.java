package com.homet.interceptor;

import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.homet.model.User;


public class LoginInterceptor implements HandlerInterceptor {
	//로그인 여부 - 세션 체크 - 검사하여 요청처리하기에 인터셉터 활용
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
	
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			response.sendRedirect(request.getContextPath() +"/login?alert=y");
	// response.sendRedirect("login?alert=y"); //http://localhost:8080/board/community/login?alert=y -> 오류
			return false;		//handler 메소드로 제어(실행)가 이동되지 않습니다.
		}else {
		//로그인 된 상태이므로 요청에 따라 handler 메소드로 이동합니다.
			System.out.println("로그인 확인");
			return true;
		}
	}
}