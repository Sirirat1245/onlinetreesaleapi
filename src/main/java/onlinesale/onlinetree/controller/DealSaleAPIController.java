package onlinesale.onlinetree.controller;

import onlinesale.onlinetree.SmtpMailSender;
import onlinesale.onlinetree.model.service.CollectProductRepository;
import onlinesale.onlinetree.model.service.DealSaleRepository;
import onlinesale.onlinetree.model.service.OrderAmountRepository;
import onlinesale.onlinetree.model.table.CollectProduct;
import onlinesale.onlinetree.model.table.DealSale;
import onlinesale.onlinetree.model.table.OrderAmount;
import onlinesale.onlinetree.model.table.ProductComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/deal_sale")
public class DealSaleAPIController {

    @Autowired
    private DealSaleRepository dealSaleRepository;

    @Autowired
    private CollectProductRepository collectProductRepository;

    @Autowired
    private OrderAmountRepository orderAmountRepository;

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

    @PostMapping("/save")
    public Object create(DealSale dealSale){
        APIResponse res = new APIResponse();
        try {
            DealSale _dealSale = dealSaleRepository.findByProfileRegisterIdAndOrderAmountId(
                    dealSale.getProfileRegisterId(),
                    dealSale.getOrderAmountId()
            );
            System.out.println("_dealSale:" + _dealSale);
            if(_dealSale == null){
                DealSale dealSaleData = dealSaleRepository.save(dealSale);
                if (dealSaleData != null){
                    res.setStatus(1);
                    res.setMessage("save");
                    res.setData(dealSale);

                    Integer updateCollect = collectProductRepository.updateStatusBuyTrue(
                            dealSale.getOrderAmountId(),
                            dealSale.getProfileRegisterId()
                    );

                    System.out.println("updateCollect:" + updateCollect);
                    System.out.println("updateCollect:" + updateCollect);
                }
            } else {
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
}
