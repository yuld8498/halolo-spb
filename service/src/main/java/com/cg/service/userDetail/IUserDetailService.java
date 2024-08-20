package com.cg.service.userDetail;

import com.cg.domain.dto.userDTO.UserDetailDTO;
import com.cg.domain.entity.user.UserDetail;
import com.cg.service.IGeneralService;

public interface IUserDetailService extends IGeneralService<UserDetail> {
    UserDetail findByUserId(String userId);

    UserDetailDTO updateUserDetail(UserDetailDTO userDetailDTO);
}
