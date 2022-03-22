package com.kamjin.gateway.configuration.swagger

import org.springframework.beans.factory.annotation.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.*
import springfox.documentation.swagger.web.*
import java.util.*

/**
 * 自定义Swagger的各个配置节点
 * Created by macro on 2020/7/9.
 */
@RestController
class SwaggerHandler {

    @Autowired
    lateinit var swaggerResources: SwaggerResourcesProvider

    @Autowired(required = false)
    lateinit var securityConfiguration: SecurityConfiguration

    @Autowired(required = false)
    lateinit var uiConfiguration: UiConfiguration

    /**
     * Swagger安全配置，支持oauth和apiKey设置
     */
    @GetMapping("/swagger-resources/configuration/security")
    fun securityConfiguration(): Mono<ResponseEntity<SecurityConfiguration>> {
        return Mono.just(
            ResponseEntity(
                Optional.ofNullable(securityConfiguration).orElse(SecurityConfigurationBuilder.builder().build()),
                HttpStatus.OK
            )
        )
    }

    /**
     * Swagger UI配置
     */
    @GetMapping("/swagger-resources/configuration/ui")
    fun uiConfiguration(): Mono<ResponseEntity<UiConfiguration>> {
        return Mono.just(
            ResponseEntity(
                Optional.ofNullable(uiConfiguration).orElse(UiConfigurationBuilder.builder().build()), HttpStatus.OK
            )
        )
    }

    /**
     * Swagger资源配置，微服务中这各个服务的api-docs信息
     */
    @GetMapping("/swagger-resources")
    fun swaggerResources(): Mono<ResponseEntity<*>> {
        return Mono.just(ResponseEntity(swaggerResources.get(), HttpStatus.OK))
    }
}