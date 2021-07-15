package com.example.demo.security;

import com.example.demo.entities.Privilege;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.repositories.PrivilegeRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        Privilege courseReadPrivilege
                = createPrivilegeIfNotFound("COURSE_READ");
        Privilege courseWritePrivilege
                = createPrivilegeIfNotFound("COURSE_WRITE");
        Privilege studentReadPrivilege
                = createPrivilegeIfNotFound("STUDENT_READ");
        Privilege studentWritePrivilege
                = createPrivilegeIfNotFound("STUDENT_WRITE");

        List<Privilege> adminPrivileges = Arrays.asList(
                courseReadPrivilege, courseWritePrivilege, studentReadPrivilege, studentWritePrivilege);
        List<Privilege> staffPrivileges = Arrays.asList(
                courseReadPrivilege, studentReadPrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_STAFF", staffPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList());

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        User user = new User();
        user.setUsername("Test");
        user.setPassword(passwordEncoder.encode("test"));
        user.setEmail("test@test.com");
        user.setRoles(Arrays.asList(adminRole));
        user.setEnabled(true);
        userRepository.save(user);

        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}