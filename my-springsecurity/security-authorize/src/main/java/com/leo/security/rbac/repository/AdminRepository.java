/**
 * 
 */
package com.leo.security.rbac.repository;

import org.springframework.stereotype.Repository;

import com.leo.security.rbac.domain.Admin;

/**
 * @author zhailiang
 *
 */
@Repository
public interface AdminRepository extends ImoocRepository<Admin> {

	Admin findByUsername(String username);

}
