package com.mws.core.utils.image;

import java.io.File;
import java.text.MessageFormat;

/**
 * 配合ImageMagick工具使用的java工具类
 *
 * @author ranfi
 */
public class ImageMagickUtils {

    private static String imageMagickPath;

    static {
        if (System.getProperty("os.name").toLowerCase().indexOf("windows") != -1) {
            imageMagickPath = "D:/ImageMagick/";
        } else {
            imageMagickPath = "/usr/local/bin/";
        }
    }

    /**
     * 创建一张透明的背景图片
     *
     * @param w        图片宽度
     * @param h        图片高度
     * @param descPath
     * @return
     */
    public static boolean createTransparentImage(int w, int h, String descPath) {
        File file = new File(descPath);
        if (!new File(file.getParent()).exists()) {
            return false;
        }
        String command = imageMagickPath + "convert -size " + w + "x" + h + " canvas:none " + descPath + "";
        return InvokeScriptUtils.runCommand(command);
    }

    /**
     * 从一张大图上面裁剪一张固定大小的图片
     *
     * @param w
     * @param h
     * @param x
     * @param y
     * @param srcName
     * @param descName
     * @return
     */
    public static boolean cutFixedImage(int w, int h, int x, int y, String srcName, String descName) {
        File file = new File(srcName);
        if (!file.exists()) {
            return false;
        }
        String command = imageMagickPath + "convert " + srcName + " -crop " + w + "x" + h + "+" + x + "+" + y + " +repage "
                + descName + "";
        return InvokeScriptUtils.runCommand(command);
    }

    /**
     * 生成固定大小的缩略图
     *
     * @param w        裁剪后的图片宽度
     * @param h        裁剪后的图片高度
     * @param srcName  裁剪前的图片名称
     * @param descName 裁剪后图片保存名称
     * @return
     */
    public static boolean cutThumbnail(int w, int h, String srcName, String descName) {
        String command = imageMagickPath + "convert -resize " + w + "x" + h + " " + srcName + " " + descName + "";
        return InvokeScriptUtils.runCommand(command);
    }

    /**
     * 已图片中心位置为坐标点生成缩略图,超过部分直接去除
     *
     * @param w        裁剪后的图片宽度
     * @param h        裁剪后的图片高度
     * @param x        裁剪的起始x坐标
     * @param y        裁剪的起始y坐标
     * @param srcPath  裁剪前的图片名称
     * @param descPath 裁剪后图片保存名称
     * @return
     */
    public static boolean cutThumbnail(int w, int h, int x, int y, String srcPath, String descPath) {
        String command = imageMagickPath + "convert -resize " + w + "x" + h + "^ -gravity Center -crop " + w + "x" + h
                + "+" + x + "+" + y + " " + srcPath + "  " + descPath + "";
        return InvokeScriptUtils.runCommand(command);
    }

    /**
     * 裁剪固定大小的缩略图,超过部分留白
     *
     * @param w        裁剪后的图片宽度
     * @param h        裁剪后的图片高度
     * @param srcPath  裁剪前的图片名称
     * @param descPath 裁剪后图片保存名称
     * @return
     */
    public static boolean cutFixedThumbnail(int w, int h, String srcPath, String descPath) {
        String command = imageMagickPath + "convert " + srcPath + "  -resize " + w + "x" + h + "! " + descPath + "";
        return InvokeScriptUtils.runCommand(command);
    }

    /**
     * 裁剪动态大小的缩略图,超过部分留白
     *
     * @param w        裁剪后的图片宽度
     * @param h        裁剪后的图片高度
     * @param srcPath  裁剪前的图片名称
     * @param descPath 裁剪后图片保存名称
     * @return
     */
    public static boolean cutZoomRatioThumbnail(int w, int h, String srcPath, String descPath) {
        String command = imageMagickPath + "convert " + srcPath + "  -resize " + w + "x" + h + " " + descPath + "";
        return InvokeScriptUtils.runCommand(command);
    }

    /**
     * 已图片中心位置为坐标点生成缩略图,超过部分直接去除,并设置图片的压缩质量
     * <p/>
     * 使其图片尽量的变小
     *
     * @param w        裁剪后的图片宽度
     * @param h        裁剪后的图片高度
     * @param x        裁剪的起始x坐标
     * @param y        裁剪的起始y坐标
     * @param srcPath  裁剪前的图片名称
     * @param descPath 裁剪后图片保存名称
     * @return
     */
    public static boolean cutThumbnail(int w, int h, int x, int y, int quality, String srcPath, String descPath) {
        String command = imageMagickPath + "convert -strip +profile \"*\"  -quality " + quality + " -resize " + w + "x"
                + h + "^ -gravity Center -crop " + w + "x" + h + "+" + x + "+" + y + " " + srcPath + "  " + descPath
                + "";
        return InvokeScriptUtils.runCommand(command);
    }

    /**
     * 合成图片
     *
     * @param offsetX       前景图偏移X坐标
     * @param offsetY       前景图偏移Y坐标
     * @param foregroundImg 前景图路径
     * @param backgroundImg 背景图路径
     * @param descPath      合成后的路径
     * @return
     */
    public static boolean compositeImage(int offsetX, int offsetY, String foregroundImg, String backgroundImg, String descPath) {
        String command = imageMagickPath + "composite -geometry +{0}+{1} {2} {3} {4}";
        command = MessageFormat.format(command, offsetX, offsetY, foregroundImg, backgroundImg, descPath);
        return InvokeScriptUtils.runCommand(command);
    }

    /**
     * 创建固定大小透明背景图合成图片
     *
     * @param w             透明背景图宽度
     * @param h             透明背景图高度
     * @param foregroundImg 前景图路径
     * @param descPath      合成后的路径
     * @return
     */
    public static boolean compositeImage(int w, int h, String foregroundImg, String descPath) {
        String folder = System.getProperty("java.io.tmpdir");
        String blankImgPath = folder + "blank.png";
        createTransparentImage(w, h, blankImgPath);
        String command = imageMagickPath + "composite -geometry +0+0 {0} {1} {2}";
        command = MessageFormat.format(command, foregroundImg, blankImgPath, descPath);
        return InvokeScriptUtils.runCommand(command);
    }

    /**
     * 分割图片为多个小图
     *
     * @param w
     * @param h
     * @param srcName
     * @param descName
     * @return
     */
    public static boolean segmentImage(int w, int h, String srcName, String descName) {
        String command = imageMagickPath + "convert " + srcName + " -crop " + w + "x" + h + " " + descName + "";
        return InvokeScriptUtils.runCommand(command);
    }

    public static void main(String[] args) {
        String suffixPath = "/Users/ranfi/Desktop/";
        boolean flag = ImageMagickUtils.compositeImage(256, 256, suffixPath + "109496_53544.png", suffixPath + "109496_53544.png");
        System.out.println(flag);
    }
}
