package cn.client.arcsoft.utils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class MyUtil {
    public static BufferInfo getBGRFromImage(BufferedImage img) {
        byte[] bgr = null;
        int width = 0;
        int height = 0;
        width = img.getWidth();
        height = img.getHeight();
        BufferedImage bgrimg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        bgrimg.setRGB(0, 0, width, height, img.getRGB(0, 0, width, height, null, 0, width), 0, width);
        bgr = ((DataBufferByte)bgrimg.getRaster().getDataBuffer()).getData();
        return new BufferInfo(width, height, bgr);
    }
}
