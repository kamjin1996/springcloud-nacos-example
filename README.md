# springcloud-nacos-example
springcloud结合nacos，gateway实现动态路由

nacos-config-example文件夹中的配置文件在nacos控制台中配置，其中各服务的bootstrap-${spring.profiles.active}.yml配置文件配置了nacos的namespace和group，服务地址等信息