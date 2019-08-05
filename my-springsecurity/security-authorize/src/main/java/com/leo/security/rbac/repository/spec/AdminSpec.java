/**
 * 
 */
package com.leo.security.rbac.repository.spec;

import com.leo.security.rbac.domain.Admin;
import com.leo.security.rbac.dto.AdminCondition;
import com.leo.security.rbac.repository.support.ImoocSpecification;
import com.leo.security.rbac.repository.support.QueryWraper;

/**
 * @author zhailiang
 *
 */
public class AdminSpec extends ImoocSpecification<Admin, AdminCondition> {

	public AdminSpec(AdminCondition condition) {
		super(condition);
	}

	@Override
	protected void addCondition(QueryWraper<Admin> queryWraper) {
		addLikeCondition(queryWraper, "username");
	}

}
