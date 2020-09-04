package com.arc.third.service.rbac.impl;

import com.arc.third.model.domain.SysUser;
import com.arc.third.model.request.ArcPageable;
import com.arc.third.repository.SysUserRepository;
import com.arc.third.service.rbac.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * @author 叶超
 * @since 2018/12/26 11:28
 */
@Transactional
@Service
public class SysUserServiceImpl implements SysUserService {

    /**
     * 用户相关dao
     */
    @Autowired
    private SysUserRepository userRepository;

    @Override
    public Long save(SysUser user) {
        SysUser save = userRepository.save(user);
        return save == null ? null : save.getId();
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public SysUser get(Long id) {
        Optional<SysUser> optional = userRepository.findById(id);
        // 此处JPA提供了多重封装, 便于使用者灵活返回,  1orElse(null)=是null就返回null, 2orElse(new SysUser())=是null返回默认值,3orElseThrow(new Exception())=是null抛出异常
        return optional.orElse(null);
        //return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<SysUser> listAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<SysUser> page(ArcPageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public List<SysUser> listByIds(List<Long> ids) {
        return userRepository.findAllByIdIn(ids);
    }

    @Override
    public List<SysUser> listByQuery(ArcPageable pageable) {
        //todo  jpa 对于多条件动态查询有点麻烦
        return null;
    }

    @Override
    public Iterable<SysUser> listBySort(Sort  sort) {
        Specification specification = new Specification() {
            /**
             * ANDs the given {@link Specification} to the current one.
             *
             * @param other can be {@literal null}.
             * @return The conjunction of the specifications
             * @since 2.0
             */
            @Override
            public Specification and(Specification other) {
                return null;
            }

            /**
             * ORs the given specification to the current one.
             *
             * @param other can be {@literal null}.
             * @return The disjunction of the specifications
             * @since 2.0
             */
            @Override
            public Specification or(Specification other) {
                return null;
            }

            /**
             * Creates a WHERE clause for a query of the referenced entity in form of a {@link Predicate} for the given
             * {@link Root} and {@link CriteriaQuery}.
             *
             * @param root            must not be {@literal null}.
             * @param query           must not be {@literal null}.
             * @param criteriaBuilder must not be {@literal null}.
             * @return a {@link Predicate}, may be {@literal null}.
             */
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };

        List<SysUser> users = userRepository.findAll(specification, sort);
        return users;
    }
}


//================================= 其他查询的业务方法
//
//    /**
//     * 登陆
//     *
//     * @param ua SysUserAuth
//     * @return SysUser
//     */
//    @Override
//    public SysUser login(SysUserAuth ua) {
//        if (ua == null || ua.getIdentifier() == null || ua.getIdentifier().trim().length() == 0 || ua.getCredential() == null) {
//            throw new BizException(ProjectCodeEnum.LOGIN_ERROR);
//        }
//        //todo 改进登录的查询效率， 需要测试 一次联表查询 两次单表查询，谁的消耗更大
//
//
//        String identifier = ua.getIdentifier();
//        String credential = ua.getCredential();
//        int identityType = ua.getIdentityType();
//        SysUserAuth auth = authMapper.getByIdentifierWithCredentialAndIdentityType(identifier, credential,identityType );
////        SysUserAuth auth = authMapper.getByIdentifierWithCredentialAndIdentityType("admin", "admin", 1);
//        if (null == auth) {
//            throw new BizException(ProjectCodeEnum.LOGIN_ERROR);
//        }
//        return userMapper.get(auth.getUserId());
//    }
//
//    @Override
//    public SysUser login(String username, String password) {
//        return login(new SysUserAuth(username, password, 1));
//    }
//
//    @Override
//    public SysUser login(String identifier, String credential, Integer identityType) {
//        return login(new SysUserAuth(identifier, credential, identityType));
//    }
//
//    @Autowired
//    private SysUserRepository userRepository;
//
//
//    @Override
//    public SysUser get(Long id) {
//        //        Optional<SysUser> byId = userRepository.findById(id);
//        //        ResponseEntity<S> error = userRepository.findOne(id).map(g -> ResponseEntity.ok(g)).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR"));
//        //        return userRepository.findById(id).get();//orElseThrow(EntityNotFoundException::new);
//        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
//    }
//
//
//    /**
//     * 测试分页&排序&动态条件查询
//     *
//     * @param request
//     * @param pageable
//     * @return
//     */
//    @Override
//    public Page<SysUser> pageByQuery(SysUserQuery request, Pageable pageable) {
//        System.out.println("#########################");
//        System.out.println(request);
//        System.out.println(request.getNickname());
//        System.out.println("#########################");
//
//        //封装查询对象Specification
//        Specification<SysUser> specifications = new Specification<SysUser>() {
//            @Override
//            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//
//                //定义集合来确定Predicate[] 的长度，因为CriteriaBuilder的or方法需要传入的是断言数组
//                List<Predicate> predicates = new ArrayList<>();
//
//                //获取客户端查询条件
//                String nickname = request.getNickname();
//
//                //对客户端查询条件进行判断,并封装Predicate断言对象
//                if (nickname != null && !"".equals(nickname)) {
//                    //root.get("company")获取字段名
//                    //company客户端请求的字段值
//                    //as(String.class)指定该字段的类型
//                    Path<Object> nicknamePath = root.get("nickname");
//                    Predicate predicate = cb.equal(nicknamePath, nickname);
//                    predicates.add(predicate);
//                }
//                //判断结合中是否有数据
//                if (predicates.size() == 0) {
//                    return null;
//                }
//
//                //将集合转化为CriteriaBuilder所需要的Predicate[]
//                Predicate[] predicateArr = new Predicate[predicates.size()];
//                predicateArr = predicates.toArray(predicateArr);
//
//                System.out.println("---------------------------------");
//                System.out.println(predicateArr);
//                System.out.println(predicateArr.length);
//                System.out.println("---------------------------------");
//                // 返回所有获取的条件： 条件 or 条件 or 条件 or 条件
//                return cb.or(predicateArr);
//            }
//        };
//        //调用Dao方法进行条件查询
//
//        Page<SysUser> page = userRepository.findAll(specifications, pageable);
//        //TotalElements  数据条数 21
//        System.out.println(page.getTotalElements());
//        //TotalPages 总页数
//        System.out.println(page.getTotalPages());
//        System.out.println(page);
//        return page;
//    }
//
//
//    /**
//     * 测试排序
//     *
//     * @param request
//     * @return
//     */
//    @Override
//    public List<SysUser> listByQuery(SysUserQuery request) {
//        //连接条件查询
//
//        //封装查询对象Specification
//        Specification<SysUser> specifications = new Specification<SysUser>() {
//            @Override
//            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                //获取客户端查询条件
//                String nickname = request.getNickname();
//                String avatar = request.getAvatar();
//
//                //定义集合来确定Predicate[] 的长度，因为CriteriaBuilder的or方法需要传入的是断言数组
//                List<Predicate> predicates = new ArrayList<>();
//
//
//                //对客户端查询条件进行判断,并封装Predicate断言对象
//                if (nickname != null && !"".equals(nickname)) {
//                    //root.get("company")获取字段名
//                    //company客户端请求的字段值
//                    //as(String.class)指定该字段的类型
//                    predicates.add(cb.equal(root.get("nickname"), nickname));
//                }
//                if (null != avatar && !"".equals(avatar)) {
//                    if (StringUtils.isEmpty(avatar)) {
//                        Predicate predicate = cb.equal(root.get("avatar"), avatar);
//                        predicates.add(predicate);
//                    }
//                }
//                //判断结合中是否有数据
//                if (predicates.size() == 0) {
//                    return null;
//                }
//
//                //将集合转化为CriteriaBuilder所需要的Predicate[]
//                query.where(predicates.toArray(new Predicate[predicates.size()]));
//                return query.getRestriction();
//            }
//        };
//        String sortColumn = "createDate";
//        //Sort sort= new Sort(new Sort.Order(Sort.Direction.DESC, sortColumn));
//        Sort sort = new Sort(Sort.Direction.DESC, Arrays.asList(sortColumn));
//        //调用DAO方法进行条件查询
//        List<SysUser> all = userRepository.findAll(specifications, sort);
//        return all;
//    }
//
//
//    @Override
//    public Object queryByQuery(SysUser user) {
//        return null;
//    }
//
//    /**
//     * 条件查询
//     *
//     * @param model
//     * @param pageable
//     * @return
//     */
//    @Override
//    public Page<SysUser> queryPage(SysUser model, Pageable pageable) {
//        //封装查询对象Specification
//        Specification<SysUser> specifications = new Specification<SysUser>() {
//            @Override
//            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                //获取客户端查询条件
//                String nickname = model.getNickname();
//                String avatar = model.getAvatar();
//
//                //定义集合来确定Predicate[] 的长度，因为CriteriaBuilder的or方法需要传入的是断言数组
//                List<Predicate> predicates = new ArrayList<>();
//
//
//                //对客户端查询条件进行判断,并封装Predicate断言对象
//                if (nickname != null && !"".equals(nickname)) {
//                    //root.get("company")获取字段名
//                    //company客户端请求的字段值
//                    //as(String.class)指定该字段的类型
//                    Path<Object> nicknamePath = root.get("nickname");
//                    Predicate predicate = cb.equal(nicknamePath, nickname);
//                    predicates.add(predicate);
//                }
//                if (null != avatar && !"".equals(avatar)) {
//                    if (StringUtils.isEmpty(avatar)) {
//                        Predicate predicate = cb.equal(root.get("avatar"), avatar);
//                        predicates.add(predicate);
//                    }
//                }
//                //判断结合中是否有数据
//                if (predicates.size() == 0) {
//                    return null;
//                }
//
//                //将集合转化为CriteriaBuilder所需要的Predicate[]
//                query.where(predicates.toArray(new Predicate[predicates.size()]));
//                return query.getRestriction();
//            }
//        };
//        //调用Dao方法进行条件查询
///*        Page<SysUser> page = userRepository.findAll(specifications, pageable);
//        System.out.println(page);
//        System.out.println(page.getTotalElements());
//        System.out.println(page.getTotalPages());
//        return page;*/
//        return null;
//    }
//
//
//    public Page<SysUser> queryPage2(SysUser model, Pageable pageable) {
//        System.out.println("#########################");
//        System.out.println(model.getNickname());
//        System.out.println(model.getNickname());
//        System.out.println(model.getNickname());
//        System.out.println(model.getAvatar());
//        System.out.println(model.getAvatar());
//        System.out.println(model.getAvatar());
//        //封装查询对象Specification
//        Specification<SysUser> specifications = new Specification<SysUser>() {
//            @Override
//            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                //获取客户端查询条件
//                String nickname = model.getNickname();
//                String avatar = model.getAvatar();
//
//                //定义集合来确定Predicate[] 的长度，因为CriteriaBuilder的or方法需要传入的是断言数组
//                List<Predicate> predicates = new ArrayList<>();
//
//
//                //对客户端查询条件进行判断,并封装Predicate断言对象
//                if (nickname != null && !"".equals(nickname)) {
//                    //root.get("company")获取字段名
//                    //company客户端请求的字段值
//                    //as(String.class)指定该字段的类型
//                    Path<Object> nicknamePath = root.get("nickname");
//                    Predicate predicate = cb.equal(nicknamePath, nickname);
//                    predicates.add(predicate);
//                }
//                if (null != avatar && !"".equals(avatar)) {
//                    if (StringUtils.isEmpty(avatar)) {
//                        Predicate predicate = cb.equal(root.get("avatar"), avatar);
//                        predicates.add(predicate);
//                    }
//                }
//                //判断结合中是否有数据
//                if (predicates.size() == 0) {
//                    return null;
//                }
//
//                //将集合转化为CriteriaBuilder所需要的Predicate[]
//                Predicate[] predicateArr = new Predicate[predicates.size()];
//                predicateArr = predicates.toArray(predicateArr);
//
//                System.out.println("---------------------------------");
//                System.out.println(predicateArr);
//                System.out.println(predicateArr.length);
//                System.out.println("---------------------------------");
//                // 返回所有获取的条件： 条件 or 条件 or 条件 or 条件
//                return cb.or(predicateArr);
//            }
//        };
//        //调用Dao方法进行条件查询
///*        Page<SysUser> page = userRepository.findAll(specifications, pageable);
//        System.out.println(page);
//        System.out.println(page.getTotalElements());
//        System.out.println(page.getTotalPages());
//        return page;*/
//        return null;
//
//    }

//--------

//back

//    public static Specification<SysUser> queryPage(SysUser user) {
//lambda表达式
//        return (Root<SysUser> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
//开始
//            Predicate finalConditions = criteriaBuilder.conjunction();
//
//                //提取参数
//                String taskFast = MapUtils.getString(params, "taskFast");
//
//                //lile 和join 用法 join可跟,JoinType.LEFT等
//                if (StringUtils.isNotBlank(taskFast)) {
//                    Predicate taskFastPre = criteriaBuilder.like(root.join("taskType",JoinType.LEFT).<String>get("id"), "%" + taskFast + "%");
//                    finalConditions = criteriaBuilder.and(finalConditions, taskFastPre);
//                }
//between用法
//                if ((null != createBegin) && (null != createEnd)) {
//                    Predicate datePredicate = null;
//                    if (createBegin.after(createEnd)) {
//                        datePredicate = criteriaBuilder.between(root.get("gmtCreate"), createEnd, createBegin);
//                    } else {
//                        datePredicate = criteriaBuilder.between(root.get("gmtCreate"), createBegin, createEnd);
//                    }
//                    finalConditions = criteriaBuilder.and(finalConditions, datePredicate);
//                }
//                //equale
//                if (null != emergency && 0 != emergency) {
//                    finalConditions = criteriaBuilder.and(finalConditions, criteriaBuilder.equal(root.get("emergencyLevel"), emergency));
//                }
//                //大于 不等于
//                if (status != null) {
//                    finalConditions = criteriaBuilder.and(finalConditions, criteriaBuilder.greaterThan(root.get("startDate"), new Date()));
//                    finalConditions = criteriaBuilder.and(finalConditions, criteriaBuilder.notEqual(root.get("status"), 1));
//                }
//                // or
//                if (StringUtils.isNotBlank(keyword)) {
//                    finalConditions = criteriaBuilder.and(finalConditions, criteriaBuilder.or(
//                            criteriaBuilder.like(root.get("taskName"), "%" + keyword + "%"),
//                            criteriaBuilder.like(root.join("project").get("name"), "%" + keyword + "%"))
//                    );
//                }
//                //in
//                if (taskIds.size() > 0) {
//                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("id"));
//                    for (String id : taskIds) {
//                        in.value(id);
//                    }
//                    finalConditions = criteriaBuilder.and(finalConditions, in);
//                }

//            return query.where(finalConditions).getRestriction();
//        };
//    }


//---
//        public  Specification<SysUser> where(String keyword, Date createdAtBegin, Date createdAtEnd, List<String> userIds) {
//            return (Root<SysUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
//                List<Predicate> predicates = new ArrayList<>();
//
//                if (!StringUtils.isEmpty(keyword)) {
//                    List<Predicate> temp = new ArrayList<>();
//                    Set<String> keywordCopyStr = StringUtil.cutToArray(keyword);
//                    for (String oneKeyword : keywordCopyStr) {
//                        temp.add(cb.like(root.<String>get("mobile"), "%" + oneKeyword + "%"));
//                        temp.add(cb.like(root.<String>get("trueName"), "%" + oneKeyword + "%"));
//                    }
//                    predicates.add(cb.or(temp.toArray(new Predicate[temp.size()])));
//                }
//                //未删除
////                predicates.add(cb.equal(root.get("isDelete"), Constant.NOT_DELETED));
//                query.where(predicates.toArray(new Predicate[predicates.size()]));
//                return query.getRestriction();
//            };
//            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
//        }

//    @Override
//    public List<SysUser> findByCondition(SysUser user) {
//        System.out.println("#######################################");
//        System.out.println(user);
//        System.out.println("#######################################");
//        List<SysUser> resultList = null;
//        Specification querySpecifi = new Specification<SysUser>() {
//            @Override
//            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                List<Predicate> predicates = new ArrayList<>();
//                if (user.getState() != null) {
//                    predicates.add(criteriaBuilder.equal(root.get("state"), user.getState()));
//                }
//                if (user.getAvatar() != null && !"".equals(user.getAvatar().trim())) {
//                    predicates.add(criteriaBuilder.equal(root.get("avatar"), user.getAvatar()));
//                }
//                if (null != user.getNickname() && !"".equals(user.getNickname().trim())) {
//                    predicates.add(criteriaBuilder.equal(root.get("nickname"), "%" + user.getNickname() + "%"));
//                }
//                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
//            }
//        };
//        resultList = this.userRepository.findAll(querySpecifi);
//        return resultList;
//    }
//
//
//}

//https://www.cnblogs.com/happyday56/p/4661839.html


//between用法
//                if ((null != createBegin) && (null != createEnd)) {
//                    Predicate datePredicate = null;
//                    if (createBegin.after(createEnd)) {
//                        datePredicate = criteriaBuilder.between(root.get("gmtCreate"), createEnd, createBegin);
//                    } else {
//                        datePredicate = criteriaBuilder.between(root.get("gmtCreate"), createBegin, createEnd);
//                    }
//                    finalConditions = criteriaBuilder.and(finalConditions, datePredicate);
//                }
//
//equale
//                if (null != emergency && 0 != emergency) {
//                    finalConditions = criteriaBuilder.and(finalConditions, criteriaBuilder.equal(root.get("emergencyLevel"), emergency));
//                }
//大于 不等于
//                if (status != null) {
//                    finalConditions = criteriaBuilder.and(finalConditions, criteriaBuilder.greaterThan(root.get("startDate"), new Date()));
//                    finalConditions = criteriaBuilder.and(finalConditions, criteriaBuilder.notEqual(root.get("status"), 1));
//
//                }
// or
//                if (StringUtils.isNotBlank(keyword)) {
//                    finalConditions = criteriaBuilder.and(finalConditions, criteriaBuilder.or(
//                            criteriaBuilder.like(root.get("taskName"), "%" + keyword + "%"),
//                            criteriaBuilder.like(root.join("project").get("name"), "%" + keyword + "%"))
//                    );
//                }
//in
//                if (taskIds.size() > 0) {
//                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("id"));
//                    for (String id : taskIds) {
//                        in.value(id);
//                    }
//                    finalConditions = criteriaBuilder.and(finalConditions, in);
