package com.ypf.ccneed.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @program: ccneed
 * @author: yanpengfan
 * @create: 2022-07-30 09:24
 **/
@Slf4j
public class GzipUtils {
    public  static  String gzipText (String text) throws IOException {
        if (null == text || text.length() <= 0) {
            return text;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        //System.out.println(text.getBytes().length);
        int read = 0;
        while (read<=text.getBytes().length){
            gzip.write(text.getBytes());
            read+=2048;
        }

        gzip.close();
        long time2 = System.currentTimeMillis();
        // System.out.println("compress time:"+(time2 - time1)/1000.0);
        // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
        return out.toString("ISO-8859-1");
    }

    public  static  String unGzipText(String text) throws IOException {
        if (null == text || text.length() <= 0) {
            return text;
        }
        // 创建一个新的输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 创建一个 ByteArrayInputStream，使用 buf 作为其缓冲 区数组
        ByteArrayInputStream in = new ByteArrayInputStream(text.getBytes("ISO-8859-1"));
        // 使用默认缓冲区大小创建新的输入流
        GZIPInputStream gzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n = 0;
        // 将未压缩数据读入字节数组
        while ((n = gzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
        return out.toString("utf-8");
    }
    /**
     * 使用org.apache.commons.codec.binary.Base64压缩字符串
     * @param str 要压缩的字符串
     * @return
     */
    public static String compress(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return Base64.encodeBase64String(str.getBytes());
    }

    /**
     * 使用org.apache.commons.codec.binary.Base64解压缩
     * @param compressedStr 压缩字符串
     * @return
     */
    public static String uncompress(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }
        return new String(Base64.decodeBase64(compressedStr)) ;
    }
}
