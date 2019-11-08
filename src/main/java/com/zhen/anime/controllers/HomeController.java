package com.zhen.anime.controllers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.zhen.anime.models.Anime;
import com.zhen.anime.models.FavoriteAnimes;
import com.zhen.anime.models.User;
import com.zhen.anime.services.ApiService;

@Controller
public class HomeController {
	@Autowired
	private ApiService service;
	
	@GetMapping("/home")
	public String home(Model model, HttpSession session) {
		if (session.getAttribute("userId") == null) {
			return "redirect:/";
		}
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://api.jikan.moe/v3/top/anime/1";
		LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>> r = restTemplate.getForObject(url, LinkedHashMap.class);
		ArrayList<LinkedHashMap<String, Object>> topAnimes = r.get("top");
		model.addAttribute("topAnimes", topAnimes);
		return "home.jsp";
	}
	
	@GetMapping("/showAnime/{id}")
	public String showAnime(@PathVariable("id") Long id, Model model, HttpSession session) {
		if (session.getAttribute("userId") == null) {
			return "redirect:/";
		}
		Long uid = (Long)session.getAttribute("userId");
		User u = service.findUserbyId(uid);
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://api.jikan.moe/v3/anime/" + id;
		LinkedHashMap<String, Object> anime = restTemplate.getForObject(url, LinkedHashMap.class);
		List<FavoriteAnimes> favorites = service.youFavorites(u);
		boolean added = false;
		for(FavoriteAnimes fa : favorites) {
			if(fa.getAnime().getId().equals(id)) {
				added = true;
			}
		}
		model.addAttribute("added", added);
		model.addAttribute("anime", anime);
		return "showAnime.jsp";
	}
	
	@PostMapping("/add/{id}")
	public String addAnime(@PathVariable("id") Long id, Model model, HttpSession session) {
		if (session.getAttribute("userId") == null) {
			return "redirect:/";
		}
		Long uid = (Long)session.getAttribute("userId");
		User u = service.findUserbyId(uid);
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://api.jikan.moe/v3/anime/" + id;
		LinkedHashMap<String, Object> anime = restTemplate.getForObject(url, LinkedHashMap.class);
		Anime newAnime = new Anime();
		newAnime.setId(Long.valueOf((int)anime.get("mal_id")));
		newAnime.setTitle((String)anime.get("title"));
		newAnime.setImageUrl((String)anime.get("image_url"));
		service.createAnime(newAnime);
		service.addAnime(u, newAnime);
		return "redirect:/showAnime/"+ id;
	}
	@PostMapping("/delete/{id}")
	public String deleteAnime(@PathVariable("id") Long id, Model model, HttpSession session) {
		if (session.getAttribute("userId") == null) {
			return "redirect:/";
		}
		Long uid = (Long)session.getAttribute("userId");
		User u = service.findUserbyId(uid);
		Anime a = service.findAnimebyId(id);
		service.deleteAnime(u, a);
		return "redirect:/showAnime/"+ id;
	}
	
	@GetMapping("/showProfile")
	public String showProfile(Model model, HttpSession session) {
		if (session.getAttribute("userId") == null) {
			return "redirect:/";
		}
		Long uid = (Long)session.getAttribute("userId");
		User u = service.findUserbyId(uid);
		List<FavoriteAnimes> userFavorites = service.youFavorites(u);
		model.addAttribute("userFavorites", userFavorites);
		model.addAttribute("user", u);
		return "profile.jsp";
	}
	
