package com.cg.service.roomMember;

import com.cg.domain.entity.message.RoomMember;
import com.cg.repository.message.RoomMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class RoomMemberServiceImp implements IRoomMemberService{
    @Autowired
    private RoomMemberRepository repository;
    @Override
    public List<RoomMember> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<RoomMember> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<RoomMember> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public RoomMember save(RoomMember roomMember) {
        return repository.save(roomMember);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void remove(String id) {

    }
}
