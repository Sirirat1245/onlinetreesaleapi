package onlinesale.onlinetree.model.table;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Data
@Entity(name = "billing_delivery")
public class BillingDelivery {

    @Id
    @Column(name = "billing_delivery_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int BillingDeliveryId;

    @Column(name = "order_id")
    private int orderId;

    @Column(name = "delivery_status")
    private String deliveryStatus;

    @Column(name = "pick_up_status")
    private boolean pickUpStatus;
}
