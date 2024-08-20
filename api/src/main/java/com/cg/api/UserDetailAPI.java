package com.cg.api;

import com.cg.domain.dto.userDTO.UserDetailDTO;
import com.cg.service.userDetail.IUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-detail")
public class UserDetailAPI {
    @Autowired
    private IUserDetailService userDetailService;


    @PutMapping("/update")
    public ResponseEntity<?> updateUserDetail(@RequestBody UserDetailDTO userDetailDTO){

        UserDetailDTO newUserDetail = userDetailService.updateUserDetail(userDetailDTO);

        if (newUserDetail == null) {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>( HttpStatus.OK);
    }
}
