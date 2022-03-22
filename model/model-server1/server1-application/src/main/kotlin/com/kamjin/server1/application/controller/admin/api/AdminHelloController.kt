package com.kamjin.server1.application.controller.admin.api

import io.swagger.annotations.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

/**
 * <p>
 * admin hello controller
 * </p>
 *
 * @author kam
 * @since 2022/03/22
 */
@RestController
@RequestMapping("/admin/hello/")
@Api(value = "AdminHelloController", tags = ["【后台 Hello控制器】"])
class AdminHelloController {

    @ApiOperation(value = "adminSayHello", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(code = 200, message = "success", response = String::class)
    @GetMapping("say")
    fun say() = "admin hello server1"
}