package com.cg.service.information;

import com.cg.domain.dto.userDTO.InformationDTO;
import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.entity.user.Information;
import com.cg.domain.entity.user.User;
import com.cg.domain.entity.user.UserDetail;
import com.cg.repository.user.InformationRepository;
import com.cg.repository.user.UserDetailRepository;
import com.cg.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Transactional
public class InformationServiceImp implements IInformationService{
    @Autowired
    private InformationRepository informationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailRepository userDetailRepository;
    @Override
    public List<Information> findAll() {
        return informationRepository.findAll();
    }

    @Override
    public Optional<Information> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Information> findById(String id) {
        return informationRepository.findById(id);
    }


    @Override
    public Information save(Information information) {
        return informationRepository.save(information);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void remove(String id) {
        Optional<Information> informationOptional = informationRepository.findById(id);
        if (informationOptional.isPresent()){
            Information information = informationOptional.get();
            information.setDeleted(true);
            informationRepository.save(information);
        }
    }

    @Override
    public Optional<Information> findByUserId(String userId) {
        return informationRepository.findByUserId(userId);
    }

    @Override
    public Optional<Information> findByUserEmail() {
        return informationRepository.findByEmailUser(getPrincipal());
    }

    @Override
    public InformationDTO doCreate(InformationDTO informationDTO) {

        Information information = informationDTO.toInformation();

        information.setId("");

        information.setDeleted(false);

        Information newInformation = informationRepository.save(information);

        return newInformation.toInformationDTO();
    }

    @Override
    public InformationDTO updateInformation(InformationDTO informationDTO) {
        Optional<Information> informationTemp = informationRepository.findByEmailUser(getPrincipal());
        Information information = new Information();

        if(informationTemp.isPresent()){
            information = informationTemp.get();
        }

        User user = userRepository.findByEmail(getPrincipal()).get();
        UserDetail userDetail = userDetailRepository.findByUserId(user.getId());
        if (informationDTO.getFullName()!=null){
            userDetail.setFullName(informationDTO.getFullName());
        }
        Date dobOfUser;
        SimpleDateFormat fomater = new SimpleDateFormat("dd-M-yyyy", Locale.ENGLISH);
        try {
            if(informationDTO.getDob()!=null){
                dobOfUser = fomater.parse(informationDTO.getDob());
                userDetail.setDob(dobOfUser);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        information.setAddress(informationDTO.getAddress());
        information.setJob(informationDTO.getJob());
        information.setNickName(informationDTO.getNickName());
        information.setPhoneNumber(informationDTO.getPhoneNumber());
        information.setUser(user);

        if (informationTemp.isPresent()){
            information.setId(informationTemp.get().getId());
        }

        userDetailRepository.save(userDetail);
        Information newInformation = informationRepository.save(information);
        return newInformation.toInformationDTO();
    }

    @Override
    public InformationDTO getInformationDTOByUserId(String userId) {
        return informationRepository.getInformationByUserId(userId);
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
