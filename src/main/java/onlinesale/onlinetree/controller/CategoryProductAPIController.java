package onlinesale.onlinetree.controller;

import onlinesale.onlinetree.model.service.CategoryProductRepository;
import onlinesale.onlinetree.model.table.CategoryProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/category_product")
public class CategoryProductAPIController {

    @Autowired
    private CategoryProductRepository categoryProductRepository;

    @PostMapping("/save")
    public Object create(CategoryProduct categoryProduct){
        APIResponse res = new APIResponse();
        try {
            System.out.println("*********getProductId**********" + categoryProduct.getCategoryProductId());
            System.out.println("********getProductName*********" + categoryProduct.getCategoryName());
            CategoryProduct _catProduct = categoryProductRepository.findByCategoryName(
                    categoryProduct.getCategoryName());
            System.out.println("*********_catProduct**********" + _catProduct);
            if(_catProduct == null){
                categoryProductRepository.save(categoryProduct);
                res.setStatus(1);
                res.setMessage("save");
                res.setData(categoryProduct);
            } else {
                res.setStatus(0);
                res.setMessage("repeat");
                res.setData(categoryProduct);
            }
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("error : " + err.toString());
        }
        return res;
    }

    @PostMapping("/list")
    public Object list(CategoryProduct categoryProduct){
        APIResponse res = new APIResponse();
        try {
            List lstData = categoryProductRepository.findByProductType(categoryProduct.getProductType());
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
    public Object edit(CategoryProduct categoryProduct){
        APIResponse res = new APIResponse();
        try {
            Integer status = categoryProductRepository.updateCategoryProduct(
                    categoryProduct.getCategoryName(),
                    categoryProduct.getProductType(),
                    categoryProduct.getCategoryPic(),
                    categoryProduct.getCategoryProductId()
            );
            System.out.println("status : " + status);
            if(status == 1){
                res.setStatus(1);
                res.setMessage("edit");
                res.setData(categoryProductRepository.findByCategoryProductId(categoryProduct.getCategoryProductId()));
            }
        } catch (Exception err){
            res.setStatus(-1);
            res.setMessage("err : " + err.toString());
        }
        return res;
    }

    @PostMapping("/delete")
    public Object delete(CategoryProduct categoryProduct){
        APIResponse res = new APIResponse();
        try{
            categoryProductRepository.deleteById(categoryProduct.getCategoryProductId());
            res.setStatus(1);
            res.setMessage("delete product");
            res.setData(categoryProduct);
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("err : " + err.toString());
        }
        return res;
    }
}
