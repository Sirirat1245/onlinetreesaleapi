package onlinesale.onlinetree.model.table;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Data
@Entity(name = "category_product")
public class CategoryProduct {

    @Id
    @Column(name = "category_product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int categoryProductId;

    @Column(name = "category_name")
    private String categoryName;
}
