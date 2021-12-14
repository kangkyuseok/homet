package com.homet.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.homet.model.PageDto;
import com.homet.model.User;
import com.homet.model.Videos;
import com.homet.service.UserService;
import com.homet.service.VideosService;

@Controller
@SessionAttributes(names="user")
public class VideosController {
	
	private final VideosService service;
	
	public VideosController(VideosService service) {
		this.service = service;
	}
	
	@RequestMapping("/video")
	public String pageList(@RequestParam Map<String, Object> param, Model model) {
		
		int currentPage; //현재 페이지
		List<Videos> list;
		int totalCount; int pageSize=10;
		String page=(String) param.get("page");
		if(page==null || page.trim().length()==0) currentPage = 1;
		else currentPage = Integer.parseInt(page);
		
		PageDto pageDto;
		
		String category = (String) param.get("category");
		
		
		totalCount=service.listCount(param);
		
		pageDto=new PageDto(currentPage, pageSize, totalCount, category);
		list = service.getPageList(pageDto);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("category", category);
		map.put("page", pageDto);
		map.put("list", list);
		model.addAllAttributes(map);
		
		return "video/videolist";
	}
	
	// 상세보기 뷰로 이동
	@RequestMapping("/videodetail")
	public String detail(int vidx, int page, String category, Model model, HttpServletResponse response,
			@CookieValue(name = "read", defaultValue = "abcde") String readvidx) {
		
		if(!readvidx.contains(String.valueOf(vidx))) {
			readvidx += "/" + vidx;
			service.updateReadCnt(vidx);
		}
		
		Cookie cookie = new Cookie("read", readvidx);
		cookie.setMaxAge(30*60);
		cookie.setPath("/videos");
		response.addCookie(cookie);
		
		model.addAttribute("video", service.getOne(vidx));
		model.addAttribute("page", page);
		model.addAttribute("category", category);
		
		return "video/videodetail";
	}
	
	// 글쓰기 뷰로 이동
	@RequestMapping("/videoinsert")
	public String insert(int page, Model model) {
		model.addAttribute("page", page);
		
		return "video/videoregist";
	}
	
	// 글쓰기 -> 저장
	@RequestMapping("/videosave")
	public String save(@ModelAttribute Videos video) {
		service.insert(video);
		
		return "redirect:video";
	}
	
	// 수정 뷰로 이동
	@RequestMapping("/videoupdate")
	public String update(int vidx, int page, String category, Model model) {
		model.addAttribute("video", service.getOne(vidx));
		model.addAttribute("vidx", vidx);
		model.addAttribute("page", page);
		
		return "video/videoupdate";
	}
	
	// 수정 -> 저장
	@RequestMapping(value="/videomodify", method=RequestMethod.POST)
	public String modify(@ModelAttribute Videos video, int vidx, int page, Model model) {
		service.update(video);
		
		model.addAttribute("vidx", video.getVidx());
		model.addAttribute("page", page);
		model.addAttribute("category", video.getCategory());
		
		String url = "redirect:videodetail?&vidx="+vidx+"&page="+page;
		// redirect 할 때 vidx, page 값이 넘어가지 않아서 url에 저장한 주소값을 리턴했습니다.
		
		return url;
	}
	
	// 삭제
	@RequestMapping("/videodelete")
	public String delete(int vidx, int page, String category, Model model) {
		service.delete(vidx);
		
		model.addAttribute("page", page);
		model.addAttribute("category", category);
		return "redirect:video";
	}
	
}
