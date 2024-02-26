package utils;

import commands.AppBotCommand;
import functions.FilterOperation;
import functions.ImageOperation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ImageUtils {
    static public BufferedImage getImage(String path){
        final File file = new File(path);
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static public void saveImage(BufferedImage image, String path){
        try {
            ImageIO.write(image, "jpg", new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static float[] rgbIntToArray(int pixel){
        Color color = new Color(pixel);
        return  color.getRGBColorComponents(null);
    }
    static int arrayToRgbInt(float[] pixel){
        Color color = null;
        if(pixel.length == 3){
            color = new Color(pixel[0], pixel[1], pixel[2]);
        }else if(pixel.length == 4){
            color = new Color(pixel[0], pixel[1], pixel[2], pixel[3]);
        }
        if(color != null){
            return color.getRGB();
        }
        try {
            throw new Exception("Неверный цвет");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static ImageOperation getOperation(String operationName){
        FilterOperation filterOperation = new FilterOperation();
        Method[] classMethods = filterOperation.getClass().getDeclaredMethods();
        for(Method method : classMethods) {
            if (method.isAnnotationPresent(AppBotCommand.class)) {
                AppBotCommand command = method.getAnnotation(AppBotCommand.class);
                if (command.name().equals(operationName)) {
                    return (f) -> {
                        try {
                            return (float[]) method.invoke(filterOperation, f);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.getStackTrace();
                        }
                        return f;
                    };
                }
            }
        }
        return null;
    }
}
