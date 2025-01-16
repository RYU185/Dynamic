package com.dw.dynamic.service;
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

    public UserDTO getIdByEmail(String email) { // 이메일로 통하여 아이디 찾기
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("존재하지 않는 이메일입니다");
        }
        return user.toDTO();
    }

    public UserDTO ModifyPwByIDAndPhoneNumber(String id, String phoneNumber) {
        // 아이디와 전화번호를 통해 임시비밀번호 전송하여 비밀번호 수정
        Optional<User> user = userRepository.findById(id);
        User user1 = user.orElseThrow(() -> new ResourceNotFoundException("찾을 수 없는 ID입니다"));

        if (!user1.getPhoneNumber().equals(phoneNumber)) {
            throw new InvalidRequestException("옳지 않은 전화번호 입니다.");
        }
        return null;
    }

    public UserDTO ModifyUserData(UserDTO userDTO) { // 회원 정보 수정(이름, 이메일, 전화번호)
        User currentUser = userRepository.findById(userDTO.getUserName()).orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 유저 ID 입니다"));
        if (userDTO.getUserName() != null ||
                userDTO.getPassword() != null ||
                userDTO.getRole() != null ||
                userDTO.getGender() != null ||
                userDTO.getBusinessNumber() != null ||
                userDTO.getBusinessType() != null ||
                userDTO.getPoint() <0 ||
                userDTO.getCompanyName() != null
        ) {
            throw new InvalidRequestException("이름, 이메일, 전화번호 이외로는 수정이 불가능합니다");
        }
        return userRepository.save(currentUser).toDTO();
    }

        public UserDTO saveUserBusinessNumber(UserDTO userDTO){ // 사업자번호 등록
            User user = new User(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    userDTO.getBusinessNumber(),
                    userDTO.getBusinessType(),
                    userDTO.isExistBusinessOperator(),
                    userDTO.getPoint(),
                    authorityRepository.findById("USER")
                            .orElseThrow(() -> new ResourceNotFoundException("권한이 없습니다"))
            );
            return userRepository.save(user).toDTO();
    }

    public UserDTO addPoint(UserDTO userDTO){
        if (userDTO.getUserName() == null){
            throw new IllegalArgumentException("정상적인 요청이 아닙니다");
        }
        User user = userRepository.findById(userDTO.getUserName())
                .orElse(new User());
        return null;
        // SETTER 없음?
    }

    public UserDTO userPoint(UserDTO userDTO){
        if (userDTO.getUserName() == null || userDTO.getPoint()<=0 ){
            throw new IllegalArgumentException("정상적인 요청이 아닙니다");
        }
        User user = userRepository.findById(userDTO.getUserName())
                .orElse(new User());
        return null;
        }
    }


