package utils;

import functions.ImageOperation;

import java.awt.image.BufferedImage;

public class RgbMaster {
    BufferedImage image;
    private int width;
    private int hight;
    private boolean hasAlphaChannel;
    private int[] pixels;

    public RgbMaster(BufferedImage image) {
        this.image = image;
        this.width = image.getWidth();
        this.hight = image.getHeight();
        this.hasAlphaChannel = image.getAlphaRaster() != null;
        this.pixels = image.getRGB(0, 0, width, hight, null, 0, width);
    }
    public BufferedImage getImage(){
        return image;
    }
    public void changeImage(ImageOperation operation){
        for(int i=0; i<pixels.length; i++){
            float[] pixel = ImageUtils.rgbIntToArray(pixels[i]);
            float[] newPixel = operation.execute(pixel);
            pixels[i] = ImageUtils.arrayToRgbInt(newPixel);
        }
        image.setRGB(0, 0, width, hight, pixels, 0, width);
    }
}
