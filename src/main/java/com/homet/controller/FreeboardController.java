package com.homet.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.multipart.MultipartFile;

import com.homet.model.Freeboard;
import com.homet.model.Likes;
import com.homet.model.User;
import com.homet.model.FPageDto;
import com.homet.service.FreeBoardService;
import com.homet.service.LikesService;

@Controller
@SessionAttributes(names = {"user"})
//@RequestMapping("/board")
public class FreeboardController {
	
	@Autowired
	LikesService lservice;
	
	private final FreeBoardService service;
	
	
	
	public FreeboardController(FreeBoardService service) {
		this.service = service;
	}
	
	@RequestMapping(value="/list")    
	public String pageList(@RequestParam Map<String, Object> param,Model model) {
		
		int currentPage;//현재 페이지
		List<Freeboard> list;
		int totalCount; int pageSize=10;
		String page=(String) param.get("page");
		if(page==null || page.trim().length()==0) currentPage = 1;
		else currentPage = Integer.parseInt(page);
		
		FPageDto FpageDto;
		
		String findText = (String) param.get("findText");
		String field=(String) param.get("field");
		
		totalCount=service.searchCount(param);  
		FpageDto=new FPageDto(currentPage, pageSize, totalCount, field, findText);
		list=service.searchList(FpageDto); 
		Map<String,Object> map = new HashMap<String,Object>();    
		map.put("page", FpageDto);		
		map.put("list",list);	
		model.addAllAttributes(map);	
	
		return "board/list";
	
	}
	
	//글 쓰기
	@RequestMapping(value="/write")  
	    public String write(int page, Model model) {
			model.addAttribute("page", page);
	        return "board/write";
	    }
	

	//글 상세보기
	@RequestMapping("/detail")     
		public String detail(int fidx, int page, Model model, @SessionAttribute("user")User user) {
			Freeboard board = null;
			user.getNickname();
			service.like_cnt(fidx);
			System.out.println(fidx);
			model.addAttribute("detail", service.getBoardOne(fidx));
			System.out.println(service.getBoardOne(fidx));
			System.out.println(model);
			model.addAttribute("page",page);
			System.out.println(model);
			board = service.getBoardOne(fidx);
			model.addAttribute("count", lservice.selectByNicknameFidx(Likes.builder().fidx(fidx).nickname(user.getNickname()).build()));
			System.out.println(model);
		return "board/detail";
		}
	
	//글 수정
	@RequestMapping("/update")
		public String update(int fidx, int page, Model model) {
			model.addAttribute("detail", service.getBoardOne(fidx));
			model.addAttribute("fidx", fidx);
			model.addAttribute("page", page);
		return "board/update";
	}
	
	//수정 후 저장
	@RequestMapping(value = "/updatesave", method = RequestMethod.POST)
	public String updatesave(@ModelAttribute Freeboard freeboard, int fidx, int page, Model model ) {
		service.update(freeboard);
		
		model.addAttribute("fidx", fidx);
		model.addAttribute("page", page);
		
		String url = "redirect:detail?&fidx="+fidx+"&page="+page;      //fidx가 넘어가지 않아서 따로 url을 작성했습니다. 
		return url;
		/* return "redirect:detail"; */
	}
	
	//글 삭제
	@RequestMapping("/boarddelete")
	public String boarddelete(int fidx, int page, Model model) {
		service.delete(fidx);
		
		model.addAttribute("page", page);
		return "redirect:list";
	}
	
	//글, 파일저장
	@RequestMapping(value = "/save" ,method = RequestMethod.POST)
	   public String insert(@RequestParam MultipartFile fimage, String subject, String nickname
			   , String content, String hashtag) throws IllegalStateException, IOException {
	      System.out.println(fimage.getOriginalFilename());
	     String random_img = null;
	     if(!fimage.isEmpty())
	    random_img =UUID.randomUUID().toString()+fimage.getOriginalFilename();  
	    
	     Freeboard freeboard = new Freeboard(0, subject, nickname,
	    		  hashtag, content, random_img, null, 0);
	      
	      service.insert(freeboard);
	      
			
			  String path ="C:\\freeboard\\img";
			  File upfile = null; 
			  if(random_img !=null){
				  String img = path+"\\"+random_img;
				  upfile = new File(img);
				  fimage.transferTo(upfile); 
				  }
	 
	      return "redirect:list";
	}
	 //좋아요
	 @PostMapping("/like")
	 @ResponseBody
	 	public int like(@RequestParam("fidx") int fidx, @RequestParam("nickname") String nickname, @SessionAttribute("user")User user) {
		 
		 Likes like = Likes.builder().fidx(fidx).nickname(nickname).build();
		 System.out.println(fidx);
		 System.out.println(nickname);
		 System.out.println(like);

		 int count=0;
		 
		 lservice.insert(Likes.builder().fidx(fidx).nickname(user.getNickname()).build());
		 return count;
	 }
	 		
	//좋아요삭제
		 @PostMapping("/like2")
		 @ResponseBody
		 	public int like2(@RequestParam("fidx") int fidx, @RequestParam("nickname") String nickname, @SessionAttribute("user")User user) {
			 
			 Likes like = Likes.builder().fidx(fidx).nickname(nickname).build();
			 System.out.println(fidx);
			 System.out.println(nickname);
			 System.out.println(like);

			 int count=0;
			 
			 lservice.delete(Likes.builder().fidx(fidx).nickname(user.getNickname()).build());
			 
			 System.out.println(count);
			 return count;
		 }
	 
	}	

