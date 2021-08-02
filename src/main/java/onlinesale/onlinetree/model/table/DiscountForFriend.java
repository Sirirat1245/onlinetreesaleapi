package onlinesale.onlinetree.model.table;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Data
@Entity(name = "discount_for_friend")
public class DiscountForFriend {

    @Id
    @Column(name = "discount_for_friend_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int discountForFriendId;

    @Column(name = "discount_name")
    private String discountName;

    @Column(name = "discount_volume")
    private int discountVolume;
}
