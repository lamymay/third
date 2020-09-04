package com.arc.third.controller.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 叶超
 * @since 2019/4/30 9:43
 */
@Slf4j
@RestController
@RequestMapping("/test/request")
public class TestRequestController {

    @PostMapping("/test/list/string")
    public Object testStringList(@RequestBody List<String> stringList) {
        log.debug("#############################");
        log.debug("parameter length={}", stringList.size());
        log.debug("#############################");
        return stringList;
    }
}
