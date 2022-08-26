package com.ypf.ccneed.vo;

import com.ypf.ccneed.entity.Note;
import lombok.Data;

/**
 * @program: ccneed
 * @author: yanpengfan
 * @create: 2022-07-31 16:33
 **/
@Data
public class NoteVo {
   private  Note note;
   private  String summary;
   private  Integer viewsCount = 0;
   private  Integer commentsCount=0;
}
