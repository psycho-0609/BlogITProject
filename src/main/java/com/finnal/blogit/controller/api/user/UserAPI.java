package com.finnal.blogit.controller.api.user;

import com.finnal.blogit.constant.Constant;
import com.finnal.blogit.dto.request.PersonalInforRequest;
import com.finnal.blogit.dto.response.MessageDTO;
import com.finnal.blogit.dto.response.UserInforAccountDTO;
import com.finnal.blogit.dto.response.UserInforDto;
import com.finnal.blogit.entity.UserDetailEntity;
import com.finnal.blogit.exception.api.APIException;
import com.finnal.blogit.exception.api.ItemCannotEmptyException;
import com.finnal.blogit.exception.api.ItemNotFoundException;
import com.finnal.blogit.service.inter.IUserAccountService;
import com.finnal.blogit.service.inter.IUserDetailService;
import com.finnal.blogit.service.inter.UploadFileService;
import com.finnal.blogit.untils.UploadFileUtils;
import com.finnal.blogit.user.CustomUserDetail;
import com.finnal.blogit.user.UserInfor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/api/user")
public class UserAPI {


    @Autowired
    private IUserDetailService detailService;

    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    @Qualifier("uploadAvatarUser")
    private UploadFileService fileService;

    @Autowired
    private UploadFileUtils fileUtils;


    @PutMapping("/image")
    public ResponseEntity<MessageDTO> updateImage(@RequestParam MultipartFile file, HttpSession session) throws Exception {
        UserInforAccountDTO accountDTO = (UserInforAccountDTO) session.getAttribute(Constant.USER);
        if(file == null || Strings.isEmpty(file.getOriginalFilename())){
            throw new ItemCannotEmptyException("Image cannot empty");
        }
        UserDetailEntity detailEntity = detailService.findByAccountId(accountDTO.getId()).orElseThrow(() -> new ItemNotFoundException("Not found user"));
        String thumbnail = detailEntity.getThumbnail();
        detailEntity.setThumbnail(file.getOriginalFilename());
        detailEntity = detailService.save(detailEntity);
        //set avatar to save to the session
        accountDTO.getUserDetail().setThumbnail(detailEntity.getThumbnail());
        //save to the session
        session.setAttribute(Constant.USER, accountDTO);
        if (thumbnail != null && !Strings.isEmpty(thumbnail)) {
            String deleteDir = Constant.DIR_USER_IMG + detailEntity.getId() + "/" + thumbnail;
            fileUtils.deleteFile(deleteDir);
//            fileService.delete(accountDTO.getUserDetail().getId(), thumbnail);
        }
        try {
            fileUtils.writeOrUpdate(detailEntity.getId(), file.getBytes(), file.getOriginalFilename(), Constant.DIR_USER_IMG);
//            fileService.save(accountDTO.getUserDetail().getId(), file);
        } catch (Exception e) {
            throw new Exception("fail");
        }

        return new ResponseEntity<>(new MessageDTO("Success"), HttpStatus.OK);
    }

    @PutMapping("/personalInfor")
    public ResponseEntity<MessageDTO> updatePersonal(@RequestBody PersonalInforRequest request, HttpSession session) throws APIException {
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        validateData(request);
        if (userDetail == null) {
            throw new ItemNotFoundException("User not found");
        }
        UserDetailEntity detailEntity = detailService.findByAccountId(userDetail.getId()).orElseThrow(() -> new ItemNotFoundException("User not found"));
        detailEntity.setLastName(request.getLastName());
        detailEntity.setFirstName(request.getFirstName());
        detailEntity.setDob(request.getDob());
        detailEntity.setPhone(request.getPhone());
        detailEntity = detailService.save(detailEntity);
        UserInforAccountDTO accountDTO = (UserInforAccountDTO) session.getAttribute(Constant.USER);
        accountDTO.getUserDetail().setLastName(detailEntity.getLastName());
        accountDTO.getUserDetail().setFirstName(detailEntity.getFirstName());
        session.setAttribute(Constant.USER,accountDTO);
        return new ResponseEntity<>(new MessageDTO("Success"),HttpStatus.OK);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<UserInforDto> findUserById(@PathVariable("id") Long id) throws APIException{
        UserInforDto userInforDto = userAccountService.findUserById(id).orElseThrow(()-> new ItemNotFoundException("User is not found"));
        return new ResponseEntity<>(userInforDto, HttpStatus.OK);
    }

    private void validateData(PersonalInforRequest request) throws APIException {
        if (request.getFirstName() == null || Strings.isEmpty(request.getFirstName())) {
            throw new ItemCannotEmptyException("Please input complete your information");
        }
        if (request.getLastName() == null || Strings.isEmpty(request.getLastName())) {
            throw new ItemCannotEmptyException("Please input complete your information");
        }

        if (request.getDob() == null || request.getDob().equals("")) {
            throw new ItemCannotEmptyException("Please input complete your information");
        }

        if (request.getPhone() == null || Strings.isEmpty(request.getPhone())) {
            try {
                Integer.parseInt(request.getPhone());
            } catch (Exception e) {
                throw new ItemCannotEmptyException("Please input complete your information");
            }
        }

    }

}
