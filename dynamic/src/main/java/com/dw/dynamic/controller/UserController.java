package com.dw.dynamic.controller;

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

    @GetMapping("/registeruser")
    public ResponseEntity<UserDTO> registerUser(@RequestParam UserDTO userDTO) {
        return new ResponseEntity<>(
                UserDTO.registerUser(userDTO),
                HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UserDTO userDTO) {
        return new ResponseEntity<>(
                userService.getUserById,
                HttpStatus.OK);
    }
    @GetMapping("/realname/{realname}")
    public ResponseEntity<List<UserDTO>> getUserByRealName (@PathVariable UserDTO userDTO) {
        return new ResponseEntity<>(
                userService.getUserByRealName,
                HttpStatus.OK);
    }

    @GetMapping("/exist-business-operator/{exist-business-operator}")
    public ResponseEntity<List<UserDTO>>getUserByExistBusinessOperator (@PathVariable boolean existBusinessOperator ) {
        return new ResponseEntity<>(
                userService.getUserByExistBusinessOperator,
                HttpStatus.OK);
    }

    @GetMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestParam UserDTO userDTO){
        return new ResponseEntity<>(
                userService.getAllUsers(userDTO),
                HttpStatus.CREATED);
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
    public ResponseEntity<UserDTO> getIdByEmail(@PathVariable String email) {
        return new ResponseEntity<>(
                UserService.getIdByEmail,
                HttpStatus.OK);
    }

    @PostMapping("/modify-pw-by-id-and-phonenumber")
    public ResponseEntity<UserDTO> ModifyPwByIDAndPhoneNumber(@RequestBody User userDTO) {
        return new ResponseEntity<>(
                UserService.ModifyPwByIDAndPhoneNumber,
                HttpStatus.OK);
    }

    @PostMapping("/user-data")
    public ResponseEntity<UserDTO> ModifyUserData(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(
                UserService.ModifyUserData,
                HttpStatus.OK);
    }

    @PostMapping("/user-business-number")
    public ResponseEntity<UserDTO> saveUserBusinessNumber(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(
                UserService.saveUserBusinessNumber,
                HttpStatus.OK);
    }

    @PostMapping("/add/point")
    public ResponseEntity<UserDTO> addPoint(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(
                UserService.addPoint,
                HttpStatus.OK);
    }

    @PostMapping("/use/point")
    public ResponseEntity<UserDTO> usePoint(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(
                UserService.usePoint,
                HttpStatus.OK);
    }
}

