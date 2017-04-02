import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pixels {

    public static BufferedImage pixelate(BufferedImage img, int pixelsize) {
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < img.getHeight(); y += pixelsize) {
            for (int x = 0; x < img.getWidth(); x += pixelsize) {

                for (int py = 0; py < pixelsize; py++) {
                    for (int px = 0; px < pixelsize; px++) {
                        Color cor2 = new Color(img.getRGB(x, y));
                        out.setRGB(px + x, py + y, cor2.getRGB());
                    }
                }
            }
        }
        return out;
    }

    public static BufferedImage convolve(BufferedImage img, float[][] kernel) {

        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Vector3i pixel = new Vector3i(0, 0, 0);
                for (int ky = 0; ky < 3; ky++) {
                    for (int kx = 0; kx < 3; kx++) {
                        int px = x + (kx - 1);
                        int py = y + (ky - 1);
                        if (px < 0 || px >= img.getWidth() ||
                                py < 0 || py >= img.getHeight()) {
                            continue;
                        }

                        Vector3i p = new Vector3i(img.getRGB(px, py));
                        p.multiply(kernel[kx][ky]);
                        pixel.add(p);
                    }
                }
                pixel.saturate();
                result.setRGB(x, y, pixel.get());
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        BufferedImage img = ImageIO.read(new File("C:/Users/Lukas/Documents/Prog3D/img/cor/puppy.png"));

        BufferedImage img2 = pixelate(img, 10);

        ImageIO.write(img2, "png", new File("C:/Users/Lukas/Documents/Prog3D/img/cor/puppy_pixel.png"));

        BufferedImage img3 = ImageIO.read(new File ("C:/Users/Lukas/Documents/Prog3D/img/cor/puppy_pixel.png"));

        float[][] contraste = {
                { 0.0f, -1.0f,   0.0f},
                {-1.0f,  5.0f, -1.0f},
                { 0.0f, -1.0f,  0.0f}
        };

        BufferedImage r = convolve(img3, contraste);
        ImageIO.write(r, "png", new File("C:/Users/Lukas/Documents/Prog3D/img/cor/puppy_pixel_contraste.png"));

    }


}