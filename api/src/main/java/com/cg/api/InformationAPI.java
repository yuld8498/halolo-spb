package com.cg.api;

import com.cg.domain.dto.postDTO.PostDTO;
import com.cg.domain.dto.userDTO.InformationDTO;
import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.entity.user.Information;
import com.cg.domain.entity.user.UserDetail;
import com.cg.service.information.IInformationService;
import com.cg.service.userDetail.IUserDetailService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/information")
public class InformationAPI {
    @Autowired
    private IInformationService informationService;
    @Autowired
    private IUserDetailService userDetailService;

    @Autowired
    private AppUtils appUtils;

    @GetMapping()
    public ResponseEntity<?> getInformationByEmailUser(){
        Optional <Information> information = informationService.findByUserEmail();

        if (!information.isPresent()) {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }

        InformationDTO informationDTO = information.get().toInformationDTO();
        UserDTO userDTO =informationDTO.getUserDTO();
        if (userDTO != null){
            userDTO.setPassword("null");
        }
        informationDTO.setUserDTO(userDTO);

        return new ResponseEntity<>(informationDTO, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getInformationByUserId(@PathVariable String userId){
        InformationDTO information = informationService.getInformationDTOByUserId(userId);

        if (information == null) {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
        UserDTO userDTO = information.getUserDTO();
        if (userDTO != null){
            userDTO.setPassword("null");
            information.setUserDTO(userDTO);
        }
        return new ResponseEntity<>(information, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> doCreate(@RequestBody InformationDTO informationDTO){

        try {
            InformationDTO informationDTORes = informationService.doCreate(informationDTO);
            UserDTO userDTO = informationDTORes.getUserDTO();
            if (userDTO != null){
                userDTO.setPassword("null");
                informationDTORes.setUserDTO(userDTO);
            }

            return new ResponseEntity<>(informationDTORes, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>("Server ko xử lý được", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PutMapping("/update")
    public ResponseEntity<?> doUpdateInformation(@Validated @RequestBody InformationDTO informationDTO,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        try {

            InformationDTO newInformationDTO = informationService.updateInformation(informationDTO);
            String userId = newInformationDTO.getUserDTO().getId();
            String fullName = newInformationDTO.getUserDTO().getFullName();

            for (PostDTO postDTO : PostAPI.listAllPosts){
                if (postDTO.getUserId().equals(userId)){
                    postDTO.setFullName(fullName);
                }
            }
            UserDTO userDTO = newInformationDTO.getUserDTO();
            if (userDTO != null){
                userDTO.setPassword("null");
                newInformationDTO.setUserDTO(userDTO);
            }
            return new ResponseEntity<>(newInformationDTO, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Server error!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
