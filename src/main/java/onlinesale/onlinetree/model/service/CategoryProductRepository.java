package onlinesale.onlinetree.model.service;

import onlinesale.onlinetree.model.table.CategoryProduct;
import onlinesale.onlinetree.model.table.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryProductRepository extends JpaRepository<CategoryProduct, Integer> {

    public CategoryProduct findByCategoryProductIdAndCategoryName(Integer categoryProductId, String categoryName);

    public CategoryProduct findByCategoryName(String categoryName);

    public CategoryProduct findByCategoryProductId(Integer categoryProductId);

    public List<CategoryProduct> findByProductType(Integer productType);

    @Query(value = "SELECT COUNT(*) FROM category_product",nativeQuery = true)
    public Integer getAmountCategoryProduct();

    @Query(value = "SELECT *  FROM category_product ORDER BY product_type DESC LIMIT 1",nativeQuery = true)
    public CategoryProduct getLastByProductType();

    @Query(value = "SELECT *  FROM category_product WHERE product_type = :product_type",nativeQuery = true)
    public CategoryProduct findCategoryProduct(@Param("product_type") Integer productType);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE category_product " +
            "SET category_name = :category_name, " +
            "product_type = :product_type, " +
            "category_pic = :category_pic " +
            "WHERE category_product_id = :category_product_id")
    public Integer updateCategoryProduct(@Param("category_name") String categoryName,
                                         @Param("product_type") Integer productType,
                                         @Param("category_pic") String categoryPic,
                                         @Param("category_product_id") Integer categoryProductId);
}
