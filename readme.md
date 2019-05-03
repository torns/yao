## 技术栈
- 注册中心：Nacos
- 服务网关：Spring cloud-Gateway
- 配置中心：Nacos
- 服务调用：Spring-cloud-open-Feign
- 负载均衡：Spring-cloud-loadbalancer
- 熔断降级：Sentinel
- 链路追踪：Skywalking
- 消息队列：RabbitMQ
- 权限认证：Spring secruity Oauth2
- 项目部署：Docker+Rancher+K8S
- 前端框架：Vue2.0+ElementUI

# 项目结构说明
- y3tu-cloud-common 公共模块
- y3tu-cloud-auth  Oauth2 认证服务器 提供token
- y3tu-cloud-back 后台管理模块
- y3tu-cloud-transcation 基于mq最终一致性实现可靠消息的分布式事务方案
  - y3tu-cloud-transaction-message 独立消息服务微服务
  - y3tu-cloud-transaction-sample 基于支付宝转账的演示
  - y3tu-cloud-transaction-web消息补偿管理后台
- y3tu-cloud-monitor Spring boot admin监控以及Skywalking监控
- y3tu-cloud-log 日志中心模块
- y3tu-cloud-file 文件上传服务,这个服务可以暂时不起，因为前端还没有对接
- y3tu-cloud-gen 代码生成模块
- y3tu-cloud-starter 自定义封装各种starer 目前封装了日志处理
- y3tu-cloud-gateway 后端统一入口，提供动态路由，oauth2的资源服务器

## 项目运行
```
git clone https://github.com/y3tu/y3tu-cloud
先配置数据库，然后reids，需要启动rabbitmq,启动nacos,启动sentinel
启动顺序：最好按顺序启动，不按顺序启动，至少要把网关放到最后启动
注意：Nacos先修改配置连自己本地数据库，并把nacos的配置数据库导入到自己本地数据库
导入之后，检查nacos各个微服务相关配置的mysql，redis,rabbitmq配置是否正确
y3tu-cloud-auth
y3tu-cloud-back
y3tu-cloud-log
y3tu-cloud-gen
y3tu-cloud-monitor
y3tu-cloud-transcation
y3tu-cloud-file 
y3tu-cloud-gateway
```