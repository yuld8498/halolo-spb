package com.cg.service.groupMember;

import com.cg.domain.entity.message.GroupMember;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IGroupMemberService extends IGeneralService<GroupMember> {
    Iterable<GroupMember> saveAll(List<GroupMember> groupMembers);
}
