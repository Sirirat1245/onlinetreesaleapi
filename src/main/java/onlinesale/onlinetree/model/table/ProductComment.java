package onlinesale.onlinetree.model.table;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@ToString
@Data
@Entity(name = "product_comment")
public class ProductComment {

    @Id
    @Column(name = "product_comment_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int productCommentId;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "profile_register_id")
    private int profileRegisterId;

    @Column(name = "comment", columnDefinition = "text")
    private String comment;

    //0: pending, 1: approve, 2: disapproved
    @Column(name = "status")
    private int status;

    @Column(name = "create_date")
    private LocalDateTime createDate = LocalDateTime.now(ZoneId.of("UTC+07:00"));
}
