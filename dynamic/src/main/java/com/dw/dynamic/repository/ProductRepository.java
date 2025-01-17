package com.dw.dynamic.repository;

import com.dw.dynamic.DTO.CategoryEnrollmentAndIncomeDTO;
import com.dw.dynamic.DTO.CourseEnrollmentAndIncomeDTO;
import com.dw.dynamic.DTO.PayrollSubscriptionsEnrollmentAndIncomeDTO;
import com.dw.dynamic.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {
    @Query("select c from Course c where c.title like %:title%")
    List<Product> findCourseByTitleLike(String title);

    @Query("select ps from PayrollSubscription ps where ps.title like %:title%")
    List<Product> findPayrollSubscriptionByTitleLike(String title);

    @Query("select new com.dw.dynamic.DTO.CourseEnrollmentAndIncomeDTO(c.title,count(ph),sum(ph.product.price))" +
            "from Course c join PurchaseHistory ph on c.id = ph.product.id group by c.title")
    public List<CourseEnrollmentAndIncomeDTO> getCoursesEnrollmentsAndIncomes();


    @Query("select new com.dw.dynamic.DTO.PayrollSubscriptionsEnrollmentAndIncomeDTO(ps.title,count(ph),sum(ph.product.price))" +
            "from PayrollSubscription ps join PurchaseHistory ph on ps.id = ph.product.id group by ps.title")
    public List<PayrollSubscriptionsEnrollmentAndIncomeDTO> getPayrollSubscriptionsEnrollmentsAndIncomes();

    @Query("select new com.dw.dynamic.DTO.CategoryEnrollmentAndIncomeDTO(c.name,count(ph),sum(ph.product.price))" +
            "from Category c join PurchaseHistory ph on c.id = ph.product.id group by c.name")
    public List<CategoryEnrollmentAndIncomeDTO> getCategoryEnrollmentsAndIncomes();

}
