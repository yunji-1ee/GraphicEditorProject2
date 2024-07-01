import java.awt.*;
import java.awt.geom.*; //2D 그래픽처리
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;


public class Property {
    private final Shape shape;
    private final Color color;
    private final float thickness;

    public Property(Shape shape, Color color, float thickness) {
        this.shape = shape;
        this.color = color;
        this.thickness = thickness;
    }

    public Shape getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    public float getThickness() {
        return thickness;
    }

    public String toFileString() {
        // 선일 때
        if (shape instanceof Line2D) {
            Line2D line = (Line2D) shape;
            return String.format("Line %.1f %.1f %.1f %.1f %d %d %d %.1f\n", line.getX1(), line.getY1(), line.getX2(), line.getY2(), color.getRed(), color.getGreen(), color.getBlue(), thickness);
        }
        // 원일 때
        else if (shape instanceof Ellipse2D) {
            Ellipse2D ellipse = (Ellipse2D) shape;
            return String.format("Circle %.1f %.1f %.1f %.1f %d %d %d %.1f\n", ellipse.getX(), ellipse.getY(), ellipse.getWidth(), ellipse.getHeight(), color.getRed(), color.getGreen(), color.getBlue(), thickness);
        }
        // 사각형일 때
        else if (shape instanceof Rectangle2D) {
            Rectangle2D rectangle = (Rectangle2D) shape;
            return String.format("Rectangle %.1f %.1f %.1f %.1f %d %d %d %.1f\n", rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), color.getRed(), color.getGreen(), color.getBlue(), thickness);
        }
        // 프리라인일 때
        else if (shape instanceof Path2D) {
            Path2D path = (Path2D) shape;
            StringBuilder pathData = new StringBuilder("FreeLine");
            for (PathIterator pi = path.getPathIterator(null); !pi.isDone(); pi.next()) {
                double[] coords = new double[6];
                int type = pi.currentSegment(coords);
                pathData.append(" ").append(type).append(" ").append(coords[0]).append(" ").append(coords[1]);

            }
            return String.format("%s %d %d %d %.1f\n", pathData, color.getRed(), color.getGreen(), color.getBlue(), thickness);
        }
        return "";
    }

    public static Property fromFileString(String fileString) {
        String[] parts = fileString.split(" ");
        String type = parts[0];
        double x1 = Double.parseDouble(parts[1]);
        double y1 = Double.parseDouble(parts[2]);
        double x2 = Double.parseDouble(parts[3]);
        double y2 = Double.parseDouble(parts[4]);
        int r = Integer.parseInt(parts[5]);
        int g = Integer.parseInt(parts[6]);
        int b = Integer.parseInt(parts[7]);
        float thickness = Float.parseFloat(parts[8]);
        Color color = new Color(r, g, b);
        Shape shape = null;

        switch (type) {
            case "Line" -> shape = new Line2D.Double(x1, y1, x2, y2);
            case "Circle" -> shape = new Ellipse2D.Double(x1, y1, x2, y2);
            case "Rectangle" -> shape = new Rectangle2D.Double(x1, y1, x2, y2);
            case "FreeLine" -> { //끊이지 않도록
                Path2D path = new Path2D.Double();
                path.moveTo(x1, y1);
                for (int i = 9; i < parts.length; i += 3) {
                    int typeInt = Integer.parseInt(parts[i]);
                    double x = Double.parseDouble(parts[i + 1]);
                    double y = Double.parseDouble(parts[i + 2]);
                    if (typeInt == PathIterator.SEG_LINETO) {
                        path.lineTo(x, y);
                    }
                }
                shape = path;
            }
        }
        return new Property(shape, color, thickness);
    }
}
