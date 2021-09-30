package onlinesale.onlinetree.controller;

import onlinesale.onlinetree.model.service.DiscountForFriendRepository;
import onlinesale.onlinetree.model.table.CollectProduct;
import onlinesale.onlinetree.model.table.DiscountForFriend;
import onlinesale.onlinetree.model.table.FriendGetFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/discount_for_friend")
public class DiscountForFriendAPIController {

    @Autowired
    private DiscountForFriendRepository discountForFriendRepository;

    @PostMapping("/save")
    public Object create(DiscountForFriend discountForFriend){
        APIResponse res = new APIResponse();
        try {
            DiscountForFriend discount = discountForFriendRepository.findByDiscountForFriendId(discountForFriend.getDiscountForFriendId());
            if(discount == null){
                discountForFriendRepository.save(discountForFriend);
                res.setStatus(1);
                res.setMessage("save");
                res.setData(discountForFriend);
            } else {
                res.setStatus(0);
                res.setMessage("repeat");
                res.setData(discountForFriend);
            }
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("error : " + err.toString());
        }
        return res;
    }

    @PostMapping("/edit")
    public Object edit(DiscountForFriend discountForFriend){
        APIResponse res = new APIResponse();
        try {
            Integer status = discountForFriendRepository.updateDiscountForFriend(
                    discountForFriend.getDiscountName(),
                    discountForFriend.getDiscountVolume(),
                    discountForFriend.getAmountToGive(),
                    discountForFriend.getStatus(),
                    discountForFriend.getDiscountForFriendId()
            );
            System.out.println("status : " + status);
            if(status == 1){
                res.setStatus(1);
                res.setMessage("edit");
                res.setData(discountForFriendRepository.findByDiscountForFriendId(
                        discountForFriend.getDiscountForFriendId()
                ));
            }
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("error : " + err.toString());
        }
        return res;
    }

    //เพื่อแสดง discount ที่พร้อมให้ user ใช้งาน ตอน admin จะ add discount ให้ user จะเช็คที่ status = true
    @PostMapping("/list_collect_product")
    public Object listCollectProduct(DiscountForFriend discountForFriend){
        APIResponse res = new APIResponse();
        try {
            List<DiscountForFriend> lstData = discountForFriendRepository.lstDiscountForFriendByStatus(
                    discountForFriend.getStatus()
            );
            System.out.println("********* lstData ********" + lstData);
            if (lstData == null){
                res.setStatus(0);
                res.setMessage("don't have discount");
            } else {
                res.setStatus(1);
                res.setMessage("show list");
                res.setData(lstData);
            }
        } catch (Exception err){
            res.setStatus(-1);
            res.setMessage("err : " + err.toString());
        }
        return res;
    }
}
