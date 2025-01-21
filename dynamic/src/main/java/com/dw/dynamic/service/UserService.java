package com.dw.dynamic.service;
import com.dw.dynamic.DTO.PasswordDTO;
import com.dw.dynamic.DTO.UserDTO;
import com.dw.dynamic.enums.Gender;
import com.dw.dynamic.exception.PermissionDeniedException;
import com.dw.dynamic.exception.UnauthorizedUserException;
import com.dw.dynamic.model.User;
import com.dw.dynamic.repository.AuthorityRepository;
import com.dw.dynamic.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.dw.dynamic.exception.InvalidRequestException;
import com.dw.dynamic.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    AuthorityRepository authorityRepository;

    public UserDTO registerUser(UserDTO userDTO) {  // 회원가입: 새로운 사용자를 등록
        Optional<User> user = userRepository.findById(userDTO.getUserName());
        if (user.isPresent()) {
            throw new InvalidRequestException("already exist ID");
        }

        Gender gender;
        try {
            gender = Gender.valueOf(userDTO.getGender().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("Invalid gender value: " + userDTO.getGender());
        }

        return userRepository.save(
                new User(
                        userDTO.getUserName(),
                        userDTO.getCompanyName(),
                        userDTO.getRealName(),
                        passwordEncoder.encode(userDTO.getPassword()),
                        gender,
                        userDTO.getEmail(),
                        userDTO.getPhoneNumber(),
                        userDTO.getBusinessNumber(),
                        userDTO.getBusinessType(),
                        userDTO.isExistBusinessOperator(),
                        0,
                        authorityRepository.findById("USER")
                                .orElseThrow(() -> new ResourceNotFoundException("No role"))
                )
        ).toDTO();
    }

    public boolean validateUser(String username, String password) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new InvalidRequestException("Invalid Username"));
        return passwordEncoder.matches(password, user.getPassword());
    }

    public User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);  // 세션이 없으면 예외 처리
        if (session == null) {
            throw new UnauthorizedUserException("No Session exist");
        }
        String userName = (String) session.getAttribute("username");  // 세션에서 유저네임 반환
        return userRepository.findById(userName)
                .orElseThrow(() -> new InvalidRequestException("No username"));
    }

    public List<UserDTO> getAllUsers(HttpServletRequest request) { // 관리자가 전체 유저 조회
        User currentUser = getCurrentUser(request);
        if (!currentUser.getAuthority().getAuthorityName().equals("ADMIN")) {
            throw new PermissionDeniedException("권한이 없습니다.");
        }
        return userRepository.findAll().stream().map(User::toDTO).toList();
    }

    public UserDTO getUserById(String id, HttpServletRequest request) { //id를 통한 유저 조회
        User currentUser = getCurrentUser(request);
        if (!currentUser.getAuthority().getAuthorityName().equals("ADMIN")) {
            throw new PermissionDeniedException("권한이 없습니다");
        }
        Optional<User> users = userRepository.findById(id);
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("존재하지 않는 ID입니다.");
        }
        return userRepository.findById(id).map(User::toDTO).orElseThrow(() -> new ResourceNotFoundException("유저를 찾을 수 없습니다"));
    }

    public List<UserDTO> getUserByRealName(String realName, HttpServletRequest request) {
        // realName을 통한 유저 조회
        User currentUser = getCurrentUser(request);
        if (!currentUser.getAuthority().getAuthorityName().equals("ADMIN")) {
            throw new PermissionDeniedException("권한이 없습니다");
        }
        List<User> users = userRepository.findByRealNameLike("%" + realName + "%");

        if (users.isEmpty()) {
            throw new ResourceNotFoundException("존재하지 않는 실명입니다.");
        }
        return users.stream().map(User::toDTO).toList();
    }

    public List<UserDTO> getUserByExistBusinessOperator(boolean existBusinessOperator, HttpServletRequest request) {
        // 관리자가 기존 사업자 여부를 통한 유저 조회
        User currentUser = getCurrentUser(request);
        if (!currentUser.getAuthority().getAuthorityName().equals("ADMIN")) {
            throw new PermissionDeniedException("권한이 없습니다");
        }
        List<User> users = userRepository.findUserByExistBusinessOperator(existBusinessOperator);

        if (users.isEmpty()) {
            throw new ResourceNotFoundException("에러발생 : 기존 사업자 여부를 양식에 맞게 입력해주세요");
        }
        return userRepository.findUserByExistBusinessOperator(existBusinessOperator)
                .stream().map(User::toDTO).toList();
    }

    public String getIdByEmail(String email) { // 이메일로 통하여 아이디 찾기

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("존재하지 않는 이메일입니다");
        }
        return "아이디는 "+ user.getUserName()+" 입니다.";
    }

    public String ModifyPw(PasswordDTO passwordDTO, HttpServletRequest request) {
        User currentUser = getCurrentUser(request);
        if (currentUser == null){
            throw new IllegalArgumentException("올바르지 않은 접근입니다");
        }
        if (passwordDTO.getCurrentPassword() == null) {
            throw new IllegalArgumentException("현재 비밀번호를 입력해 주세요.");
        }
        if (!passwordEncoder.matches(passwordDTO.getCurrentPassword(), currentUser.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 올바르지 않습니다.");
        }

        if (!passwordDTO.getNewPassword().equals(passwordDTO.getConfirmNewPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }

        currentUser.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
        userRepository.save(currentUser);
        return "유저 id: "+ currentUser.getUserName() + " 님의 비밀번호가 정상적으로 변경되었습니다.";
    }

    public UserDTO ModifyUserData(UserDTO userDTO, HttpServletRequest request) { // 회원 정보 수정(이름, 이메일, 전화번호)
        User currentUser = getCurrentUser(request);
        if (currentUser == null){
            throw new IllegalArgumentException("올바르지 않은 접근입니다");
        }

        //이름, 이메일, 전화번호 이외는 수정이 불가능
        if (userDTO.getRealName() !=null){
            currentUser.setRealName(userDTO.getRealName());
        }
        if (userDTO.getEmail()!=null){
            currentUser.setPhoneNumber(userDTO.getEmail());
        }
        if (userDTO.getPhoneNumber()!=null){
            currentUser.setPhoneNumber(userDTO.getPhoneNumber());
        }

        if (userDTO.getUserName() !=null){
            throw new IllegalArgumentException("이름, 이메일, 전화번호 외에는 수정할 수 없습니다.");
        }
        if (userDTO.getRole() !=null){
            throw new IllegalArgumentException("이름, 이메일, 전화번호 외에는 수정할 수 없습니다.");
        }
        if (userDTO.getPassword() !=null){
            throw new IllegalArgumentException("이름, 이메일, 전화번호 외에는 수정할 수 없습니다.");
        }
        if (userDTO.getGender() !=null){
            throw new IllegalArgumentException("이름, 이메일, 전화번호 외에는 수정할 수 없습니다.");
        }
        if (userDTO.getPoint() != 0){
            throw new IllegalArgumentException("이름, 이메일, 전화번호 외에는 수정할 수 없습니다.");
        }
        if (userDTO.getBusinessNumber() !=null){
            throw new IllegalArgumentException("사업자 등록 번호 등록/수정을 이용해주세요");
        }
        if (userDTO.getBusinessType() !=null){
            throw new IllegalArgumentException("사업자 등록 번호 등록/수정을 이용해주세요");
        }
        if (userDTO.getCompanyName() !=null){
            throw new IllegalArgumentException("사업자 등록 번호 등록/수정을 이용해주세요");
        }

        return userRepository.save(currentUser).toDTO();
    }

    public UserDTO saveUserBusinessNumber(UserDTO userDTO, HttpServletRequest request){ // 사업자번호 등록
        User currentUser = getCurrentUser(request);
        if (currentUser == null){
            throw new IllegalArgumentException("올바르지 않은 접근입니다");
        }

        if (userDTO.getRealName() !=null){
            throw new IllegalArgumentException("회원 정보 수정을 이용해 주세요.");
        }
        if (userDTO.getEmail()!=null){
            throw new IllegalArgumentException("회원 정보 수정을 이용해 주세요.");
        }
        if (userDTO.getPhoneNumber()!=null){
            throw new IllegalArgumentException("회원 정보 수정을 이용해 주세요.");
        }

        if (userDTO.getUserName() !=null){
            throw new IllegalArgumentException("회원 정보 수정을 이용해 주세요.");
        }
        if (userDTO.getRole() !=null){
            throw new IllegalArgumentException("회원 정보 수정을 이용해 주세요.");
        }
        if (userDTO.getPassword() !=null){
            throw new IllegalArgumentException("회원 정보 수정을 이용해 주세요.");
        }
        if (userDTO.getGender() !=null){
            throw new IllegalArgumentException("성별은 수정할 수 없습니다.");
        }
        if (userDTO.getPoint() != 0){
            throw new IllegalArgumentException("포인트는 수정할 수 없습니다.");
        }

        if (userDTO.getBusinessNumber() !=null){
            currentUser.setBusinessNumber(userDTO.getBusinessNumber());
        }
        if (userDTO.getBusinessType() !=null){
            currentUser.setBusinessType(userDTO.getBusinessType());
        }
        if (userDTO.getCompanyName() !=null){
            currentUser.setCompanyName(userDTO.getCompanyName());
        }

        return userRepository.save(currentUser).toDTO();
    }

    public UserDTO addPoint(UserDTO userDTO, HttpServletRequest request) {
        User currentUser = getCurrentUser(request);

        if (userDTO.getUserName() == null) {
            throw new IllegalArgumentException("정상적인 요청이 아닙니다");
        }

        User pointUser = userRepository.findByUserName(userDTO.getUserName())
                .orElseThrow(()->new ResourceNotFoundException("해당 유저를 찾을 수 없습니다"));

        if (!currentUser.getUserName().equals(pointUser.getUserName())){
            throw new IllegalArgumentException("포인트는 자신의 계정에서만 사용할 수 있습니다.");
        }

        pointUser.setPoint(pointUser.getPoint()+userDTO.getPoint());
        userRepository.save(pointUser);

        return pointUser.toDTO();
    }

    public UserDTO usePoint(UserDTO userDTO, HttpServletRequest request){
        User currentUser = getCurrentUser(request);
        if (userDTO.getUserName() == null || userDTO.getPoint() <= 0) {
            throw new IllegalArgumentException("정상적인 요청이 아닙니다");
        }

        User pointUser = userRepository.findByUserName(userDTO.getUserName())
                .orElseThrow(()->new ResourceNotFoundException("해당 유저를 찾을 수 없습니다"));

        if (!currentUser.getUserName().equals(pointUser.getUserName())){
            throw new IllegalArgumentException("포인트는 자신의 계정에서만 사용할 수 있습니다.");
        }

        pointUser.setPoint(pointUser.getPoint() - userDTO.getPoint());
        userRepository.save(pointUser);

        return pointUser.toDTO();
    }
}
