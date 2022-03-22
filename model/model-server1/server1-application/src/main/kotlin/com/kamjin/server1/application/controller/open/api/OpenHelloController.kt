package com.kamjin.server1.application.controller.open.api

import io.swagger.annotations.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/hello/")
@Api(value = "OpenHelloController", tags = ["【前端 hello控制器】"])
class OpenHelloController {

    @GetMapping("say")
    @ApiOperation(value = "sayHello", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(code = 200, message = "success", response = String::class)
    fun sayHello() = "hello server1"
}