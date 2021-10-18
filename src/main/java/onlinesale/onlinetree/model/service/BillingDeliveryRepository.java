package onlinesale.onlinetree.model.service;

import onlinesale.onlinetree.model.table.BillingDelivery;
import onlinesale.onlinetree.model.table.CollectProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BillingDeliveryRepository extends JpaRepository<BillingDelivery, Integer> {

    public BillingDelivery findByProfileRegisterIdAndOrderId(int profileRegisterId, String orderId);
    public List<BillingDelivery> findByProfileRegisterId(Integer profileRegisterId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE billing_delivery " +
            "SET product_price = :product_price, " +
            "discount_price = :discount_price, " +
            "delivery_status = :delivery_status," +
            "pick_up_status = :pick_up_status " +
            "WHERE profile_register_id = :profile_register_id " +
            "AND order_id = :order_id")
    public Integer updateBillingDelivery(@Param("product_price") int productPrice,
                                         @Param("discount_price") int discountPrice,
                                         @Param("delivery_status") int deliveryStatus,
                                         @Param("pick_up_status") boolean pickUpStatus,
                                         @Param("profile_register_id") int profileRegisterId,
                                         @Param("order_id") String orderId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE billing_delivery " +
            "SET delivery_status = 1 " +
            "WHERE profile_register_id = :profile_register_id " +
            "AND order_id = :order_id")
    public Integer updateDeliveryStatusTrue(@Param("profile_register_id") int profileRegisterId,
                                            @Param("order_id") String orderId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE billing_delivery " +
            "SET delivery_status = 2 " +
            "WHERE profile_register_id = :profile_register_id " +
            "AND order_id = :order_id")
    public Integer updateDeliveryStatusPrepareToShip(@Param("profile_register_id") int profileRegisterId,
                                            @Param("order_id") String orderId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE billing_delivery " +
            "SET pick_up_status = true " +
            "WHERE profile_register_id = :profile_register_id " +
            "AND order_id = :order_id")
    public Integer updatePickup(@Param("profile_register_id") int profileRegisterId,
                                @Param("order_id") String orderId);

}
