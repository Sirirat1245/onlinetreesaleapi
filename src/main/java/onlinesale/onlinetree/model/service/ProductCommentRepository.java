package onlinesale.onlinetree.model.service;

import onlinesale.onlinetree.model.table.CollectProduct;
import onlinesale.onlinetree.model.table.ProductComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductCommentRepository extends JpaRepository<ProductComment, Integer> {

    public ProductComment findByProductIdAndProductCommentId(Integer ProductId, Integer ProductCommentId);

    public ProductComment findByProductCommentId(Integer ProductCommentId);

    public ProductComment findByProductIdAndProductCommentIdAndProfileRegisterId(
            Integer ProductId,
            Integer productCommentId,
            Integer profileRegisterId
    );

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE product_comment " +
            "SET comment = :comment, " +
            "status = :status "+
            "WHERE product_id = :product_id " +
            "AND profile_register_id = :profile_register_id " +
            "AND product_comment_id = :product_comment_id")
    public Integer updateProductCommentUser(@Param("comment") String comment,
                                            @Param("status") Integer status,
                                            @Param("product_id") Integer productId,
                                            @Param("profile_register_id") Integer profileRegisterId,
                                            @Param("product_comment_id") Integer productCommentId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE product_comment " +
            "SET status = :status " +
            "WHERE product_id = :product_id " +
            "AND product_comment_id = :product_comment_id")
    public Integer updateProductCommentAdmin(@Param("status") Integer status,
                                             @Param("product_id") Integer productId,
                                             @Param("product_comment_id") Integer productCommentId);

    //admin update product comment at status = 1: approve, 2: disapproved
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE product_comment " +
            "SET status = :status " +
            "WHERE product_comment_id = :product_comment_id")
    public Integer updateProductCommentAdminByProductCommentId(@Param("status") Integer status,
                                                               @Param("product_comment_id") Integer productCommentId);


    public List<ProductComment> findByProductId(Integer productId);

    public List<ProductComment> findByProfileRegisterId(Integer profileRegisterId);

    //admin list productComment for check status true, false (ตอนแอดมินจะเช็คเพื่ออนุมัติ)
    public List<ProductComment> findByStatus(Integer status);
}
