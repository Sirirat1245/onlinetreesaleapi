package onlinesale.onlinetree.model.service;

import onlinesale.onlinetree.model.table.CollectProduct;
import onlinesale.onlinetree.model.table.FriendGetFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FriendGetFriendRepository extends JpaRepository<FriendGetFriend, Integer> {

    //ถ้า status เป็น false ยังชวนซ้ำได้อยู่ เมื่อเขากลับมา active ใหม่ หลังจากที่ cancel ไปแล้ว
//    public FriendGetFriend findByInviterIdAndInvitedIdAndStatusFriend(
//            int inviterId,
//            int invitedId,
//            int statusFriend
//    );

    public FriendGetFriend findByInvitedIdAndInviterId(
            int invitedId,
            int inviterId
    );

    public FriendGetFriend findByInvitedIdOrInviterId(
            int invitedId,
            int inviterId
    );

    @Query(value = "SELECT * FROM friend_get_friend " +
            "WHERE inviter_id = :inviter_id", nativeQuery = true)
    public List<FriendGetFriend> listByInviterId(
            @Param("inviter_id") int inviterId
    );

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE friend_get_friend " +
            "SET status_friend = 1 " +
            "WHERE friend_get_friend_id = :friend_get_friend_id")
    public Integer updateStatusFriend(
            @Param("friend_get_friend_id") int friendGetFriendId
    );

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE friend_get_friend " +
            "SET status_friend = 1," +
            "discount_for_friend_id = :discount_for_friend_id " +
            "WHERE friend_get_friend_id = :friend_get_friend_id")
    public Integer updateStatusFriendAndAddDiscountForFriendId(
            @Param("discount_for_friend_id") int discountForFriendId,
            @Param("friend_get_friend_id") int friendGetFriendId
    );

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE friend_get_friend " +
            "SET status_friend = 2 " +
            "WHERE inviter_id = :inviter_id " +
            "OR invited_id = :invited_id")
    public Integer updateStatusSuspend(
            @Param("inviter_id") int inviterId,
            @Param("invited_id") int invitedId
    );
}
