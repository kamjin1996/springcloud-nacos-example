package com.kamjin.gateway.configuration.swagger

import com.kamjin.gateway.configuration.*
import org.slf4j.*
import org.springframework.beans.factory.annotation.*
import org.springframework.beans.factory.config.*
import org.springframework.boot.context.properties.*
import org.springframework.cloud.gateway.handler.predicate.*
import org.springframework.cloud.gateway.route.*
import org.springframework.context.annotation.*
import org.springframework.context.support.*
import org.springframework.core.io.*
import org.springframework.stereotype.*
import springfox.documentation.swagger.web.*
import java.util.stream.*

@Configuration
class SwaggerFacedBundleConfig {

    private val swaggerFacedBundleName = "swagger-faced.yml"

    @Bean
    fun swaggerFacedProperties(): PropertySourcesPlaceholderConfigurer? {
        val configurer = PropertySourcesPlaceholderConfigurer()
        val yaml = YamlPropertiesFactoryBean()
        yaml.setResources(ClassPathResource(swaggerFacedBundleName))
        configurer.setProperties(yaml.getObject()!!)
        return configurer
    }

    @Bean
    @ConfigurationProperties(prefix = "swagger.faced.open")
    fun open(): SwaggerFacedItem = SwaggerFacedItem()

    @Bean
    @ConfigurationProperties(prefix = "swagger.faced.admin")
    fun admin(): SwaggerFacedItem = SwaggerFacedItem()

}

open class SwaggerFacedItem {
    open lateinit var groupName: String
    open lateinit var groupUrl: String
    open lateinit var applications: List<String>
}

@Component
@Primary
class SwaggerResourceConfig : SwaggerResourcesProvider {

    val log = LoggerFactory.getLogger(SwaggerResourceConfig::class.java)

    @Autowired
    lateinit var routeLocator: RouteLocator

    @Autowired
    lateinit var swaggerFaced: List<SwaggerFacedItem>

    override fun get(): List<SwaggerResource> {
        val resources: MutableList<SwaggerResource> = ArrayList()
        val routes: MutableList<String> = mutableListOf()
        routeLocator.routes.subscribe { route: Route -> routes.add(route.id) }
        NacosRouteDefinitionRepository.routeDefinitions.stream().forEach { route: RouteDefinition? ->
            route!!.predicates.stream()
                .filter { predicateDefinition: PredicateDefinition ->
                    "Path".equals(
                        predicateDefinition.name,
                        ignoreCase = true
                    )
                }
                .forEach { predicateDefinition: PredicateDefinition ->
                    addResource(
                        resources,
                        route,
                        predicateDefinition
                    )
                }
        }
        return resources.stream().sorted(Comparator.comparing { obj: SwaggerResource -> obj.name })
            .collect(Collectors.toList())
    }

    private fun addResource(
        resources: MutableList<SwaggerResource>,
        route: RouteDefinition?,
        predicateDefinition: PredicateDefinition
    ) {
        for (facedItem in swaggerFaced) {
            facedItem.applications.filter { route!!.id.equals(it, ignoreCase = true) }.forEach { _ ->
                resources.add(
                    swaggerResource(
                        "【${facedItem.groupName}】" + route!!.id,//中括号更直观
                        predicateDefinition.args[NAME_PREFIX]!!
                            .replace("**", SWAGGER_DOC_PATH + facedItem.groupUrl)
                    )
                )
            }
        }
    }

    private fun swaggerResource(name: String, location: String): SwaggerResource {
        log.debug("name:{},location:{}", name, location)
        val swaggerResource = SwaggerResource()
        swaggerResource.name = name
        swaggerResource.location = location
        swaggerResource.swaggerVersion = "2.0"
        return swaggerResource
    }

    companion object {
        private const val SWAGGER_DOC_PATH = "v2/api-docs?"
        private const val NAME_PREFIX = "pattern"
    }
}