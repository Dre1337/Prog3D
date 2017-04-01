
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import static java.lang.StrictMath.sqrt;

public class FT {
    int[] pallete64 = {
            0x000000, 0x00AA00, 0x0000AA, 0x00AAAA, 0xAA0000, 0xAA00AA, 0xAAAA00, 0xAAAAAA,
            0x000055, 0x0000FF, 0x00AA55, 0x00AAFF, 0xAA0055, 0xAA00FF, 0xAAAA55, 0xAAAAFF,
            0x005500, 0x0055AA, 0x00FF00, 0x00FFAA, 0xAA5500, 0xAA55AA, 0xAAFF00, 0xAAFFAA,
            0x005555, 0x0055FF, 0x00FF55, 0x00FFFF, 0xAA5555, 0xAA55FF, 0xAAFF55, 0xAAFFFF,
            0x550000, 0x5500AA, 0x55AA00, 0x55AAAA, 0xFF0000, 0xFF00AA, 0xFFAA00, 0xFFAAAA,
            0x550055, 0x5500FF, 0x55AA55, 0x55AAFF, 0xFF0055, 0xFF00FF, 0xFFAA55, 0xFFAAFF,
            0x555500, 0x5555AA, 0x55FF00, 0x55FFAA, 0xFF5500, 0xFF55AA, 0xFFFF00, 0xFFFFAA,
            0x555555, 0x5555FF, 0x55FF55, 0x55FFFF, 0xFF5555, 0xFF55FF, 0xFFFF55, 0xFFFFFF
    };


    public double distancia(Color c1, Color c2) {
        double dr = c1.getRed() - c2.getRed();
        double dg = c1.getGreen() - c2.getGreen();
        double db = c1.getBlue() - c2.getBlue();
        return sqrt(dr*dr + dg*dg + db*db);
    }

    public Color findClosest(Color color, int[] pallete) {

        Color closest = new Color(pallete[0]);
        double closestDistance = distancia(color, closest);

        for (int i = 1; i < pallete.length; i++) {

            Color c = new Color(pallete[i]);
            double distance = distancia(color, c);

            if (distance < closestDistance) {
                closest = c;
                closestDistance = distance;
            }
        }
        return closest;
    }

    public BufferedImage converter(BufferedImage in, int[] pallete) {

        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < in.getHeight(); y++) {
            for (int x = 0; x < in.getWidth(); x++) {
                Color pixIn = new Color(in.getRGB(x, y));
                Color pixOut = findClosest(pixIn, pallete);
                out.setRGB(x, y, pixOut.getRGB());
            }
        }
        return out;
    }


    public void run() throws IOException {
        BufferedImage in = ImageIO.read(new File("C:/Users/Lukas/Documents/Prog3D/img/cor/puppy.png"));
        BufferedImage  out = converter(in, pallete64);
        ImageIO.write(out, "png", new File("C:/Users/Lukas/Documents/Prog3D/img/cor/puppy64.png"));
    }
    public static void main(String[] args) throws IOException {
        new FT().run();
    }
}
