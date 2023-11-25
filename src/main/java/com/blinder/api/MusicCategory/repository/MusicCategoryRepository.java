package com.blinder.api.MusicCategory.repository;

import com.blinder.api.MusicCategory.model.MusicCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicCategoryRepository extends JpaRepository<MusicCategory, String> {
}
