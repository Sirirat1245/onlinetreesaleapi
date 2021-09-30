package onlinesale.onlinetree.model.service;

import onlinesale.onlinetree.model.table.CollectProduct;
import onlinesale.onlinetree.model.table.DiscountForFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DiscountForFriendRepository extends JpaRepository<DiscountForFriend, Integer> {

    public DiscountForFriend findByDiscountForFriendId(Integer discountForFriendId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE discount_for_friend " +
            "SET discount_name = :discount_name, " +
            "discount_volume = :discount_volume, " +
            "amount_to_give = :amount_to_give, " +
            "status = :status " +
            "WHERE discount_for_friend_id = :discount_for_friend_id")
    public Integer updateDiscountForFriend(@Param("discount_name") String discountName,
                                           @Param("discount_volume") int discountVolume,
                                           @Param("amount_to_give") String amountToGive,
                                           @Param("status") int status,
                                           @Param("discount_for_friend_id") int discountForFriendId);

    @Query(value = "SELECT * FROM discount_for_friend " +
            "WHERE status = :status", nativeQuery = true)
    public List<DiscountForFriend> lstDiscountForFriendByStatus(
            @Param("status") int status
    );

    @Query(value = "SELECT * FROM discount_for_friend " +
            "WHERE status = 1", nativeQuery = true)
    public DiscountForFriend findByDiscountForFriendStatusTrue();
}
