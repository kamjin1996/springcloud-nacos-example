package com.kamjin.server2.application.controller.open.api

import io.swagger.annotations.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/world/")
@Api(value = "OpenWorldController", tags = ["【前端 world控制器】"])
class OpenWorldController {

    @GetMapping("round")
    @ApiOperation(value = "环绕世界", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(code = 200, message = "success", response = String::class)
    fun round() = "round world server2"
}