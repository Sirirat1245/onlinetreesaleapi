package onlinesale.onlinetree.controller;

import onlinesale.onlinetree.SmtpMailSender;
import onlinesale.onlinetree.model.service.*;
import onlinesale.onlinetree.model.table.*;
import org.hibernate.mapping.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/deal_sale")
public class DealSaleAPIController {

    @Autowired
    private PayForRepository payForRepository;

    @Autowired
    private DealSaleRepository dealSaleRepository;

    @Autowired
    private CollectProductRepository collectProductRepository;

    @Autowired
    private OrderAmountRepository orderAmountRepository;

    @Autowired
    private BillingDeliveryRepository billingDeliveryRepository;

    @Autowired
    private SmtpMailSender smtpMailSender;

    @PostMapping("/calculate")
    public Object calculate(DealSale dealSale){
        APIResponse res = new APIResponse();
        try {
//            List<CollectProduct> collect = collectProductRepository.lstCollectProductByProfileRegisterIdAndStatusBuy(
//                    dealSale.getProfileRegisterId()
//            );
//            List<OrderAmount> order = orderAmountRepository.lstOrderAmountAndStatus(
//                    dealSale.getProfileRegisterId()
//            );
            List<DealSale> dealSales = dealSaleRepository.lstDealSaleByProfileRegisterIdAndOrderAmountId(
                    dealSale.getProfileRegisterId(),
                    dealSale.getOrderAmountId()
            );
            System.out.println("dealSales: " + dealSales);
            if(dealSales != null){
                Integer updateData = collectProductRepository.updateOrderAmountId(
                        dealSale.getOrderAmountId(),
                        dealSale.getProfileRegisterId()
                );
                System.out.println("updateData: " + updateData);
                res.setStatus(0);
                res.setMessage("update OrderAmountId");
                res.setData(updateData);
            }else {
                dealSaleRepository.save(dealSale);
                Integer updateData = collectProductRepository.updateOrderAmountId(
                        dealSale.getOrderAmountId(),
                        dealSale.getProfileRegisterId()
                );
                System.out.println("updateData: " + updateData);
                res.setStatus(0);
                res.setMessage("update OrderAmountId");
                res.setData(updateData);
            }
//            System.out.println("order: " + order);
            //รวมราคาจาก t.collectProduct แล้วส่งราคารวมไปที่ t.orderAmount
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("error : " + err.toString());
        }
        return res;
    }

    @PostMapping("/send_email")
    public Object sendEmail(DealSale dealSale){
        APIResponse res = new APIResponse();
        try {
            smtpMailSender.send("pornnapha.noiin@gmail.com", "สวัสดีค่ะ", "ฉันชื่อ พรนภา น้อยอินทร์");
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("error : " + err.toString());
        }
        return res;
    }

//    @PostMapping("/save")
//    public Object create(int orderAmountId,
//                         int profileRegisterId,
//                         String profileAddress,
//                         int productPrice,
//                         int discountPrice,
//                         int quantity,
//                         int status,
//                         int cancel){
//        APIResponse res = new APIResponse();
//        try {
//            DealSale _dealSale = dealSaleRepository.findByProfileRegisterIdAndOrderAmountId(
//                    profileRegisterId,
//                    orderAmountId
//            );
//            System.out.println("_dealSale:" + _dealSale);
//
//            String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%&";
//            Random rnd = new Random();
//            int len = 6;
//            StringBuilder orderId = new StringBuilder(len);
//            for (int i = 0; i < len; i++)
//                orderId.append(chars.charAt(rnd.nextInt(chars.length())));
//            System.out.println("sb:" + orderId);
//
////            HashMap<String, String> hash_map = new HashMap<String, String>();
////            hash_map.put("orderId", sb.toString());
////            System.out.println("hash_map:" + hash_map);
//
//            if(_dealSale == null){
//                DealSale dealSaleData = dealSaleRepository.saveDealSaleWithCondition(
//                        orderId.toString(),
//                        orderAmountId,
//                        profileRegisterId,
//                        profileAddress,
//                        productPrice,
//                        discountPrice,
//                        quantity,
//                        status,
//                        cancel
//                );
//                System.out.println("dealSaleData:" + dealSaleData);
//                if (dealSaleData != null){
//                    res.setStatus(1);
//                    res.setMessage("save");
//                    res.setData(dealSaleData);
//
//                    Integer updateCollect = collectProductRepository.updateStatusBuyTrue(
//                            orderAmountId,
//                            profileRegisterId
//                    );
////                    Integer updateLastCollect = collectProductRepository.updateIsStatusFalse(
////                            orderAmountId,
////                            profileRegisterId
////                    );
//
//                    System.out.println("updateCollect:" + updateCollect);
////                    System.out.println("updateLastCollect:" + updateLastCollect);
//                }
//            } else {
//                res.setStatus(0);
//                res.setMessage("duplicate");
//                res.setData(_dealSale);
//            }
//        }catch (Exception err){
//            res.setStatus(-1);
//            res.setMessage("error : " + err.toString());
//        }
//        return res;
//    }

