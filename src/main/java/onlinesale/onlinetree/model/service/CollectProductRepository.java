package onlinesale.onlinetree.model.service;

import onlinesale.onlinetree.model.table.CollectProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface CollectProductRepository extends JpaRepository<CollectProduct, Integer> {

    public CollectProduct findByProductId(Integer productId);

    public CollectProduct findByProductIdAndProfileRegisterId(Integer productId, Integer profileRegisterId);

    List<CollectProduct> findByProfileRegisterId(Integer profileRegisterId);

    @Query(value = "SELECT * FROM collect_product " +
            "WHERE profile_register_id = :profile_register_id " +
            "AND status_buy = 0", nativeQuery = true)
    public List<CollectProduct> lstCollectProductByProfileRegisterIdAndStatusBuy(
            @Param("profile_register_id") Integer profileRegisterId);

    @Query(value = "SELECT * FROM collect_product " +
            "WHERE profile_register_id = :profile_register_id " +
            "AND is_status = 1", nativeQuery = true)
    public List<CollectProduct> lstCollectProductByProfileRegisterIdAndStatusTrue(
            @Param("profile_register_id") Integer profileRegisterId);

    @Query(value = "SELECT SUM(price) " +
            "FROM collect_product " +
            "WHERE profile_register_id = :profile_register_id " +
            "AND is_status = 1", nativeQuery = true)
    public Integer sumCollectProductByProfile(@Param("profile_register_id") Integer profileRegisterId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE collect_product " +
            "SET is_status = :is_status, " +
            "amount = :amount, " +
            "price = :price " +
            "WHERE product_id = :product_id " +
            "AND profile_register_id = :profile_register_id")
    public Integer updateCollectProduct(@Param("is_status") Integer isStatus,
                                        @Param("amount") Integer amount,
                                        @Param("price") Integer price,
                                        @Param("product_id") Integer productId,
                                        @Param("profile_register_id") Integer profileRegisterId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE collect_product " +
            "SET order_amount_id = :order_amount_id " +
            "WHERE profile_register_id = :profile_register_id " +
            "AND is_status = 1")
    public Integer updateOrderAmountId(@Param("order_amount_id") Integer orderAmountId,
                                       @Param("profile_register_id") Integer profileRegisterId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE collect_product " +
            "SET order_amount_id = :order_amount_id " +
            "WHERE profile_register_id = :profile_register_id " +
            "AND is_status = 1 " +
            "AND status_buy = 0")
    public Integer updateOrderAmountIdAndStatusBuyFalse(@Param("order_amount_id") Integer orderAmountId,
                                       @Param("profile_register_id") Integer profileRegisterId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE collect_product " +
            "SET status_buy = 1 " +
            "WHERE order_amount_id = :order_amount_id " +
            "AND profile_register_id = :profile_register_id")
    public Integer updateStatusBuyTrue(@Param("order_amount_id") Integer orderAmountId,
                                       @Param("profile_register_id") Integer profileRegisterId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE collect_product " +
            "SET is_status = 0 " +
            "WHERE order_amount_id = :order_amount_id " +
            "AND profile_register_id = :profile_register_id")
    public Integer updateIsStatusFalse(@Param("order_amount_id") Integer orderAmountId,
                                       @Param("profile_register_id") Integer profileRegisterId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE collect_product " +
            "SET status_buy = 1 " +
            "WHERE product_id = :product_id " +
            "AND profile_register_id = :profile_register_id")
    public Integer updateCollectProductForBuy(@Param("product_id") Integer productId,
                                              @Param("profile_register_id") Integer profileRegisterId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE collect_product " +
            "SET status_buy = 0," +
            "is_status = 0 " +
            "WHERE order_amount_id = :order_amount_id " +
            "AND profile_register_id = :profile_register_id")
    public Integer updateCollectProductForCancelDealSale(@Param("order_amount_id") int orderAmountId,
                                                         @Param("profile_register_id") int profileRegisterId);

    @Query(value = "SELECT * FROM collect_product " +
            "WHERE profile_register_id = :profile_register_id " +
            "AND is_status = :is_status AND status_buy = 0", nativeQuery = true)
    public List<CollectProduct> lstCollectProductByProfileRegisterIdAndIsStatus(
            @Param("profile_register_id") Integer profileRegisterId,
            @Param("is_status") Integer isStatus);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE collect_product " +
            "SET amount = :amount, price = :price " +
            "WHERE collect_product_id = :collect_product_id")
    public Integer updateAmountById(@Param("collect_product_id") Integer collectProductId, @Param("amount") Integer amount, @Param("price") Integer price);
}
