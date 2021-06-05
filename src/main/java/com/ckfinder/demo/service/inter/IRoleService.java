package com.ckfinder.demo.service.inter;

import com.ckfinder.demo.entity.RoleEntity;

public interface IRoleService {

    RoleEntity findRoleByName(String name);
}
