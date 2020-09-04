
自定义sql的时候报错，但是数据库数据是发生了预期变化的
jpa获取数据报could not extract ResultSet错误提示

Caused by: org.hibernate.exception.SQLGrammarException: could not extract ResultSet 报错原因


解决方法：

1. 查询类的时候可能是：
我们在写项目的时候，经常会用到一些没有主键的关系表，而hibernate呢必须给实体类指定主键。所以我们在实体类里面加入注解@Id就可以了

加入注解后，项目可以正常的启动，但是在调用hibernate的save方法的时候会报一个could not extract ResultSet的错误。

这个错误出现的原因其实很简单，因为jpa的save方法，在保存后会返回一个实体，因为没有主键的原因，所以就导致了报了这个错误。

 解决这个问题的方法就是，将@Id加到任何一个get方法的上面就行了

2. 
数据库连接池配置方言与数据库不匹配
表与字段没有对应起来
entity 缺少主键
自定义的sql是DDL，加上两个注解
 
#oracle数据库方言
hibernate.dialect=org.hibernate.dialect.Oracle10gDialect
  #mysql数据库方言
 hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
 
 通过修改hibernate.cfg.xml中的方言配置
 MySQL的方言配置：<property name="hibernate.dialect">org.hibernate.dialect.MySQL5Dialect</property>
 oracle的方言配置：<property name="hibernate.dialect">org.hibernate.dialect.Oracle10gDialect</property>
   --------------------- 

JPA写SQL
查询的简单
参数传递
```$text
where 属性=？1 AND 属性=？2         selectXXByYY(String name,String pwd)
where 属性=:java变量名字                  selectByTestAlias(@org.springframework.data.repository.query.Param("alias") String alias);

```
@org.springframework.data.jpa.repository.Query(value = "SELECT * FROM `t_project_requirement` WHERE alias=:alias ", nativeQuery = true)

@Query(value = "SELECT * FROM `t_project_requirement` WHERE apply_example=?1  AND  note =?2", nativeQuery = true)

修改类 
@org.springframework.transaction.annotation.Transactional
@org.springframework.data.jpa.repository.Modifying
