package onlinesale.onlinetree.model.table;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Data
@Entity(name = "collect_product")
public class CollectProduct {

    @Id
    @Column(name = "collect_product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int collectProductId;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "profile_register_id")
    private int profileRegisterId;
}
