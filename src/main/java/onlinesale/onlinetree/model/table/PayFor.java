package onlinesale.onlinetree.model.table;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@ToString
@Data
@Entity(name = "pay_for")
public class PayFor {

    @Id
    @Column(name = "pay_for_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int payForId;

    @Column(name = "is_pay_for_status")
    private Boolean isPayForStatus;

    @Column(name = "transfer_slip")
    private String transferSlip;

    @Column(name = "pay_for_day")
    private String payForDay;

    @Column(name = "pay_for_time")
    private String payForTime;

//    @Column(name = "pay_for_date")
//    public LocalDateTime payForDate = LocalDateTime.now(ZoneId.of("UTC+07:00"));

    @Column(name = "use_account_pay")
    private String useAccountPay;

    @Column(name = "pay_for_price")
    private Double payForPrice;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "profile_register_id")
    private int profileRegisterId;

    @Column(name = "email")
    private String email;

    @Column(name = "tel")
    private String tel;

    //for check time by admin
    @Column(name = "date_time")
    public LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("UTC+07:00"));
}
