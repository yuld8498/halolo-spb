package com.cg.service.groupMessage;

import com.cg.domain.entity.message.GroupMessage;
import com.cg.repository.message.GroupMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GroupMessageServiceImp implements IGroupMessageService{
    @Autowired
    private GroupMessageRepository repository;

    @Override
    public List<GroupMessage> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<GroupMessage> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<GroupMessage> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public GroupMessage save(GroupMessage groupMessage) {
        return repository.save(groupMessage);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void remove(String id) {
        Optional<GroupMessage> groupMessageOptional = repository.findById(id);
        if (groupMessageOptional.isPresent()){
            GroupMessage groupMessage = groupMessageOptional.get();
            groupMessage.setDeleted(true);
            repository.save(groupMessage);
        }
    }

    @Override
    public List<GroupMessage> findAllByGroupId(String groupId) {
        return repository.findAllByGroupChatIdOrderByTsAsc(groupId);
    }
}
