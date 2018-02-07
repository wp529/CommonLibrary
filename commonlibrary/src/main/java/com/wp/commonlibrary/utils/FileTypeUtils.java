package com.wp.commonlibrary.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by WangPing on 2018/2/2.
 */

public final class FileTypeUtils {
    public static final HashMap<String, String> mFileTypes = new HashMap<>();

    static {
        mFileTypes.put("FFD8FF", "jpg");
        mFileTypes.put("89504E47", "png");
        mFileTypes.put("47494638", "gif");
        mFileTypes.put("3C3F786D6C", "xml");
        mFileTypes.put("68746D6C3E", "html");
        mFileTypes.put("D0CF11E0", "doc");
        mFileTypes.put("25504446", "pdf");
        mFileTypes.put("504B0304", "docx");
        mFileTypes.put("52617221", "rar");
        mFileTypes.put("41564920", "avi");
    }

    /**
     * @param filePath 文件路径
     * @return 文件头信息
     * @author guoxk
     * <p>
     * 方法描述：根据文件路径获取文件头信息
     */
    public static String getFileType(String filePath) {
        try {
            return mFileTypes.get(getFileHeader(new FileInputStream(filePath)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getFileType(InputStream is){
        return mFileTypes.get(getFileHeader(is));
    }
    /**
     * @param is 文件输入流
     * @return 文件头信息
     * @author guoxk
     * <p>
     * 方法描述：根据文件路径获取文件头信息
     */
    private static String getFileHeader(InputStream is) {
        if(is == null)
            return null;
        String value = null;
        try {
            byte[] b = new byte[4];
            is.read(b, 0, b.length);
            value = bytesToHexString(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * @param src 要读取文件头信息的文件的byte数组
     * @return 文件头信息
     * @author guoxk
     * <p>
     * 方法描述：将要读取文件头信息的文件的byte数组转换成string类型表示
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (byte aSrc : src) {
            hv = Integer.toHexString(aSrc & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }
}
