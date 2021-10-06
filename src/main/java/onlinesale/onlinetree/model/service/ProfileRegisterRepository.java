package onlinesale.onlinetree.model.service;

import onlinesale.onlinetree.model.table.CollectProduct;
import onlinesale.onlinetree.model.table.ProfileRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProfileRegisterRepository extends JpaRepository<ProfileRegister, Integer> {

    public ProfileRegister findByUserNameAndPassWord(String userName, String passWord);

    public ProfileRegister findByUserName(String userName);

    public ProfileRegister findByProfileRegisterId(int ProfileRegisterId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE profile_register " +
            "SET prefix = :prefix, " +
            "first_name = :first_name, " +
            "last_name = :last_name, " +
            "email = :email, " +
            "tel = :tel, " +
            "address = :address, " +
            "status = :status " +
            "WHERE user_name = :user_name " +
            "AND pass_word = :pass_word")
    public Integer updateProfileRegisterByUserNameAndPassWord(@Param("prefix") String prefix,
                                                              @Param("first_name") String firstName,
                                                              @Param("last_name") String lastName,
                                                              @Param("email") String email,
                                                              @Param("tel") String tel,
                                                              @Param("address") String address,
                                                              @Param("status") boolean status,
                                                              @Param("user_name") String userName,
                                                              @Param("pass_word") String passWord);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE profile_register " +
            "SET status = false " +
            "WHERE profile_register_id = :profile_register_id ")
    public Integer updateProfileRegisterByProfileRegisterId(@Param("profile_register_id") int profileRegisterId);

    @Query(value = "SELECT * FROM profile_register " +
            "WHERE status = :status " +
            "AND user_type != 0", nativeQuery = true)
    public List<ProfileRegister> lstProfileRegisterByStatus(@Param("status") boolean status);
}
