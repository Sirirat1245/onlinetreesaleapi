package onlinesale.onlinetree.model.service;

import onlinesale.onlinetree.model.table.CategoryProduct;
import onlinesale.onlinetree.model.table.CollectProduct;
import onlinesale.onlinetree.model.table.PayFor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public interface PayForRepository extends JpaRepository<PayFor, Integer> {

    public PayFor findByOrderId(String orderId);

    public PayFor findByPayForId(Integer payForId);

    public PayFor findByProfileRegisterIdAndOrderId(int profileRegisterId, String orderId);
//
//    public List<PayFor> findByProfileRegisterId(Integer profileRegisterId);
//
//    public List<PayFor> findByOrderId(Integer orderId);
//
//    public List<PayFor> findByUseAccountPay(String useAccountPay);
//
//    public List<PayFor> findByDateTime(LocalDateTime dateTime);

    //for admin set payFor status = true when register paid
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE pay_for " +
            "SET is_pay_for_status = :is_pay_for_status, " +
            "transfer_slip = :transfer_slip, " +
            "pay_for_day = :pay_for_day, " +
            "pay_for_time = :pay_for_time, " +
            "use_account_pay = :use_account_pay, " +
            "pay_for_price = :pay_for_price, " +
            "email = :email, " +
            "tel = :tel, " +
            "date_time = :date_time " +
            "WHERE pay_for_id = :pay_for_id " +
            "AND profile_register_id = :profile_register_id " +
            "AND order_id = :order_id")
    public Integer updatePayFor(@Param("is_pay_for_status") Boolean isPayForStatus,
                                         @Param("transfer_slip") String transferSlip,
                                         @Param("pay_for_day") String payForDay,
                                         @Param("pay_for_time") String payForTime,
                                         @Param("use_account_pay") String useAccountPay,
                                         @Param("pay_for_price") Number payForPrice,
                                         @Param("email") String email,
                                         @Param("tel") String tel,
                                         @Param("date_time") LocalDateTime dateTime,
                                         @Param("pay_for_id") Integer payForId,
                                         @Param("profile_register_id") Integer profileRegisterId,
                                         @Param("order_id") String orderId);

    @Query(value = "SELECT * FROM pay_for " +
            "WHERE is_pay_for_status = :is_pay_for_status", nativeQuery = true)
    public List<PayFor> lstPayForByIsPayForStatus(
            @Param("is_pay_for_status") Boolean isPayForStatus);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE pay_for " +
            "SET is_pay_for_status = true " +
            "WHERE pay_for_id = :pay_for_id " +
            "AND profile_register_id = :profile_register_id")
    public Integer approvePayFor(@Param("pay_for_id") Integer payForId,
                                 @Param("profile_register_id") Integer profileRegisterId);
}
