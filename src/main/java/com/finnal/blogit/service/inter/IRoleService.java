package com.finnal.blogit.service.inter;

import com.finnal.blogit.entity.RoleEntity;

public interface IRoleService {

    RoleEntity findRoleByName(String name);
}
