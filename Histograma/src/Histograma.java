import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.round;

public class Histograma {

    int    []histogram(BufferedImage img){

        int[] hist = new int[256];
        for(int y = 0; y < img.getHeight(); y++)
        {
            for(int x = 0; x < img.getWidth(); x++)
            {
                Color r = new Color(img.getRGB(x,y));
                int tom = (r.getRed() + r.getBlue() + r.getGreen()) / 3;
                hist[tom] ++;
            }
        }
        return hist;
    }
    BufferedImage equalHistogram(int[] histogram, BufferedImage img) {

        int min = 0;
        float pixels = img.getHeight() * img.getWidth();
        int [] ha = new int[256];

        for (int i = 0; i < 256; i++)
        {
            if (histogram[i] > 0) {
                min = histogram[i];
                break;
            }
        }

        ha[0] = histogram[0];

        for (int i= 1; i < 256; i++)
        {
            ha[i] = ha[i - 1] + histogram[i];
        }

        for (int i = 0; i < 256; i++)
        {
            histogram[i] = round(((ha[i] - min) / (pixels - min)) * 255);
        }

        for(int y = 0; y < img.getHeight(); y++)
        {
            for(int x = 0; x < img.getWidth(); x++)
            {
                Color r = new Color(img.getRGB(x,y));
                int media = (r.getRed() + r.getBlue() + r.getGreen()) / 3;
                int tom = histogram[media];
                img.setRGB(x, y, new Color(tom, tom, tom).getRGB());
            }
        }
        return img;
    }

    public void Run() throws IOException
    {
        BufferedImage img = ImageIO.read(new File("C:/Users/Lukas/Documents/Prog3D/img/gray/crowd.png"));
        int[] h = histogram(img);
        BufferedImage lara = equalHistogram(h, img);
        ImageIO.write(lara, "png", new File("C:/Users/Lukas/Documents/Prog3D/img/gray/crowd2.png"));
    }

    public static void main(String[] args) throws Exception {
        new Histograma().Run();
    }

}