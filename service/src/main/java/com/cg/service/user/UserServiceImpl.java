package com.cg.service.user;

import com.cg.domain.dto.userDTO.ChangePwDTO;
import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.entity.user.*;
import com.cg.repository.user.FriendListRepository;
import com.cg.repository.user.RoleRepository;
import com.cg.repository.user.UserDetailRepository;
import com.cg.repository.user.UserRepository;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;


@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FriendListRepository friendListRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }


    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

//    @Override
//    public List<UserDTO> getAllUserNonFriend(String id) {
////        return userRepository.getAllUserNonFriend(id);
//        return null;
//    }

    @Override
    public void blockUser(String id) {
        userRepository.blockUser(id);
    }

    @Override
    public void unlockUser(String id) {
        userRepository.unlockUser(id);
    }

    @Override
    public List<UserDTO> findDtoByNameLike(String nameChar) {
        return userRepository.findDtoByNameLike(nameChar);
    }

    @Override
    public UserDTO doCreate(UserDTO userDTO) {
        User user = userDTO.toUser();
        user.setId("");
        UserDetail userDetail = new UserDetail();

        Date dobOfUser;
        SimpleDateFormat fomater = new SimpleDateFormat("dd-M-yyyy", Locale.ENGLISH);
        try {
            dobOfUser = fomater.parse(userDTO.getDob());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Gender gender = new Gender();
        gender.setId(userDTO.getGenderDTO().getId());
        Date thisDate = new Date();
        Long thisDay = thisDate.getTime();
        Long dobUser = dobOfUser.getTime();
        long time = thisDay - dobUser;

        if (Math.ceil(time) < 12) {
            throw new RuntimeException();
        }

        if (userDTO.getGenderDTO().getId().equals(64L)) {
            userDetail.setAvatarFileFolder("gender");
            userDetail.setAvatarFileName("male_nfljey.jpg");
            userDetail.setAvatarFileUrl("https://res.cloudinary.com/dupfcd5kp/image/upload/v1670290825/gender/male_nfljey.jpg");

        }

        if (userDTO.getGenderDTO().getId().equals(83L)) {
            userDetail.setAvatarFileFolder("gender");
            userDetail.setAvatarFileName("female_y8shaz.jpg");
            userDetail.setAvatarFileUrl("https://res.cloudinary.com/dupfcd5kp/image/upload/v1670290825/gender/female_y8shaz.jpg");
        }

        if (userDTO.getGenderDTO().getId().equals(175L)) {
            userDetail.setAvatarFileFolder("gender");
            userDetail.setAvatarFileName("orther_iurlsh.jpg");
            userDetail.setAvatarFileUrl("https://res.cloudinary.com/dupfcd5kp/image/upload/v1670290825/gender/orther_iurlsh.jpg");
        }

        Role role = new Role();
        role.setId(29031999L);

        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = userRepository.save(user);

        userDetail.setUser(newUser);
        userDetail.setFullName(userDTO.getFullName());
        userDetail.setDob(dobOfUser);
//        userDetail.setAvatarFileFolder(userDTO.getAvatarFileFolder());
//        userDetail.setAvatarFileName(userDTO.getAvatarFileName());
        userDetail.setGender(gender);
        userDetailRepository.save(userDetail);

        FriendList friendList = new FriendList();
        friendList.setUser(newUser);
        friendList.setFriends(newUser.getId());
        friendListRepository.save(friendList);

        return newUser.toUserDTO();
    }

    @Override
    public UserDTO updateUser(String userId, UserDTO userDTO) {
        userDTO.setId(userId);
        User user = userRepository.save(userDTO.toUser());
        User newUser = userRepository.save(user);
        return newUser.toUserDTO();
    }

    @Override
    public List<?> findUserByName(String name) {
        User user = userRepository.findByEmail(appUtils.getPrincipal()).get();
        List<?> dtoList = userRepository.findUserDTOByName(user.getId(), name);
        return dtoList;
    }

    @Override
    public List<?> searchUserByName(String userName) {
        return userRepository.searchUserDTOByName(userName);
    }

    @Override
    public void changePassword(ChangePwDTO changePwDTO) {
        User user = userRepository.findByEmail(appUtils.getPrincipal()).get();
        String userPass = passwordEncoder.encode(changePwDTO.getPassword());
        System.out.println(passwordEncoder.matches(user.getPassword(), userPass));

        if (user.getPassword().equals(userPass) && changePwDTO.getNewPassword().equals(changePwDTO.getConfirmPassword())) {
            user.setPassword(passwordEncoder.encode(changePwDTO.getConfirmPassword()));
            userRepository.save(user);
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public UserDTO getUserAndDetailByUserId(String userId) {
        return userRepository.getUserAndDetailByUserId(userId);
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void remove(Long id) {
    }

    @Override
    public void remove(String id) {
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return UserPrinciple.build(user.get());
    }

}
