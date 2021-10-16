package onlinesale.onlinetree.model.table;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@ToString
@Data
@Entity(name = "deal_sale")
public class DealSale {

    @Id
    @Column(name = "deal_sale_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dealSaleId;

    //from t.collectProduct เพื่อเช็คว่า product ที่เลือกเป็นอันไหน ใช้หรือยัง?
//    @Column(name = "collect_product_id")
//    private int collectProductId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "order_amount_id")
    private int orderAmountId;

//    @Column(name = "product_id")
//    private int productId;

    @Column(name = "profile_register_id")
    private int profileRegisterId;

    @Column(name = "profile_address")
    private String profileAddress;

    @Column(name = "product_price")
    private int productPrice;

    @Column(name = "discount_price")
    private int discountPrice;

    //จำนวนที่สั่ง
    @Column(name = "quantity")
    private int quantity;

    //0 = false, 1 = true
    @Column(name = "status")
    private int status;

    //0 = false, 1 = true
    @Column(name = "cancel")
    private String cancel;

    @Column(name = "order_date")
    private LocalDateTime orderDate = LocalDateTime.now(ZoneId.of("UTC+07:00"));
}
