package com.nod.lone.service.impl;

import com.nod.lone.dto.LoginRequest;
import com.nod.lone.dto.SignupRequest;
import com.nod.lone.dto.UserDto;
import com.nod.lone.model.BankCard;
import com.nod.lone.model.User;
import com.nod.lone.model.enums.RoleName;
import com.nod.lone.payload.AllApiResponse;
import com.nod.lone.payload.TokenPayload;
import com.nod.lone.repository.BankCardRepository;
import com.nod.lone.repository.FileStorageRepository;
import com.nod.lone.repository.UserRepository;
import com.nod.lone.securityConfiguration.JwtTokenProvider;
import com.nod.lone.service.UserService;
import com.nod.lone.service.file.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    FileStorageRepository fileStorageRepository;

    @Autowired
    BankCardRepository bankCardRepository;


    @Override
    public HttpEntity<?> findAllUsers() {
        try {
            List<User> users = userRepository.findAllByRoleNotIn(List.of(RoleName.SUPER_ADMIN));
            if (users != null){
                return ResponseEntity.ok(users);
            }else {
                return AllApiResponse.response(404,0,"Userlar mavjud emas");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AllApiResponse.response(500, 0, "Bad Request");
    }

    @Override
    public ResponseEntity<?> identification(UserDto userDto){
        try {
            User user = new User();
            user.setEmail(userDto.getEmail());
            user.setUsername(userDto.getUsername());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setRole(RoleName.USER);
            user.setDateOfBirth(LocalDate.parse(userDto.getDateOfBirth()));
            user.setRole(userDto.getRole());
            Long oldFileId = null;
            if (userDto.getPhoto() != null) {
                if (user.getPhoto() != null) {
                    oldFileId = user.getPhoto().getId();
                }
                user.setPhoto(fileStorageService.save(userDto.getPhoto()));
            }
            userRepository.save(user);
            if (oldFileId != null){
                fileStorageService.deleteFile(oldFileId);
                fileStorageRepository.deleteById(oldFileId);
            }
            return AllApiResponse.response(1, "Successfully Baby!", user);
        }catch (Exception e){
            return AllApiResponse.response(500,0,e.getMessage());
        }
    }

    @Override
    public HttpEntity<?> findById(Long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()){
                return ResponseEntity.ok(user);
            }else AllApiResponse.response(500, 0, "Bunaqa user yuq");
        }catch (Exception e){
            e.printStackTrace();
        }
        return AllApiResponse.response(500, 0, "Bad Request");
    }

    @Override
    public ResponseEntity<?> findByEmail(String email) {
        try {
            Optional<User> user = userRepository.findUserByEmail(email);
            if (user.isPresent()){
                return ResponseEntity.ok(user);
            }else AllApiResponse.response(500, 0, "Bunaqa email yuq");
        }catch (Exception e){
            e.printStackTrace();
        }
        return AllApiResponse.response(500, 0, "Bad Request");
    }

    @Override
    public HttpEntity<?> updateUser(UserDto userDto) {
        try {
            if (userDto.getId() == null) return AllApiResponse.response(400,"userId is null");
            Optional<User> byId = userRepository.findById(userDto.getId());
                if (byId.isEmpty()) return AllApiResponse.response(400,"not found user");
                User user = byId.get();
                if (userDto.getFirstName() != null)user.setFirstName(userDto.getFirstName());
                if (userDto.getLastName() != null)user.setLastName(userDto.getLastName());
                if (userDto.getDateOfBirth() != null)user.setDateOfBirth(LocalDate.parse(userDto.getDateOfBirth()));
                if (userDto.getUsername() != null)user.setUsername(userDto.getUsername());
                if (userDto.getPassword() != null)user.setPassword(userDto.getPassword());
                if (userDto.getRole() != null)user.setRole(userDto.getRole());
            if (userDto.getPhoto() != null) {
                Long oldPhoto = user.getPhoto().getId();
                if (oldPhoto != null ) {
                        fileStorageService.deleteFile(oldPhoto);
                    }
                user.setPhoto(fileStorageService.save(userDto.getPhoto()));
            }
                    userRepository.save(user);
            return AllApiResponse.response(200,1,"updated");
        }catch (Exception e){
            e.printStackTrace();
            return AllApiResponse.response(500,0,"Bad");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteUser(Long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()){
                if (user.get().getPhoto()==null){
                    userRepository.deleteById(user.get().getId());
                }else {
                    Long fileId = user.get().getPhoto().getId();
                    fileStorageService.deleteFile(fileId);
                    userRepository.deleteById(user.get().getId());
                }

                return AllApiResponse.response(1,"deleted");
            }else return AllApiResponse.response(404, 0, "Not Found");
        }catch (Exception e){
            e.printStackTrace();
        }
        return AllApiResponse.response(200, 1, "success");
    }

    @Override
    public ResponseEntity<?> signIn(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User principal = (User) authentication.getPrincipal();
            TokenPayload jwt = jwtTokenProvider.generateToken(principal);
            System.out.println("successfully");
            return ResponseEntity.ok(jwt);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("you don't signIn");
        }
    }

    @Override
    public ResponseEntity<?> signUp(SignupRequest signupRequest) {
        if (userRepository.existsUserByUsername(signupRequest.getUsername())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Choose different name");
        }
        if (userRepository.existsUsersByEmail(signupRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("choose different email");
        }

        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());


        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("successfully baby");
    }

    public User loadByUserId(Long id) {
        try {
            Optional<User> byId = userRepository.findById(id);
            return byId.orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<User> byUsername = userRepository.findByUsername(username);
            if (byUsername.isPresent()) {
                return byUsername.get();
            } else {
                throw new UsernameNotFoundException("Username or password incorrect!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("Error enter with login and password!");
        }
    }

    @Override
    public HttpEntity<?> userInfo(Long id) {
        try {
            if (id == null) return AllApiResponse.response(400,"id is null");
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isEmpty()) return AllApiResponse.response(400,"not fount user");
            return AllApiResponse.response(200,"ok",toUserDto(userOptional.get()));
        }catch (Exception e){
            e.printStackTrace();
            return AllApiResponse.response(409,"conflict");
        }
    }


    public UserDto toUserDto(User user) throws Exception {
        try {
            List<BankCard> userCards = bankCardRepository.findAllByUserOrderByCreatedDate(user);
            return new UserDto(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    String.valueOf(user.getDateOfBirth()),
                    user.getEmail(),
                    userCards
                    );
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Error is toUserDto method");
        }

    }


}
