package com.ckfinder.demo.service.inter;

import com.ckfinder.demo.entity.UserAccountEntity;
import com.ckfinder.demo.dto.request.RegisterRequest;

public interface IUserAccountService {

    UserAccountEntity registryAccount(RegisterRequest request);
    UserAccountEntity findByUsername(String username);
}
