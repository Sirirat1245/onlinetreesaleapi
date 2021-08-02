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
    private int FriendGetFriendId;

    @Column(name = "invited_id")
    private int InvitedId;

    @Column(name = "inviter_id")
    private int inviterId;

    @Column(name = "discount_for_friend_id")
    private int discountForFriendId;

    @Column(name = "status")
    private String status;
}
