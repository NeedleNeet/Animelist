package com.zhen.anime.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhen.anime.models.Anime;
import com.zhen.anime.models.FavoriteAnimes;
import com.zhen.anime.models.User;
import com.zhen.anime.repositories.AnimeRepository;
import com.zhen.anime.repositories.FavoriteAnimesRepository;
import com.zhen.anime.repositories.UserRepository;

@Service
public class ApiService {
	@Autowired
	private AnimeRepository aR;
	@Autowired
	private FavoriteAnimesRepository faR;
	@Autowired
	private UserRepository uR;
	
	public void createAnime(Anime a) {
		aR.save(a);
	}
	
	public void addAnime(User u, Anime a) {
		FavoriteAnimes fa = new FavoriteAnimes();
		fa.setAnime(a);
		fa.setUser(u);
		faR.save(fa);
	}
	
	public User findUserbyId(Long id) {
		Optional<User> o = uR.findById(id);
		if(o.isPresent()) {
			return o.get();
		}
		return null;
	}
	
	public List<FavoriteAnimes> youFavorites(User user){
		List<FavoriteAnimes> fa = faR.findByUser(user);
		return fa;
	}
	
	public Anime findAnimebyId(Long id) {
		Optional<Anime> o = aR.findById(id);
		if(o.isPresent()) {
			return o.get();
		}
		return null;
	}
	
	public void deleteAnime(User u, Anime a) {
		FavoriteAnimes fa = faR.findByUserAndAnime(u, a);
		faR.delete(fa);
	}
}