    @PostMapping("/save")
    public Object save(DealSale dealSale){
        APIResponse res = new APIResponse();
        try {
            DealSale _dealSale = dealSaleRepository.findByProfileRegisterIdAndOrderAmountId(
                    dealSale.getProfileRegisterId(),
                    dealSale.getOrderAmountId()
            );
            System.out.println("_dealSale:" + _dealSale);
            if(_dealSale == null){
                String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%&";
                Random rnd = new Random();
                int len = 6;
                StringBuilder orderId = new StringBuilder(len);
                for (int i = 0; i < len; i++) orderId.append(chars.charAt(rnd.nextInt(chars.length())));
                dealSale.setOrderId(orderId.toString());
                System.out.println("sb:" + dealSale.getOrderId());
                DealSale data= dealSaleRepository.save(dealSale);
                    if (data != null) {
                        res.setStatus(1);
                        res.setMessage("save");
                        res.setData(data);
                        Integer updateCollect = collectProductRepository.updateStatusBuyTrue(
                                dealSale.getOrderAmountId(),
                                dealSale.getProfileRegisterId()
                        );
                        System.out.println("updateCollect:" + updateCollect);
                    }
            }else{
                res.setStatus(0);
                res.setMessage("duplicate");
                res.setData(_dealSale);
            }

        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("error : " + err.toString());
        }
        return res;
    }

    //เช็ค t.payfor ด้วย ว่าสร้างยัง ถ้าสร้างแล้วจะไม่สามารถยกเลิกได้
    @PostMapping("/cancel")
    public Object cancel(DealSale dealSale){
        APIResponse res = new APIResponse();
        try {
            DealSale _dealSale = dealSaleRepository.findByProfileRegisterIdAndOrderAmountId(
                    dealSale.getProfileRegisterId(),
                    dealSale.getOrderAmountId()
            );
            System.out.println("_dealSale:" + _dealSale);
            if(_dealSale != null){
                    PayFor checkPayFor = payForRepository.findByProfileRegisterIdAndOrderId(
                            dealSale.getProfileRegisterId(),
                            dealSale.getOrderId()
                    );
                    System.out.println("checkPayFor:" + checkPayFor);
                    if(checkPayFor != null){
                        res.setStatus(0);
                        res.setMessage("ไม่สามารถยกเลิกคำสั่งซื้อได้ เนื่องจากมีการชำระเงินแล้ว");
                    }else {
                        Integer updateCancel = dealSaleRepository.updateDealSaleCancel(
                                dealSale.getProfileRegisterId(),
                                dealSale.getOrderId()
                        );
                        System.out.println("updateCancel:" + updateCancel);
                        Integer updateCollect = collectProductRepository.updateCollectProductForCancelDealSale(
                                dealSale.getOrderAmountId(),
                                dealSale.getProfileRegisterId()
                        );
                        System.out.println("updateCollect:" + updateCollect);
                        res.setStatus(1);
                        res.setMessage("ยกเลิกคำสั่งซื้อสำเร็จ");
                    }
            }
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("error : " + err.toString());
        }
        return res;
    }

    //api list ยืนยันคำสั่งซื้อของ user ที่หน้าแอดมิน by status false or status true => t.dealsale
    @PostMapping("/list_status")
    public Object list(DealSale dealSale){
        APIResponse res = new APIResponse();
        try {
            List lstData = dealSaleRepository.lstDealSaleByStatus(
                    dealSale.getStatus()
            );
            if (lstData == null){
                res.setStatus(0);
                res.setMessage("don't have dealsale");
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

    //เมื่อ admin กดอนุมัติ ให้ไป update t.dealsale => status = true && t.billing => deliveryStatus = 1 รอการชำระเงิน
    @PostMapping("/admin_approve")
    public Object adminApprove(DealSale dealSale){
        APIResponse res = new APIResponse();
        try {
            DealSale _dealSale = dealSaleRepository.findByProfileRegisterIdAndOrderAmountId(
                    dealSale.getProfileRegisterId(),
                    dealSale.getOrderAmountId()
            );
            System.out.println("_dealSale:" + _dealSale);
            if(_dealSale != null){
                    Integer updateApprove = dealSaleRepository.adminUpdateStatusTrue(
                            dealSale.getProfileRegisterId(),
                            dealSale.getOrderAmountId()
                    );
                    System.out.println("updateApprove:" + updateApprove);
                    Integer updateBilling = billingDeliveryRepository.updateDeliveryStatusTrue(
                            dealSale.getProfileRegisterId(),
                            dealSale.getOrderId()
                    );
                    System.out.println("updateCollect:" + updateBilling);
                    res.setStatus(1);
                    res.setMessage("อนุมัติคำสั่งซื้อสำเร็จ");
            }
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("error : " + err.toString());
        }
        return res;
    }

    @PostMapping("list_all")
    public Object listAll(DealSale dealSale){
        APIResponse res = new APIResponse();
        try{
            res.setStatus(1);
            res.setMessage("list all");
            res.setData(dealSaleRepository.findAll());
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("error : " + err.toString());
        }
        return res;
    }
}
