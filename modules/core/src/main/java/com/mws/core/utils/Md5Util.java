/**
 * 2015-2-12
 * core
 */
package com.mws.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/*
 * MD5 算法
*/
public class Md5Util {
    
    // 全局数组
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public Md5Util() {
    }

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 返回形式只为数字
    public static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    public static String GetMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

    /**
     * 文件进行MD5加密
     * @param file
     * @return
     */
    public static String getMD5(File file) {
        FileInputStream fis = null;
        try {
          MessageDigest md = MessageDigest.getInstance("MD5");
          fis = new FileInputStream(file);
          byte[] buffer = new byte[2048];
          int length = -1;
          while ((length = fis.read(buffer)) != -1) {
             md.update(buffer, 0, length);
          }
          byte[] b = md.digest();
			return byteToString(b);
        } catch (Exception ex) {
          ex.printStackTrace();
          return null;
        } finally {
          try {
             fis.close();
          } catch (IOException ex) {
             ex.printStackTrace();
          }
        }
     }
    

//  public static void main(String[] args) {
//      System.out.println(Md5Util.GetMD5Code("100000"));
//  }
    
    public static void main(String arg[]) {
        System.out.println(getMD5(new File("D:/WNPHP/nginx/html/data/scenic/zip/4.zip")));
     }
}