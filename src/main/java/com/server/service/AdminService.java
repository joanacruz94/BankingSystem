package com.server.service;

import com.server.dto.SignUpAdminRequest;
import com.server.dto.AdminSummary;
import com.server.enums.RoleName;
import com.server.exceptions.AppException;
import com.server.exceptions.ConflictException;
import com.server.exceptions.NotFoundException;
import com.server.model.Admin;
import com.server.model.Role;
import com.server.repository.AdminRepository;
import com.server.repository.RoleRepository;
import com.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LogManager.getLogger(ThirdPartyService.class);

    public AdminSummary findAdminById(Long id) {
        LOGGER.info("Look for admin user with ID " + id);
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new NotFoundException("Admin with that ID doesn't exist"));
        return new AdminSummary(admin.getId(), admin.getName(), admin.getEmail());
    }

    public List<AdminSummary> findAllAdmins(){
        LOGGER.info("Look for all admin users");
        return adminRepository.findAll().stream().map(admin -> new AdminSummary(admin.getId(), admin.getName(), admin.getEmail())).collect(Collectors.toList());
    }

    public Long registerAdmin(SignUpAdminRequest signUpAdminRequest) {
        if(userRepository.existsByEmail(signUpAdminRequest.getEmail())) {
            throw new ConflictException("Email [email: " + signUpAdminRequest.getEmail() + "] is already taken");
        }

        Admin admin = new Admin(signUpAdminRequest.getName(), signUpAdminRequest.getEmail(), signUpAdminRequest.getPassword());
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        Role userRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new AppException("User Role not set. Add default roles to database."));
        admin.setRoles(Collections.singleton(userRole));

        LOGGER.info("Successfully registered user with [email: {}]", admin.getEmail());

        return adminRepository.save(admin).getId();
    }
}
