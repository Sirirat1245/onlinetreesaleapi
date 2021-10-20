package onlinesale.onlinetree.model.table;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Data
@Entity(name = "billing_delivery")
public class BillingDelivery {

    @Id
    @Column(name = "billing_delivery_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int BillingDeliveryId;

    @Column(name = "profile_register_id")
    private int profileRegisterId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "product_price")
    private int productPrice;

    @Column(name = "discount_price")
    private int discountPrice;

    // 0 = รอยืนยันจากแอดมิน (ใช้ครั้งแรกเมื่อกดยืนยัน รอแอดมินมาอนุมัติ), 1 = รอการชำระเเงิน, 2 = เตรียมจัดส่ง, 3 = ส่งแล้วสำเร็จ
    @Column(name = "delivery_status")
    private int deliveryStatus;

    // 0 = false, 1 = true
    @Column(name = "pick_up_status")
    private boolean pickUpStatus;

    @Column(name = "order_date")
    private LocalDateTime orderDate;
}
