package com.kamjin.server2.application.controller.open.api

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/hello/")
class HelloController {

    @GetMapping("say")
    fun sayHello() = "hello server2"
}