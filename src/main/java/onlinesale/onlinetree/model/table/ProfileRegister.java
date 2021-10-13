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

    @Column(name = "pass_word")
    private String passWord;

    @Column(name = "prefix")
    private String prefix;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "tel")
    private String tel;

    @Column(name = "address")
    private String address;

    //code ที่เอามาจากเพื่อน
    @Column(name = "code_get_friend")
    private String codeGetFriend;

    //code ของเราที่เอาให้เพื่อนได้
    @Column(name = "my_code")
    private String myCode;

    //true = active, false = inactive
    @Column(name = "status")
    private boolean status;

    @Column(name = "user_type")
    private int userType;
}
