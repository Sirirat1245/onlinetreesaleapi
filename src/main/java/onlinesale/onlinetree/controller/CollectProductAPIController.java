package onlinesale.onlinetree.controller;

import onlinesale.onlinetree.model.service.CollectProductRepository;
import onlinesale.onlinetree.model.table.CollectProduct;
import onlinesale.onlinetree.model.table.PayFor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/collect_product")
public class CollectProductAPIController {

    @Autowired
    private CollectProductRepository collectProductRepository;

    @PostMapping("/save")
    public Object create(CollectProduct collectProduct){
        System.out.println("Input = "+collectProduct);
        APIResponse res = new APIResponse();
        try {
            System.out.println("collectProduct:" + collectProduct);
            CollectProduct _collectProduct = collectProductRepository.findByProductIdAndProfileRegisterId(
                    collectProduct.getProductId(),
                    collectProduct.getProfileRegisterId());
            if(_collectProduct == null){
                collectProductRepository.save(collectProduct);
                res.setStatus(1);
                res.setMessage("save");
                res.setData(collectProduct);
            } else {
                Integer status = collectProductRepository.updateCollectProduct(
                        collectProduct.getIsStatus(),
                        collectProduct.getAmount(),
                        collectProduct.getPrice(),
                        collectProduct.getProductId(),
                        collectProduct.getProfileRegisterId()
                );
                if(status == 1){
                    res.setStatus(1);
                    res.setMessage("update collect");
                    res.setData(collectProductRepository.findByProductIdAndProfileRegisterId(
                            collectProduct.getProductId(),
                            collectProduct.getProfileRegisterId()
                    ));
                }
            }
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("error : " + err.toString());
        }
        return res;
    }

    @PostMapping("/edit")
    public Object edit(CollectProduct collectProduct){
        APIResponse res = new APIResponse();
        try {
            System.out.println("********* collectProduct ********" + collectProduct);
            Integer status = collectProductRepository.updateCollectProduct(
                    collectProduct.getIsStatus(),
                    collectProduct.getAmount(),
                    collectProduct.getPrice(),
                    collectProduct.getProductId(),
                    collectProduct.getProfileRegisterId()
            );
            System.out.println("status : " + status);
            if(status == 1){
                res.setStatus(1);
                res.setMessage("edit");
                res.setData(collectProductRepository.findByProductIdAndProfileRegisterId(
                        collectProduct.getProductId(),
                        collectProduct.getProfileRegisterId()
                ));
            }
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("error : " + err.toString());
        }
        return res;
    }

    //ต้องทำแบบ filter ผ่าน status t/f ได้ เผื่ออยากดูอันที่ชอบ และอันที่เลิกชอบแล้ว
    @PostMapping("/list_collect_product")
    public Object listCollectProduct(CollectProduct _collectProduct){
        APIResponse res = new APIResponse();
        try {
            List<CollectProduct> lstData = collectProductRepository.lstCollectProductByProfileRegisterIdAndIsStatus(
                    _collectProduct.getProfileRegisterId(),
                    _collectProduct.getIsStatus());
            System.out.println("********* lstData ********" + lstData);
            if (lstData == null){
                res.setStatus(0);
                res.setMessage("don't have product");
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


    @PostMapping("/delete")
    public Object delete(CollectProduct collectProduct){
        APIResponse res = new APIResponse();
        try {
            collectProductRepository.deleteById(collectProduct.getCollectProductId());
            res.setStatus(1);
            res.setMessage("delete");
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("err : " + err.toString());
        }
        return res;
    }
}
