package com.ypf.ccneed.controller;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ypf.ccneed.entity.Message;
import com.ypf.ccneed.entity.Note;
import com.ypf.ccneed.exception.ErrorCodeEnum;
import com.ypf.ccneed.service.IMessageService;
import com.ypf.ccneed.service.INoteService;
import com.ypf.ccneed.service.IUserService;
import com.ypf.ccneed.utils.GzipUtils;
import com.ypf.ccneed.vo.NoteVo;
import com.ypf.ccneed.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
@RequestMapping("/note")
@Slf4j
public class NoteController {
    private static final String COMMENT_COUNT_KEY = "ccneed:commentCount:";
    private static final String VIEWS_COUNT_KEY = "ccneed:viewsCount:";
    @Autowired
    INoteService noteService;
    @Autowired
    IUserService userService;
    @Autowired
    IMessageService messageService;
    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping("/subscribe")
    @ResponseBody
    @CachePut(cacheNames = {"note", "articleList"}, keyGenerator = "selfKeyGenerate")
    public String subscribe(@RequestParam("noteBody") String noteBody,
                            @RequestParam("noteTitle") String noteTitle,
                            @RequestParam("textType") String noteType,
                            @RequestParam("userId") Integer userId) {

        if (noteBody == null || noteBody.length() < 50 || noteTitle == null) {
            return ResponseVo.failed(ErrorCodeEnum.SUBSCRIBE_TEXT_SHOUT_ERROR.getCode(), ErrorCodeEnum.SUBSCRIBE_TEXT_SHOUT_ERROR.getMsg());
        }

        String gzipText = GzipUtils.compress(noteBody);
        Note note = new Note();
        note.setId(0);
        note.setNoteDesc(noteBody.substring(0, 150).replace("#", " ").replace("'", ""));
        note.setNoteBody(gzipText);
        note.setNoteTitle(noteTitle);
        note.setUploadTime(LocalDateTime.now());
        note.setUserId(userId);
        note.setNoteType(noteType);
        noteService.save(note);
        log.info("文章" + noteTitle + "保存成功");
        return ResponseVo.success("30000", "文章上传成功");
    }

    @ResponseBody
    // @Cacheable(cacheNames = {"articleList"}, keyGenerator = "selfKeyGenerate")
    @RequestMapping("/getArticleList/{PageNo}")
    public String getArticleList(@PathVariable Long PageNo) {
        IPage<Note> page = new Page(PageNo, 10);
        List<Note> records = noteService.page(page, new QueryWrapper<Note>().select("note_title", "note_desc", "upload_time", "note_type", "user_id", "id")).getRecords();
        List<NoteVo> noteVos = new ArrayList<>();
        Integer CommentsCount = 0;
        for (Note note : records
        ) {
            if (redisTemplate.opsForValue().get(COMMENT_COUNT_KEY + note.getId()) != null) {
                CommentsCount = (Integer) redisTemplate.opsForValue().get(COMMENT_COUNT_KEY + note.getId());
            } else {
                CommentsCount = messageService.count(new QueryWrapper<Message>().eq("id", note.getId()));
            }
            NoteVo noteVo = new NoteVo();
            noteVo.setNote(note);
            noteVo.setCommentsCount(CommentsCount);
            noteVo.setViewsCount(redisTemplate.opsForValue().get(VIEWS_COUNT_KEY + note.getId()) == null ? 0 : (Integer) redisTemplate.opsForValue().get(VIEWS_COUNT_KEY + note.getId()));
            noteVos.add(noteVo);

        }
        return JSON.toJSONString(noteVos);
    }
//    getArticleById

