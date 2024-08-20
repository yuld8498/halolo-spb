package com.cg.service.userDetail;

import com.cg.domain.dto.userDTO.UserDetailDTO;
import com.cg.domain.entity.user.Information;
import com.cg.domain.entity.user.User;
import com.cg.domain.entity.user.UserDetail;
import com.cg.repository.user.UserDetailRepository;
import com.cg.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserDetailServiceImp implements IUserDetailService{
    @Autowired
    private UserDetailRepository userDetailRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDetail> findAll() {
        return userDetailRepository.findAll();
    }

    @Override
    public Optional<UserDetail> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDetail> findById(String id) {
        return userDetailRepository.findById(id);
    }

    @Override
    public UserDetail save(UserDetail userDetail) {
        return userDetailRepository.save(userDetail);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void remove(String id) {

    }

    @Override
    public UserDetail findByUserId(String userId) {
        return userDetailRepository.findByUserId(userId);
    }

    @Override
    public UserDetailDTO updateUserDetail(UserDetailDTO userDetailDTO) {
        User user = userRepository.findByEmail(getPrincipal()).get();
        UserDetail userDetailTemp = userDetailRepository.findByUserId(user.getId());
        userDetailDTO.setId(userDetailTemp.getId());
        userDetailDTO.setAvatarFileFolder(userDetailTemp.getAvatarFileFolder());
        userDetailDTO.setAvatarFileName(userDetailTemp.getAvatarFileName());
        userDetailDTO.setAvatarFileUrl(userDetailTemp.getAvatarFileUrl());
        userDetailDTO.setGenderDTO(userDetailTemp.getGender().toGenderDTO());
        userDetailDTO.setUserDTO(userDetailTemp.getUser().toUserDTO());
        UserDetail userDetail = userDetailRepository.save(userDetailDTO.toUserDetail());
        UserDetail newUserDetail = userDetailRepository.save(userDetail);
        return newUserDetail.toUserDetailDTO();

    }
    private String getPrincipal() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = "";
        }

        return username;
    }
}
