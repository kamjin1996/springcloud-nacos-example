package com.kamjin.gateway

import org.springframework.boot.*
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = ["com.kamjin"], exclude = [DataSourceAutoConfiguration::class])
class GatewayApplication

fun main(args: Array<String>) {
    runApplication<GatewayApplication>(*args)
}

