import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Pixelate {

    public BufferedImage lerp(BufferedImage img1, BufferedImage img2, float percent) {
        BufferedImage result = new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < img1.getWidth(); x++) {
                Vector3i p1 = new Vector3i(img1.getRGB(x, y));
                Vector3i p2 = new Vector3i(img2.getRGB(x, y));
                p1.multiply(1.0f - percent);
                p2.multiply(percent);
                p1.add(p2);
                result.setRGB(x, y, p1.get());
            }
        }
        return result;
    }

    public BufferedImage multiply(BufferedImage img, Vector3f color) {
        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Vector3f p = new Vector3f(img.getRGB(x, y));
                p.multiply(color).saturate();
                result.setRGB(x, y, p.get());
            }
        }
        return result;
    }

    public BufferedImage convolve(BufferedImage img, float[][] kernel) {

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

    public BufferedImage BrightnessHSV(BufferedImage img, float brilho)
    {
        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {

                Color pixel = new Color(img.getRGB(x, y));

                int r = pixel.getRed();
                int g = pixel.getGreen();
                int b = pixel.getBlue();

                float[] hsv = new float[3];
                hsv = Color.RGBtoHSB(r, g, b, hsv);

                hsv[2] = hsv[2] * brilho;
                if (hsv[2] > 1.0f)
                    hsv[2] = 1.0f;
                if (hsv[2] < 0.0f)
                    hsv[2] = 0.0f;

                float h = hsv[0];
                float s = hsv[1];
                float v = hsv[2];

                int rgb = Color.HSBtoRGB(h, s, v);
                Color pixel2 = new Color(rgb);

                result.setRGB(x, y, pixel2.getRGB());
            }
        }

        return result;
    }

    public BufferedImage SaturationHSV(BufferedImage img, float valor)
    {
        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {

                Color pixel = new Color(img.getRGB(x, y));

                int r = pixel.getRed();
                int g = pixel.getGreen();
                int b = pixel.getBlue();

                float[] hsv = new float[3];
                hsv = Color.RGBtoHSB(r, g, b, hsv);

                hsv[1] = hsv[1] * valor;
                if (hsv[1] > 1.0f)
                    hsv[1] = 1.0f;
                if (hsv[1] < 0.0f)
                    hsv[1] = 0.0f;

                float h = hsv[0];
                float s = hsv[1];
                float v = hsv[2];

                int rgb = Color.HSBtoRGB(h, s, v);
                Color pixel2 = new Color(rgb);

                result.setRGB(x, y, pixel2.getRGB());
            }
        }

        return result;
    }

    public BufferedImage MatizHSV(BufferedImage img, float valor)
    {
        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {

                Color pixel = new Color(img.getRGB(x, y));

                int r = pixel.getRed();
                int g = pixel.getGreen();
                int b = pixel.getBlue();

                float[] hsv = new float[3];
                hsv = Color.RGBtoHSB(r, g, b, hsv);

                hsv[0] = hsv[1] * valor;
                if (hsv[0] > 1.0f)
                    hsv[0] = 1.0f;
                if (hsv[0] < 0.0f)
                    hsv[0] = 0.0f;

                float h = hsv[0];
                float s = hsv[1];
                float v = hsv[2];

                int rgb = Color.HSBtoRGB(h, s, v);
                Color pixel2 = new Color(rgb);

                result.setRGB(x, y, pixel2.getRGB());
            }
        }

        return result;
    }

    public void run() throws Exception {
       /* //Exercicio do lerp. Faz lerp de 0.1 ate 0.9
        BufferedImage img1 = ImageIO.read(new File("C:/Users/Lukas/Documents/Prog3D/img/cor/mario.jpg"));
        BufferedImage img2 = ImageIO.read(new File("C:/Users/Lukas/Documents/Prog3D/img/cor/sonic.jpg"));
        for (int i = 0; i < 10; i++) {
            float amount = i / 10f;
            BufferedImage r = lerp(img1, img2, amount);
            ImageIO.write(r, "png", new File("C:/Users/Lukas/Documents/Prog3D/img/cor/lerp" + i + ".png"));
        }

        //Multiply
        BufferedImage metroid = ImageIO.read(new File("C:/Users/Lukas/Documents/Prog3D/img/cor/metroid1.jpg"));
        Vector3f sunLight = new Vector3f(1.0f, 1.0f, 0.6f);
        BufferedImage r = multiply(metroid, sunLight);
        ImageIO.write(r, "png", new File("C:/Users/Lukas/Documents/Prog3D/img/multiply.png"));

        //*** Convolve ***
        float[][] prewitt = {
                {-1.0f, 0.0f, 1.0f},
                {-2.0f, 0.0f, 2.0f},
                {-1.0f, 0.0f, 1.0f}
        };
        r = convolve(metroid, prewitt);
        ImageIO.write(r, "png", new File("C:/Users/Lukas/Documents/Prog3D/img/cor/prewitt.png"));

        //Contraste
        float[][] contraste = {
                { 0.0f, -1.0f,   0.0f},
                {-1.0f,  5.0f, -1.0f},
                { 0.0f, -1.0f,  0.0f}
        };
        r = convolve(metroid, contraste);
        ImageIO.write(r, "png", new File("C:/Users/Lukas/Documents/Prog3D/img/cor/contraste.png"));

        float umNono = 1.0f / 9.0f;
        float[][] blur = {
                {umNono, umNono, umNono},
                {umNono, umNono, umNono},
                {umNono, umNono, umNono}
        };

        r = convolve(metroid, blur);
        ImageIO.write(r, "png", new File("C:/Users/Lukas/Documents/Prog3D/img/cor/blur.png"));

        // Brilho em HSV
        BufferedImage lara = ImageIO.read(new File ("C:/Users/Lukas/Documents/Prog3D/img/cor/lara.png"));
        BufferedImage lara2 = BrightnessHSV(lara, 50.0f);
        ImageIO.write(lara2, "png", new File("C:/Users/Lukas/Documents/Prog3D/img/lara_brilho_HSV.png"));

        //Satura��o em HSV
        BufferedImage puppy = ImageIO.read(new File ("C:/Users/Lukas/Documents/Prog3D/img/cor/puppy.png"));
        BufferedImage puppy2 = SaturationHSV(puppy, 0.5f);
        ImageIO.write(puppy2, "png", new File("C:/Users/Lukas/Documents/Prog3D/img/puppy_satura��o_HSV.png"));

        //Matiz em HSV
        BufferedImage metroid1 = ImageIO.read(new File ("C:/Users/Lukas/Documents/Prog3D/img/cor/metroid1.jpg"));
        BufferedImage metroid2 = MatizHSV(metroid1, 0.3f);
        ImageIO.write(metroid2, "jpg", new File("C:/Users/Lukas/Documents/Prog3D/img/metroid1_8_matiz_HSV.jpg"));

        //Eros�o
        BufferedImage universidade = ImageIO.read(new File("C:/Users/Lukas/Documents/Prog3D/img/gray/university.png"));
        float[][] erosao = {
                { 0.0f, 0.0f,   0.0f},
                {1.0f,  1.0f, 1.0f},
                { 0.0f, 0.0f,  0.0f}
        };
        r = convolve(universidade, erosao);
        ImageIO.write(r, "png", new File("C:/Users/Lukas/Documents/Prog3D/img/gray/erosao.png"));

        //Dilata��o
        float[][] dilatacao = {
                {0.0f, 1.0f, 0.0f},
                {0.0f, 1.0f, 0.0f},
                {0.0f, 1.0f, 0.0f}
        };
        r = convolve(universidade, dilatacao);
        ImageIO.write(r, "png", new File("C:/Users/Lukas/Documents/Prog3D/img/gray/dilatacao.png"));
*/

        //Eros�o
        BufferedImage img = ImageIO.read(new File("C:/Users/Lukas/Documents/Prog3D/img/cor/puppy.png"));

        float[][] emboss = {
                { -2.0f, -1.0f,  0.0f},
                { -1.0f, 1.0f,  1.0f},
                { 0.0f, 1.0f,  2.0f}
        };
        BufferedImage r = convolve(img, emboss);
        ImageIO.write(r, "png", new File("C:/Users/Lukas/Documents/Prog3D/img/cor/puppy2.png"));

        //Contraste
        float[][] contraste = {
                { 0.0f, -1.0f,   0.0f},
                {-1.0f,  5.0f, -1.0f},
                { 0.0f, -1.0f,  0.0f}
        };
        r = convolve(img, contraste);
        ImageIO.write(r, "png", new File("C:/Users/Lukas/Documents/Prog3D/img/cor/puppy3.png"));

    }


    public static void main(String[] args) throws Exception {

        new Pixelate().run();
    }
}

