package com.arc.third.repository;

import com.arc.third.model.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository层中为了支持这样的查询，
 * sysUserRepository需要继承JpaRepository（基本查询），
 * JpaSpecificationExecutor（分页），这个接口是不需要再去实现的，到了Repository层就行，再对此进行扩充（比Mybatis简单多了）。
 *
 * @author: yechao
 * @date: 2019/1/10 15:04
 */
public interface SysUserRepository extends JpaRepository<SysUser, Long>, JpaSpecificationExecutor<SysUser> {
    //public interface SysUserRepository extends JpaRepository<SysUser, Long> {

    /**
     * in 查询
     *
     * @param ids ids
     * @return 列表数据
     */
    List<SysUser> findAllByIdIn(@Param("id") List<Long> ids);


    //    /**
    //     * 双in 查询测试
    //     *
    //     * @param ids
    //     * @param avatars
    //     * @return
    //     */
    //    List<SysUser> findAllByIdInAndAvatarIn(@Param("id") List<Long> ids, @Param("avatar") List<String> avatars);

    //
    //    /**
    //     * 测试多条件查询  包含in 和 普条字段
    //     *
    //     * @param ids
    //     * @param avatars
    //     * @param state
    //     * @return
    //     */
    //    List<SysUser> findAllByIdInAndAndAvatarInAndState(@Param("id") List<Long> ids, @Param("avatar") List<String> avatars, @Param("state") Integer state);

    //Page<SysUser> findAll(Specification<SysUser> model, Pageable pageable);

    //    Object findAll(Specification<SysUser> example, Pageable pageable);

    //    List<SysUser> findAll(Specification querySpecifi);

       // List<SysUser> findAll(Specification<SysUser> specifications, Sort sort);


}
