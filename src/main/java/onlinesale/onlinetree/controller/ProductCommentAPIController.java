package onlinesale.onlinetree.controller;

import onlinesale.onlinetree.model.service.ProductCommentRepository;
import onlinesale.onlinetree.model.table.CollectProduct;
import onlinesale.onlinetree.model.table.PayFor;
import onlinesale.onlinetree.model.table.ProductComment;
import onlinesale.onlinetree.model.table.ProfileRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/product_comment")
public class ProductCommentAPIController {

    @Autowired
    private ProductCommentRepository productCommentRepository;

    //user บันทึกการแสดงความคิดเห็นตอนแรก แสดง status = pending, ถ้า admin อนุมัติ status = approve, ถ้า admin ไม่อนุมัติ status = disapproved
    @PostMapping("/save")
    public Object create(ProductComment productComment){
        APIResponse res = new APIResponse();
        try {
            System.out.println("productComment:" + productComment);
            if(productComment == null){
                res.setStatus(0);
                res.setMessage("data is require");
                res.setData(productComment);
            } else {
                productCommentRepository.save(productComment);
                res.setStatus(1);
                res.setMessage("save");
                res.setData(productComment);
            }
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("error : " + err.toString());
        }
        return res;
    }

    //ถ้ามี profileRegister ส่งมา = เป็น user จะแก้ได้แค่คอมเม้นต์กับวันที่อัพเดท
    @PostMapping("/edit")
    public Object edit(ProductComment productComment, Integer profileRegisterId){
        APIResponse res = new APIResponse();
        try {
            if(profileRegisterId != null){
                System.out.println("********* in if profileRegisterId ********" + profileRegisterId);
                Integer statusUser = productCommentRepository.updateProductCommentUser(
                        productComment.getComment(),
                        productComment.getStatus(),
                        productComment.getProductId(),
                        productComment.getProfileRegisterId(),
                        productComment.getProductCommentId()
                );
                System.out.println("status : " + statusUser);
                if(statusUser == 1){
                    res.setStatus(1);
                    res.setMessage("edit comment");
                    res.setData(productCommentRepository.findByProductIdAndProductCommentIdAndProfileRegisterId(
                            productComment.getProductId(),
                            productComment.getProductCommentId(),
                            productComment.getProfileRegisterId()
                    ));
                }
            }else {
                System.out.println("********* in else profileRegisterId ********");
                Integer statusAdmin = productCommentRepository.updateProductCommentAdmin(
                        productComment.getStatus(),
                        productComment.getProductId(),
                        productComment.getProductCommentId()
                );
                System.out.println("status : " + statusAdmin);
                if(statusAdmin == 1){
                    res.setStatus(1);
                    res.setMessage("update status true");
                    res.setData(productCommentRepository.findByProductIdAndProductCommentId(
                            productComment.getProductId(),
                            productComment.getProductCommentId()
                    ));
                }
            }
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("error : " + err.toString());
        }
        return res;
    }

    @PostMapping("/delete")
    public Object delete(ProductComment productComment){
        APIResponse res = new APIResponse();
        try{
            productCommentRepository.deleteById(productComment.getProductCommentId());
            res.setStatus(1);
            res.setMessage("delete productComment");
            res.setData(productComment);
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("err : " + err.toString());
        }
        return res;
    }

    @PostMapping("/list_product_comment")
    public Object listProductComment(ProductComment productComment, Integer productId, Integer profileRegisterId, Integer status){
        APIResponse res = new APIResponse();
        try {
            if(productId != null){
                List<ProductComment> lstProductByProductId = productCommentRepository.findByProductId(
                        productComment.getProductId()
                );
                        System.out.println("********* lstProductByProductId ********" + lstProductByProductId);
                if (lstProductByProductId == null){
                    res.setStatus(0);
                    res.setMessage("don't have by productId");
                } else {
                    res.setStatus(1);
                    res.setMessage("show list by productId");
                    res.setData(lstProductByProductId);
                }
            }else if (profileRegisterId != null){
                List<ProductComment> lstProductByProfileRegisterId = productCommentRepository.findByProfileRegisterId(
                        productComment.getProfileRegisterId()
                );
                System.out.println("********* lstProductByProfileRegisterId ********" + lstProductByProfileRegisterId);
                if (lstProductByProfileRegisterId == null){
                    res.setStatus(0);
                    res.setMessage("don't have by profileRegisterId");
                } else {
                    res.setStatus(1);
                    res.setMessage("show list by profileRegisterId");
                    res.setData(lstProductByProfileRegisterId);
                }
            }else if(status != null){
                List<ProductComment> lstProductByStatus = productCommentRepository.findByStatus(
                        productComment.getStatus()
                );
                System.out.println("********* lstProductByStatus ********" + lstProductByStatus);
                if (lstProductByStatus == null){
                    res.setStatus(0);
                    res.setMessage("don't have by status");
                } else {
                    res.setStatus(1);
                    res.setMessage("show list by status");
                    res.setData(lstProductByStatus);
                }
            }else {
                List<ProductComment> lstAll = productCommentRepository.findAll();
                if (lstAll == null){
                    res.setStatus(0);
                    res.setMessage("don't have all");
                } else {
                    res.setStatus(1);
                    res.setMessage("show list all");
                    res.setData(lstAll);
                }
            }
        } catch (Exception err){
            res.setStatus(-1);
            res.setMessage("err : " + err.toString());
        }
        return res;
    }

    @PostMapping("/update_status")
    public Object updateStatus(ProductComment productComment){
        APIResponse res = new APIResponse();
        try {
            Integer status = productCommentRepository.updateProductCommentAdminByProductCommentId(
                    productComment.getStatus(),
                    productComment.getProductCommentId()
            );
            System.out.println("status : " + status);
            if(status == 1){
                res.setStatus(1);
                res.setMessage("edit");
                res.setData(productCommentRepository.findByProductCommentId(productComment.getProductCommentId()));
            }
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("error : " + err.toString());
        }
        return res;
    }

    @PostMapping("/detail")
    public Object detail(ProductComment productComment){
        APIResponse res = new APIResponse();
        try {
            ProductComment dbProduct = productCommentRepository.findByProductCommentId(productComment.getProductCommentId());
            if( dbProduct == null ){
                res.setStatus(0);
                res.setMessage("no product");
                res.setData(productComment);
            } else {
                res.setStatus(1);
                res.setMessage("success");
                res.setData(dbProduct);
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
        try{
            res.setStatus(1);
            res.setMessage("list all");
            res.setData(productCommentRepository.findAll());
        }catch (Exception err){
            res.setStatus(-1);
            res.setMessage("err : " + err.toString());
        }
        return res;
    }
}
