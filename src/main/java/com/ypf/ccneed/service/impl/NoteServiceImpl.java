package com.ypf.ccneed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ypf.ccneed.entity.Note;
import com.ypf.ccneed.mapper.NoteMapper;
import com.ypf.ccneed.service.INoteService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ypf
 * @since 2022-07-27
 */
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements INoteService {

}
