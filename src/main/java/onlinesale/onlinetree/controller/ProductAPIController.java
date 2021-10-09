package onlinesale.onlinetree.controller;

import onlinesale.onlinetree.config.Config;
import onlinesale.onlinetree.model.service.ProductRepository;
import onlinesale.onlinetree.model.table.CategoryProduct;
import onlinesale.onlinetree.model.table.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/product")
public class ProductAPIController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/save")
    public Object create(Product product,
                         @RequestParam(value = "file1",required = false) MultipartFile file1,
                         @RequestParam(value = "file2",required = false) MultipartFile file2,
                         @RequestParam(value = "file3",required = false) MultipartFile file3,
                         @RequestParam(value = "file4",required = false) MultipartFile file4,
                         @RequestParam(value = "file5",required = false) MultipartFile file5
    ){
        APIResponse res = new APIResponse();
        Config conf = new Config();
        try {
            System.out.println("*********getProductId**********" + product.getProductId());
            System.out.println("********getProductName*********" + product.getProductName());

            /* Generate product id in Product */
            Integer _proAmount = productRepository.getAmountProduct();
            if(_proAmount == 0){
                product.setProductId(10000); //start with 10000
            }else{
                Product _lastProductId = productRepository.getLastByProductId();
                product.setProductId(_lastProductId.getProductId()+1);
            }
            /* End Generate product id in Product */

            Product _product = productRepository.findByProductIdOrProductName(product.getProductId(), product.getProductName());
            if(_product == null){

                /* File transfer */
                if(file1 != null){
                    new File(conf.getStorePath()+"product\\"+product.getProductType()).mkdirs();
                    File fileToSave = new File(conf.getStorePath()+"product\\"+product.getProductType()+"\\product-"+product.getProductId()+"-1"+".png");
                    file1.transferTo(fileToSave);
                    System.out.println("save file 1 success");
                    product.setProductPic("product-"+product.getProductId()+"-1"+".png");
                }else{
                    System.out.println("file 1 not found!");
                }
                if(file2 != null){
                    new File(conf.getStorePath()+"product\\"+product.getProductType()).mkdirs();
                    File fileToSave = new File(conf.getStorePath()+"product\\"+product.getProductType()+"\\product-"+product.getProductId()+"-2"+".png");
                    file2.transferTo(fileToSave);
                    System.out.println("save file 2 success");
                    product.setPicOne("product-"+product.getProductId()+"-2"+".png");
                }else{
                    System.out.println("file 2 not found!");
                }
                if(file3 != null){
                    new File(conf.getStorePath()+"product\\"+product.getProductType()).mkdirs();
                    File fileToSave = new File(conf.getStorePath()+"product\\"+product.getProductType()+"\\product-"+product.getProductId()+"-3"+".png");
                    file3.transferTo(fileToSave);
                    System.out.println("save file 3 success");
                    product.setPicTwo("product-"+product.getProductId()+"-3"+".png");
                }else{
                    System.out.println("file 3 not found!");
                }
                if(file4 != null){
                    new File(conf.getStorePath()+"product\\"+product.getProductType()).mkdirs();
                    File fileToSave = new File(conf.getStorePath()+"product\\"+product.getProductType()+"\\product-"+product.getProductId()+"-4"+".png");
                    file4.transferTo(fileToSave);
                    System.out.println("save file 4 success");
                    product.setPicThree("product-"+product.getProductId()+"-4"+".png");
                }else{
                    System.out.println("file 4 not found!");
                }
                if(file5 != null){
                    new File(conf.getStorePath()+"product\\"+product.getProductType()).mkdirs();
                    File fileToSave = new File(conf.getStorePath()+"product\\"+product.getProductType()+"\\product-"+product.getProductId()+"-5"+".png");
                    file5.transferTo(fileToSave);
                    System.out.println("save file 5 success");
                    product.setPicFour("product-"+product.getProductId()+"-5"+".png");
                }else{
                    System.out.println("file 5 not found!");
                }

                /* End file transfer */


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
    @PostMapping("/list_all")
    public Object listAll(){
        APIResponse res = new APIResponse();
        try {
                res.setStatus(1);
                res.setMessage("show list");
                res.setData(productRepository.findAll());

        } catch (Exception err){
            res.setStatus(-1);
            res.setMessage("err : " + err.toString());
        }

        return res;
    }

    @PostMapping("/edit")
    public Object edit(Product product,
                       @RequestParam(value = "file1",required = false) MultipartFile file1,
                       @RequestParam(value = "file2",required = false) MultipartFile file2,
                       @RequestParam(value = "file3",required = false) MultipartFile file3,
                       @RequestParam(value = "file4",required = false) MultipartFile file4,
                       @RequestParam(value = "file5",required = false) MultipartFile file5
    ){
        APIResponse res = new APIResponse();
        Config conf = new Config();
        try {
            System.out.println("product : " + product);
            /* File transfer */
            if(file1 != null){
                System.out.println("product : " + product);
                File fileToSave = new File(conf.getStorePath()+"product\\"+product.getProductType()+"\\product-"+product.getProductId()+"-1"+".png");
                fileToSave.delete();
                file1.transferTo(fileToSave);
                System.out.println("update file 1 success");
                product.setProductPic("product-"+product.getProductId()+"-1"+".png");
                res.setMessage("edit with file 1");
            }else{
                res.setMessage("edit not file 1");
            }
            if(file2 != null){
                File fileToSave = new File(conf.getStorePath()+"product\\"+product.getProductType()+"\\product-"+product.getProductId()+"-2"+".png");
                fileToSave.delete();
                file2.transferTo(fileToSave);
                System.out.println("update file 2 success");
                product.setPicOne("product-"+product.getProductId()+"-2"+".png");
                res.setMessage("edit with file 2");
            }else{
                res.setMessage("edit not file 2");
            }
            if(file3 != null){
                File fileToSave = new File(conf.getStorePath()+"product\\"+product.getProductType()+"\\product-"+product.getProductId()+"-3"+".png");
                fileToSave.delete();
                file3.transferTo(fileToSave);
                System.out.println("update file 3 success");
                product.setPicTwo("product-"+product.getProductId()+"-3"+".png");
                res.setMessage("edit with file 3");
            }else{
                res.setMessage("edit not file 3");
            }
            if(file4 != null){
                File fileToSave = new File(conf.getStorePath()+"product\\"+product.getProductType()+"\\product-"+product.getProductId()+"-4"+".png");
                fileToSave.delete();
                file4.transferTo(fileToSave);
                System.out.println("update file 4 success");
                product.setPicThree("product-"+product.getProductId()+"-4"+".png");
                res.setMessage("edit with file 4");
            }else{
                res.setMessage("edit not file 4");
            }
            if(file5 != null){
                File fileToSave = new File(conf.getStorePath()+"product\\"+product.getProductType()+"\\product-"+product.getProductId()+"-5"+".png");
                fileToSave.delete();
                file5.transferTo(fileToSave);
                System.out.println("update file 5 success");
                product.setPicFour("product-"+product.getProductId()+"-5"+".png");
                res.setMessage("edit with file 5");
            }else{
                res.setMessage("edit not file 5");
            }
            /* End file transfer */

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

    @PostMapping("/detail_product")
    public Object detail(Product product){
        APIResponse res = new APIResponse();
        try{
            Product detail = productRepository.findByProductId(product.getProductId());
            res.setStatus(1);
            res.setMessage("detail");
            res.setData(detail);
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("err : " + err.toString());
        }
        return res;
    }

    @PostMapping("/delete_product")
    public Object delete(Product product){
        APIResponse res = new APIResponse();
        Config conf = new Config();
        try{
            /* --- Start delete Files --- */
            Product detail = productRepository.findByProductId(product.getProductId());
            if(detail.getProductPic() != null)new File(conf.getStorePath()+"product\\"+detail.getProductType()+"\\"+detail.getProductPic()).delete();
            if(detail.getPicOne() != null)new File(conf.getStorePath()+"product\\"+detail.getProductType()+"\\"+detail.getPicOne()).delete();
            if(detail.getPicTwo() != null)new File(conf.getStorePath()+"product\\"+detail.getProductType()+"\\"+detail.getPicTwo()).delete();
            if(detail.getPicThree() != null)new File(conf.getStorePath()+"product\\"+detail.getProductType()+"\\"+detail.getPicThree()).delete();
            if(detail.getPicFour() != null)new File(conf.getStorePath()+"product\\"+detail.getProductType()+"\\"+detail.getPicFour()).delete();
            /* --- End delete Files --- */

            productRepository.deleteById(detail.getId());

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
