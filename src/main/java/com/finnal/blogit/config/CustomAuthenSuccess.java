package com.finnal.blogit.config;

import com.finnal.blogit.dto.response.CustomerUserDetailDTO;
import com.finnal.blogit.dto.response.UserInforAccountDTO;
import com.finnal.blogit.entity.UserAccountEntity;
import com.finnal.blogit.entity.UserDetailEntity;
import com.finnal.blogit.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@Component
public class CustomAuthenSuccess implements AuthenticationSuccessHandler {

    @Autowired
    private UserAccountRepository repository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {

        UserInforAccountDTO inforDTO = new UserInforAccountDTO();
        Optional<UserAccountEntity> accountEntity = repository.findByEmail(authentication.getName());
        if(accountEntity.isPresent()){
            UserAccountEntity entity = accountEntity.get();
            UserDetailEntity detailEntity = entity.getUserDetailEntity();
            inforDTO.setId(entity.getId());
            inforDTO.setEmail(entity.getEmail());
            inforDTO.setUserDetail(new CustomerUserDetailDTO(detailEntity.getId(), detailEntity.getFirstName(),
                    detailEntity.getLastName(), detailEntity.getThumbnail()));
        }

        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("user", inforDTO);
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/");
    }
}
