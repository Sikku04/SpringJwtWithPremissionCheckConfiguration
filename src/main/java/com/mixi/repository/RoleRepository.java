package com.mixi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mixi.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	@Query(value = "SELECT role_name FROM mis.role", nativeQuery = true)
	String[] findAllByRoleName();

	Role findByRoleName(String roleName);

	@Query("Select r.id from Role r where r.roleName=:roleName")
	long findRoleIdByRoleName(@Param("roleName") String roleName);

	@Query(value = "SELECT r.id FROM CMSResponsibility r INNER JOIN r.roles re WHERE re.id =:id")
	List<Long> findRespnsbilitiesByRoleId(@Param("id") Long id);

}