    @ResponseBody
    @Cacheable(cacheNames = {"note"}, keyGenerator = "selfKeyGenerate", cacheManager = "articleCacheManager")
    @RequestMapping("/getArticleById/{id}")
    public String getArticleById(@PathVariable Long id) {
        NoteVo noteVo = new NoteVo();
        try {
            Note note = noteService.getById(id);
            note.setNoteBody(GzipUtils.uncompress(note.getNoteBody()));
            Integer CommentsCount = 0;
            if (redisTemplate.opsForValue().get(COMMENT_COUNT_KEY + note.getId()) != null) {
                CommentsCount = (Integer) redisTemplate.opsForValue().get(COMMENT_COUNT_KEY + note.getId());
            } else {
                CommentsCount = messageService.count(new QueryWrapper<Message>().eq("id", note.getId()));
            }
            Integer now = noteVo.getViewsCount() + 1;
            redisTemplate.opsForValue().set(VIEWS_COUNT_KEY + note.getId(), now);
            redisTemplate.opsForValue().set(COMMENT_COUNT_KEY + note.getId(), CommentsCount);
            Object viewCount = redisTemplate.opsForValue().get(VIEWS_COUNT_KEY + note.getId());
            noteVo.setViewsCount((Integer) viewCount);
            noteVo.setCommentsCount(CommentsCount);
            noteVo.setNote(note);
        } catch (Exception e) {
            return ResponseVo.failed(ErrorCodeEnum.FETCH_TEXT_IO_ERROR.getCode(), ErrorCodeEnum.FETCH_TEXT_IO_ERROR.getMsg());
        }
        if (noteVo.getNote() == null) {
            return ResponseVo.failed(ErrorCodeEnum.FETCH_TEXT_IO_ERROR.getCode(), ErrorCodeEnum.FETCH_TEXT_IO_ERROR.getMsg());

        }
        return JSON.toJSONString(noteVo);
    }

    @ResponseBody
    @RequestMapping("/search")
    public String searchKeywords(@RequestParam("words") String words, @RequestParam("pageNo") Integer PageNo) {
        IPage<Note> page = new Page(PageNo, 10);
        List<Note> notes = noteService.page(page, new QueryWrapper<Note>().like("note_title", words).select("note_title", "note_desc", "upload_time", "note_type", "user_id", "id")).getRecords();
        List<NoteVo> NoteVos = new ArrayList<>();

        notes.forEach(note -> {
            Integer CommentsCount = 0;
            if (redisTemplate.opsForValue().get(COMMENT_COUNT_KEY + note.getId()) != null) {
                CommentsCount = (Integer) redisTemplate.opsForValue().get(COMMENT_COUNT_KEY + note.getId());
            } else {
                CommentsCount = messageService.count(new QueryWrapper<Message>().eq("id", note.getId()));
            }
            NoteVo noteVo = new NoteVo();
            noteVo.setNote(note);
            noteVo.setCommentsCount(CommentsCount);
            noteVo.setViewsCount(redisTemplate.opsForValue().get(VIEWS_COUNT_KEY + note.getId()) == null ? 0 : (Integer) redisTemplate.opsForValue().get(VIEWS_COUNT_KEY + note.getId()));
            NoteVos.add(noteVo);
        });
        return ResponseVo.dataReturn("50000", NoteVos);
    }

    @ResponseBody
    @RequestMapping("/byCategory")
    public String searchByCategory(@RequestParam("cate") String cate, @RequestParam("pageNo") Integer PageNo) {
        IPage<Note> page = new Page(PageNo, 10);
        List<Note> notes = noteService.page(page, new QueryWrapper<Note>().eq("note_type", cate).select("note_title", "note_desc", "upload_time", "note_type", "user_id", "id")).getRecords();
        List<NoteVo> NoteVos = new ArrayList<>();

        notes.forEach(note -> {
            Integer CommentsCount = 0;
            if (redisTemplate.opsForValue().get(COMMENT_COUNT_KEY + note.getId()) != null) {
                CommentsCount = (Integer) redisTemplate.opsForValue().get(COMMENT_COUNT_KEY + note.getId());
            } else {
                CommentsCount = messageService.count(new QueryWrapper<Message>().eq("id", note.getId()));
            }
            NoteVo noteVo = new NoteVo();
            noteVo.setNote(note);
            noteVo.setCommentsCount(CommentsCount);
            noteVo.setViewsCount(redisTemplate.opsForValue().get(VIEWS_COUNT_KEY + note.getId()) == null ? 0 : (Integer) redisTemplate.opsForValue().get(VIEWS_COUNT_KEY + note.getId()));
            NoteVos.add(noteVo);
        });
        return ResponseVo.dataReturn("50000", NoteVos);
    }

    public void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        //定义资源会对谁生效
        rule.setResource("");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(2);


    }
}

