# Yao 
>基于Spring Cloud Alibaba、Oauth2、Vue的快速开发平台。

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

## 项目结构说明
- yao-common 公共模块
- yao-auth  Oauth2认证服务 提供token
  >yao-authentication 签权服务   
  >yao-authorization 授权服务
- yao-back >后台管理模块 
  >包括upms(通用权限管理) oa(流程办公) cms(内容管理) ...
- yao-log 日志中心模块
  >yao-log-sever 日志服务   
  >yao-log-starter 日志starter
- yao-gateway 后端统一入口，提供动态路由，oauth2的资源服务器
- yao-ui 基于Vue ElementUI的后台管理界面，可打包进jar包，也可单独部署

## 功能模块
```
√ 用户管理 
√ 资源管理(菜单和权限) 
√ 角色管理 
√ 部门管理 
√ 字典管理
```


## 默认端口
```
yao-gateway:2019
yao-back:9001
yao-authentication:9002
yao-authorization:9003
yao-log:8999
```

## 编译部署
* 启动Nacos`sh startup.sh -m standalone`,然后通过浏览器输入`http://127.0.0.1:8848/nacos/`访问Nacos控制台
* 跳过单元测试打包
```
mvn clean package -Dmaven.test.skip=true
```
* docker启动服务
```
docker run -p 9001:9001 --name back-server -v /yxy/logs/back-server/:/logs/back-server/ -d y3tu/back-server
```