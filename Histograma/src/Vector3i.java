import java.awt.Color;

public class Vector3i {
    private int r;
    private int g;
    private int b;

    public Vector3i(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Vector3i(int rgb) {
        Color c = new Color(rgb);
        this.r = c.getRed();
        this.g = c.getGreen();
        this.b = c.getBlue();
    }

    public Vector3i saturate() {
        r = r > 255 ? 255 : (r < 0 ? 0 : r);
        g = g > 255 ? 255 : (g < 0 ? 0 : g);
        b = b > 255 ? 255 : (b < 0 ? 0 : b);
        return this;
    }

    public Vector3i add(Vector3i v) {
        r += v.r;
        g += v.g;
        b += v.b;
        return this;
    }

    public Vector3i sub(Vector3i v) {
        r -= v.r;
        g -= v.g;
        b -= v.b;
        return this;
    }

    public Vector3i multiply(float value) {
        this.r *= value;
        this.g *= value;
        this.b *= value;
        return this;
    }

    public int get() {
        return new Color(r, g, b).getRGB();
    }
}

