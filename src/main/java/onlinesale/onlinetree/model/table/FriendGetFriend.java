package onlinesale.onlinetree.model.table;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Data
@Entity(name = "friend_get_friend")
public class FriendGetFriend {

    @Id
    @Column(name = "friend_get_friend_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int friendGetFriendId;

    //ผู้ถูกเชิญ
    @Column(name = "invited_id")
    private int invitedId;

    //ผู้เชิญ
    @Column(name = "inviter_id")
    private int inviterId;

    @Column(name = "discount_for_friend_id")
    private int discountForFriendId;

    //0 = ยังไม่เคย active, 1 = active, 2 = suspend
    @Column(name = "status_friend")
    private int statusFriend;

    //เก็บจำนวนที่เราชวนเพื่อนไป ว่าชวนไปเท่าไหร่แล้ว เพื่อนำไปเช็คกับ F.amountToGive
    //@Column(name = "amount_code")
    //private String amountCode;
}
