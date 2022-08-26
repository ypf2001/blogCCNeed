package com.ypf.ccneed.service.impl;

import com.ypf.ccneed.entity.Message;
import com.ypf.ccneed.mapper.MessageMapper;
import com.ypf.ccneed.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ypf
 * @since 2022-08-01
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

}