	@PostMapping("/search")
	public String search(@RequestParam("keyword") String keyword, @RequestParam("page") String page, Model model, HttpSession session) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://api.jikan.moe/v3/search/anime?q=" + keyword + "&page=" + page;
		LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>> r = restTemplate.getForObject(url, LinkedHashMap.class);
		ArrayList<LinkedHashMap<String, Object>> animes = r.get("results");
		List<Integer> pages = new ArrayList<>();
		Object totalPages = r.get("last_page");
		int maxPage = (Integer) totalPages;
		int s = Integer.parseInt(page);
		if(maxPage == 1) {
			pages.add(s);
		}else if(maxPage == 2) {
			pages.add(1);
			pages.add(2);
		}else if(maxPage == 3) {
			pages.add(1);
			pages.add(2);
			pages.add(3);
		}else if(maxPage == 4) {
			pages.add(1);
			pages.add(2);
			pages.add(3);
			pages.add(4);
		}else if(s >= maxPage - 5) {
			pages.add(maxPage - 5);
			pages.add(maxPage - 4);
			pages.add(maxPage - 3);
			pages.add(maxPage - 2);
			pages.add(maxPage - 1);
		}else if(s < 4) {
			pages.add(1);
			pages.add(2);
			pages.add(3);
			pages.add(4);
			pages.add(5);
		}else{
			pages.add(s-2);
			pages.add(s-1);
			pages.add(s);
			pages.add(s+1);
			pages.add(s+2);
		}
		model.addAttribute("oldKeyword", keyword);
		model.addAttribute("pages", pages);
		model.addAttribute("page", page);
		model.addAttribute("animes", animes);
		return "result.jsp";
	}
	
	@PostMapping("/previousPage")
	public String previousPage(@RequestParam("page") String page, @RequestParam("oldKeyword") String keyword, Model model, HttpSession session) {
		RestTemplate restTemplate = new RestTemplate();
		int s =  Integer.parseInt(page);
		int newPage = s - 1;
		if(newPage < 1) {
			newPage = 1;
		}
		String url = "https://api.jikan.moe/v3/search/anime?q=" + keyword + "&page=" + newPage;
		LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>> r = restTemplate.getForObject(url, LinkedHashMap.class);
		ArrayList<LinkedHashMap<String, Object>> animes = r.get("results");
		List<Integer> pages = new ArrayList<>();
		Object totalPages = r.get("last_page");
		int maxPage = (Integer) totalPages;
		if(maxPage == 1) {
			pages.add(s);
		}else if(maxPage == 2) {
			pages.add(1);
			pages.add(2);
		}else if(maxPage == 3) {
			pages.add(1);
			pages.add(2);
			pages.add(3);
		}else if(maxPage == 4) {
			pages.add(1);
			pages.add(2);
			pages.add(3);
			pages.add(4);
		}else if(s >= maxPage - 5) {
			pages.add(maxPage - 5);
			pages.add(maxPage - 4);
			pages.add(maxPage - 3);
			pages.add(maxPage - 2);
			pages.add(maxPage - 1);
		}else if(s < 4) {
			pages.add(1);
			pages.add(2);
			pages.add(3);
			pages.add(4);
			pages.add(5);
		}else{
			pages.add(s-2);
			pages.add(s-1);
			pages.add(s);
			pages.add(s+1);
			pages.add(s+2);
		}
		model.addAttribute("pages", pages);
		model.addAttribute("oldKeyword", keyword);
		model.addAttribute("page", newPage);
		model.addAttribute("animes", animes);
		return "result.jsp";
	}

	@PostMapping("/nextPage")
	public String nextPage(@RequestParam("page") String page, @RequestParam("oldKeyword") String keyword, Model model, HttpSession session) {
		RestTemplate restTemplate = new RestTemplate();
		int s =  Integer.parseInt(page);	
		int testPage = s + 1;
		String testUrl = "https://api.jikan.moe/v3/search/anime?q=" + keyword + "&page=" + testPage;
		LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>> test = restTemplate.getForObject(testUrl, LinkedHashMap.class);
		if(test.get("results")==null) {
			s -= 1;
		}
		int newPage = s + 1;
		String url = "https://api.jikan.moe/v3/search/anime?q=" + keyword + "&page=" + newPage;
		LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>> r = restTemplate.getForObject(url, LinkedHashMap.class);
		ArrayList<LinkedHashMap<String, Object>> animes = r.get("results");
		List<Integer> pages = new ArrayList<>();
		Object totalPages = r.get("last_page");
		int maxPage = (Integer) totalPages;		
		if(maxPage == 1) {
			pages.add(s);
		}else if(maxPage == 2) {
			pages.add(1);
			pages.add(2);
		}else if(maxPage == 3) {
			pages.add(1);
			pages.add(2);
			pages.add(3);
		}else if(maxPage == 4) {
			pages.add(1);
			pages.add(2);
			pages.add(3);
			pages.add(4);
		}else if(s >= maxPage - 5) {
			pages.add(maxPage - 5);
			pages.add(maxPage - 4);
			pages.add(maxPage - 3);
			pages.add(maxPage - 2);
			pages.add(maxPage - 1);
		}else if(s < 4) {
			pages.add(1);
			pages.add(2);
			pages.add(3);
			pages.add(4);
			pages.add(5);
		}else{
			pages.add(s-2);
			pages.add(s-1);
			pages.add(s);
			pages.add(s+1);
			pages.add(s+2);
		}
		model.addAttribute("pages", pages);
		model.addAttribute("oldKeyword", keyword);
		model.addAttribute("page", newPage);
		model.addAttribute("animes", animes);
		return "result.jsp";
	}
}
