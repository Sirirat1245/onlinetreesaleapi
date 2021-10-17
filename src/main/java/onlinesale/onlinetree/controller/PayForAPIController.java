package onlinesale.onlinetree.controller;

import onlinesale.onlinetree.model.service.PayForRepository;
import onlinesale.onlinetree.model.table.PayFor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Properties;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/pay_for")
public class PayForAPIController {

    @Autowired
    private PayForRepository payForRepository;

    @PostMapping("/save")
    public Object create(PayFor payFor){
        APIResponse res = new APIResponse();
        try {
            System.out.println("*********payFor**********" + payFor);
            PayFor _payFor = payForRepository.findByOrderId(payFor.getOrderId());
            System.out.println("*********_catProduct**********" + _payFor);
            if(_payFor == null){
                payForRepository.save(payFor);
                res.setStatus(1);
                res.setMessage("save");
                res.setData(payFor);
            } else {
                res.setStatus(0);
                res.setMessage("repeat"); //order นี้ มีการแจ้งชำระไปแล้ว 1 order แจ้งชำระได้ 1 ครั้ง
                res.setData(payFor);
            }
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("error : " + err.toString());
        }
        return res;
    }

    @PostMapping("/edit")
    public Object edit(PayFor payFor){
        APIResponse res = new APIResponse();
        try {
            System.out.println("payFor : " + payFor);
            Integer status = payForRepository.updateCategoryProduct(
                    payFor.getIsPayForStatus(),
                    payFor.getTransferSlip(),
                    payFor.getPayForDay(),
                    payFor.getPayForTime(),
                    payFor.getUseAccountPay(),
                    payFor.getPayForPrice(),
                    payFor.getEmail(),
                    payFor.getTel(),
                    payFor.getDateTime(),
                    payFor.getPayForId(),
                    payFor.getProfileRegisterId(),
                    payFor.getOrderId()
            );
            System.out.println("status : " + status);
            if(status == 1){
                res.setStatus(1);
                res.setMessage("edit");
                res.setData(payForRepository.findByPayForId(payFor.getPayForId()));
            }
        } catch (Exception err){
            res.setStatus(-1);
            res.setMessage("err : " + err.toString());
        }
        return res;
    }

//    @PostMapping("/list")
//    public Object list(PayFor payFor){
//        APIResponse res = new APIResponse();
//        try {
////            PayFor pay = new PayFor();
////            System.out.println("payFor : " + pay);
////            if(pay !== null){
////                System.out.println("call list");
////                System.out.println("payFor : " + pay);
////            }
//            List lstData = payForRepository.findByOrderId(payFor.getOrderId());
//            if (lstData == null){
//                res.setStatus(0);
//                res.setMessage("don't have product");
//            } else {
//                res.setStatus(1);
//                res.setMessage("show list");
//                res.setData(lstData);
//            }
//        } catch (Exception err){
//            res.setStatus(-1);
//            res.setMessage("err : " + err.toString());
//        }
//        return res;
//    }

    @PostMapping("/list_all")
    public Object listAll(PayFor payFor){
        APIResponse res = new APIResponse();
        try{
            res.setStatus(1);
            res.setMessage("list all");
            res.setData(payForRepository.findAll());
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("err : " + err.toString());
        }
        return res;
    }

    @PostMapping("/delete")
    public Object delete(PayFor payFor){
        APIResponse res = new APIResponse();
        try{
            payForRepository.deleteById(payFor.getPayForId());
            res.setStatus(1);
            res.setMessage("delete payFor");
            res.setData(payFor);
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("err : " + err.toString());
        }
        return res;
    }

    @PostMapping("/list_is_status")
    public Object listIsStatus(PayFor payFor){
        APIResponse res = new APIResponse();
        try {
            List lstData = payForRepository.lstPayForByIsPayForStatus(
                    payFor.getIsPayForStatus()
            );
            if (lstData == null){
                res.setStatus(0);
                res.setMessage("don't have payFor");
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
