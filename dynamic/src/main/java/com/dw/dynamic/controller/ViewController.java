package com.dw.dynamic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/dynamic/index.html")
    public String index() {
        return "forward:/index.html";
    }

    @GetMapping("/dynamic/after_login.html")
    public String after_login() {
        return "forward:/after_login.html";
    }

    @GetMapping("/dynamic/login.html")
    public String login() {
        return "forward:/login.html";
    }

    @GetMapping("/dynamic/myPurchaseHistory.html")
    public String myPurchaseHistory() {
        return "forward:/myPurchaseHistory.html";
    }

    @GetMapping("/dynamic/businessoperator_true.html")
    public String businessoperator_1() {
        return "forward:/businessoperator_true.html";
    }

    @GetMapping("/dynamic/businessoperator_false.html")
    public String businessoperatbusinessoperator_false() {return "forward:/businessoperator_false.html";}


    @GetMapping("/dynamic/joinMembership.html")
    public String joinMembership() {
        return "forward:/joinMembership.html";
    }

    @GetMapping("/dynamic/myCourse.html")
    public String myCourse() {return "forward:/myCourse.html";}

    @GetMapping("/dynamic/mypage.html")
    public String mypage() {
        return "forward:/mypage.html";
    }


    @GetMapping("/dynamic/cart.html")
    public String cart() {return "forward:/cart.html";}

    @GetMapping("/dynamic/edit_profile.html")
    public String edit_profile() {return "forward:/edit_profile.html";}

    @GetMapping("/dynamic/find_id.html")
    public String find_id() {return "forward:/find_id.html";}

    @GetMapping("/dynamic/formation_data.html")
    public String formation_data() {return "forward:/formation_data.html";}

    @GetMapping("/dynamic/guide_detail.html")
    public String guide_detail() {return "forward:/guide_detail.html";}

    @GetMapping("/dynamic/main_guide.html")
    public String main_guide() {return "forward:/main_guide.html";}

    @GetMapping("/dynamic/main_notice.html")
    public String main_notice() {return "forward:/main_notice.html";}

    @GetMapping("/dynamic/notice_detail.html")
    public String notice_detail() {return "forward:/notice_detail.html";}

    @GetMapping("/dynamic/my_payrolltemplate.html")
    public String my_payrolltemplate() {return "forward:/my_payrolltemplate.html";}

    @GetMapping("/dynamic/my_payrolltemplate_detail.html")
    public String my_payrolltemplate_detail() {return "forward:/my_payrolltemplate_detail.html";}

    @GetMapping("/dynamic/my_point.html")
    public String my_point() {return "forward:/my_point.html";}

    @GetMapping("/dynamic/product.html")
    public String product() {return "forward:/product.html";}

    @GetMapping("/dynamic/main_board.html")
    public String main_board() {return "forward:/main_board.html";}

    @GetMapping("/dynamic/board_detail.html")
    public String board_detail() {return "forward:/board_detail.html";}

    @GetMapping("/dynamic/free_payrolltemplate_information.html")
    public String free_payrolltemplate_information() {return "forward:/free_payrolltemplate_information.html";}

    @GetMapping("/dynamic/my_employee_payrolltemplate_information.html")
    public String my_employee_payrolltemplate_information() {return "forward:/my_employee_payrolltemplate_information.html";}

    @GetMapping("/dynamic/my_employee.html")
    public String my_employee() {return "forward:/my_employee.html";}

    @GetMapping("/dynamic/payrolltemplate.html")
    public String payrolltemplate() {return "forward:/payrolltemplate.html";}

    @GetMapping("/dynamic/my_employee_payrolltemplate.html")
    public String my_employee_payrolltemplate() {return "forward:/my_employee_payrolltemplate.html";}
    @GetMapping("/dynamic/product!.html")
    public String producth1() {return "forward:/product!.html";}







}
