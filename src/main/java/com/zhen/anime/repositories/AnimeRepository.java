package com.zhen.anime.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.zhen.anime.models.Anime;

public interface AnimeRepository extends CrudRepository<Anime, Long>{
	Optional<Anime> findById(Long id);
}
