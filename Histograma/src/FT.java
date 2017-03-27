
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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

    int[] pallete48 = {	//pinterest
            0xD2E3F5, 0x2F401E, 0x3E0A11, 0x4B3316,
            0xA5BDE5, 0x87A063, 0x679327, 0x3A1B0F,
            0x928EB1, 0xBFE8AC, 0xA4DA65, 0x5A3810,
            0x47506D, 0x98E0E8, 0x989721, 0x8E762C,
            0x0B205C, 0x55BEd7, 0xB8B366, 0xD8C077,
            0x134D9C, 0x2A6E81, 0xE1EAB6, 0xF0DEA6,
            0xFFF3D0, 0x610A0A, 0x7D000E, 0x45164B,
            0xFFFCCC, 0x6B330F, 0x990515, 0x250D3B,
            0xB24801, 0x8B4517, 0xE0082D, 0x50105A,
            0xFFF991, 0xB96934, 0xC44483, 0x8E2585,
            0xDF5900, 0xF8A757, 0xC44483, 0xD877CF,
            0xFFEF00, 0xDF7800, 0xF847CE, 0xF0A6E8

    };

    public double dist(Color c1, Color c2) {
        double dr = c1.getRed() - c2.getRed();
        double dg = c1.getGreen() - c2.getGreen();
        double db = c1.getBlue() - c2.getBlue();
        return Math.sqrt(dr*dr + dg*dg + db*db);
    }
    public Color findClosest(Color color, int[] pallete) {
        Color closest = new Color(pallete[0]);
        double closestDistance = dist(color, closest);

        for (int i = 1; i < pallete.length; i++) {
            Color c = new Color(pallete[i]);
            double distance = dist(color, c);
            if (distance < closestDistance) {
                closest = c;
                closestDistance = distance;
            }
        }
        return closest;
    }

    public Color errorColor(Color in, int[] error, double value) {
        double r = in.getRed() + error[0] * value;
        double g = in.getGreen() + error[1] * value;
        double b = in.getBlue() + error[2] * value;
        r = r > 255 ? 255 : (r < 0 ? 0 : r);
        g = g > 255 ? 255 : (g < 0 ? 0 : g);
        b = b > 255 ? 255 : (b < 0 ? 0 : b);
        return new Color((int)r, (int)g, (int)b);
    }

    public BufferedImage toPallete(BufferedImage in, int[] pallete, boolean fix) {
        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < in.getHeight(); y++) {
            for (int x = 0; x < in.getWidth(); x++) {
                Color pixIn = new Color(in.getRGB(x, y));
                Color pixOut = findClosest(pixIn, pallete);
                out.setRGB(x, y, pixOut.getRGB());

               /* if (!fix) continue;

                int[] error = {
                        pixIn.getRed() - pixOut.getRed(),
                        pixIn.getGreen() - pixOut.getGreen(),
                        pixIn.getBlue() - pixOut.getBlue()
                };
                if (x+1 < in.getWidth()) {
                    Color p = new Color(in.getRGB(x+1, y));
                    in.setRGB(x+1, y, errorColor(p, error, 7.0/16).getRGB());
                }
                if (x-1 >= 0 && y+1 < in.getHeight()) {
                    Color p = new Color(in.getRGB(x-1, y+1));
                    in.setRGB(x-1, y+1, errorColor(p, error, 3.0/16).getRGB());
                }
                if (y+1 < in.getHeight()) {
                    Color p = new Color(in.getRGB(x, y+1));
                    in.setRGB(x, y+1, errorColor(p, error, 5.0/16).getRGB());
                }
                if (x+1 < in.getWidth() && y+1 < in.getHeight()) {
                    Color p = new Color(in.getRGB(x+1, y+1));
                    in.setRGB(x+1, y+1, errorColor(p, error, 1.0/16).getRGB());
                }*/
            }

        }
        return out;
    }

    public BufferedImage reverseChannels(BufferedImage in) {
        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < in.getHeight(); y++) {
            for (int x = 0; x < in.getWidth(); x++) {
                Color pixIn = new Color(in.getRGB(x, y));
                Color pixOut = new Color(
                        pixIn.getBlue(), pixIn.getGreen(),	pixIn.getRed());
                out.setRGB(x, y, pixOut.getRGB());
            }
        }
        return out;
    }

    public void run() throws IOException {
        BufferedImage in = ImageIO.read(new File("C:/Users/Lukas/Documents/Prog3D/img/cor/turtle.jpg"));
        System.out.println("Converting pallete...");
        BufferedImage  out = toPallete(in, pallete64, false);
        ImageIO.write(out, "png", new File("C:/Users/Lukas/Documents/Prog3D/img/cor/puppy64.png"));

        System.out.println("Converting with Floyd-Steinberg correction...");
        out = toPallete(in, pallete64, false);
        ImageIO.write(out, "png", new File("C:/Users/Lukas/Documents/Prog3D/img/cor/puppyft.png"));

        System.out.println("Done!");
    }
    public static void main(String[] args) throws IOException {
        new FT().run();
    }
}
