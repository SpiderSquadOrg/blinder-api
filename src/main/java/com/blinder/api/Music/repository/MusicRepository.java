package com.blinder.api.Music.repository;

import com.blinder.api.Music.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, String> {
    boolean existsByName(String name);
}
