import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.*;
import javax.swing.*;



//--------캔버스 패널 설정----------------------------------------------------------------

public class CanvasPanel extends JPanel {

    private int startX, startY, endX, endY;//좌표설정

    private final GraphicEditor editor;
    // 생성자에서 GraphicEditor 인스턴스를 받음
    public CanvasPanel(GraphicEditor editor) {
        this.editor = editor;

        // 마우스 리스너 설정-----------------------------------------------------------------

        addMouseListener(new MouseAdapter() {

            //마우스가 눌려졌을 때
            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();

                if (editor.FreeLineMode) { //자유곡선 그릴 때
                    editor.freeLinePoints.clear(); //새로운 좌표부터 그리기 시작
                    editor.freeLinePoints.add(new Point(startX, startY)); //방금 그린 그림 추가하기
                }
                else if (editor.EraserMode) { //자유곡선 그릴 때
                    editor.freeLinePoints.clear(); //새로운 좌표부터 그리기 시작
                    editor.freeLinePoints.add(new Point(startX, startY)); //방금 그린 그림 추가하기
                }
            }
            //마우스를 땠을 때
            @Override
            public void mouseReleased(MouseEvent e) {
                endX = e.getX();
                endY = e.getY();
                Shape shape = null;

                //그림 그리고 지우기 전까지 그 모양 shape에 넣어두기 (그렸던 그림들 유지하기)
                if (editor.FreeLineMode) {

                    Path2D path = new Path2D.Double();

                    path.moveTo(editor.freeLinePoints.get(0).x, editor.freeLinePoints.get(0).y);

                    for (Point p : editor.freeLinePoints) {
                        path.lineTo(p.x, p.y);
                    }
                    shape = path;

                } //프리라인모드
                else if (editor.EraserMode) {

                    Path2D path = new Path2D.Double();

                    path.moveTo(editor.freeLinePoints.get(0).x, editor.freeLinePoints.get(0).y);

                    for (Point p : editor.freeLinePoints) {
                        path.lineTo(p.x, p.y);
                    }
                    shape = path;

                } //지우개모드
                else if (editor.LineMode) {
                    shape = new Line2D.Double(startX, startY, endX, endY);
                }
                else if (editor.CircleMode) {
                    shape = new Ellipse2D.Double(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
                }
                else if (editor.RectangleMode) {
                    shape = new Rectangle2D.Double(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
                }
                else if (editor.ColoredCircleMode) {
                    shape = new Ellipse2D.Double(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
                }
                else if (editor.ColoredRectangleMode) {
                    shape = new Rectangle2D.Double(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
                }
                else if (editor.PatternMode) {

                   // endX,endY

                }

                if (shape != null) { //뭔가 그림이 있다면 box에 더하기
                    editor.box.add(new Property(shape, editor.currentColor, editor.currentThickness));
                }

                repaint();
            }
        });

        // 마우스 모션 리스너 설정
        addMouseMotionListener(new MouseAdapter() {
            //마우스를 드래그할 때
            @Override
            public void mouseDragged(MouseEvent e) {
                endX = e.getX();
                endY = e.getY();

                if (editor.FreeLineMode) {
                    editor.freeLinePoints.add(new Point(endX, endY));
                }
                else if (editor.EraserMode) {
                    //색 하얗게,,,,?
                    editor.freeLinePoints.add(new Point(endX, endY));
                }
                repaint();
            }
        });
    }


    // 그림 그리기ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡpaintㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(editor.currentThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));


        // 박스에 넣어둔 도형들 그리기---------------------------------------------------------------------------------------------------------
        for (Property property : editor.box) { // 저장된 도형들을 모두
            g2.setColor(property.getColor());
            g2.setStroke(new BasicStroke(property.getThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.draw(property.getShape());   //그림그리기
        }

        g2.setColor(editor.currentColor);
        g2.setStroke(new BasicStroke(editor.currentThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // 모드에 따라 좌표를 찍어 그리기 (how to)---------------------------------------------------------------------------------------------
        //라인
        if (editor.LineMode) {
            g2.drawLine(startX, startY, endX, endY); //시작좌표, 끝좌표
        }
        //원
        else if (editor.CircleMode) {
            g2.drawOval(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
        }
        //사각형
        else if (editor.RectangleMode) {
            g2.drawRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
        }
        //자유곡선
        else if (editor.FreeLineMode) {

            if (!editor.freeLinePoints.isEmpty()) { //자유곡선으로 그린 점들이 존재한다면

                Path2D path = new Path2D.Double();
                path.moveTo(editor.freeLinePoints.get(0).x, editor.freeLinePoints.get(0).y);

                for (Point p : editor.freeLinePoints) {
                    path.lineTo(p.x, p.y);
                }
                g2.draw(path); //그리기
            }
        }
        //지우개
        else if (editor.EraserMode) { //지우개 모드일때도 자유곡선 그리듯이 그리기 근데 색은 하얀색으로,,

            if (!editor.freeLinePoints.isEmpty()) { //자유곡선으로 그린 점들이 존재한다면

                Path2D path = new Path2D.Double();

                path.moveTo(editor.freeLinePoints.get(0).x, editor.freeLinePoints.get(0).y);

                for (Point p : editor.freeLinePoints) {
                    path.lineTo(p.x, p.y);
                }
                g2.draw(path); //그리기
            }
        }
        else if (editor.PatternMode) {
            g2.drawString("♥ ♡", startX, startY);
             }
        else if (editor.ColoredRectangleMode) {
            g2.fillRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
        }
        else if (editor.ColoredCircleMode) {
            g2.fillOval(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
        }

        }
    }

