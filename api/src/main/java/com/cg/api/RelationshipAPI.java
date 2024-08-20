package com.cg.api;

import com.cg.domain.dto.relationshipDTO.RelationshipDTO;
import com.cg.domain.dto.userDTO.UserDTO;
import com.cg.domain.entity.relationship.Relationship;
import com.cg.domain.entity.user.FriendList;
import com.cg.domain.entity.user.User;
import com.cg.service.friendlist.IFriendListService;
import com.cg.service.relationship.IRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/relationships")

public class RelationshipAPI {
    @Autowired
    private IRelationshipService relationshipService;

    @Autowired
    private IFriendListService friendListService;

    @GetMapping
    public ResponseEntity<?> getAllConnection() {
        try{
            List<Relationship> relationships = relationshipService.findAll();
            List<RelationshipDTO> relationshipDTOS = new ArrayList<>();

            for (Relationship relationship : relationships){
                User sender = relationship.getSender();
                User recipient = relationship.getRecipient();
                sender.setPassword("null");
                recipient.setPassword("null");
                relationship.setSender(sender);
                relationship.setRecipient(recipient);
                relationshipDTOS.add(relationship.toRelationshipDTO());
            }

            return new ResponseEntity<>(relationshipDTOS,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/friendList/{userId}")
    public ResponseEntity<?> getFriendListByUserId(@PathVariable String userId){
        try{
            FriendList friendList = friendListService.findByUserId(userId);
            return new ResponseEntity<>(friendList.getFriends(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/friends/{userId}")
    public ResponseEntity<?> getFriendOfUser(@PathVariable String userId) {
       try {
           List<UserDTO> userDTOS = relationshipService.getFriendOfUser(userId);
           return new ResponseEntity<>(userDTOS, HttpStatus.OK);
       }catch (Exception e){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }

    @GetMapping("/follows/{userId}")
    public ResponseEntity<?> getFollowOfUser(@PathVariable String userId) {
        try{
            List<UserDTO> userDTOS = relationshipService.getFollowOfUser(userId);
            return new ResponseEntity<>(userDTOS,HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/count-friend/{userId}")
    public ResponseEntity<?> countFriendByUserId(@PathVariable String userId){
        return new ResponseEntity<>(relationshipService.countFriendByUserId(userId),HttpStatus.OK);
    }

    @GetMapping("/count-follower/{userId}")
    public ResponseEntity<?> countFollowerByUserId(@PathVariable String userId){
        return new ResponseEntity<>(relationshipService.countFollowerByUserId(userId),HttpStatus.OK);
    }


    @GetMapping("/count-following/{userId}")
    public ResponseEntity<?> countFollowingByUserId(@PathVariable String userId){
        return new ResponseEntity<>(relationshipService.countFollowingByUserId(userId),HttpStatus.OK);
    }

    @GetMapping("/not-friend/{userId}")
    public ResponseEntity<?> getNonFriendOfUser(@PathVariable String userId) {
      try{
          List<UserDTO> userDTOS = relationshipService.getNonFriendOfUser(userId);
          return new ResponseEntity<>(userDTOS,HttpStatus.OK);
      }catch (Exception e){
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }

    @PostMapping("/follow/{userId}/{friendId}")
    public ResponseEntity<?> addFollow(@PathVariable String id, @PathVariable String friendId){
       try {
         relationshipService.addFollow(id,friendId);
           return new ResponseEntity<>("Follow is success", HttpStatus.OK);
       }
       catch (Exception e){
           return new ResponseEntity<>("Server can't handle!", HttpStatus.BAD_REQUEST);
       }
    }

    @PostMapping("/unfollow/{relationshipId}")
    public ResponseEntity<?> UnFollow(@PathVariable String relationshipId){
        try {
            relationshipService.UnFollow(relationshipId);
            return new ResponseEntity<>("Follow is success", HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Server can't handle!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/add-friend/{userId}/{emailFriend}")
    public ResponseEntity<?> addFriend(@PathVariable String userId,@PathVariable String emailFriend){
        try {
            relationshipService.addFriend(userId,emailFriend);
            return new ResponseEntity<>("Add friend is success", HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Server can't handle!",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/unfriend/{relationshipId}")
    public ResponseEntity<?> unfriend(@PathVariable String relationshipId){
        try{
            relationshipService.remove(relationshipId);
            return new ResponseEntity<>("Unfriend is success",HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Server can't handle!", HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/accept/{senderId}/{recipientId}")
    public ResponseEntity<?> doAccept(@PathVariable String senderId,@PathVariable String recipientId) {
        try {
            relationshipService.doAccept( senderId, recipientId);
            return new ResponseEntity<>("Accept is success", HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Server can't handle!",HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("check-friend/{userId}")
    public ResponseEntity<?> checkFriend(@PathVariable String userId){
        try {
            Relationship check = relationshipService.checkFriend(userId);
            if(check !=null){
                User sender = check.getSender();
                User recipient = check.getRecipient();
                sender.setPassword("null");
                recipient.setPassword("null");
                check.setSender(sender);
                check.setRecipient(recipient);
                return new ResponseEntity<>(check.toRelationshipDTO(), HttpStatus.OK);
            }
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>("Server can't handle!",HttpStatus.BAD_REQUEST);
        }
    }

}
