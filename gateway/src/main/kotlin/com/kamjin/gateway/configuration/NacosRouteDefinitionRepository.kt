package com.kamjin.gateway.configuration

import org.springframework.context.ApplicationEventPublisher
import com.alibaba.cloud.nacos.NacosConfigManager
import com.alibaba.fastjson.*
import com.alibaba.nacos.api.config.listener.*
import org.springframework.cloud.gateway.route.RouteDefinitionRepository
import org.springframework.cloud.gateway.route.RouteDefinition
import com.alibaba.nacos.api.exception.NacosException
import org.slf4j.*
import reactor.core.publisher.Flux
import org.springframework.cloud.gateway.event.RefreshRoutesEvent
import reactor.core.publisher.Mono

class NacosRouteDefinitionRepository(
    private val routerDataId: String,
    private val publisher: ApplicationEventPublisher,
    private val nacosConfigManager: NacosConfigManager
) : RouteDefinitionRepository {

    private val log = LoggerFactory.getLogger(javaClass.name)

    private val getConfigTimeoutMs = 6000L

    init {
        addListener()
    }

    /**
     * 获取路由列表信息
     *
     * @return
     */
    private fun routeDefinitions0(): List<RouteDefinition> {
        try {
            val content = nacosConfigManager.configService.getConfig(
                routerDataId,
                nacosConfigManager.nacosConfigProperties.group,
                getConfigTimeoutMs
            )
            return parseRouteDefinition(content)
        } catch (e: NacosException) {
            log.error("nacos gateway路由文件解析失败", e)
        }
        return listOf()
    }

    override fun getRouteDefinitions() = Flux.fromIterable(routeDefinitions0())

    /**
     * 添加Nacos监听
     */
    private fun addListener() {
        try {
            nacosConfigManager.configService.addListener(
                routerDataId,
                nacosConfigManager.nacosConfigProperties.group,
                object : Listener {
                    override fun getExecutor() = null

                    override fun receiveConfigInfo(configInfo: String) {
                        publisher.publishEvent(RefreshRoutesEvent(this))
                    }
                })
        } catch (e: NacosException) {
            log.error("nacos gateway添加路由变更监听器失败", e)
        }
    }

    override fun save(route: Mono<RouteDefinition>) = null

    override fun delete(routeId: Mono<String>) = null

    /**
     * 解析路由
     *
     * @param content
     * @return
     */
    private fun parseRouteDefinition(content: String): List<RouteDefinition> =
        JSONObject.parseArray(content, RouteDefinition::class.java)

}