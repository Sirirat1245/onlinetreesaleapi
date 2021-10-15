package onlinesale.onlinetree.model.service;

import onlinesale.onlinetree.model.table.BillingDelivery;
import onlinesale.onlinetree.model.table.CollectProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BillingDeliveryRepository extends JpaRepository<BillingDelivery, Integer> {

    public BillingDelivery findByProfileRegisterIdAndOrderId(int profileRegisterId, int orderId);

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
                                         @Param("order_id") int orderId);

}
