package onlinesale.onlinetree.model.service;

import onlinesale.onlinetree.model.table.CollectProduct;
import onlinesale.onlinetree.model.table.DealSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DealSaleRepository extends JpaRepository<DealSale, Integer> {

//    public DealSale findByProfileRegisterIdAndOrderIdAndProductId(
//            Integer profileRegisterId,
//            Integer orderId,
//            Integer productId
//    );

    public DealSale findByProfileRegisterIdAndOrderAmountId(
            int profileRegisterId,
            int orderAmountId
    );

    @Query(value = "SELECT * FROM deal_sale " +
            "WHERE profile_register_id = :profile_register_id " +
            "AND order_amount_id = :order_amount_id", nativeQuery = true)
    public List<DealSale> lstDealSaleByProfileRegisterIdAndOrderAmountId(
            @Param("profile_register_id") Integer profileRegisterId,
            @Param("order_amount_id") Integer orderAmountId);

    @Query(value = "SELECT * FROM deal_sale " +
            "WHERE status = :status", nativeQuery = true)
    public List<DealSale> lstDealSaleByStatus(
            @Param("status") int status);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO deal_sale(" +
            "order_id, " +
            "order_amount_id, " +
            "profile_register_id, " +
            "profile_address, " +
            "product_price, " +
            "discount_price, " +
            "quantity, " +
            "status, " +
            "cancel) " +
            "VALUES(:order_id, " +
            ":order_amount_id, " +
            ":profile_register_id, " +
            ":profile_address, " +
            ":product_price, " +
            ":discount_price, " +
            ":quantity, " +
            ":status, " +
            ":cancel)", nativeQuery = true)
    public DealSale saveDealSaleWithCondition(
            @Param("order_id") String orderId,
            @Param("order_amount_id") int orderAmountId,
            @Param("profile_register_id") int profileRegisterId,
            @Param("profile_address") String profileAddress,
            @Param("product_price") int productPrice,
            @Param("discount_price") int discountPrice,
            @Param("quantity") int quantity,
            @Param("status") int status,
            @Param("cancel") int cancel
    );

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE deal_sale " +
            "SET cancel = 1 " +
            "WHERE profile_register_id = :profile_register_id " +
            "AND order_id = :order_id")
    public Integer updateDealSaleCancel(@Param("profile_register_id") int profileRegisterId,
                                        @Param("order_id") String orderId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE deal_sale " +
            "SET status = 1 " +
            "WHERE profile_register_id = :profile_register_id " +
            "AND order_amount_id = :order_amount_id")
    public Integer adminUpdateStatusTrue(@Param("profile_register_id") int profileRegisterId,
                                        @Param("order_amount_id") int orderAmountId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE deal_sale " +
            "SET status = :status " +
            "WHERE profile_register_id = :profile_register_id " +
            "AND order_amount_id = :order_amount_id")
    public Integer adminUpdateStatusApprove(@Param("profile_register_id") int profileRegisterId,
                                            @Param("order_amount_id") int orderAmountId);
}
