package com.dw.dynamic.service;

import com.dw.dynamic.exception.PermissionDeniedException;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.model.Category;
import com.dw.dynamic.model.User;
import com.dw.dynamic.repository.CategoryRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserService userService;

    public List<Category> getAllCategorys(){
        return categoryRepository.findAll().stream().toList();
    }

    public Category getCategoryById(String id){
        return categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("존재하지 않는 카테고리입니다"));
    }

    public Category saveCategory(Category category, HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        if (!currentUser.getAuthority().getAuthorityName().equals("ADMIN")){
            throw new PermissionDeniedException("권한이 없습니다");
        }

        Category category1 = new Category();
        category1.setName(category.getName());

        return categoryRepository.save(category1);
    }

    public String deleteCategory(String name, HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        if (!currentUser.getAuthority().getAuthorityName().equals("ADMIN")){
            throw new PermissionDeniedException("권한이 없습니다");
        }

        Category category = categoryRepository.findByName(name)
                .orElseThrow(()-> new ResourceNotFoundException("존재하는 카테고리가 없습니다"));

        categoryRepository.delete(category);

        return "카테고리가 정상적으로 삭제되었습니다.";
    }
}