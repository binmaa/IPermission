package com.ipermission.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JLabel;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class Xp {
    public static BufferedImage image;

    public static String createImage(String fileLocation) {
        try {
            FileOutputStream fos = new FileOutputStream(fileLocation);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
            encoder.encode(image);
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileLocation;
    }


    /***
     * 生成图片
     * @param map
     *          菜品集合，可以为map或者list 根据情况修改
     * @param imgPath
     *          生成的图片存放的路径
     * @return
     *         此处返回的是生成的图片高度，因为掉打印接口的时候需要这个高度
     */
    public static int graphicsGeneration(Map<String,String> map,String imgPath) {
        int x = 10;
        int y = 20;
        int marginY = 30;
        //Double price = 20.0;

        createDir(imgPath.substring(0, imgPath.lastIndexOf("/")));//创建目录
        Random ne=new Random();
        int imageWidth = 302;//80打印机能打印的图片的宽度（固定值）       计算公式  打印机宽度（80打印机为800，58打印机为580）/96*25.4
        int imageHeight = 500+map.keySet().size()*40;//图片的高度（500是标题的高度）    根据map/list中的值自动计算
        image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, imageWidth, imageHeight);
        graphics.setColor(Color.BLACK);

        /***
         * 标题字体
         */
        Font fontTitle=new Font("黑体",Font.BOLD,22);
        graphics.setFont(fontTitle);
        FontMetrics fm = new JLabel().getFontMetrics(fontTitle);

        String title = "新辣道北京龙旗广场店";
        int width = fm.stringWidth(title);
        y = y + marginY;
        int titleMaxLength = 220;
        if(width > titleMaxLength){
            graphics.drawString(title.substring(0, 9), (imageWidth - titleMaxLength) / 2, y);
            y = y + marginY;
            String lastTitle = title.substring(9, title.length());
            int lastLength = fm.stringWidth(lastTitle);
            graphics.drawString(lastTitle, (imageWidth - lastLength) / 2, y);
        }else{
            graphics.drawString(title, (imageWidth - width) / 2, y);
        }


        y = y + marginY;

        String yjd = "预结单";
        int yjdWidth = fm.stringWidth(yjd);
        graphics.drawString(yjd, (imageWidth - yjdWidth) / 2, y);
        y = y + marginY;

        String zh = "厅:A区-客位"+ne.nextInt(10)+1;
        graphics.drawString(zh, x, y);
        y = y + marginY;

        /*String ddbm = "订单编码:"+ne.nextInt(9999-1000+1);
        graphics.drawString(ddbm, x, y);
        y = y + marginY;*/


        Font font=new Font("宋体",Font.PLAIN,16);
        fm = new JLabel().getFontMetrics(font);
        graphics.setFont(font);
        graphics.drawString("NO:"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+System.currentTimeMillis(), x, y);
        y = y + marginY;
        graphics.drawString("操作人:冯超", x, y);
        y = y + marginY;
        graphics.drawString("订单时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), x, y);
        y = y + marginY;
        graphics.drawString("支付状态:未支付", x, y);

        y = y + 15;
        graphics.drawString("-----------------------------------", x, y);
        y = y + 15;
        graphics.drawString("品名", x, y);
        graphics.drawString("单价 * 数量", 140, y);
        graphics.drawString("金额", imageWidth-(x+40), y);
        y = y + 15;
        graphics.drawString("-----------------------------------", x, y);
        int cpWidth = 0;
        Double allMoney = 0D;
        for(String str : map.keySet()){
            y = y + marginY;
            String cp = str;
            cpWidth = fm.stringWidth(cp);

            if(cpWidth >= 130){

                String cp1 = cp.substring(0, 7);
                graphics.drawString(cp1, x, y);
                String cp2 = cp.substring(7, cp.length());
                graphics.drawString(cp2, x, y+20);

            }else{
                graphics.drawString(cp, x, y);
            }

            graphics.drawString(map.get(str)+".0 * "+1, 140, y);
            Double totalVal = (Double.parseDouble(map.get(str))*1);
            allMoney = allMoney + totalVal;
            graphics.drawString(""+totalVal, imageWidth-(x+40), y);

            if(cpWidth >= 130){
                y = y + marginY;
            }
        }


        y = y + marginY;
        if(cpWidth >= 130){
            y = y + 15;
        }
        graphics.drawString("-----------------------------------", x, y);
        y = y + 15;
        DecimalFormat    df   = new DecimalFormat("######0.00");
        graphics.drawString("消费合计", x, y);
        graphics.drawString(""+map.keySet().size(), 140, y);
        graphics.drawString(df.format(allMoney), imageWidth-(x+60), y);
        y = y + 15;
        graphics.drawString("-----------------------------------", x, y);
        y = y + marginY;


        String totalMoney = "总金额:"+df.format(allMoney);
        int mWith = fm.stringWidth(totalMoney);
        graphics.drawString(totalMoney, imageWidth-(mWith+20), y);
        y = y + marginY;

        graphics.setFont(fontTitle);
        String ysje = "应收金额："+df.format(allMoney);
        int ysWidth = fm.stringWidth(ysje);
        graphics.drawString(ysje, imageWidth-(ysWidth+x+60), y);
        BufferedImage bimg = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        graphics.drawImage(bimg, imageWidth, 0, null);
        graphics.dispose();
        String  imageUrl = createImage(imgPath);
        int height = (int) (imageHeight/96*25.4);
        return height;//返回生成的图片高度或者地址
    }

    /**
     * 创建图片存放目录
     * 如果目录不存在   创建
     * @param destDirName
     * @return
     */
    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if(dir.exists()) {
            System.out.println("创建目录" + destDirName + "失败，目标目录已存在！");
            return false;
        }
        if(!destDirName.endsWith(File.separator))
            destDirName = destDirName + File.separator;
        // 创建单个目录
        if(dir.mkdirs()) {
            System.out.println("创建目录" + destDirName + "成功！");
            return true;
        } else {
            System.out.println("创建目录" + destDirName + "成功！");
            return false;
        }
    }

    public static void main(String[] args) {

        Map<String,String> map = new HashMap<String, String>();
        map.put("蒜香锅底", "43");
        map.put("红趟锅底", "43");
        map.put("鲜椒巴沙鱼锅", "167");
        map.put("温泉梭边鱼.红汤", "52");
        map.put("精选蔬菜吧", "18");
        map.put("手锤鲜虾滑", "30");
        map.put("龙利鱼", "57");
        map.put("蟹粉丸", "25");
        map.put("羊肋卷", "19");
        map.put("土贡梅煎", "20");

        Xp.graphicsGeneration(map,"D:/Xp4.jpg");
    }
}
