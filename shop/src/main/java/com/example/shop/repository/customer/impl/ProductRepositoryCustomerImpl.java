package com.example.shop.repository.customer.impl;

import com.example.shop.dto.NewSearch;
import com.example.shop.entity.Product;
import com.example.shop.enums.SortEnum;
import com.example.shop.repository.customer.ProductRepositoryCustomer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryCustomerImpl implements ProductRepositoryCustomer {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> search(NewSearch newSearch) {
        StringBuilder sql = new StringBuilder("SELECT * FROM product WHERE 1 = 1 ");

        if(newSearch.getMinPrice() != null){
            sql.append(" AND price >= " + newSearch.getMinPrice());
        }

        if(newSearch.getMaxPrice() != null){
            sql.append(" AND price <= " + newSearch.getMaxPrice());
        }

        if(newSearch.getCategories().size() > 0){
            List<String> categories = newSearch.getCategories().stream()
                    .map(category -> "'" + category + "'") // thêm dấu nháy đơn nếu là chuỗi
                    .collect(Collectors.toList());

            String categoriesSql = String.join(", ", categories); // tạo chuỗi danh sách phân cách bằng dấu phẩy

            sql.append(" AND category IN (" + categoriesSql + ")");
        }

        if(newSearch.getTypeSort().equals(SortEnum.DECS_PRICE)){
            sql.append(" order by price desc ");
        }

        if(newSearch.getTypeSort().equals(SortEnum.ORDER_PRICE)){
            sql.append(" order by price ");
        }

        if(newSearch.getTypeSort().equals(SortEnum.NEW)){
            sql.append(" order by create_date ");
        }

        if(newSearch.getTypeSort().equals(SortEnum.SALE)){
            sql.append(" order by discount ");
        }

        Query query = entityManager.createNativeQuery(sql.toString(), Product.class);
        return query.getResultList();
    }
}
