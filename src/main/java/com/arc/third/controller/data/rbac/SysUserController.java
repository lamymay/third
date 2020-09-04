package com.arc.third.controller.data.rbac;

import com.arc.third.model.domain.SysUser;
import com.arc.third.model.request.ArcPageable;
import com.arc.third.service.rbac.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * data 包下的 controller仅仅用作  返回json数据 ，禁止页面跳转使用，页面跳转使用的操作请移步web包
 * 用户相关的的接口by RESTful
 *
 * @author 叶超
 * @since 2018/12/25
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    private SysUserService userService;

//    @Autowired
//    private SysUserRepository userRepository;


    //增删改查

    /**
     * 新建用户
     * 注意
     * 1请求类型为Content-Type:application/json
     * 2方法： POST
     *
     * @param user pageable
     * @return ResponseVo
     */
    @PostMapping({"/save", ""})
    public ResponseEntity save(@RequestBody SysUser user) {
        log.debug("新建用户，参数 user={}, ", user);
        return ResponseEntity.ok(userService.save(user));
    }

    /**
     * 删除
     * 表示删除id为1的数据
     * 方法： DELETE
     * http://lip:port/user/1
     *
     * @param id Long id
     * @return ResponseVo
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        log.debug("参数删除用户，参数id={}", id);
        return ResponseEntity.ok(userService.delete(id));
    }


    @GetMapping("/get")
    public ResponseEntity get(@RequestParam Long id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<SysUser>> list() {
        return ResponseEntity.ok(userService.listAll());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<SysUser>> pge(@RequestBody ArcPageable pageable) {
        return ResponseEntity.ok(userService.page(pageable));
    }


    //  其他条件查询 in\join\sort\order by\between and

    /**
     * 测试条件查询IN 参数用 ?ids=[] 格式传递
     *
     * @param ids ids
     * @return ResponseEntity
     */
    @GetMapping("/query/in")
    public ResponseEntity testQueryIn(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(userService.listByIds(ids));
    }

    @PostMapping("/query")
    public ResponseEntity query(@RequestBody ArcPageable pageable) {
        return ResponseEntity.ok(userService.listByQuery(pageable));
    }

    /**
     * 排序查询
     */
    @GetMapping("/sort")
    public ResponseEntity getAllUserBySort(String sortColumn) {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC,sortColumn);
        Sort sort = JpaSort.by(order);
        return ResponseEntity.ok(userService.listBySort(sort));
    }


}
