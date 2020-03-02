package com.scsse.workflow.controller;

import com.scsse.workflow.service.LoginService;
import com.scsse.workflow.service.UserService;
import com.scsse.workflow.util.result.Result;
import com.scsse.workflow.util.result.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Andrew Dong
 * @ProjectName workflow
 * @date 2019-09-15 08:56
 */
@RestController
public class LoginController {

    private final LoginService loginService;

    private final UserService userService;

    @Autowired
    public LoginController(LoginService loginService, UserService userService) {
        this.loginService = loginService;
        this.userService = userService;
    }

    @PostMapping("/webLogin")
    public Result webLogin(@RequestParam("stuId")String stuId,
                          @RequestParam("password")String password) {
        UsernamePasswordToken token = new UsernamePasswordToken();
        token.setUsername(stuId);
        token.setPassword(password.toCharArray());

        SecurityUtils.getSubject().login(token);

        return ResultUtil.success(
                userService.findUserByStuNumber(stuId)
        );
    }

    @GetMapping("/wxLogin")
    public Result wxLogin(@RequestParam(name = "code") String code) {

        String openid = loginService.getWxSession(code);

        UsernamePasswordToken token = new UsernamePasswordToken();
        token.setUsername(openid);
        token.setPassword("123456".toCharArray());

        SecurityUtils.getSubject().login(token);

        return ResultUtil.success(openid);
    }


}
