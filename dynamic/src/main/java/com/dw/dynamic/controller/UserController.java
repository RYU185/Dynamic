package com.dw.dynamic.controller;

import com.dw.dynamic.DTO.PasswordDTO;
import com.dw.dynamic.DTO.UserDTO;
import com.dw.dynamic.exception.UnauthorizedUserException;
import com.dw.dynamic.model.User;
import com.dw.dynamic.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping ("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(
                userService.registerUser(userDTO),
                HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUser(HttpServletRequest request){
        return new ResponseEntity<>(
                userService.getAllUsers(request),
                HttpStatus.OK);
    }

    @GetMapping("/id/{userName}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String userName, HttpServletRequest request) {
        return new ResponseEntity<>(
                userService.getUserById(userName, request),
                HttpStatus.OK);
    }
    @GetMapping("/realname/{realName}")
    public ResponseEntity<List<UserDTO>> getUserByRealName (@PathVariable String realName, HttpServletRequest request) {
        return new ResponseEntity<>(
                userService.getUserByRealName(realName, request),
                HttpStatus.OK);
    }

    @GetMapping("/exist-business-operator/{existBusinessOperator}")
    public ResponseEntity<List<UserDTO>>getUserByExistBusinessOperator (@PathVariable boolean existBusinessOperator, HttpServletRequest request ) {
        return new ResponseEntity<>(
                userService.getUserByExistBusinessOperator(existBusinessOperator, request),
                HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO,
                                        HttpServletRequest request) {
        String username = userDTO.getUserName();
        String password = userDTO.getPassword();

        if(userService.validateUser(username, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            return new ResponseEntity<>(
                    "Login successful",
                    HttpStatus.OK);
        }else {
            throw new UnauthorizedUserException("Authentication Failed");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletRequest response) {
        request.getSession().invalidate();
        return new ResponseEntity<>(
                "정상 로그아웃 되었습니다.",
                HttpStatus.OK);
    }

    @GetMapping("/find-user/email/{email}")
    public ResponseEntity<List<String>> getIdByEmail(@PathVariable String email) {
        return new ResponseEntity<>(
                userService.getIdByEmail(email),
                HttpStatus.OK);
    }

    @PostMapping("/modify-pw")
    public ResponseEntity<String> ModifyPw(@RequestBody PasswordDTO passwordDTO, HttpServletRequest request) {
        return new ResponseEntity<>(
                userService.ModifyPw(passwordDTO, request),
                HttpStatus.OK);
    }

    @PostMapping("/user-data")
    public ResponseEntity<UserDTO> ModifyUserData(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        return new ResponseEntity<>(
                userService.ModifyUserData(userDTO, request),
                HttpStatus.OK);
    }

    @PostMapping("/user-business-number")
    public ResponseEntity<UserDTO> saveUserBusinessNumber(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        return new ResponseEntity<>(
                userService.saveUserBusinessNumber(userDTO, request),
                HttpStatus.OK);
    }

    @PostMapping("/add/point")
    public ResponseEntity<UserDTO> addPoint(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        return new ResponseEntity<>(
                userService.addPoint(userDTO, request),
                HttpStatus.OK);
    }

    @PostMapping("/use/point")
    public ResponseEntity<UserDTO> usePoint(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        return new ResponseEntity<>(
                userService.usePoint(userDTO, request),
                HttpStatus.OK);
    }
}