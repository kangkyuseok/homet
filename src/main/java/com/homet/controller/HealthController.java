package com.homet.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.homet.model.Health;
import com.homet.service.HealthService;

@Controller
@RequestMapping("/health")
@SessionAttributes("user")
public class HealthController {
	
	@Autowired
	HealthService service;
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public String healthView() {
		return "health/health";
	}
	
	@RequestMapping(value="/healthMap", method = RequestMethod.GET)  //헬스장 위치조회
	public String kakaoMap() {
		return "health/healthMap";
	}
	
	
	@ExceptionHandler(NumberFormatException.class)
	public ModelAndView handleErr(HttpServletRequest request ) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("url", request.getRequestURL());   //애트리뷰트 저장
		mav.setViewName("/error/error");
		return mav;
	}

	@RequestMapping(value = "/healthlist", method = RequestMethod.GET)  //전체 리스트 조회
	public String healthList(Model model) {
		
		model.addAttribute("list", service.healthList() );
		
		return "health/healthlist";
	}
	
	//@RequestMapping(value="view", method = RequestMethod.GET)
	//public String healthView(Model model) {
	//	model.addAttribute("healthView",service.healthOne());
	//	
	//	return "healthView";
	//}
	
	
	// 11 - 18 서치 추가 작업
	@RequestMapping(value="/search", method = RequestMethod.GET)
	public String search(Model model) {
		
//		model.addAttribute("search",service.healthSearch());
		
		return "health/healthView";
		
	}
	
	@RequestMapping(value="/search", method = RequestMethod.POST)
	public String search(String health, Model model) {
		
		model.addAttribute("search",service.healthSearch(health));
		
		return "health/healthView";
		
	}
	/*
	@GetMapping("healthView/{hidx}")
	public String getById(@PathVariable("hidx")int hidx){
	return "healthView";
	}
	*/
}



//@RequestMapping(value="/healthView", method=RequestMethod.POST)
//public String healthViewPOST(Health health) {


//	return "redirect:/healthView";
//}
//
//@PostMapping("")
//public Health post(@RequestBody Health health) {
//	healthMapper.insert(health);
///	return health;					//데이터 입력
//}
//
/*
 * @RequestMapping(value="/healthall", method = RequestMethod.GET) public String
 * healthgetList() { return "healthall"; }
 */

/*   2021 11 17 2:20 pm
	@RequestMapping("/healthall")
	public String healthgetList(Model model){
		List<Health> list = new ArrayList<Health>();
		list = service.healthList();
		list.addAll(list);
		model.addAttribute("list",list);
		System.out.println(list);
		return "healthall";				//전체조회
	}
 */

//@GetMapping("/{hidx}")
//public Health getById(@PathVariable("hidx")int hidx){
//	return healthMapper.getByID(hidx);			// idx 로조회
//}
//
//@RequestMapping(value={"/healthall"})   //(value={"/","/list"})
//public String health(Model model) {

//	List<Health> list = healthMapper.getList();
//	model.addAttribute("list", list);           //전체 리스트조회
//	System.out.println(list);
//	return "/healthall";
//}
//@RequestMapping(value = "/healthall", method = RequestMethod.GET)
//public String healthall() {
// 맵퍼로부터 리스트를 받아옴.
//    List<Health> health = healthMapper.getList();
//   return "/healthall";
//}