package com.cg.service.groupChat;

import com.cg.domain.dto.messageDTO.IGroupMessages;
import com.cg.domain.entity.message.GroupChat;
import com.cg.domain.entity.message.GroupMember;
import com.cg.repository.message.GroupChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GroupChatServiceImp implements IGroupChatService{
    @Autowired
    private GroupChatRepository repository;
    @Override
    public List<GroupChat> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<GroupChat> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<GroupChat> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public GroupChat save(GroupChat groupChat) {
        return repository.save(groupChat);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void remove(String id) {
        Optional<GroupChat> groupChatOptional = repository.findById(id);
        if (groupChatOptional.isPresent()){
            GroupChat groupChat = groupChatOptional.get();
            groupChat.setDeleted(true);
            repository.save(groupChat);
        }
    }

    @Override
    public Optional<GroupChat> findByGroupMember(List<GroupMember> list, String groupName) {
        return repository.getGroupChatByGroupMembers(list,groupName);
    }

    @Override
    public GroupChat findGroupChatByListUser(String listUser, int count) {
        return repository.existsGroupChatByListUser(listUser,count);
    }
}
