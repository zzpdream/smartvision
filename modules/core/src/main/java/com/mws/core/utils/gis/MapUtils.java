package com.mws.core.utils.gis;

/**
 * Created by ranfi on 10/13/15.
 */
public class MapUtils {

    private static final double EARTH_RADIUS = 6378137; // 长半轴,单位米
    private static final double[] WEB_MERCATOR_XRADNG = new double[]{-20037508.3427892, 20037508.3427892}; //世界X取值范围
    private static final double[] WEB_MERCATOR_YRADNG = new double[]{-20037508.3427892, 20037508.3427892}; //世界X取值范围

    /**
     * 经度转换像素坐标
     *
     * @param lng
     * @param zoom
     * @return
     */
    public static double lngToPixel(double lng, int zoom) {
        return 256 * Math.pow(2, zoom) * (lng + 180) / 360;
    }

    /**
     * 像素坐标转换经度
     *
     * @param pixelX
     * @param zoom
     * @return
     */
    public static double pixelToLng(double pixelX, int zoom) {

        return pixelX / (256 * Math.pow(2, zoom)) * 360.0 - 180.0;

    }


    /**
     * 纬度转换地图像素坐标
     *
     * @param lat
     * @param zoom
     * @return
     */
    public static double latToPixel(double lat, int zoom) {

        double lat_rad = (lat * Math.PI) / 180.0; //计算纬度的弧度

        double y = Math.log(Math.tan(lat_rad) + (1 / Math.cos(lat_rad))) / Math.PI;

        return 256 * Math.pow(2, zoom) * (1 - y) / 2;

    }

    /**
     * 地图像素坐标转换纬度
     *
     * @param pixelY
     * @param zoom
     * @return
     */
    public static double pixelToLat(double pixelY, int zoom) {
        double y = 2 * Math.PI * (1 - pixelY / (128 << zoom));
        double z = Math.pow(Math.E, y);
        double siny = (z - 1) / (z + 1);
        return Math.asin(siny) * 180 / Math.PI;

    }


    /**
     * 计算当前经度的瓦片列号
     *
     * @param lng 地图经度
     * @return
     */
    public static int getTileX(double lng, int level) {

        double pixels = lngToPixel(lng, level);
        int tileSize = 256;
        return (int) Math.floor(pixels / (tileSize));

    }

    /**
     * 计算当前纬度瓦片行号
     *
     * @param lat
     * @return
     */
    public static int getTileY(double lat, int level) {
        double pixels = latToPixel(lat, level);
        int tileSize = 256;
        return (int) Math.floor(pixels / tileSize);

    }

    /**
     * 获取baidu地图当前级别的分辨率,1个像素代表的地理长度，单位为米
     *
     * @param level
     * @return
     */
    public static double getBaiduResolution(int level) {
        return Math.pow(2, (18 - level));
    }

    /**
     * 获取google地图当前级别的分辨率,1个像素代表的地理长度，单位为米
     *
     * @param level
     * @return
     */
    public static double getResolution(int level) {
        return 2 * Math.PI * EARTH_RADIUS / 256 / Math.pow(2, level);
    }

    /**
     * 计算地面分辨率, 表示一个像素代表多少米
     *
     * @param lat   当前纬度
     * @param level 地图等级
     * @return
     */
    public static double getResolution(double lat, int level) {
        return (Math.cos(lat * Math.PI / 180) * 2 * Math.PI * EARTH_RADIUS) / (256 * Math.pow(2, level));
    }

    /**
     * 获取地图比例尺
     *
     * @param resolution
     * @param ppi        每英寸的像素点数，一般是72dpi,常见有96dpi和300dpi
     * @return
     */
    public static double getScale(double resolution, double ppi) {
        return 1 / (resolution * ppi / 0.0254);
    }


    public static void main(String[] args) {

        double resolution = getResolution(31.156408414557, 18);
        System.out.println("分辨率:" + getResolution(31.163606, 18));
        System.out.println("地图比例尺:" + getScale(resolution, 72));

        int level = 20;
        double lng = 100.4893912, lat = 13.7518540, lng1 = 100.4957720, lat1 = 13.7459652;

        double x = lngToPixel(lng, level);

        double x1 = lngToPixel(lng1, level);

        double y = latToPixel(lat, level);

        double y1 = latToPixel(lat1, level);

        double tileX = getTileX(lng, level);
        double tileY = getTileY(lat, level);

        double tileX1 = getTileX(lng1, level);
        double tileY1 = getTileY(lat1, level);

        //左上角
        double xlng = pixelToLng(tileX * 256, level);
        double ylat = pixelToLat(tileY * 256, level);

        //右下角
        double xlng1 = pixelToLng((tileX1 + 1) * 256, level);
        double ylat1 = pixelToLat((tileY1 + 1) * 256, level);

        System.out.println("x像素点：" + x);
        System.out.println("x1像素点：" + x1);
        System.out.println("y像素点：" + y);
        System.out.println("y1像素点：" + y1);

        System.out.println("获取当前经纬度的行号：" + tileX);
        System.out.println("获取当前经纬度的列号：" + tileY);

        System.out.println("获取当前经纬度的行号：" + tileX1);
        System.out.println("获取当前经纬度的列号：" + tileY1);

        System.out.println("瓦片左上角经度：" + xlng);
        System.out.println("瓦片左上角纬度：" + ylat);
        System.out.println("瓦片左上角经度像素坐标：" + lngToPixel(xlng, level));
        System.out.println("瓦片左上角纬度像素坐标：" + +latToPixel(ylat, level));

        System.out.println("地图左上角偏移x坐标像素：" + Math.ceil(x - tileX * 256));
        System.out.println("地图左上角偏移y坐标像素：" + Math.ceil(y - tileY * 256));


        System.out.println("地图右下角偏移x坐标像素：" + Math.abs(x1 - lngToPixel(xlng1, level)));
        System.out.println("地图右下角偏移y坐标像素：" + Math.abs(latToPixel(ylat1, level) - y1));

        System.out.println("修正前的地图分辨率：" + Math.ceil(x1 - x) + "_" + Math.ceil((y1 - y)));
        System.out.println("修正后的地图分辨率：" + Math.ceil(tileX1 - tileX + 1) * 256 + "_" + Math.ceil((tileY1 - tileY + 1) * 256));
    }
}
