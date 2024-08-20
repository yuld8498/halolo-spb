package com.cg.service.friendlist;

import com.cg.domain.entity.user.FriendList;
import com.cg.service.IGeneralService;

public interface IFriendListService extends IGeneralService<FriendList> {
    FriendList findByUserId(String userId);
}
