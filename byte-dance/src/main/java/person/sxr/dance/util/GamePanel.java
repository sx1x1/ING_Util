package person.sxr.dance.util;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author sxr
 * @date 2021/10/5 9:08 上午
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel {

    private final int fps = 6;
    private final BufferedImage[] charImgs;
    int count = 0;

    public GamePanel() {
        charImgs = ImgToCharacter.getCharImgs();
        initBackgroundImg();
        Thread t = new MyThread();
        t.start();
    }

    public void initBackgroundImg() {
        BufferedImage background = new BufferedImage(MainFrame.WIDTH, MainFrame.HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics gb = background.getGraphics();
        gb.setColor(Color.white);
        gb.fillRect(0, 0, background.getWidth(), background.getHeight());
        gb.dispose();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int size = charImgs.length;
        int index = count % size;
        BufferedImage img = charImgs[index];
        int w = img.getWidth();
        int h = img.getHeight();
        // int x = (MainFrame.WIDTH - w)/2;
        // int y = (MainFrame.HEIGHT - h)/2;
        //背景涂白
        g.setColor(Color.white);
        g.fillRect(0, 0, MainFrame.WIDTH, MainFrame.HEIGHT);
        g.setColor(Color.black);
        // 3/4为显示比例，考虑到有的gif图片太大，所以缩小至一定比例在窗口中显示
        g.drawImage(img, 0, MainFrame.HEIGHT - h, w * 3 / 4, h * 3 / 4, null);
        count++;
    }

    class MyThread extends Thread {

        long startTime;
        long endTime;
        long sleepTime;
        long spendTime;
        long period = 1000 / fps;

        @Override
        public void run() {
            while (true) {
                startTime = System.currentTimeMillis();
                repaint();
                endTime = System.currentTimeMillis();
                spendTime = endTime - startTime;
                sleepTime = period - spendTime;
                if (sleepTime < 0) {
                    sleepTime = 2;
                }
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}

