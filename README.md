# y3tu-cloud

## 基于spring cloud的分布式应用平台

通过网关登录发送用户名密码获取token:  
```
http://localhost:8443/authorization-server/oauth/token?grant_type=password&client_id=app&client_secret=123456&password=123456&username=admin
```
然后在请求头中加入
```
key=Authorization
value=bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsaWNlbnNlIjoibWFkZSBieSB5M3R1IiwiYXVkIjpbInRlc3QiXSwidXNlcl9uYW1lIjoiYWRtaW4iLCJzY29wZSI6WyJzZXJ2ZXIiXSwiZXhwIjoxNTM1MjIxNjIyLCJ1c2VySWQiOiIxMDEiLCJhdXRob3JpdGllcyI6WyJBRE1JTiJdLCJqdGkiOiJhMzg5Mjk2Yy0wNTgxLTQwZTAtYjg3OS1kOGE1MGJlMzFiMGYiLCJjbGllbnRfaWQiOiJhcHAifQ.sR6e4JkGRQxvypWFGWqibWRVGMF0kpf8GMQk_bUtWD8
```
就可以通过网关访问其他服务

启动服务： 

| 服务分类  | 服务名                     |   简介     |  应用地址                | 文档 |
|----------|---------------------------|-----------|-------------------------|------|
|  center  | y3tu-cloud-eureka         | 注册中心   |  http://localhost:8501  |      |
|  center  | y3tu-cloud-bus            | 消息中心   |  http://localhost:8071  |      |
|  center  | y3tu-cloud-config         | 配置中心   |  http://localhost:8888  |      |
|  modules | y3tu-cloud-upms-service   |通用用户权限服务| http://localhost:8081|     | 
|  auth    | y3tu-cloud-authorization  | 授权服务   |  http://localhost:8000  |    |
|  auth    | y3tu-cloud-authentication | 签权服务   |  http://localhost:8001  |      |
|  gateway | y3tu-cloud-gateway        | 网关       |  http://localhost:8443 |      |
|  visual  | y3tu-cloud-monitor        | 可视化监控 |  http://localhost:5000 |      |

y3tu-cloud-monitor 可视化监控集成了spring boot admin 2.x 和 turbine
比如要访问y3tu-cloud-authorization的hystrix dashboard地址为    
`http://localhost:8000/actuator/hystrix.stream`  
要访问turbine,地址为  
`http://localhost:5000/turbine.stream`


