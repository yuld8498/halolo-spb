package com.cg.api;

import com.cg.domain.dto.userDTO.ChangePwDTO;
import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.dto.userDTO.UserDetailDTO;
import com.cg.domain.entity.user.User;
import com.cg.domain.entity.user.UserDetail;
import com.cg.service.user.IUserService;
import com.cg.service.userDetail.IUserDetailService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/users")
public class UserAPI {

    @Autowired
    private IUserService userService;
    @Autowired
    private IUserDetailService userDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){
        UserDTO userDTO = userService.findById(id).get().toUserDTO();

        if (userDTO == null) {
            return new ResponseEntity<>("User ID :" + id + "not found !", HttpStatus.NOT_FOUND);
        }

        userDTO.setPassword("null");
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/user-detail/{userId}")
    public ResponseEntity<?> getUserAndDetailByUserId(@PathVariable String userId){
        UserDTO userDTO = userService.getUserAndDetailByUserId(userId);

        if (userDTO == null) {
            return new ResponseEntity<>("User ID :" + userId + "not found !", HttpStatus.NOT_FOUND);
        }
        userDTO.setPassword("null");
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


    @GetMapping("/profile/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email){
        UserDTO userDTO = userService.findByEmail(email).get().toUserDTO();

        if (userDTO == null){
            return new ResponseEntity<>("User has email: " + email + "not found !", HttpStatus.NOT_FOUND);
        }

        userDTO.setPassword("null");
        return new ResponseEntity<>(userDTO,HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<?> findUserByName(@RequestParam String name){
        try{
            List<?> list = userService.findUserByName(name);
            return new ResponseEntity<>(list,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping ("/search/{userName}")
    public ResponseEntity<?> searchUser(@PathVariable String userName){
        try{
            List<?> users = userService.searchUserByName(userName);
            return new ResponseEntity<>(users,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/find/{nameChar}")
//    public ResponseEntity<?> findUserLike(@PathVariable String nameChar){
//        List<UserDTO> list = userService.findDtoByNameLike(nameChar);
//        List<UserDTO> newlist = new ArrayList<>();
//        int i =0;
//        for (UserDTO userDTO : list){
//            if (list.indexOf(userDTO)>2){
//                return new ResponseEntity<>(newlist,HttpStatus.OK);
//            }
//            newlist.add(userDTO);
//        }
//        return new ResponseEntity<>(list, HttpStatus.OK);
//    }

    @PostMapping("/create")
    public ResponseEntity<?> doCreate(@Validated @RequestBody UserDTO userDTO){
        if (userService.findByEmail(userDTO.getEmail()).isPresent()){
            return new ResponseEntity<>("Email already exists",HttpStatus.NOT_FOUND);
        }
        try {
            UserDTO userDTORes = userService.doCreate(userDTO);
            userDTORes.setPassword("null");

            return new ResponseEntity<>(userDTORes, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Server ko xử lý được", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> doUpdateAccount(@PathVariable String id, @Validated @RequestBody UserDTO userDTO) {
        Optional<User> u = userService.findById(id);

        if (!u.isPresent()) {
            return new ResponseEntity<>("This account is not found!", HttpStatus.NOT_FOUND);
        }

        try {
           UserDTO newDTO= userService.updateUser(id,userDTO);

           newDTO.setPassword("null");
            return new ResponseEntity<>(newDTO, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Server error!!", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/block/{userId}")
        public ResponseEntity<?> doBlock(@PathVariable String userId){
        Optional<User> user = userService.findById(userId);

        if (!user.isPresent()) {
            return new ResponseEntity<>("User id:" + userId + "is not found!", HttpStatus.NOT_FOUND);
        }

        try{
            if(!user.get().isDeleted()) {
                userService. blockUser(userId);
            }
            else {
                userService.unlockUser(userId);
            }

            user = userService.findById(userId);

            UserDTO userDTO = user.get().toUserDTO();
            userDTO.setPassword("null");

            return new ResponseEntity<>(userDTO,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Server không xử lý được", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@Validated @RequestBody ChangePwDTO changePwDTO, BindingResult bindingResult){
        if (bindingResult.hasFieldErrors()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try{
            userService.changePassword(changePwDTO);
            return new ResponseEntity<>( HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

    @GetMapping("/get-principal")
    public ResponseEntity<?> getUser(){
        String username = getPrincipal();

        if (username.equals("")) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        User user = userService.findByEmail(username).get();
        UserDTO userDTO = user.toUserDTO();
        userDTO.setPassword("null");

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
