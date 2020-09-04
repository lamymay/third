package com.arc.third.service.rbac;

import com.arc.third.model.domain.SysUser;
import com.arc.third.model.request.ArcPageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * JAVA项目是分层来写的，
 * 这是服务层，目的是处理业务，
 *
 * @author yechao
 * @date 2018/12/21
 */
public interface SysUserService {

    Long save(SysUser user);

    int delete(Long id);

    SysUser get(Long id);

    Page<SysUser> page(ArcPageable pageable);

    List<SysUser> listAll();

    List<SysUser>  listByIds(List<Long> ids);

    List<SysUser>  listByQuery(ArcPageable pageable);

    Iterable<SysUser> listBySort(Sort sort);

//
//    List<SysUser> findByCondition(SysUser user);
//
//    Page<SysUser> pageByQuery(SysUserQuery userQuery, Pageable pageable);

//    Long save(SysUser user);
//
//    int delete(Long id);
//
//    int update(SysUser user);
//
//    int updateBatch(List<SysUser> users);
//
//    List<SysUser> list();
//
}
