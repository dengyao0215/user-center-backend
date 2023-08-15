package com.yupi.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.usercenter.common.BaseResponse;
import com.yupi.usercenter.common.ErrorCode;
import com.yupi.usercenter.common.ResultUtils;
import com.yupi.usercenter.constant.UserConstant;
import com.yupi.usercenter.exception.BusinessException;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.request.UserCreateRequest;
import com.yupi.usercenter.model.domain.request.UserLoginRequest;
import com.yupi.usercenter.model.domain.request.UserRegisterRequest;
import com.yupi.usercenter.model.dto.UserDTO;
import com.yupi.usercenter.service.UserService;
import com.yupi.usercenter.utils.UserHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.yupi.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.yupi.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/***
 * @title UserController
 * @description TODO 用户接口
 * @author Skadhi
 * @version 1.0.0
 * @create 2023-02-20 22:08
 **/
@RestController
@RequestMapping("/user")
//@CrossOrigin(origins = {"http://43.137.0.99/"}, allowCredentials = "true")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PRAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
            throw new BusinessException(ErrorCode.PRAMS_ERROR);
        }

        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);

        return ResultUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<UserDTO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PRAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PRAMS_ERROR);
        }

        UserDTO userDTO = userService.userLogin(userAccount, userPassword, request);

        return ResultUtils.success(userDTO);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PRAMS_ERROR);
        }

        int result = userService.userLogout(request);

        return ResultUtils.success(result);
    }

    @GetMapping("/current")
    public BaseResponse<UserDTO> getCurrentUser() {
        //Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        //User currentUser = (User) userObj;
        //if (currentUser == null) {
        //    throw new BusinessException(ErrorCode.NOT_LOGIN);
        //}

        // 从ThreadLocal中获取登录的用户
        User currentUser = UserHolder.getUser();

        Long userId = currentUser.getId();
        // todo 校验用户是否合法
        User user = userService.getById(userId);
        UserDTO safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }

    @GetMapping("/search")
    public BaseResponse<List<UserDTO>> searchUsers(String username) {
        // 从ThreadLocal中获取登录的用户
        User currentUser = UserHolder.getUser();

        // 仅管理员可查询
        if (!isAdmin(currentUser)) {
            throw new BusinessException(ErrorCode.NOT_AUTH);
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<UserDTO> resultList = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(resultList);
    }

    @PostMapping("create")
    public BaseResponse<Long> createUser(@RequestBody UserCreateRequest userCreateRequest) {
        // 从ThreadLocal中获取登录的用户
        User currentUser = UserHolder.getUser();

        // 仅管理员可新建
        if (!isAdmin(currentUser)) {
            throw new BusinessException(ErrorCode.NOT_AUTH);
        }

        if (userCreateRequest == null) {
            throw new BusinessException(ErrorCode.PRAMS_ERROR);
        }
        String userAccount = userCreateRequest.getUserAccount();
        String userPassword = userCreateRequest.getUserPassword();
        String planetCode = userCreateRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, planetCode)) {
            throw new BusinessException(ErrorCode.PRAMS_ERROR);
        }

        long result = userService.userCreateByAdmin(userCreateRequest);

        return ResultUtils.success(result);
    }

    @PostMapping("/update")
    public BaseResponse<Long> updateUser(@RequestBody UserCreateRequest userCreateRequest) {
        // 从ThreadLocal中获取登录的用户
        User currentUser = UserHolder.getUser();

        // 仅管理员可编辑
        if (!isAdmin(currentUser)) {
            throw new BusinessException(ErrorCode.NOT_AUTH);
        }

        if (userCreateRequest == null) {
            throw new BusinessException(ErrorCode.PRAMS_ERROR);
        }
        String userAccount = userCreateRequest.getUserAccount();
        String userPassword = userCreateRequest.getUserPassword();
        String planetCode = userCreateRequest.getPlanetCode();
        Long userDTOId = userCreateRequest.getId();
        if (StringUtils.isAnyBlank(userAccount, userPassword, planetCode)) {
            throw new BusinessException(ErrorCode.PRAMS_ERROR);
        }
        if (userDTOId == null) {
            throw new BusinessException(ErrorCode.PRAMS_ERROR, "ID不存在");
        }

        long result = userService.userUpdateByAdmin(userCreateRequest);

        return ResultUtils.success(result);
    }


    @GetMapping("/delete")
    public BaseResponse<Boolean> deleteUser(Long id) {
        // 从ThreadLocal中获取登录的用户
        User currentUser = UserHolder.getUser();

        // 仅管理员可删除
        if (!isAdmin(currentUser)) {
            throw new BusinessException(ErrorCode.NOT_AUTH);
        }

        if (id <= 0) {
            throw new BusinessException(ErrorCode.PRAMS_ERROR);
        }

        boolean result = userService.removeById(id);
        return ResultUtils.success(result);
    }

    /**
     * 是否为管理员
     * @param currentUser 用户角色
     * @return 是否为管理员
     */
    private boolean isAdmin(User currentUser) {
        //Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        //User user = (User) userObj;
        return currentUser != null && currentUser.getUserRole() == ADMIN_ROLE;
    }

}
