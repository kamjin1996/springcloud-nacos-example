package com.kamjin.gateway.configuration

import org.springframework.context.ApplicationEventPublisher
import com.alibaba.cloud.nacos.NacosConfigManager
import org.springframework.beans.factory.annotation.*
import org.springframework.context.annotation.*

@Configuration
class NacosDynamicRoute {

    @Bean
    fun nacosRouteDefinitionRepository(
        publisher: ApplicationEventPublisher,
        nacosConfigManager: NacosConfigManager,
        @Value("\${spring.cloud.nacos.config.router-data-id:gateway-router.json}")
        routerDataId: String
    ) = NacosRouteDefinitionRepository(routerDataId, publisher, nacosConfigManager)
}