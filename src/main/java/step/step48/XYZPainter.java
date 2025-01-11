package step.step48;

import numviz.NvFrame;
import numviz.NvPainter;

import java.awt.*;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class XYZPainter extends NvPainter {

    double[][] xyt;

    public XYZPainter(int width, int height, Object model) {
        super(width, height, model);
        xyt = (double[][]) model;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpiralDataset spiralDataset = new SpiralDataset();
        spiralDataset.generate(true);
        int n = spiralDataset.getDataSize();
        double[][] xyt = new double[n][3];
        double[][] xy = spiralDataset.getX();
        int[] t = spiralDataset.getTarget();
        for (int i = 0; i < n; i++) {
            xyt[i][0] = xy[i][0];
            xyt[i][1] = xy[i][1];
            xyt[i][2] = (double) t[i];
        }

        XYZPainter painter = new XYZPainter(800, 800, xyt);
        final NvFrame frame = new NvFrame(painter);
        frame.setTitle("Spiral Data");
        painter.saveImageAsPng("SpiralData");
        frame.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {

        clear(g);
        int numColorLevels = xyt[0].length;
        xOffset = width / 2;
        yOffset = height / 2;
        int gridWidth = this.width * 4 / 5;
        int gridHeight = this.height * 4 / 5;
        scale = gridHeight / 2.0;
        int r = 6, r2 = r * 2;
        g.setColor(colorMap.getBlack());

        Graphics2D g2 = (Graphics2D) g;
        BasicStroke bs = new BasicStroke(3);
        g2.setStroke(bs);
        g.setColor(Color.BLACK);
        g.drawLine(xOffset - gridWidth / 2, yOffset, xOffset + gridWidth / 2, yOffset);
        g.drawLine(xOffset, yOffset - gridHeight / 2, xOffset, yOffset + gridHeight / 2);

        bs = new BasicStroke(1);
        g2.setStroke(bs);
        for (int i = 0; i < xyt.length; i++) {
            int n = (int) ((xyt[i][2]) * 128);
            if (n > 255) {
                n = 255;
            } else if (n < 0) {
                n = 0;
            }
            g.setColor(colorMap.getColors()[n]);
            int xc = xOffset + (int) (scale * xyt[i][0]);
            int yc = yOffset - (int) (scale * xyt[i][1]);
            g.fillOval(xc - r, yc - r, r2, r2);
        }
        int xs = xOffset - gridWidth / 2;
        int ys = yOffset + gridHeight / 2;
        int xe = xOffset + gridWidth / 2;
        int ye = yOffset - gridHeight / 2;
        g.setColor(Color.BLACK);
        g.drawLine(xs, ys, xs, ye);
        g.drawLine(xe, ys, xe, ye);
        g.drawLine(xs, ys, xe, ys);
        g.drawLine(xs, ye, xe, ye);

    }

}
