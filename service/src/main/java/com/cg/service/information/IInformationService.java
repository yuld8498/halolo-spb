package com.cg.service.information;

import com.cg.domain.dto.userDTO.InformationDTO;
import com.cg.domain.entity.notification.Notification;
import com.cg.domain.entity.user.Information;
import com.cg.service.IGeneralService;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IInformationService extends IGeneralService<Information> {
    Optional<Information> findByUserId(String userId);
    Optional<Information> findByUserEmail();
    InformationDTO doCreate(InformationDTO informationDTO);

    InformationDTO updateInformation(InformationDTO informationDTO);

    InformationDTO getInformationDTOByUserId(String userId);
}
