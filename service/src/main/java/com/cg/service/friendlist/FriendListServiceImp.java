package com.cg.service.friendlist;

import com.cg.domain.entity.user.FriendList;
import com.cg.repository.user.FriendListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FriendListServiceImp implements IFriendListService{
    @Autowired
    private FriendListRepository friendListRepository;

    @Override
    public List<FriendList> findAll() {
        return friendListRepository.findAll();
    }

    @Override
    public Optional<FriendList> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<FriendList> findById(String id) {
        return friendListRepository.findById(id);
    }

    @Override
    public FriendList save(FriendList friendList) {
        return friendListRepository.save(friendList);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void remove(String id) {

    }

    @Override
    public FriendList findByUserId(String userId) {
        return friendListRepository.findByUserId(userId);
    }
}
