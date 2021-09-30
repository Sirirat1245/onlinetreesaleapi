package onlinesale.onlinetree.model.service;

import onlinesale.onlinetree.model.table.DiscountForFriend;
import onlinesale.onlinetree.model.table.FriendGetFriend;
import onlinesale.onlinetree.model.table.OrderAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderAmountRepository extends JpaRepository<OrderAmount, Integer> {

    public OrderAmount findByProfileRegisterId(int profileRegisterId);

    public OrderAmount findByProfileRegisterIdAndDiscountForFriendId(int ProfileRegisterId, int discountForFriendId);

    @Query(value = "SELECT * FROM order_amount " +
            "WHERE profile_register_id = :profile_register_id " +
            "AND discount_for_friend_id != 0", nativeQuery = true)
    public OrderAmount findByProfileRegisterIdAndGetDiscountForFriendId(
            @Param("profile_register_id") int profileRegisterId
    );

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE order_amount " +
            "SET amount_order = :amount_order, " +
            "discount_for_friend_id = :discount_for_friend_id, " +
            "is_discount = 1, " +
            "status = 1 " +
            "WHERE profile_register_id = :profile_register_id " +
            "AND order_amount_id = :order_amount_id")
    public Integer updateOrderAmountStatusTrue(@Param("amount_order") int amountOrder,
                                               @Param("discount_for_friend_id") int discountForFriendId,
                                               @Param("profile_register_id") int profileRegisterId,
                                               @Param("order_amount_id") int orderAmountId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE order_amount " +
            "SET amount_order = :amount_order, " +
            "is_discount = 0, " +
            "status = 0 " +
            "WHERE profile_register_id = :profile_register_id ")
    public Integer updateOrderAmountStatusFalse(@Param("amount_order") int amountOrder,
                                                @Param("profile_register_id") int profileRegisterId);

    @Query(value = "SELECT * FROM order_amount " +
            "WHERE profile_register_id = :profile_register_id ", nativeQuery = true)
    public List<OrderAmount> lstOrderAmount(
            @Param("profile_register_id") int profileRegisterId
    );

    @Query(value = "SELECT * FROM order_amount " +
            "WHERE profile_register_id = :profile_register_id " +
            "AND status = 0", nativeQuery = true)
    public List<OrderAmount> lstOrderAmountAndStatus(
            @Param("profile_register_id") int profileRegisterId
    );
}
