package com.trungbeso.dreamshops.repositories;

import com.trungbeso.dreamshops.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IImageRepository extends JpaRepository<Image, Long> {
}