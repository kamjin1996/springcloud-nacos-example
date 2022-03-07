package com.kamjin.server2.application;

import org.springframework.boot.runApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients(basePackages = ["com.kamjin"])
@EnableDiscoveryClient
@EnableCircuitBreaker
@SpringBootApplication(scanBasePackages = ["com.kamjin"])
class Server2Application

fun main(args: Array<String>) {
    runApplication<Server2Application>(*args)
}
