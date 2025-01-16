package com.dw.dynamic.repository;

import com.dw.dynamic.DTO.CategoryEnrollmentAndIncomeDTO;
import com.dw.dynamic.DTO.CourseEnrollmentAndIncomeDTO;
import com.dw.dynamic.DTO.PayrollSubscriptionsEnrollmentAndIncomeDTO;
import com.dw.dynamic.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {
   // List<Product> findByTitleLike(String title);

    @Query("select com.dw.dynamic.DTO.CourseEnrollmentAndIncomeDTO(c.title,count(ph),sum(ph.product.price))" +
            "from Course c join PurchaseHistory ph on c.id = ph.product.id group by c.title")
    public List<CourseEnrollmentAndIncomeDTO> getCoursesEnrollmentsAndIncomes();


    @Query("select com.dw.dynamic.DTO.PayrollSubscriptionsEnrollmentAndIncomeDTO(ps.title,count(ph),sum(ph.product.price))" +
            "from PayrollSubscription ps join PurchaseHistory ph on ps.id = ph.product.id group by ps.title")
    public List<PayrollSubscriptionsEnrollmentAndIncomeDTO> getPayrollSubscriptionsEnrollmentsAndIncomes();

    @Query("select com.dw.dynamic.DTO.CategoryEnrollmentAndIncomeDTO(c.name,count(ph),sum(ph.product.price))" +
            "from Category c join PurchaseHistory ph on c.id = ph.product.id group by c.name")
    public List<CategoryEnrollmentAndIncomeDTO> getCategoryEnrollmentsAndIncomes();
}
