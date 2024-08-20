package com.cg.service.roleMember;

import com.cg.domain.entity.message.RoleMember;
import com.cg.repository.message.RoleMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleMemberServiceImp implements IRoleMemberService{
    @Autowired
    private RoleMemberRepository repository;
    @Override
    public List<RoleMember> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<RoleMember> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<RoleMember> findById(String id) {
        return Optional.empty();
    }

    @Override
    public RoleMember save(RoleMember roleMember) {
        return repository.save(roleMember);
    }

    @Override
    public void remove(Long id) {
        Optional<RoleMember> role = repository.findById(id);
        if (role.isPresent()){
            RoleMember roleMember = role.get();
            roleMember.setDeleted(true);
            repository.save(roleMember);
        }
    }

    @Override
    public void remove(String id) {

    }
}
