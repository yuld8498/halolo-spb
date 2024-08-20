package com.cg.service.groupMember;

import com.cg.domain.entity.message.GroupMember;
import com.cg.repository.message.GroupMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GroupMemberServiceImp implements IGroupMemberService{
    @Autowired
    private GroupMemberRepository repository;

    @Override
    public List<GroupMember> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<GroupMember> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<GroupMember> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public GroupMember save(GroupMember groupMember) {
        return repository.save(groupMember);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void remove(String id) {
        Optional<GroupMember> groupMemberOptional = repository.findById(id);
        if (groupMemberOptional.isPresent()){
            GroupMember groupMember = groupMemberOptional.get();
            groupMember.setDeleted(true);
            repository.save(groupMember);
        }
    }

    @Override
    public Iterable<GroupMember> saveAll(List<GroupMember> groupMembers) {
        return repository.saveAll(groupMembers);
    }
}
