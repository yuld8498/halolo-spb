package com.cg.service.gender;

import com.cg.domain.entity.user.Gender;
import com.cg.repository.user.GenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GenderServiceImp implements IGenderService {
    @Autowired
    private GenderRepository repository;

    @Override
    public List<Gender> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Gender> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Gender> findById(String id) {
        return Optional.empty();
    }

    @Override
    public Gender save(Gender gender) {
        return repository.save(gender);
    }

    @Override
    public void remove(Long id) {
        Optional<Gender> genderOptional = repository.findById(id);
        if (genderOptional.isPresent()){
            Gender gender = genderOptional.get();
            gender.setDeleted(true);
            repository.save(gender);
        }
    }

    @Override
    public void remove(String id) {

    }
}
