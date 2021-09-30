package onlinesale.onlinetree.model.table;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@ToString
@Data
@Entity(name = "order_amount")
public class OrderAmount {

    @Id
    @Column(name = "order_amount_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderAmountId;

    //คำนวนมาตั้งแต่หน้าบ้านแล้ว ว่าราคารวมของสินค้าทั้งหมดเท่าไหร่ ซื้อกี่ชิ้นก็รวมให้หมดแล้ว
    @Column(name = "amount_order")
    private int amountOrder;

    @Column(name = "is_discount")
    private int isDiscount;

    @Column(name = "profile_register_id")
    private int profileRegisterId;

    @Column(name = "discount_for_friend_id")
    private int discountForFriendId;

    //1 = ใช้แล้ว, 0 = ยังไม่ใช้
    @Column(name = "status")
    private int status;

    @Column(name = "update_date")
    private LocalDateTime updateDate = LocalDateTime.now(ZoneId.of("UTC+07:00"));

}
