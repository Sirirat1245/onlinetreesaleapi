package onlinesale.onlinetree.model.table;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Data
@Entity(name = "profile_register")
public class ProfileRegister {

    @Id
    @Column(name = "profile_register_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int profileRegisterId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String passWord;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "tel")
    private int tel;

    @Column(name = "address")
    private String address;

    @Column(name = "code_get_friend")
    private String codeGetFriend;

    @Column(name = "status")
    private boolean status;

    @Column(name = "userType")
    private int userType;
}
