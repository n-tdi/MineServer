package world.ntdi.api.chat;

import net.md_5.bungee.api.ChatColor;

public class RainbowTextAnimator {
    private final String text;
    private int step = 0;
    private final int totalColors = 360; // Full hue spectrum

    public RainbowTextAnimator(String text) {
        this.text = text;
    }

    public String getNextFrame() {
        StringBuilder result = new StringBuilder();
        int colorIndex = step % totalColors;
        
        for (char c : text.toCharArray()) {
            result.append(ChatColor.of(hsvToRgb(colorIndex, 1, 1))).append(c);
            colorIndex = (colorIndex + 5) % totalColors; // Adjust step size for faster/slower color change
        }
        
        step++;
        return result.toString();
    }

    private String hsvToRgb(float hue, float saturation, float value) {
        int h = (int) (hue / 60);
        float f = hue / 60 - h;
        float p = value * (1 - saturation);
        float q = value * (1 - f * saturation);
        float t = value * (1 - (1 - f) * saturation);

        float r, g, b;
        switch (h) {
            case 0: r = value; g = t; b = p; break;
            case 1: r = q; g = value; b = p; break;
            case 2: r = p; g = value; b = t; break;
            case 3: r = p; g = q; b = value; break;
            case 4: r = t; g = p; b = value; break;
            default: r = value; g = p; b = q; break;
        }

        int ri = Math.round(r * 255);
        int gi = Math.round(g * 255);
        int bi = Math.round(b * 255);

        return String.format("#%02X%02X%02X", ri, gi, bi);
    }
}