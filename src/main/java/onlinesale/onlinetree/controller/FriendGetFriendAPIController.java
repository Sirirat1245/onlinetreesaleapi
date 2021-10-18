package onlinesale.onlinetree.controller;

import onlinesale.onlinetree.model.service.DiscountForFriendRepository;
import onlinesale.onlinetree.model.service.FriendGetFriendRepository;
import onlinesale.onlinetree.model.table.DealSale;
import onlinesale.onlinetree.model.table.DiscountForFriend;
import onlinesale.onlinetree.model.table.FriendGetFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/friend_get_friend")
public class FriendGetFriendAPIController {

    @Autowired
    private FriendGetFriendRepository friendGetFriendRepository;

    @Autowired
    private DiscountForFriendRepository discountForFriendRepository;

    //check ui ถ้ามีการกรอกโค้ด ให้มายิงที่ api นี้ด้วย
    //สร้างตอนที่ ผู้ถูกชวน active ที่ T.profileRegister
    //ส่ง status from T.profileRegister มาเพื่อเช็คว่า profile คนนั้น active ยัง
    //ถ้า statusFriend = 0 ถึงจะ save ได้
    @PostMapping("/save")
    public Object create(FriendGetFriend friendGetFriend, boolean status, int statusFriend){
        APIResponse res = new APIResponse();
        try {
            if(status == true && statusFriend != 1 ){
                System.out.println("status = true");
                FriendGetFriend checkFriend = friendGetFriendRepository.findByInvitedIdAndInviterId(
                        friendGetFriend.getInvitedId(),
                        friendGetFriend.getInviterId()
                );
                System.out.println("checkFriend :" + checkFriend);
                if(checkFriend == null){
                    DiscountForFriend checkDiscount = discountForFriendRepository.findByDiscountForFriendStatusTrue();
                    List<FriendGetFriend> checkAmountInvite = friendGetFriendRepository.listByInviterId(friendGetFriend.getInviterId());
                    System.out.println("checkAmountInvite :" + checkAmountInvite.size());
                    Integer amountForGive = Integer.parseInt(checkDiscount.getAmountToGive());
                    Integer amountInvite = checkAmountInvite.size();
                    System.out.println("amountInvite :" + amountInvite);
                    if(checkDiscount != null){
                        if(amountInvite <= amountForGive){
                            System.out.println("amountInvite <= amountForGive");
                            FriendGetFriend showSave = friendGetFriendRepository.save(friendGetFriend);
                            System.out.println("showSave: " + showSave);
                            if (showSave != null){
                                System.out.println("showSave.getFriendGetFriendId(): " + showSave.getFriendGetFriendId());
                                Integer updateData = friendGetFriendRepository.updateStatusFriendAndAddDiscountForFriendId(
                                        checkDiscount.getDiscountForFriendId(),
                                        showSave.getFriendGetFriendId()
                                );
                                if(updateData == 1){
                                    res.setStatus(1);
                                    res.setMessage("create friend get friend success");
                                    res.setData(friendGetFriendRepository.findById(showSave.getFriendGetFriendId()));
                                }
                            }
                        }
                    }
                }
                else {
                    res.setStatus(0);
                    res.setMessage("duplicate");
                    res.setData(friendGetFriend);
                }
            }
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("error : " + err.toString());
        }
        return res;
    }

    @PostMapping("/update_suspend")
    public Object updateStatusSuspend(FriendGetFriend friendGetFriend){
        APIResponse res = new APIResponse();
        try {
            Integer updateData = friendGetFriendRepository.updateStatusSuspend(
                    friendGetFriend.getInviterId(),
                    friendGetFriend.getInvitedId()
            );
            System.out.println("updateData :" + updateData);
            if(updateData == 1){
                res.setStatus(1);
                res.setMessage("edit");
            }else {
                res.setStatus(0);
                res.setMessage("can't suspend");
            }
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("error : " + err.toString());
        }
        return res;
    }
}
