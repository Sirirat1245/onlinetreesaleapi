package onlinesale.onlinetree.controller;

import onlinesale.onlinetree.model.service.ProductRepository;
import onlinesale.onlinetree.model.table.CategoryProduct;
import onlinesale.onlinetree.model.table.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/product")
public class ProductAPIController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/save")
    public Object create(Product product){
        APIResponse res = new APIResponse();
        try {
            System.out.println("*********getProductId**********" + product.getProductId());
            System.out.println("********getProductName*********" + product.getProductName());
            Product _product = productRepository.findByProductIdAndProductName(product.getProductId(), product.getProductName());
            if(_product == null){
                productRepository.save(product);
                res.setStatus(1);
                res.setMessage("save");
                res.setData(product);
            } else {
                res.setStatus(0);
                res.setMessage("repeat");
                res.setData(product);
            }
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("error : " + err.toString());
        }
        return res;
    }

    @PostMapping("/list")
    public Object list(Product product){
        APIResponse res = new APIResponse();
        try {
            List lstData = productRepository.findByProductType(product.getProductType());
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

    @PostMapping("/edit")
    public Object edit(Product product){
        APIResponse res = new APIResponse();
        try {
            System.out.println("product : " + product);
            Integer status = productRepository.updateProduct(
                    product.getProductName(),
                    product.getStatus(),
                    product.getProductType(),
                    product.getPrice(),
                    product.getDescription(),
                    product.getShippingCost(),
                    product.getProductPic(),
                    product.getPicOne(),
                    product.getPicTwo(),
                    product.getPicThree(),
                    product.getPicFour(),
                    product.getProductId()
            );
            System.out.println("status : " + status);
            if(status == 1){
                res.setStatus(1);
                res.setMessage("edit");
                res.setData(productRepository.findByProductId(product.getProductId()));
            }
        } catch (Exception err){
            res.setStatus(-1);
            res.setMessage("err : " + err.toString());
        }
        return res;
    }

    @PostMapping("/delete_product")
    public Object delete(Product product){
        APIResponse res = new APIResponse();
        try{
            productRepository.deleteById(product.getId());
            res.setStatus(1);
            res.setMessage("delete product");
            res.setData(product);
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("err : " + err.toString());
        }
        return res;
    }
}
