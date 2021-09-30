package onlinesale.onlinetree.model.service;

import onlinesale.onlinetree.model.table.CollectProduct;
import onlinesale.onlinetree.model.table.DealSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DealSaleRepository extends JpaRepository<DealSale, Integer> {

    public DealSale findByProfileRegisterIdAndOrderIdAndProductId(
            Integer profileRegisterId,
            Integer orderId,
            Integer productId
    );

    @Query(value = "SELECT * FROM deal_sale " +
            "WHERE profile_register_id = :profile_register_id " +
            "AND order_amount_id = :order_amount_id", nativeQuery = true)
    public List<DealSale> lstDealSaleByProfileRegisterIdAndOrderAmountId(
            @Param("profile_register_id") Integer profileRegisterId,
            @Param("order_amount_id") Integer orderAmountId);
}
