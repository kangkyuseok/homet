package com.homet.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.homet.model.Mealkit;
import com.homet.model.Orders;
import com.homet.model.SetMenu;
import com.homet.model.User;
import com.homet.service.MealkitService;

@Controller
@RequestMapping("/mealkit")
public class MealkitController {
	
	@Autowired
	MealkitService service;
	
	//이게 밀키트 메인 페이지
	@RequestMapping(value = {"/","/mealkitHome"}) 
	public String mealkithome(HttpSession session, @SessionAttribute("cart") List<HashMap<String,Object>> cart, 
			@SessionAttribute("cart_set") List<HashMap<String,Object>> cart_set, 
			String idx_list, String price, String type, String name) {
		
		if(type != null && idx_list!= null) {	// 카트에 담고 mealkitHome 으로 올때 동작
				List<Mealkit> list = new ArrayList<Mealkit>();
				HashMap<String, Object> map = new HashMap();
				StringTokenizer token = new StringTokenizer(idx_list, "/");
				while(token.hasMoreTokens()) {
					Mealkit meal = service.getByIdx(Integer.parseInt(token.nextToken()));
					list.add(meal);
				}
				map.put("price", price);
				map.put("list", list);
				if(type.equals("mealkit")) {	// 밀키트 만들기일때
					cart.add(map);
					session.setAttribute("cart", cart);
				}else {	//세트메뉴 일때 이름도 받아서 넘김
					map.put("name", name);
					cart_set.add(map);
					session.setAttribute("cart_set", cart_set);
				}
		}
		
		return "kit/mealkitHome";
	}
	
	//나만의 밀키트 선택 첫페이지
	@RequestMapping(value = "/mealkit", method = RequestMethod.GET)
	public String makeKit(Model model) {
		model.addAttribute("meatList",service.getByCategory("meat"));
		model.addAttribute("saladList",service.getByCategory("salad"));
		model.addAttribute("toppingList",service.getByCategory("topping"));
		model.addAttribute("sauceList",service.getByCategory("sauce"));
		return "kit/mealkit";
	}

	/*
	 * @RequestMapping(value = "/mealkit", params = "idx_list") public String
	 * makeKit2(Model model, @RequestParam String idx_list, HttpServletRequest
	 * request) {
	 * 
	 * HttpSession session = request.getSession();
	 * 
	 * //list에 list를 담음 List<List<Mealkit>> cart = new ArrayList<List<Mealkit>>();
	 * 
	 * List<Mealkit> list = new ArrayList<Mealkit>();
	 * 
	 * StringTokenizer token = new StringTokenizer(idx_list, "/");
	 * while(token.hasMoreTokens()) { Mealkit meal =
	 * service.getByIdx(Integer.parseInt(token.nextToken())); list.add(meal); }
	 * cart.add(list); session.setAttribute("cart", cart);
	 * 
	 * 
	 * model.addAttribute("meatList",service.getByCategory("meat"));
	 * model.addAttribute("saladList",service.getByCategory("salad"));
	 * model.addAttribute("toppingList",service.getByCategory("topping"));
	 * model.addAttribute("sauceList",service.getByCategory("sauce")); return
	 * "kit/mealkit"; }
	 */
	//장바구니로 그냥 가기
	@RequestMapping(value = "/cart")
	public String cart() {
		
		return "kit/cart";
	}
	
	// 장바구니에 담고 장바구니로 이동
	@RequestMapping(value = "/cart", params = "idx_list")
	public String cart2(@RequestParam String idx_list, Model model, HttpServletRequest request,
			@SessionAttribute("cart") List<HashMap<String,Object>> cart,
			@SessionAttribute("cart_set") List<HashMap<String,Object>> cart_set, 
			String price, String type, String name) {
		HttpSession session = request.getSession();
		
		//list에 list를 담음			// 세션에서 장바구니 불러옴
		if(idx_list != null) {
			List<Mealkit> list = new ArrayList<Mealkit>();
			HashMap<String, Object> map = new HashMap();
			StringTokenizer token = new StringTokenizer(idx_list, "/");
			while(token.hasMoreTokens()) {
				Mealkit meal = service.getByIdx(Integer.parseInt(token.nextToken()));
				list.add(meal);
			}
			map.put("price", price);
			map.put("list", list);
			if(type.equals("mealkit")) {	// 밀키트 만들기일때
				cart.add(map);
				session.setAttribute("cart", cart);
			}else {	//세트메뉴 일때 이름도 받아서 넘김
				map.put("name", name);
				cart_set.add(map);
				session.setAttribute("cart_set", cart_set);
			}
		}
		return "kit/cart";
	}
	
	
	//추천 세트 모음 페이지
	@RequestMapping(value = "/mealkitset", method = RequestMethod.GET)
	public String mealkitset(Model model) {
		
		List<SetMenu> setList = new ArrayList<SetMenu>();
		setList = service.getSetByCategory("set");
		model.addAttribute("setList",setList);
		
		return "kit/mealkitset";
	}
	
	
	
