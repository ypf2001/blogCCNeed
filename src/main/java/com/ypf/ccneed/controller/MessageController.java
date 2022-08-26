package com.ypf.ccneed.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ypf.ccneed.entity.Message;
import com.ypf.ccneed.exception.ErrorCodeEnum;
import com.ypf.ccneed.service.IMessageService;
import com.ypf.ccneed.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @program: ccneed
 * @author: yanpengfan
 * @create: 2022-08-02 09:51
 **/
@Controller
@RequestMapping("/message")
@Slf4j
public class MessageController {
    @Autowired
    IMessageService messageService;

    @RequestMapping("/getMsg/{id}")
    @ResponseBody
    public String getMessage(@PathVariable("id") Integer id){
        List<Message> note=null;
        try {
           note = messageService
                    .list(new QueryWrapper<Message>().eq("note_id", id));
        }catch (Exception e){
            log.info(e.getMessage());
            return  ResponseVo.failed(ErrorCodeEnum.FETCH_COMMENT_IO_ERROR.getCode(),ErrorCodeEnum.FETCH_COMMENT_IO_ERROR.getMsg());

        }
        if(note==null){
            return  ResponseVo.failed(ErrorCodeEnum.FETCH_COMMENT_IO_ERROR.getCode(),ErrorCodeEnum.FETCH_COMMENT_IO_ERROR.getMsg());
        }
        return ResponseVo.dataReturn("40000",note);
    }


    @RequestMapping("/sendMsg")
    @ResponseBody
    public String sendMessage( @RequestBody Message message){
        String res = "";
        try {
            message.setUploadTime(LocalDateTime.now());
            if(message.getMessageBody()==null||message.getMessageBody().equals("")){
                res=ResponseVo.failed(ErrorCodeEnum.SEND_COMMENT_IO_ERROR.getCode(),
                        ErrorCodeEnum.SEND_COMMENT_IO_ERROR.getMsg());
            }
            messageService.save(message);
            res=ResponseVo.success("40000","发送成功");
        }catch (Exception e){
            log.info(e.getMessage());
            return res;

        }
        return res;
    }
}