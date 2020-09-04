package com.arc.third.model.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

/**
 * @description:
 * @author: yechao
 * @date: 2019/1/22 14:52
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_user")
public class SysUser extends BaseModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;// 自增id

    @Column(name = "nickname")
    private String nickname;// 用户昵称

    @Column(name = "avatar")
    private String avatar;// 头像

    @Column(name = "state")
    private Integer state;// 账号状态(0：正常 1:暂停)

    @Column(name = "create_at")
    private Date createAt;// 创建时间

    @Column(name = "update_at")
    private Date updateAt;// 更新时间


}
