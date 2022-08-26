package com.ypf.ccneed.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ypf.ccneed.entity.User;
import com.ypf.ccneed.exception.CommonException;
import com.ypf.ccneed.exception.ErrorCodeEnum;
import com.ypf.ccneed.service.IUserService;
import com.ypf.ccneed.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ypf
 * @since 2022-07-27
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @Cacheable(cacheNames = {"user"},keyGenerator = "selfKeyGenerate")
    @RequestMapping("/list/{pageNo}")
    @ResponseBody
    public String userList(@PathVariable Long pageNo) {
        if (pageNo == null) {
            pageNo = 0l;
        }
        IPage<User> page = new Page(pageNo, 100l);
        List<User> records = userService.page(page,
                new QueryWrapper<User>().select("id", "nickname", "admin", "email", "user_status")).getRecords();
        return JSON.toJSONString(records);

    }

    @CacheEvict
    @RequestMapping("/deleteByids")
    @ResponseBody
    public String userList(@RequestBody List<Integer> ids) {
        if (ids == null && ids.size() > 0) {
            return ResponseVo.failed(ErrorCodeEnum.UPDATE_IMG_ERROR.getCode(), ErrorCodeEnum.DELETE_USER_WRONG.getMsg());
        }
        try {
            userService.removeByIds(ids);
        } catch (RuntimeException e) {
            throw new CommonException(ErrorCodeEnum.DELETE_USER_RUNTIME_WRONG.getCode(), ErrorCodeEnum.DELETE_USER_RUNTIME_WRONG.getMsg());
        }
        return ResponseVo.success("10000", "删除成功");

    }
}
