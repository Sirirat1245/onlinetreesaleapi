package onlinesale.onlinetree.model.table;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Data
@Entity(name = "collect_product")
public class CollectProduct {

    @Id
    @Column(name = "collect_product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int collectProductId;

    @Column(name = "order_amount_id")
    private int orderAmountId;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "profile_register_id")
    private int profileRegisterId;

    //จำนวนที่เลือก
    @Column(name = "amount")
    private int amount;

    //ราคา 1 ชิ้น, ราคา 2 ชิ้น, ราคา 3 ชิ้น (เป็นราคาที่รวมตามจำนวนสินค้าที่เราเลือกมาแล้ว)
    @Column(name = "price")
    private int price;

    //0 = ชอบ, 1 = กดซื้อ, 2 = เลิกชอบ, 3 = ซื้ออีกครั้ง
    //กดซื้อจะยิง api update status = 1
    @Column(name = "is_status")
    private int isStatus;

    //0 = ยังไม่ซื้อ, 1 = ซื้อแล้ว
    @Column(name = "status_buy")
    private int statusBuy;
}
