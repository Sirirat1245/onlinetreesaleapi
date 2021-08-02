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

    @Column(name = "order_amount")
    private int orderAmount;

    @Column(name = "is_discount")
    private boolean isDiscount;

    @Column(name = "update_date")
    private LocalDateTime updateDate = LocalDateTime.now(ZoneId.of("UTC+07:00"));

}
