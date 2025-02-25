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
    public String myCourse() {return "forward:/myCourse.html";}

    @GetMapping("/dynamic/mypage.html")
    public String mypage() {
        return "forward:/mypage.html";
    }

    @GetMapping("/dynamic/part_time.html")
    public String part_time() {
        return "forward:/part_time.html";
    }

    @GetMapping("/dynamic/regural_part_time_employee.html")
    public String regural_part_time_employee() {return "forward:/regural_part_time_employee.html";}

    @GetMapping("/dynamic/add_employee.html")
    public String add_employee() {return "forward:/add_employee.html";}

    @GetMapping("/dynamic/add_part_time.html")
    public String add_part_time() {return "forward:/add_part_time.html";}

    @GetMapping("/dynamic/add_regural_part_time.html")
    public String add_regural_part_time() {return "forward:/add_regural_part_time.html";}

    @GetMapping("/dynamic/board.html")
    public String board() {return "forward:/board.html";}

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

    @GetMapping("/dynamic/my_payrolltemplate.html")
    public String my_payrolltemplate() {return "forward:/my_payrolltemplate.html";}

    @GetMapping("/dynamic/my_point.html")
    public String my_point() {return "forward:/my_point.html";}

    @GetMapping("/dynamic/notice_detail.html")
    public String notice_detail() {return "forward:/notice_detail.html";}

    @GetMapping("/dynamic/product.html")
    public String product() {return "forward:/product.html";}

    @GetMapping("/dynamic/main_board.html")
    public String main_board() {return "forward:/main_board.html";}




}
