package onlinesale.onlinetree.controller;

import onlinesale.onlinetree.model.service.DiscountForFriendRepository;
import onlinesale.onlinetree.model.service.FriendGetFriendRepository;
import onlinesale.onlinetree.model.service.ProfileRegisterRepository;
import onlinesale.onlinetree.model.table.DealSale;
import onlinesale.onlinetree.model.table.DiscountForFriend;
import onlinesale.onlinetree.model.table.FriendGetFriend;
import onlinesale.onlinetree.model.table.ProfileRegister;
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

    @Autowired
    private ProfileRegisterRepository profileRegisterRepository;

    //กรอกโค้ด ยิงเส้นนี้เลย
    //check ui ถ้ามีการกรอกโค้ด ให้มายิงที่ api นี้ด้วย
    @PostMapping("/check_code")
    public Object checkCode(String myCode){
        APIResponse res = new APIResponse();
        try {
            ProfileRegister profileRegister = profileRegisterRepository.findByMyCode(myCode);
            System.out.println("profileRegister: " + profileRegister);
            if (profileRegister != null){
                res.setStatus(1);
                res.setMessage("show profile inviter");
                res.setData(profileRegister.getProfileRegisterId());
            }else {
                res.setStatus(0);
                res.setMessage("null");
            }
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("error : " + err.toString());
        }
        return res;
    }

    //สร้างตอนที่ ผู้ถูกชวน active ที่ T.profileRegister
    //ถ้าสมัครสมาชิกสำเร็จ มี codeFromFriend ให้มายิงเส้นนี้ต่อ เพื่อสร้าง t.friendgetfriend
    //ส่ง status from T.profileRegister มาเพื่อเช็คว่า profile คนนั้น active ยัง
    //ถ้า statusFriend = 0 ถึงจะ save ได้
    //เอา profile ที่ return มาจาก api friend_get_friend/check_code
    //และ profileRegisterId ที่ได้จากตอน สมัครสมาชิกของตนเอง ส่งมาสร้าง
    //และ statusFriend = 0 คือเป็น user ใหม่ ไม่เคยยกเลิกมาก่อน
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
