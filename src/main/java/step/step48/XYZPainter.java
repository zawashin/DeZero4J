package step.step48;

import numviz.NvFrame;
import numviz.NvPainter;

import java.awt.*;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class XYZPainter extends NvPainter {

    double[][] xyt;

    int n, m;

    public XYZPainter(int width, int height, Object model) {
        super(width, height, model);
        xyt = (double[][]) model;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpiralDataset spiralDataset = new SpiralDataset();
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
        frame.setVisible(true);
        frame.setTitle("Spiral Data");
    }

    @Override
    public void paint(Graphics g) {

        clear(g);
        int numColorLevels = xyt[0].length;
        xOffset = width / 2;
        yOffset = height / 2;
        int gridWidth = this.width * 4 / 5;
        int gridHeight = this.height * 4 / 5;
        int x1, y1, x2, y2;
        g.setColor(colorMap.getBlack());

        Graphics2D g2 = (Graphics2D) g;
        BasicStroke bs = new BasicStroke(3);
        g2.setStroke(bs);
        g.setColor(Color.BLACK);
        g.drawLine(xOffset - gridWidth / 2, yOffset, xOffset + gridWidth / 2, yOffset);
        g.drawLine(xOffset, yOffset - gridHeight / 2, xOffset, yOffset + gridHeight / 2);

        int xs = xOffset - gridWidth / 2;
        int ys = yOffset + gridHeight / 2;
        int xe = xOffset + gridWidth / 2;
        int ye = yOffset - gridHeight / 2;
        bs = new BasicStroke(1);
        g2.setStroke(bs);
        g.drawLine(xs, ys, xs, ye);
        g.drawLine(xe, ys, xe, ye);
        g.drawLine(xs, ys, xe, ys);
        g.drawLine(xs, ye, xe, ye);

    }

}
