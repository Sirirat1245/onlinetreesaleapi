package onlinesale.onlinetree.model.table;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Data
@Entity(name = "discount_for_friend")
public class DiscountForFriend {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "discount_for_friend_id")
    private int discountForFriendId;

    @Column(name = "discount_name")
    private String discountName;

    @Column(name = "discount_volume")
    private int discountVolume;

    //รางวัลนี้ให้สิทธิ์คนที่ซื้อขั้นต่ำเท่าไหร่
    @Column(name = "minimum_amount")
    private int minimumAmount;

    //สำหรับเพิ่มจำนวนที่ให้ชวนเพื่อนมาใช้โค้ดนี้ได้
    @Column(name = "amount_to_give")
    private String amountToGive;

    @Column(name = "status")
    private int status;
}
