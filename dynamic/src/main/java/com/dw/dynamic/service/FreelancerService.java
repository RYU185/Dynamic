package com.dw.dynamic.service;

import com.dw.dynamic.exception.InvalidRequestException;
import com.dw.dynamic.model.Freelancer;
import com.dw.dynamic.repository.FreelancerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreelancerService {
    @Autowired
    FreelancerRepository freelancerRepository;

    public List<Freelancer> getAllFreelancer(){
        try {
            return freelancerRepository.findAll();
        }catch (InvalidRequestException e){
            throw new InvalidRequestException("정상적인 요청이 아닙니다");
        }
    }
}
