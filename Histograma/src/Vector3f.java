import java.awt.Color;

public class Vector3f {
    private float r;
    private float g;
    private float b;

    public Vector3f(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Vector3f(int rgb) {
        Color c = new Color(rgb);
        this.r = c.getRed() / 255f;
        this.g = c.getGreen() / 255f;
        this.b = c.getBlue() / 255f;
    }

    public Vector3f(Color color) {
        this(color.getRGB());
    }

    public Vector3f saturate() {
        r = r > 1 ? 1 : (r < 0 ? 0 : r);
        g = g > 1 ? 1 : (g < 0 ? 0 : g);
        b = b > 1 ? 1 : (b < 0 ? 0 : b);
        return this;
    }

    public Vector3f add(Vector3f v) {
        r += v.r;
        g += v.g;
        b += v.b;
        return this;
    }

    public Vector3f multiply(float value) {
        this.r *= value;
        this.g *= value;
        this.b *= value;
        return this;
    }

    public int get() {
        int r = (int)(this.r * 255);
        int g = (int)(this.g * 255);
        int b = (int)(this.b * 255);
        return new Color(r, g, b).getRGB();
    }

    public Vector3f multiply(Vector3f v) {
        r *= v.r;
        g *= v.g;
        b *= v.b;
        return this;
    }
}
