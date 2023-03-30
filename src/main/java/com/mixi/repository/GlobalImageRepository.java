package com.mixi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mixi.model.GlobalImage;

@Repository
public interface GlobalImageRepository extends JpaRepository<GlobalImage, Long> {

}
