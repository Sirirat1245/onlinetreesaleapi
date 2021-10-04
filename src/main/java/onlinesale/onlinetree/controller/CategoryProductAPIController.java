package onlinesale.onlinetree.controller;

import onlinesale.onlinetree.config.Config;
import onlinesale.onlinetree.model.service.CategoryProductRepository;
import onlinesale.onlinetree.model.table.CategoryProduct;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/category_product")
public class CategoryProductAPIController {

    @Autowired
    private CategoryProductRepository categoryProductRepository;

    @PostMapping("/save")
    public Object create(CategoryProduct categoryProduct, @RequestParam(value = "file",required = false) MultipartFile file){
        APIResponse res = new APIResponse();
        Config conf = new Config();
        try {
            System.out.println("*********getProductId**********" + categoryProduct.getCategoryProductId());
            System.out.println("********getProductName*********" + categoryProduct.getCategoryName());
            CategoryProduct _catProduct = categoryProductRepository.findByCategoryName(
                    categoryProduct.getCategoryName());
            System.out.println("*********_catProduct**********" + _catProduct);
            if(_catProduct == null){
                /* File transfer */
                if(file != null){
                    File fileToSave = new File(conf.getStorePath()+categoryProduct.getCategoryName()+".png");
                    file.transferTo(fileToSave);
                    System.out.println("save file success");
                    categoryProduct.setCategoryPic(categoryProduct.getCategoryName()+".png");
                }else{
                    System.out.println("file not found!");
                }
                /* End file transfer */

                /* Generate product type in Category Product */
                Integer _catAmount = categoryProductRepository.getAmountCategoryProduct();
                if(_catAmount == 0){
                    categoryProduct.setProductType(1000); //start with 1000
                }else{
                    CategoryProduct _lastProductType = categoryProductRepository.getLastByProductType();
                    categoryProduct.setProductType(_lastProductType.getProductType()+1);
                }
                /* End Generate product type in Category Product */

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

    @PostMapping("/list_all")
    public Object listAll(){
        APIResponse res = new APIResponse();
        try{
            List lstAll = categoryProductRepository.findAll();
            res.setStatus(1);
            res.setMessage("list all");
            res.setData(lstAll);
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("err : " + err.toString());
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
    public Object edit(CategoryProduct categoryProduct,@RequestParam(value = "file",required = false) MultipartFile file){
        APIResponse res = new APIResponse();
        Config conf = new Config();
        try {

            /* File transfer */
            if(file != null){
                File fileToSave = new File(conf.getStorePath()+categoryProduct.getCategoryName()+".png");
                fileToSave.delete();
                file.transferTo(fileToSave);
                System.out.println("update file success");
                categoryProduct.setCategoryPic(categoryProduct.getCategoryName()+".png");
            }else{
                CategoryProduct detail = categoryProductRepository.findCategoryProduct(categoryProduct.getProductType());

                File source = new File(conf.getStorePath()+detail.getCategoryPic());
                File dest = new File(conf.getStorePath()+categoryProduct.getCategoryName()+".png");
                FileSystemUtils.copyRecursively(source,dest);

                categoryProduct.setCategoryPic(categoryProduct.getCategoryName()+".png");
            }
            /* End file transfer */
            
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
