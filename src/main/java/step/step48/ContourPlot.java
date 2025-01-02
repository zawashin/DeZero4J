package step.step48;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */

public class ContourPlot extends JPanel {
    private static final int N = 1000;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int POINT_SIZE = 4;

    private final double[] x = new double[N];
    private final double[] y = new double[N];
    private final double[] z = new double[N];

    public ContourPlot() {
        Random random = new Random();

        // Generate random points and calculate z values
        for (int i = 0; i < N; i++) {
            x[i] = -1 + 2 * random.nextDouble(); // Random x between -1 and 1
            y[i] = -1 + 2 * random.nextDouble(); // Random y between -1 and 1
            z[i] = Math.sin(Math.PI * x[i]) * Math.cos(Math.PI * y[i]); // Function z(x, y)
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Clear the panel
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Draw the points
        for (int i = 0; i < N; i++) {
            float intensity = (float) ((z[i] + 1) / 2); // Normalize z to [0, 1]
            Color color = new Color(intensity, 0, 1 - intensity); // Gradient color
            g2d.setColor(color);

            int screenX = (int) ((x[i] + 1) / 2 * WIDTH); // Map x to screen coordinates
            int screenY = (int) ((y[i] + 1) / 2 * HEIGHT); // Map y to screen coordinates

            g2d.fillOval(screenX, screenY, POINT_SIZE, POINT_SIZE);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Contour Plot");
        ContourPlot panel = new ContourPlot();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.add(panel);
        frame.setVisible(true);
    }
}
