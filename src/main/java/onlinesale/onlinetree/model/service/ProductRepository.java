package onlinesale.onlinetree.model.service;

import onlinesale.onlinetree.model.table.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    public Product findByProductIdAndProductName(Integer productId, String productName);

    public Product findByProductId(Integer productId);

    public List<Product> findByProductType(Integer productType);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE product " +
            "SET product_name = :product_name, " +
            "status = :status, " +
            "product_type = :product_type, " +
            "price = :price, " +
            "description = :description, " +
            "shipping_cost = :shipping_cost, " +
            "product_pic = :product_pic, " +
            "pic_one = :pic_one, " +
            "pic_two = :pic_two, " +
            "pic_three = :pic_three, " +
            "pic_four = :pic_four " +
            "WHERE product_id = :product_id")
    public Integer updateProduct(@Param("product_name") String productName,
                                 @Param("status") Boolean status,
                                 @Param("product_type") Integer productType,
                                 @Param("price") Integer price,
                                 @Param("description") String description,
                                 @Param("shipping_cost") Integer shippingCost,
                                 @Param("product_pic") String productPic,
                                 @Param("pic_one") String picOne,
                                 @Param("pic_two") String picTwo,
                                 @Param("pic_three") String picThree,
                                 @Param("pic_four") String picFour,
                                 @Param("product_id") Integer productId);
}
