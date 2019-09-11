# Sky社区

## 资料
[spring 文档](https://spring.io/guides/)    
[UI参考的elasticsearch社区页面](https://elasticsearch.cn/explore/)                        
[git创建ssh秘钥](https://spring.io/guides/gs/serving-web-content/)

## 工具
[git下载](https://git-scm.com/)    
[Bootstrap下载](https://v3.bootcss.com/getting-started/)    
[利用github进行登陆的API](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)   
[注册github用于登陆的应用](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/)   
[时序图设计软件](https://www.visual-paradigm.com)   
[Java发送POST请求--OkHttp工具](https://square.github.io/okhttp/)  
[springboot整合mybatis](http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)  
[spring的数据源配置](https://docs.spring.io/spring-boot/docs/2.1.9.BUILD-SNAPSHOT/reference/html/boot-features-sql.html#boot-features-embedded-database-support)  
[数据库sql自动运行的插件](https://flywaydb.org/getstarted/firststeps/commandline)   
[Lombok,实力类get/set....等方法引入的插件](https://projectlombok.org/features/all)     
[thymeleaf文档](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)   
[spring拦截器](https://docs.spring.io/spring/docs/5.0.3.RELEASE/spring-framework-reference/web.html#spring-web)     





## 快捷键

chrome中打开匿名窗口 ctrl+shift+n


## 笔记
网络直接数据传输用【dto】  
数据库中传输用【model/pojo】  
跟换ioc图标，在static目录下放入【favicon.ico】 
static目录下面的内容会加载到项目编译后的根目录下

## 脚本
```sql    
-- auto-generated definition
create table USER
(
  ID int auto_increment primary key not null ,
  NAME         VARCHAR(50),
  ACCOUNT_ID   VARCHAR(100),
  TOKEN        CHAR(36),
  GMT_CREATE   BIGINT,
  GMT_MODIFIED BIGINT
);
```
## 笔记
```sql 
分页
select * from QUESTION limit 15, 5; 
0,5    1
5,5    2
10,5   3

公式: ((n-1)*5),5
```
