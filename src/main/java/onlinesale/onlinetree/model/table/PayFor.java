package onlinesale.onlinetree.model.table;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@ToString
@Data
@Entity(name = "pay_for")
public class PayFor {

    @Id
    @Column(name = "pay_for_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int PayForId;

    @Column(name = "pay_for_status")
    private boolean payForStatus;

    @Column(name = "transfer_slip")
    private String transferSlip;

    @Column(name = "pay_for_date")
    private LocalDateTime payForDate = LocalDateTime.now(ZoneId.of("UTC+07:00"));

    @Column(name = "use_account_pay")
    private String useAccountPay;

    @Column(name = "pay_for_price")
    private int payForPrice;

    @Column(name = "order_id")
    private int orderId;

    @Column(name = "profile_register_id")
    private int profileRegisterId;

    @Column(name = "email")
    private String email;

    @Column(name = "tel")
    private int tel;
}
