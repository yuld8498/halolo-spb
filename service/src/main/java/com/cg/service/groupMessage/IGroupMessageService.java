package com.cg.service.groupMessage;

import com.cg.domain.entity.message.GroupMessage;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IGroupMessageService extends IGeneralService<GroupMessage> {
    List<GroupMessage> findAllByGroupId(String groupId);
}
