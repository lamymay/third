package com.arc.third.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Date;

/**
 * org.springframework.data.domain.Pageable本项目实现
 *
 * @author 叶超
 * @since 2019/5/22 18:45
 */
@Setter
@Getter
@ToString
//public class ArcPageable implements Pageable {
public class ArcPageable extends PageRequest {

//    private boolean paged;
//    private int pageNumber;
//    private int pageSize = 21;
//    private long offset;
//    private Sort sort;
//    private String sortColumn;

    private Long id;// 自增id
    private String idString;// 自增id (StringId)
    private String name;// 名称
    private Integer state;//状态(0：正常 1:暂停)

    private Date createAt;// 创建时间
    private Date updateAt;// 更新时间

    public ArcPageable(int page, int size, Sort sort) {
        super(page, size, sort);
    }
}
