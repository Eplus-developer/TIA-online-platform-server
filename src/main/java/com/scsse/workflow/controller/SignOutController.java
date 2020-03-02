package com.scsse.workflow.controller;


import com.scsse.workflow.util.result.Result;
import com.scsse.workflow.util.result.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;

@RestController
public class SignOutController {

    @PostMapping("/signOut")
    public Result logout() throws IOException {
        SecurityUtils.getSubject().logout();
        return ResultUtil.success();
    }

}
