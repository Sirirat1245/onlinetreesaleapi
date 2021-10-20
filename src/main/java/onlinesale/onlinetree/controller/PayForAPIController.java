package onlinesale.onlinetree.controller;

import onlinesale.onlinetree.SmtpMailSender;
import onlinesale.onlinetree.config.Config;
import onlinesale.onlinetree.model.service.BillingDeliveryRepository;
import onlinesale.onlinetree.model.service.PayForRepository;
import onlinesale.onlinetree.model.table.PayFor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Properties;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/pay_for")
public class PayForAPIController {

    @Autowired
    private PayForRepository payForRepository;

    @Autowired
    private BillingDeliveryRepository billingDeliveryRepository;

    @Autowired
    private SmtpMailSender smtpMailSender;

    @PostMapping("/save")
    public Object create(PayFor payFor, @RequestParam(value = "file",required = false) MultipartFile file){
        APIResponse res = new APIResponse();
        Config conf = new Config();
        try {
            System.out.println("*********payFor**********" + payFor);
            PayFor _payFor = payForRepository.findByOrderId(payFor.getOrderId());
            System.out.println("*********_payFor**********" + _payFor);
            if(_payFor == null){
                /* File transfer */
                if(file != null){
                    new File(conf.getStorePath()+"slip").mkdirs();
                    File fileToSave = new File(conf.getStorePath()+"slip\\"+payFor.getOrderId()+".png");
                    file.transferTo(fileToSave);
                    System.out.println("save file success");
                    payFor.setTransferSlip(payFor.getOrderId()+".png");
                }else{
                    System.out.println("file not found!");
                }
                /* End file transfer */
                Integer billingUpdate = billingDeliveryRepository.updateDeliveryStatusWaitApprove(
                        payFor.getProfileRegisterId(),
                        payFor.getOrderId()
                );
                System.out.println("update billing wait approve: " + billingUpdate);
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
            Integer status = payForRepository.updatePayFor(
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

    //ถ้าแอดมินยืนยันแล้ว ให้ไป set t.payfor => payForStatus = true && deliveryStatus => t.billing เตรียมจัดส่ง
    @PostMapping("/approve_pay_for")
    public Object approvePayFor(PayFor payFor){
        APIResponse res = new APIResponse();
        try {
            System.out.println("payFor : " + payFor);
            Integer status = payForRepository.approvePayFor(
                    payFor.getPayForId(),
                    payFor.getProfileRegisterId()
            );
            PayFor _payFor = payForRepository.findByOrderId(payFor.getOrderId());
            if(status == 1){
                if(_payFor.getIsPayForStatus() == true){
                    Integer billingUpdate = billingDeliveryRepository.updateDeliveryStatusPrepareToShip(
                            payFor.getProfileRegisterId(),
                            payFor.getOrderId(),
                            LocalDateTime.now(ZoneId.of("UTC+07:00"))
                    );
                    smtpMailSender.send(payFor.getEmail(), "Online Tree Sale", "อนุมัติการแจ้งชำระเงิน สินค้ากำลังถูกจัดส่ง");
                    System.out.println("billingUpdate : " + billingUpdate);
                    res.setStatus(1);
                    res.setMessage("approve payFor success");
                    res.setData(payForRepository.findByPayForId(payFor.getPayForId()));
                }else {
                    //ไม่อนุมัติเนื่องจากตรวจสอบบิลแล้วไม่ถูกต้อง
                    smtpMailSender.send(payFor.getEmail(), "Online Tree Sale", "การแจ้งชำระเงินของท่านไม่ถูกต้อง กรุณาตรวจสอบ");
                    res.setStatus(1);
                    res.setMessage("disapproved payFor success");
                    res.setData(payForRepository.findByPayForId(payFor.getPayForId()));
                }
            }
        } catch (Exception err){
            res.setStatus(-1);
            res.setMessage("err : " + err.toString());
        }
        return res;
    }

    @PostMapping("/detail")
    public Object detail(PayFor payFor){
        APIResponse res = new APIResponse();
        try{
            res.setStatus(1);
            res.setMessage("detail");
            res.setData(payForRepository.findByPayForId(payFor.getPayForId()));
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("err : " + err.toString());
        }
        return res;
    }
}
