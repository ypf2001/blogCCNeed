package com.ypf.ccneed.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ypf.ccneed.entity.Img;
import com.ypf.ccneed.entity.User;
import com.ypf.ccneed.exception.ErrorCodeEnum;
import com.ypf.ccneed.service.IImgService;
import com.ypf.ccneed.service.IUserService;
import com.ypf.ccneed.utils.QiNiuUtils;
import com.ypf.ccneed.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Vector;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ypf
 * @since 2022-07-27
 */
@Controller
@RequestMapping("/img")
@Slf4j
public class ImgController {
    @Autowired
    IImgService imgService;
    @Autowired
    IUserService userService;

    @RequestMapping("/generateToken")
    @ResponseBody
    public String generateToken() {
        return QiNiuUtils.UploadToken();
    }

    @RequestMapping("/save")
    @ResponseBody
    public String saveMsg(@RequestBody Img img) {
        img.setUploadTime(LocalDateTime.now());
        try {
            imgService.saveOrUpdate(img);
        } catch (Exception e) {
            return ResponseVo.failed(ErrorCodeEnum.UPDATE_IMG_ERROR.getCode(), ErrorCodeEnum.UPDATE_IMG_ERROR.getMsg());
        }

        return ResponseVo.success("20000", "图片保存成功!");
    }

    /* 默认展示最新
     * @Param: []
     * @Return: java.lang.String
     */
    @Cacheable(cacheNames = {"imgList"},keyGenerator = "selfKeyGenerate")
    @RequestMapping("/getOneImg")
    @ResponseBody
    public String getOneImg() {
        Img one = imgService.getOne(new QueryWrapper<Img>().last("order by id desc limit 1"));
        return JSON.toJSONString(one);
    }

    @Cacheable(cacheNames = {"imgList"},keyGenerator = "selfKeyGenerate")
    @RequestMapping("/getImgList/{pageNo}")
    @ResponseBody
    public String getImgList(@PathVariable Integer pageNo) {
        IPage<Img> page = new Page(pageNo, 100l);
        List<Img> records = imgService.page(page).getRecords();
        return JSON.toJSONString(records);
    }

    @Cacheable(cacheNames = {"imgList"},keyGenerator = "selfKeyGenerate")
    @RequestMapping("/getImgListById/{userId}/{pageNo}")
    @ResponseBody
    public String getImgList(@PathVariable Integer userId,
                             @PathVariable Integer pageNo,
                             @PathParam("userToken") String token) {
        IPage<Img> page = new Page(pageNo, 100l);
        List<Img> records = imgService.page(page, new QueryWrapper<Img>().eq("user_id", userId)).getRecords();
        User user = userService.getById(userId);
        user.setImgList(new Vector<Img>(records));
        return JSON.toJSONString(records);
    }

}
