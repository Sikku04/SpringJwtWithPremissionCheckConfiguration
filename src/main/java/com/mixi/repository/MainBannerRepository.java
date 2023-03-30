package com.mixi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mixi.model.MainBanner;

@Repository
public interface MainBannerRepository extends JpaRepository<MainBanner, Long> {

	boolean existsByBannerName(String bannerName);

}
