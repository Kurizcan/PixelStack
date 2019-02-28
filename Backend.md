# Backend 

## 目录说明

	src/
	  |- main/							
		  |- java
			  |- com.pixelstack.ims	
			  	 |- common        		# 共有文件（包括异常处理）
				 |- controller       		# 控制层
				 |- domain			# 定义实体类
				 |- mapper			# 数据操作层
				 |- service			# 业务层
		  |- resources	
		  	 |- static
			 |- templates
			 |- application.properties # 数据库等配置信息文件
	  |- test					        # 测试相关
	     |- ...					
	  |- pom.xml   				   		# 依赖配置文件

## 如何映射

以 TagController 为例：

```java
@RestController
@RequestMapping(value="/tag")     // 通过这里配置使下面的映射都在 /tag 下
public class TagController {

    public final static int ERROR = 0;

    @Autowired								// 注解实现依赖注入
    TagService tagService;

    @GetMapping(value={"/selectTagById"})
    public Tag selectTagById(String id) throws NotFoundException {
        Tag tag = tagService.selectTagById(Integer.parseInt(id));
        if (tag == null) {
            throw new NotFoundException("address " + id + " is not exist!", Result_Error.ErrorCode.USER_NOT_FOUND.getCode());
        }
        return tag;
    }

}

```

@RestController ：将方法返回的对象以 json 格式展示

@RequestMapping：将一个特定请求或者请求模式映射到一个控制器之上，示例中就是将有关 http://localhost:8080/tag 开头的请求映射到控制器类 TagController 上

@GetMapping：将 HTTP Get 请求映射到特定的处理方法

示例中就是将类似的请求：http://localhost:8080/tag/selectTagById?id=xx 映射到方法 selectTagById(String id) 方法上进行处理

> @GetMapping 是一个组合注解，是 @RequestMapping(method = RequestMethod.GET)的缩写。类似的还有 @PostMapping 等

## 业务处理

控制器层将调用 service 层来处理业务逻辑

以接口 TagService 为例

```java
@Mapper									// 自动扫描
public interface TagService {

    @Select("select * from tb_Tag where tid = #{tid}")
    public Tag selectTagById(int tid);

    @Insert("insert into tb_Tag(tid,tagname) values (#{tid},#{tagname})")
    public int addTag(Tag tag);

    @Delete("delete from tb_Tag where tid=#{tid}")
    public int deleteTagById(int tid);
}

```

使用 mybatis 操作数据库可简单请参考：https://my.oschina.net/u/2278977/blog/866608


## 启动测试

请访问接口：

	http://localhost:8080/hello 
	
	http://localhost:8080/tag/selectTagById?id=1
	
	http://localhost:8080/tag/deleteTag?id=2
	
推荐使用接口测试工具：[Postman](https://www.getpostman.com/)


## 更新 2/27

添加了 mapper 层作为数据库操作层，service 层专门负责业务逻辑处理。

以 UserColloer 为例：

```java

public class UserController {

    public final static int ERROR = 0;

    @Autowired
    UserService userService;

    @PostMapping(value={"/register"})
    public Object userRegister(User user) {
        int status = userService.register(user);
        if (status == ERROR) {
            // 注册出现错误，抛出错误
            return null;
        }
        else {
            return new Result_Success("SUSSESS EFFECT " + status + " row");
        }
    }

}
```

在 userRegister 方法中将调用 service 层的 UserService 的 register 方法进行注册。

service 层的 UserService 将调用 mapper 层的 UserMapper 接口中的方法进行数据库层的操作。

```java
public interface UserMapper {

    @Insert("insert into tb_User_Info(username,password,authority,email,status) " +
            "values (#{username},#{password},#{authority},#{email},#{status})")
    public int addUser(User user);

    @Select("select * from tb_User_Info where username = #{username} and password = #{password}")
    public User checkUser(@Param("username") String username, @Param("password") String password);
}
```

