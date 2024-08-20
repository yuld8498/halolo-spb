package com.cg.api;

import com.cg.domain.dto.userDTO.ChangePwDTO;
import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.entity.JwtResponse;
import com.cg.domain.entity.user.User;
import com.cg.domain.entity.user.UserDetail;
import com.cg.domain.enums.ERole;
import com.cg.exception.DataInputException;
import com.cg.service.jwt.JwtService;
import com.cg.service.user.IUserService;
import com.cg.service.userDetail.IUserDetailService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/auth")
public class AuthAPI {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserDetailService userDetailService;

    @Autowired
    private AppUtils appUtils;


//    @PostMapping("/employees/register")
//    public ResponseEntity<?> registerEmployee(@RequestBody StaffRegisterDTO employeeRegisterDTO) {
//        Optional<User> currentUser = userService.findByEmail(employeeRegisterDTO.getEmail());
//
//        if (currentUser.isPresent()) {
//            throw new DataInputException("Email exists");
//        }
//
//        RoleDTO roleDTO = employeeRegisterDTO.getRole();
//
//        if (!roleDTO.getCode().equals(ERole.ADMIN.getValue())
//                && !roleDTO.getCode().equals(ERole.EMPLOYEE.getValue())
//        ) {
//            throw new DataInputException("Thông tin không hợp lệ. Vui lòng kiểm tra lại!!!");
//        }
//
//        try {
//            userService.registerEmployee(employeeRegisterDTO);
//            return new ResponseEntity<>(HttpStatus.OK);
//
//        } catch (Exception e) {
//            throw new DataInputException(e.getMessage());
//        }
//    }


    @PostMapping("/employees/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByEmail(userDTO.getEmail()).get();

        if(currentUser.getRole().getCode().equals(ERole.CUSTOMER.getValue())) {
            throw new DataInputException("Tài khoản không hợp lệ. Vui lòng kiểm tra lại!!!");
        }

        JwtResponse jwtResponse = new JwtResponse(
                jwt,
                currentUser.getId(),
                userDetails.getUsername(),
                currentUser.getEmail(),
                userDetails.getAuthorities()
        );

        ResponseCookie springCookie = ResponseCookie.from("JWT", jwt)
                .httpOnly(false)
                .secure(false)
                .path("/")
                .maxAge(60 * 60 * 1000)
                .domain("localhost")
                .build();

        System.out.println(jwtResponse);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                .body(jwtResponse);

    }

    @PostMapping("/users/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user1 = userService.findByEmail(user.getEmail()).get();

        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByEmail(user.getEmail()).get();
        UserDetail userDetail = userDetailService.findByUserId(user1.getId());

        if(!currentUser.getRole().getCode().equals(ERole.CUSTOMER.getValue())) {
            throw new DataInputException("Tài khoản không hợp lệ. Vui lòng kiểm tra lại!!!");
        }

        JwtResponse jwtResponse = new JwtResponse(
                jwt,
                currentUser.getId(),
                userDetails.getUsername(),
                currentUser.getEmail(),
                userDetails.getAuthorities(),
                userDetail.getFullName(),
                userDetail.getAvatarFileName(),
                userDetail.getAvatarFileFolder(),
                userDetail.getAvatarFileUrl(),
                userDetail.getGender().getName(),
                userDetail.getDob()
        );

        ResponseCookie springCookie = ResponseCookie.from("JWT", jwt)
                .httpOnly(false)
                .secure(false)
                .path("/")
                .maxAge(60 * 60 * 1000)
                .domain("localhost")
                .build();

        System.out.println(jwtResponse);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                .body(jwtResponse);

    }

    @GetMapping("/users/logout")
    public ResponseEntity<?>logOut(HttpServletRequest request){
        ResponseCookie springCookie = ResponseCookie.from("JWT", "")
                .httpOnly(false)
                .secure(false)
                .path("/")
                .maxAge(-1)
                .domain("localhost")
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                .body(null);
    }

    @GetMapping("")
    public ResponseEntity<?> checkTokenLogin(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassWord(@Validated @RequestBody ChangePwDTO changePwDTO, BindingResult bindingResult){
        if (bindingResult.hasFieldErrors()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
      try {
          User user = userService.findByEmail(appUtils.getPrincipal()).get();
          Authentication authentication = authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(user.getEmail(), changePwDTO.getPassword()));

          SecurityContextHolder.getContext().setAuthentication(authentication);

          if (changePwDTO.getNewPassword().equals(changePwDTO.getConfirmPassword())){
              user.setPassword(changePwDTO.getConfirmPassword());
              userService.save(user);
              return new ResponseEntity<>(HttpStatus.OK);
          }
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }catch (Exception e){
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
}
