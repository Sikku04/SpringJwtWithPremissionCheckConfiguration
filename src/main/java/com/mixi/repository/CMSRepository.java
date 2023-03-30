package com.mixi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mixi.model.CMSResponsibility;

@Repository
public interface CMSRepository extends JpaRepository<CMSResponsibility, Long> {

	boolean existsByRespName(String respName);

}
