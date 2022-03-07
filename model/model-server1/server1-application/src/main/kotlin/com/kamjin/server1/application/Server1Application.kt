package com.kamjin.server1.application;

import org.springframework.boot.runApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients(basePackages = ["com.kamjin"])
@EnableDiscoveryClient
@EnableCircuitBreaker
@SpringBootApplication(scanBasePackages = ["com.kamjin"])
class Server1Application

fun main(args: Array<String>) {
    runApplication<Server1Application>(*args)
}
