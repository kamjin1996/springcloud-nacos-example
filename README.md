# springcloud-nacos-example
springcloud结合nacos，gateway实现动态路由

### gateway整合nacos实现动态路由(dev分支)
nacos-config-example文件夹中的配置文件在nacos控制台中配置<br/>
dataId对应于文件名<br/>
maven的profile需要勾选dev<br/>
其中各服务的bootstrap-${spring.profiles.active}.yml配置文件配置了nacos的namespace和group，服务地址等信息

### gateway聚合knif4j实现网关统一聚合文档(knif4j分支)
