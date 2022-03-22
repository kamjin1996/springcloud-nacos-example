package com.kamjin.server2.application.configuration

import org.springframework.beans.factory.annotation.*
import org.springframework.context.annotation.*
import springfox.bean.validators.configuration.*
import springfox.documentation.builders.*
import springfox.documentation.service.*
import springfox.documentation.spi.*
import springfox.documentation.spring.web.plugins.*
import springfox.documentation.swagger2.annotations.*

/**
 * <p>
 * swagger config
 * </p>
 *
 * @author kam
 * @since 2022/03/22
 */
@Configuration
@EnableSwagger2WebMvc
@Import(
    BeanValidatorPluginsConfiguration::class
)
class SwaggerConfig {

    @Value("\${spring.application.name}")
    lateinit var appName: String

    @Bean
    fun openApi() = buildDocket(SwaggerGroupType.OPEN, appName)

    @Bean
    fun adminApi() = buildDocket(SwaggerGroupType.ADMIN, appName)

    private fun buildDocket(apiGroup: SwaggerGroupType, appName: String): Docket {
        val requestHandlerPredicate = RequestHandlerSelectors
            .basePackage("com.kamjin.${appName.replace("-", ".")}.controller.${apiGroup.key}.api")
        return Docket(DocumentationType.SWAGGER_2)
            .apiInfo(groupApiInfo())
            .groupName(apiGroup.groupName)//分组名称
            .select()
            .apis(requestHandlerPredicate)//controller扫描包路径
            .paths(PathSelectors.any())
            .build()
    }

    private fun groupApiInfo(): ApiInfo {
        val api = appName.toUpperCase() + "-API"
        return ApiInfoBuilder()
            .title(api) //.description("<div style='font-size:14px;color:red;'>swagger-bootstrap-ui-demo RESTful APIs</div>")
            .description(api)
            .termsOfServiceUrl("http://www.group.com/")
            .version("1.0")
            .build()
    }
}

enum class SwaggerGroupType(val key: String, val groupName: String) {
    OPEN("open", "openApi"),
    ADMIN("admin", "adminApi"),
}