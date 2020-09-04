jpa处理枚举类型的自定义查询时，可以这么干：

将枚举的字符串形式存储到数据库中

将枚举的字符串形式存储到数据库中
首先在model中指定枚举类型以字符串的形式存储：(@Enumerated(EnumType.STRING)注解处理)
@Enumerated(EnumType.STRING)
@Column(name="status")
private Status status;

public static enum Status{
    PREPARE("PREP"),INPROGRESS("INPR"),FINISH("FNSH");

    private String code;
    private Status(String code){
        this.code=code;
    }
    @Override
    public String toString(){
        return code;
    }   
    public static Status parseCode(String code){
        for(Status s:Status.values()){
            if(s.code.equalsIgnoreCase(code))return s;
        }
        return null;
    }
}

对应数据库status列会存储PREPARE、INPROGRESS或FINISH，而不是以0、1、2的方式进行存储了。
如何使用@Query做自定义查询
当我们使用@Query做针对枚举的自定义查询时，方法也很简单，如下：
@Query("from Period where status != 'FINISH' order by beginDate desc")

其中FINISH即对应Status.FINISH枚举项，注意在查询语句上不要忘记加“单引号”！

