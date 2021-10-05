package person.sxr.dance.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * gif图片路径，推荐使用像素在400*400以内的
 * gif图片生成路径，这里没有整合AnimatedGifEncoder包
 * 所以生成的是一些jpg图片，需要自己借助其他工具整个成gi
 * 如果有条件可以继续改进，直接生成一个gif文件
 *
 * @author sxr
 * @date 2021/10/5 9:04 上午
 */
public class Client {

    public static String input = "111.gif";

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            // 创建并添加菜单栏
            JMenuBar menuBar = new JMenuBar();
            JMenu menuFile = new JMenu("GIF图片");
            menuBar.add(menuFile);
            JMenuItem itemSave = new JMenuItem("上传");
            // itemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
            menuFile.add(itemSave);
            mainFrame.setJMenuBar(menuBar);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setVisible(true);
            itemSave.addActionListener(e -> {
                JFileChooser fd = new JFileChooser();
                // fd.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fd.showOpenDialog(null);
                fd.setFileSelectionMode(JFileChooser.FILES_ONLY);
                File f = fd.getSelectedFile();
                String url = Objects.requireNonNull(Client.class.getClassLoader().getResource("")).getFile() + "image/" + f.getName();
                File outfile = new File(url);
                try {
                    FileInputStream fi = new FileInputStream(f);
                    FileOutputStream out = new FileOutputStream(outfile);
                    // 创建搬运工具
                    byte[] data = new byte[1024 * 8];
                    // 创建长度
                    int len = 0;
                    // 循环读取数据
                    while ((len = fi.read(data)) != -1) {
                        out.write(data, 0, len);
                    }
                    Client.input = f.getName();
                    // 3.释放资源
                    fi.close();
                    out.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
        });
    }
}

@SuppressWarnings("serial")
class MainFrame extends JFrame {
    private final int x;
    private final int y;
    public static int WIDTH = 0;
    public static int HEIGHT = 0;

    {
        Toolkit kit = Toolkit.getDefaultToolkit();
        int screenWidth = kit.getScreenSize().width;
        int screenHeight = kit.getScreenSize().height;
        x = (screenWidth - WIDTH) / 2;
        y = (screenHeight - HEIGHT) / 2;
    }

    public MainFrame() {
        setTitle("byte dance");
        initBounds();
        createGif();
        setBounds(x, y, WIDTH, HEIGHT);
        setResizable(false);
        setIconImage(null);

        JPanel panel = new GamePanel();
        Container container = getContentPane();
        container.add(panel);
    }

    /**
     * 将字符图片输出到指定目录
     */
    public void createGif() {
        BufferedImage[] charImgs = ImgToCharacter.getCharImgs();
        try {
            for (int i = 0; i < charImgs.length; i++) {
                File file = new File(Objects.requireNonNull(Client.class.getClassLoader().getResource("")).getFile() + "output/" + i + ".jpg");
                ImageIO.write(charImgs[i], "jpg", file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("图片输出完成！");
    }

    public void initBounds() {
        ImgToCharacter.readGiF();
        ImgToCharacter.draw();
        BufferedImage[] charImgs = ImgToCharacter.getCharImgs();
        int maxWidth = 0;
        int maxHeight = 0;
        for (BufferedImage img : charImgs) {
            if (img.getWidth() > maxWidth) {
                maxWidth = img.getWidth();
            }
            if (img.getHeight() > maxHeight) {
                maxHeight = img.getHeight();
            }
        }
        WIDTH = maxWidth;
        HEIGHT = maxHeight;
    }
}
