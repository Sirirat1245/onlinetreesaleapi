package onlinesale.onlinetree.controller;

import onlinesale.onlinetree.model.service.BillingDeliveryRepository;
import onlinesale.onlinetree.model.table.BillingDelivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/billing_delivery")
public class BillingDeliveryAPIController {

    @Autowired
    private BillingDeliveryRepository billingDeliveryRepository;

    @PostMapping("/adjust_billing")
    public Object create(BillingDelivery billingDelivery){
        APIResponse res = new APIResponse();
        try {
            System.out.println("-----------------");
            System.out.println("billingDelivery.getPickUpStatus(): " + billingDelivery.isPickUpStatus());
            System.out.println("-----------------");
            BillingDelivery billingData = billingDeliveryRepository.findByProfileRegisterIdAndOrderId(
                    billingDelivery.getProfileRegisterId(),
                    billingDelivery.getOrderId()
            );
            System.out.println("---------------------------");
            System.out.println("billingData: " + billingData);
            System.out.println("---------------------------");
            if(billingData == null){
                System.out.println("--------------show null-------------");
                BillingDelivery data = billingDeliveryRepository.save(billingDelivery);
                System.out.println("---------------------------");
                System.out.println("data: " + data);
                System.out.println("---------------------------");
                if (data != null){
                    res.setStatus(1);
                    res.setMessage("create billing success");
                    res.setData(data);
                }else {
                    res.setStatus(-1);
                    res.setMessage("create billing failed");
                }
            }else {
                Integer updateData = billingDeliveryRepository.updateBillingDelivery(
                        billingDelivery.getProductPrice(),
                        billingDelivery.getDiscountPrice(),
                        billingDelivery.getDeliveryStatus(),
                        billingDelivery.isPickUpStatus(),
                        billingDelivery.getProfileRegisterId(),
                        billingDelivery.getOrderId()
                );
                if(updateData == 1){
                    res.setStatus(1);
                    res.setMessage("update billing");
                    res.setData(billingData);
                }else {
                    res.setStatus(0);
                    res.setMessage("can't update billing");
                }
            }
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("error : " + err.toString());
        }
        return res;
    }

    @PostMapping("/list_by_profile")
    public Object listByProfileRegisterId(BillingDelivery billingDelivery){
        APIResponse res = new APIResponse();
        try{
            res.setStatus(1);
            res.setMessage("list");
            res.setData(billingDeliveryRepository.findByProfileRegisterId(billingDelivery.getProfileRegisterId()));
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("error : " + err.toString());
        }
        return res;
    }
}
