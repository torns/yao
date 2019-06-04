## 技术栈
- 注册中心：Nacos
- 服务网关：Gateway
- 配置中心：Nacos
- 服务调用：OpenFeign
- 负载均衡：Loadbalancer
- 熔断降级：Sentinel
- 链路追踪：Skywalking
- 消息队列：RabbitMQ
- 权限认证：Oauth2
- 项目部署：Docker
- 前端框架：Vue ElementUI

# 项目结构说明
- y3tu-cloud-common 公共模块
- y3tu-cloud-auth  Oauth2认证服务 提供token
  - y3tu-cloud-auth-authentication 签权服务
  - y3tu-cloud-auth-authorization 授权服务
- y3tu-cloud-upms 基于角色的通用权限管理模块
- y3tu-cloud-transcation 基于mq最终一致性实现可靠消息的分布式事务方案
  - y3tu-cloud-transaction-message 独立消息服务微服务
  - y3tu-cloud-transaction-sample 基于支付宝转账的演示
  - y3tu-cloud-transaction-web消息补偿管理后台
- y3tu-cloud-monitor  Spring boot admin监控以及Skywalking监控
- y3tu-cloud-log 日志中心模块
- y3tu-cloud-file 文件上传服务,这个服务可以暂时不起，因为前端还没有对接
- y3tu-cloud-gen 代码生成模块
- y3tu-cloud-gateway 后端统一入口，提供动态路由，oauth2的资源服务器

## 默认端口
```
y3tu-cloud-gateway:2019
y3tu-cloud-upms:9001
y3tu-cloud-auth-authentication:9002
y3tu-cloud-auth-authorization:9003
y3tu-cloud-log:8999
y3tu-cloud-gen:
y3tu-cloud-monitor
y3tu-cloud-transcation
y3tu-cloud-file 
```

* 启动Nacos`sh startup.sh -m standalone`,然后通过浏览器输入`http://127.0.0.1:8848/nacos/`访问Nacos控制台


* 跳过单元测试打包
```
mvn clean package -Dmaven.test.skip=true

```

* docker启动服务
```
docker run -p 9001:9001 --name upms-server -v /yxy/logs/upms-server/:/logs/upms-server/ -d y3tu/upms-server
