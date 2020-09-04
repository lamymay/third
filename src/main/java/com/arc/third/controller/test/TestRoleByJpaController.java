//package com.arc.third.controller;
//
//import com.arc.jpa.model.domain.Role;
//import com.arc.jpa.repository.RoleRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * @author 叶超
// * @since 2019/4/30 9:43
// */
//@Slf4j
//@RestController
//@RequestMapping("/roles")
//public class TestRoleByJpaController {
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @GetMapping("/get/{id}")
//    public Object get(@PathVariable Long id) {
//        Role role = roleRepository.findById(id).get();
//        return role;
//    }
//
//    @GetMapping("/list")
//    public Object list() {
//        List<Role> roles = roleRepository.findAll();
//        roles.stream().forEach((item) -> System.out.println(item));
//        return roles;
//    }
//
//    @PostMapping("/update")
//    public Object update(@RequestBody Role role) {
//        log.debug("结果={}", role);
//        Role save = roleRepository.saveAndFlush(role);
//        return save;
//    }
//}
