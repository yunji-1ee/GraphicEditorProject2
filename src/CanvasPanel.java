import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;


//--------캔버스 패널 설정----------------------------------------------------------------
public class CanvasPanel extends JPanel {
    private int startX, startY, endX, endY;
    private final GraphicEditor editor;

    // 생성자에서 GraphicEditor 인스턴스를 받음
    public CanvasPanel(GraphicEditor editor) {
        this.editor = editor;

        // 마우스 리스너 설정
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
                if (editor.FreeLineMode) {
                    editor.freeLinePoints.clear();
                    editor.freeLinePoints.add(new Point(startX, startY));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                endX = e.getX();
                endY = e.getY();
                Shape shape = null;

                if (editor.FreeLineMode) {
                    Path2D path = new Path2D.Double();
                    path.moveTo(editor.freeLinePoints.get(0).x, editor.freeLinePoints.get(0).y);
                    for (Point p : editor.freeLinePoints) {
                        path.lineTo(p.x, p.y);
                    }
                    shape = path;
                } else if (editor.LineMode) {
                    shape = new Line2D.Double(startX, startY, endX, endY);
                } else if (editor.CircleMode) {
                    shape = new Ellipse2D.Double(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
                } else if (editor.RectangleMode) {
                    shape = new Rectangle2D.Double(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
                }

                if (shape != null) {
                    editor.box.add(new Property(shape, editor.currentColor, editor.currentThickness));
                }

                repaint();
            }
        });

        // 마우스 모션 리스너 설정
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                endX = e.getX();
                endY = e.getY();
                if (editor.FreeLineMode) {
                    editor.freeLinePoints.add(new Point(endX, endY));
                }
                repaint();
            }
        });
    }

    // 그림 그리기
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(editor.currentThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // 저장된 도형들 그리기
        for (Property property : editor.box) {
            g2.setColor(property.getColor());
            g2.setStroke(new BasicStroke(property.getThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.draw(property.getShape());
        }

        g2.setColor(editor.currentColor);
        g2.setStroke(new BasicStroke(editor.currentThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // 현재 모드에 따라 도형을 그리기
        if (editor.LineMode) {
            g2.drawLine(startX, startY, endX, endY);
        } else if (editor.CircleMode) {
            g2.drawOval(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
        } else if (editor.RectangleMode) {
            g2.drawRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
        } else if (editor.FreeLineMode) {
            if (!editor.freeLinePoints.isEmpty()) {
                Path2D path = new Path2D.Double();
                path.moveTo(editor.freeLinePoints.get(0).x, editor.freeLinePoints.get(0).y);
                for (Point p : editor.freeLinePoints) {
                    path.lineTo(p.x, p.y);
                }
                g2.draw(path);
            }
        }
    }
}
