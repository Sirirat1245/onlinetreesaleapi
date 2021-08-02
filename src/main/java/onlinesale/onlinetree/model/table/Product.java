package onlinesale.onlinetree.model.table;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Data
@Entity(name = "product")
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private int price;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "shipping_cost")
    private int shippingCost;

    @Column(name = "product_pic")
    private String productPic;

    @Column(name = "pic_one")
    private String picOne;

    @Column(name = "pic_two")
    private String picTwo;

    @Column(name = "pic_three")
    private String picThree;

    @Column(name = "pic_four")
    private String picFour;
}
