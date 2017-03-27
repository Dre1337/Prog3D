import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.StrictMath.round;

public class Histograma {

    public  int saturate(int value)
    {
        return value > 256 ? 256 : (value < 0 ? 0 : value);

    }


    int    []histogram(BufferedImage img){

        int[] hist = new int[256];
        for(int y = 0; y < img.getHeight(); y++)
        {
            for(int x = 0; x < img.getWidth(); x++)
            {
                Color r = new Color(img.getRGB(x,y));
                int tom = r.getRed();
                hist[tom] ++;
            }
        }

        return hist;
    }
    BufferedImage acumHistogram(int[] histogram, BufferedImage img) {

        int min = 0, pixels = img.getHeight() * img.getWidth();
        int nt;
        int [] ha = new int[256];
        //BufferedImage lara = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < histogram.length; i++) {

            if (i == 0 && histogram[i] != 0) {
                min = histogram[i];
                break;
            } else if (histogram[i] <= min && histogram[i] != 0) {
                min = histogram[i];
            }
        }

        for (int i = 0; i < histogram.length; i++)
        {
            if (i == 0)
            {
                ha[i] = histogram[i];
                min = ha[i];
            }
            else
            {
                ha[i] = ha[i - 1] + histogram[i];
            }
        }

        for (int i = 0; i < histogram.length; i++)
        {
            histogram[i] = round((ha[i] - min) / (pixels - min) * 255);
        }

        for(int y = 0; y < img.getHeight(); y++)
        {
            for(int x = 0; x < img.getWidth(); x++)
            {
                Color r = new Color(img.getRGB(x,y));
                int tom = r.getRed();
                img.setRGB(x, y, histogram[tom]);

            }
        }

        return img;
    }

    public void Run() throws IOException
    {
        BufferedImage img = ImageIO.read(new File("C:/Users/Lukas/Documents/Prog3D/img/gray/university.png"));
        int[] h = histogram(img);
        BufferedImage lara = acumHistogram(h, img);
        ImageIO.write(lara, "png", new File("C:/Users/Lukas/Documents/Prog3D/img/gray/university2.png"));


    }

    public static void main(String[] args) throws Exception {
        new Histograma().Run();
    }

}
