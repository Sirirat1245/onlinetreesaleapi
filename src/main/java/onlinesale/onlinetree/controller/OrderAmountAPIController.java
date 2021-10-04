package onlinesale.onlinetree.controller;

import onlinesale.onlinetree.model.service.CollectProductRepository;
import onlinesale.onlinetree.model.service.DiscountForFriendRepository;
import onlinesale.onlinetree.model.service.FriendGetFriendRepository;
import onlinesale.onlinetree.model.service.OrderAmountRepository;
import onlinesale.onlinetree.model.table.CollectProduct;
import onlinesale.onlinetree.model.table.DiscountForFriend;
import onlinesale.onlinetree.model.table.FriendGetFriend;
import onlinesale.onlinetree.model.table.OrderAmount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/order_amount")
public class OrderAmountAPIController {

    @Autowired
    private OrderAmountRepository orderAmountRepository;

    @Autowired
    private DiscountForFriendRepository discountForFriendRepository;

    @Autowired
    private FriendGetFriendRepository friendGetFriendRepository;

    @Autowired
    private CollectProductRepository collectProductRepository;

    //ยิง api นี้เพื่อออกไปแสดงผลบนหน้าเว็บก่อน ตอนกดซื้อแล้วแสดงราคารวม ก่อนจะยิง calculate
    //api คำนวนราคารวมของสินค้า
    //คำนวนเสร็จนำไปแสดงผลที่หน้าเว็บ ก่อนการกดตยืนยันการซื้อ
    //เรียก api นี้ เมื่อ user กดซื้อ มันจะมาคำนวนราคารวมที่นี่ ก่อนที่จะไป update ลง T.orderAmount และ status T.collectProduct = true
    @PostMapping("/prepare_to_buy")
    public Object prepareToBuy(OrderAmount orderAmount){
        APIResponse res = new APIResponse();
        try {
            Integer sumPrice = collectProductRepository.sumCollectProductByProfile(
                    orderAmount.getProfileRegisterId()
            );
            if(sumPrice != null){
                res.setStatus(1);
                res.setMessage("sum price success");
                res.setData(sumPrice);
            }
            System.out.println("sumPrice: " + sumPrice);
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("error : " + err.toString());
        }
        return res;
    }

    //เอาราคาทรวมี่ได้จาก api prepare_to_buy ที่แสดงบนหน้าเว็บตอนหลังจากกดเตรียมซื้อ
    //กดยืนยันการซื้อแล้วถึงจะมายิง api เส้นนี้
    //สร้างตอน user สั่งซื้อของ และนำมาคำนวนที่ api นี้ ว่าสั่งซื้อถึงจำนวนที่จะได้ส่วนลดหรือไม่ ?
    @PostMapping("/calculate")
    public Object create(OrderAmount orderAmount){
        APIResponse res = new APIResponse();
        try {
            DiscountForFriend dataDiscount = discountForFriendRepository.findByDiscountForFriendStatusTrue();
            System.out.println("dataDiscount: " + dataDiscount);
            int invitedId = orderAmount.getProfileRegisterId();
            int inviterId = orderAmount.getProfileRegisterId();
            FriendGetFriend dataFriend = friendGetFriendRepository.findByInvitedIdOrInviterId(
                    invitedId,
                    inviterId
            );
            System.out.println("dataFriend: " + dataFriend);
            System.out.println(orderAmount.getAmountOrder() >= dataDiscount.getMinimumAmount());
            if(orderAmount.getAmountOrder() >= dataDiscount.getMinimumAmount()){
                System.out.println("------------");
                System.out.println("conditions are correct");
                System.out.println("------------");
                OrderAmount checkOrder = orderAmountRepository.findByProfileRegisterIdAndDiscountForFriendId(
                        orderAmount.getProfileRegisterId(),
                        dataFriend.getDiscountForFriendId()
                );
                System.out.println("------checkOrder------" + checkOrder);
                if(checkOrder == null){
                    OrderAmount dataStatusTrue = orderAmountRepository.save(orderAmount);
                    System.out.println("dataStatusTrue" + dataStatusTrue);
                    if (dataStatusTrue != null){
                        System.out.println("save statusTrue");
                        Integer update = orderAmountRepository.updateOrderAmountStatusTrue(
                                orderAmount.getAmountOrder(),
                                dataFriend.getDiscountForFriendId(),
                                orderAmount.getProfileRegisterId(),
                                orderAmount.getOrderAmountId()
                        );
                        System.out.println("update: " + update);
                        System.out.println("dataStatusTrue.getOrderAmountId(): " + dataStatusTrue.getOrderAmountId());
                        if(update == 1){
                            res.setStatus(1);
                            res.setMessage("create amount order success");
                            res.setData(orderAmountRepository.findById(dataStatusTrue.getOrderAmountId()));
                        }
                    }
                }else {
                    res.setStatus(0);
                    res.setMessage("repeat");
                }
            }else {
                System.out.println("------------");
                System.out.println("The condition is not correct");
                System.out.println("------------");
                OrderAmount profileData = orderAmountRepository.findByProfileRegisterIdAndGetDiscountForFriendId(
                        orderAmount.getProfileRegisterId()
                );
                System.out.println("profileData: " + profileData);
                if(profileData == null){
                    System.out.println(" || ");
                    OrderAmount dataStatusFalse = orderAmountRepository.save(orderAmount);
                    System.out.println("dataStatusFalse" + dataStatusFalse);
                    if (dataStatusFalse != null){
                        System.out.println("save statusFalse");
                        Integer update = orderAmountRepository.updateOrderAmountStatusFalse(
                                orderAmount.getAmountOrder(),
                                orderAmount.getProfileRegisterId()
                        );
                        System.out.println("update: " + update);
                        if(update == 1){
                            res.setStatus(1);
                            res.setMessage("create status false success");
                            res.setData(orderAmountRepository.findById(dataStatusFalse.getOrderAmountId()));
                        }
                    }else {
                        Integer update = orderAmountRepository.updateOrderAmountStatusFalse(
                                orderAmount.getAmountOrder(),
                                orderAmount.getProfileRegisterId()
                        );
                        System.out.println("update: " + update);
                        if(update == 1){
                            res.setStatus(1);
                            res.setMessage("update amount_false success");
                            res.setData(orderAmountRepository.findById(dataStatusFalse.getOrderAmountId()));
                        }
                    }
                }else {
                    res.setStatus(0);
                    res.setMessage("repeat");
                }
            }
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("error : " + err.toString());
        }
        return res;
    }
}