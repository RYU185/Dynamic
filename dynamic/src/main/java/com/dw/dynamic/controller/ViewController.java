package com.dw.dynamic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/dynamic/index.html")
    public String index() {
        return "forward:/index.html";
    }

    @GetMapping("/dynamic/myPurchaseHistory.html")
    public String myPurchaseHistory() {
        return "forward:/myPurchaseHistory.html";
    }

    @GetMapping("/dynamic/businessoperator_1.html")
    public String businessoperator_1() {
        return "forward:/businessoperator_1.html";
    }

    @GetMapping("/dynamic/employee_information.html")
    public String employee_information() {
        return "forward:/employee_information.html";
    }

    @GetMapping("/dynamic/employee_modify.html")
    public String employee_modify() {
        return "forward:/employee_modify.html";
    }

    @GetMapping("/dynamic/joinMembership.html")
    public String joinMembership() {
        return "forward:/joinMembership.html";
    }

    @GetMapping("/dynamic/myCourse.html")
    public String myCourse() {
        return "forward:/myCourse.html";
    }

    @GetMapping("/dynamic/mypage.html")
    public String mypage() {
        return "forward:/mypage.html";
    }

    @GetMapping("/dynamic/part_time.html")
    public String part_time() {
        return "forward:/part_time.html";
    }

    @GetMapping("/dynamic/regural_part_time_employee.html")
    public String regural_part_time_employee() {
        return "forward:/regural_part_time_employee.html";
    }

}
