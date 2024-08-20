package com.cg.service;

import com.cg.domain.dto.messageDTO.IMessagesDTO;

import java.util.List;
import java.util.Optional;

public interface IGeneralService<T> {

    List<T> findAll();

    Optional<T> findById(Long id);

    Optional<T> findById(String id);

    T save(T t);

    void remove(Long id);

    void remove(String id);

}
