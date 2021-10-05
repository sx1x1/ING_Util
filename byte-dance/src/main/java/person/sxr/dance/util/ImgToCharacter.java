package person.sxr.dance.util;


import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author sxr
 * @date 2021/10/5 9:08 上午
 */
public class ImgToCharacter {

    private static BufferedImage[] realImgs;
    private static BufferedImage[] charImgs;
    private static final int SIZE = 4;
    private static final String[] CHARS = {" ", ".", ":", ";", "-", "~", "1", "i", "o", "r", "a",
            "2", "c", "3", "b", "n", "q", "k", "x", "S", "X",
            "7", "Z", "O", "8", "#", "$", "%", "&", "M", "B",
            "W", "@", "@"};

    public static void draw() {
        int interval = 16777215 / (CHARS.length - 1);
        BufferedImage oneRealImg = null;
        int index = 0;
        while (index < realImgs.length) {
            oneRealImg = realImgs[index];
            int width = oneRealImg.getWidth();
            int height = oneRealImg.getHeight();
            BufferedImage oneCharImg = new BufferedImage(width * 3, height * 3, BufferedImage.TYPE_INT_RGB);
            Graphics g = oneCharImg.getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, oneCharImg.getWidth(), oneCharImg.getHeight());
            g.setColor(Color.BLACK);
            g.setFont(new Font("黑体", Font.BOLD, 15));

            for (int i = 0; i < height; i += SIZE) {
                for (int j = 0; j < width; j += SIZE) {
                    int rgb = getAvgRgb(j, i, oneRealImg);
                    int k = rgb / interval;
                    g.drawString(CHARS[k], 12 * j / SIZE, 12 * i / SIZE);
                }
            }
            g.dispose();
            charImgs[index] = oneCharImg;
            index++;
        }

    }

    public static void readGiF() {
        try {
            System.out.println(Objects.requireNonNull(ImgToCharacter.class.getClassLoader().getResource("")).getFile());
            File f = new File(Objects.requireNonNull(ImgToCharacter.class.getClassLoader().getResource("")).getFile() + "image/" + Client.input);
            String name = f.getName();
            String suffix = name.substring(name.lastIndexOf('.') + 1);

            Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
            ImageReader reader = iter.next();
            ImageInputStream imageIn;
            imageIn = ImageIO.createImageInputStream(f);
            reader.setInput(imageIn);
            int count = reader.getNumImages(true);
            realImgs = new BufferedImage[count];
            charImgs = new BufferedImage[count];
            for (int i = 0; i < count; i++) {
                realImgs[i] = reader.read(i);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage[] getCharImgs() {
        return charImgs;
    }

    public static int getAvgRgb(int i, int j, BufferedImage img) {
        int result = 0;
        for (int m = 0; m < SIZE; m++) {
            for (int n = 0; n < SIZE; n++) {
                if (i + m < img.getWidth() && j + n < img.getHeight()) {
                    result += img.getRGB(i + m, j + n);
                }
            }
        }
        return Math.abs(result) / (SIZE * SIZE);
    }

}

