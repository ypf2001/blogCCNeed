package com.ypf.ccneed;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ypf.ccneed.entity.Note;
import com.ypf.ccneed.mapper.NoteMapper;
import com.ypf.ccneed.service.INoteService;
import com.ypf.ccneed.utils.GzipUtils;
import com.ypf.ccneed.utils.QiNiuUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class CcneedApplicationTests {
    @Autowired
    INoteService noteService;
    @Autowired
    NoteMapper noteMapper;
    @Test
    void contextLoads() {
        System.out.println(QiNiuUtils.UploadToken());
    }

    @Test
    void testStream() throws IOException {
    }

    @Test
    void getDesc() throws IOException {
        noteService.list().forEach(t->{
            try {

                String compress = GzipUtils.compress(GzipUtils.unGzipText(t.getNoteBody()));
                t.setNoteBody(compress);
                noteService.update(t,new QueryWrapper<Note>().eq("id",t.getId()));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
@Test
    public void getTitle(){
        Note byId = noteService.getById(19);
        System.out.println(GzipUtils.uncompress(byId.getNoteBody()));
    }

    @Test
    public void test(){

    }



//        noteService.list().forEach(
//                note->{
//                    try {if(note.getId()==1){
//                        System.out.println(GzipUtils.unGzipText(note.getNoteBody()));
//                    }
//
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//        );
}


