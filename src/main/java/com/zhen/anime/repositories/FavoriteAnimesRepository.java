package com.zhen.anime.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.zhen.anime.models.Anime;
import com.zhen.anime.models.FavoriteAnimes;
import com.zhen.anime.models.User;

public interface FavoriteAnimesRepository extends CrudRepository<FavoriteAnimes, Long>{
	List<FavoriteAnimes> findByUser(User user);
	FavoriteAnimes findByUserAndAnime(User user, Anime anime);
}
