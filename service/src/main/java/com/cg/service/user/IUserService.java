package com.cg.service.user;

import com.cg.domain.dto.userDTO.ChangePwDTO;
import com.cg.domain.dto.userDTO.InformationDTO;
import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.entity.user.Information;
import com.cg.domain.entity.user.User;
import com.cg.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;


public interface IUserService extends IGeneralService<User>, UserDetailsService {

    Optional<User> findByEmail(String email);

    void blockUser(String id);
    void unlockUser(String id);

    List<UserDTO> findDtoByNameLike(String nameChar);

    UserDTO doCreate(UserDTO userDTO);

    UserDTO updateUser(String id, UserDTO userDTO);

    List<?> findUserByName(String name);

    List<?> searchUserByName(String userName);

    void changePassword(ChangePwDTO changePwDTO);

    UserDTO getUserAndDetailByUserId(String userId);
}
