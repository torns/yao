#### 使用日志服务
1.Maven
在需要记录日志的服务的pom中添加下面的依赖
```
<dependency>
    <groupId>com.y3tu</groupId>
    <artifactId>yao-log-starter</artifactId>
    <version>1.0</version>
</dependency>
```
2.在需要记录日志的方法上添加注解
```
@Log(serviceId = 'log-server', moduleName = '查询', actionName = "日志信息分页查询",saveMode = SaveModeEnum.DB)
```
> 记录日志采用spring aop切面方式，所以要保证方法所属的对象在spring容器内已被实例化，否则切面将失效，日记无法记录！

* serviceId：服务名
* moduleName：模块名
* actionName：操作名
* saveMode:保存方式 (SaveModeEnum.DB:数据库;SaveModeEnum.ES:elasticsearch;  默认保存到数据库)

3.可以配置默认的日志保存方式
```
y3tu.cloud.log.saveMode = ES 

ES:elasticsearch DB:数据库
```
4.目前只支持往消息队列中插入记录日志的消息，日志服务会监听消息然后把日志写到数据库中,所以要使用日志服务需要添加消息队列依赖，并和日志服务配置同一个消息队列。