package com.scsse.workflow.service.shiro;

import com.scsse.workflow.entity.dto.UserDto;
import com.scsse.workflow.entity.model.User;
import com.scsse.workflow.repository.UserRepository;
import com.scsse.workflow.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    AuthorityQuerier authorityQuerier;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        Object principal = principals.getPrimaryPrincipal();
        UserDto userDTO = (UserDto) principal;

        int id = userDTO.getUserId();//user id not student id

        //获取用户的角色、权限信息
        List<String> roleNameList = authorityQuerier.getRoleNameListByUser_id(id);
        List<String> permissionNameList = authorityQuerier.getPermissionNameListByUser_id(id);

        //注入角色与权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        info.addRoles(roleNameList);
        info.addStringPermissions(permissionNameList);


        return info;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        //数据库匹配，认证
        String username = token.getUsername(); //web端用学号，小程序端用openid
        User user;
        if (username.length() <= 11) { //学号长度为11位，学号密码登录不会超过11位。
            String password = new String(token.getPassword());

            user = userRepository.findByStuNumber(username);
            if (user == null || !(user.getPassword() + "").equals(password)) throw new AuthenticationException();
        } else { //超过11位说明用的是
            user = userRepository.findByOpenId(username);
        }


        // 处理登录信息
        UserDto userDTO = new UserDto();
        userDTO.setCollege(user.getCollege());
        userDTO.setGender(user.getGender());
        userDTO.setUserEmail(user.getCollege());
        userDTO.setUserGrade(user.getGrade());
        userDTO.setUserId(user.getId());
        userDTO.setUserName(user.getName());
        userDTO.setUserNumber(user.getStuNumber());
        userDTO.setUserPhone(user.getPhone());
        userDTO.setUserResume(user.getResume());
        userDTO.setUserSpecialty(user.getSpecialty());
        userDTO.setWxId(user.getWxId());

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userDTO, token.getCredentials(), getName());
        return info;

    }
}