	//주문하기
	@RequestMapping(value="/order")
	public String order(Model model, HttpServletRequest request, String type) {
		HttpSession session = request.getSession();		//로그인 한 id의 것만 보여주려고
		User user = (User)session.getAttribute("user");	//세션에서 user 정보 가져오기
		
		if(type != null && type.equals("1")) {	//주문하기 insert 할때
			List<HashMap<String,Object>> cart = (List<HashMap<String,Object>>)session.getAttribute("cart");
			List<HashMap<String,Object>> cart_set = (List<HashMap<String,Object>>)session.getAttribute("cart_set");
			List<String> idx_lists = new ArrayList<String>();
			
			// cart 인서트
			for(HashMap<String, Object> map : cart) {
				String idx_list = "";
				List<Mealkit> list = (List<Mealkit>)map.get("list");	//cart에서 list 꺼내기
				for(Mealkit vo : list) {
					idx_list += "/" + vo.getMidx();
				}
				idx_lists.add(idx_list);
			}
			// cart_set 인서트
			for(HashMap<String, Object> map : cart_set) {
				String idx_list = "";
				List<Mealkit> list = (List<Mealkit>)map.get("list");	//cart에서 list 꺼내기
				for(Mealkit vo : list) {
					idx_list += "/" + vo.getMidx();
				}
				idx_lists.add(idx_list);
			}
			// 인서트 하는곳 // 만든거, 세트메뉴 한번에 인서트
			for(String x : idx_lists) {	
				Orders order = Orders.builder().nickname(user.getNickname()).order_list(x).build();
				service.insertOrder(order); //밀키트 하나씩 insert
			}
			
		}
		// 장바구니 초기화
		List<HashMap<String, Object>> cart = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> cart_set = new ArrayList<HashMap<String, Object>>();
		session.setAttribute("cart", cart);
		session.setAttribute("cart_set", cart_set);
		
		// 주문하고 주문내역 보여주기, 그냥 구매내역 볼때 보여주기
		List<Orders> orderList = service.getOrdersByNickname(user.getNickname());
		model.addAttribute("orderList",orderList);
		System.out.println(orderList);
		return "kit/purchase";
	}
	
	@RequestMapping(value = "/chooseDetail")
	   public String setDetail1(Model model,@RequestParam String idx_list, String price, String name) {
		System.out.println(idx_list);
	      List<Mealkit> meal_list = new ArrayList<Mealkit>();
	      StringTokenizer token = new StringTokenizer(idx_list, "/");
	      
	      while(token.hasMoreTokens()) {
	    	  Mealkit meal = service.getByIdx(Integer.parseInt(token.nextToken()));
	    	  meal_list.add(meal);
	      }
	      
	      model.addAttribute("meal_list",meal_list);
	      model.addAttribute("idx_list",idx_list);
	      model.addAttribute("price",price);
	      model.addAttribute("name",name);
	      return "kit/setdetail";
	   }
	
	//1~5번 추천 세트로 이동
		@RequestMapping(value = "/chooseSet", params = "category")
		public String setList1(Model model, String category) {
			
			List<SetMenu> setList = new ArrayList<SetMenu>();
			
			if(category.equals("1")) {
				setList = service.getSetByCategory("bulk");
			}else if(category.equals("2")) {
				setList = service.getSetByCategory("balance");
			}else if(category.equals("3")) {
				setList = service.getSetByCategory("bodyprofile");
			}else if(category.equals("4")) {
				setList = service.getSetByCategory("diet");
			}else if(category.equals("5")) {
				setList = service.getSetByCategory("salad");
			}
			model.addAttribute("setList",setList);
			return "kit/set"+category;
		}
	
	
}
