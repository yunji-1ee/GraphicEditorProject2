//--------import------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

// ---------초기설정-----------------------------------------------------------------------
public class GraphicEditor {
    private boolean LineMode = false;
    private boolean CircleMode = false;
    private boolean RectangleMode = false;
    private boolean PatternMode = false;
    private boolean FreeLineMode = false;
    private float currentThickness = 2.0F; //굵기 실수형으로 조절
    private Color currentColor = Color.BLACK; // 기본 색상 검정으로 설정

    ArrayList<Property> box = new ArrayList<>(); // 도형 저장하기 (그릴 때마다 초기화되지 않도록)
    ArrayList<Point> freeLinePoints = new ArrayList<>(); // 프리라인을 만들기 위한,, 점들 저장할 곳

    private CanvasPanel canvasPanel; // CanvasPanel 인스턴스를 멤버 변수로 추가


    public void UI() {  //기본값 세팅
        // 프레임 설정
        JFrame frame = new JFrame("Graphic Editor ( ◡̉̈ )\u200B");
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setSize(1200, 800);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 버튼 패널 설정
        JPanel buttonPanel = new JPanel(); // 버튼 패널 생성
        buttonPanel.setLayout(new GridLayout(15, 1, 5, 5)); // 버튼 패널 레이아웃 설정
        buttonPanel.setBounds(0, 0, 250, 770); // 프레임 내 버튼 패널 좌표
        buttonPanel.setBackground(new Color(230, 230, 220)); // 버튼 패널 배경색 rgb로 설정
        frame.add(buttonPanel); // 프레임에 버튼 패널 넣기

        // 캔버스 패널 설정
        canvasPanel = new CanvasPanel(); // 그림 그릴용 캔버스 패널 생성
        canvasPanel.setBounds(250, 0, 950, 800); // 캔버스 패널 좌표 설정
        canvasPanel.setBackground(Color.WHITE); // 흰색 도화지로,,
        frame.add(canvasPanel); // 프레임에 캔버스 패널 추가하기

        frame.setVisible(true); // 프레임 보이게 설정 = 창 생성

        //---------------버튼세팅-----------------------------------------------------------------------

        // 버튼들 이름 배열에 정해놓기
        String[] buttonNames = {
                "[Draw]", "FreeLine : (  ︴)", "Line : ( ╱ )", "Circle : ( 〇 )", "Rectangle : ( ⎕ )", // 5
                "[Property]", "Color : ( 🌈 )", "Thickness : (⠐•● )", "Style", // 4
                "[More]", "Pattern", "Eraser ❌", "❌ Clear ❌", "LOAD", "SAVE" // 5
        };

        JButton[] buttons = new JButton[buttonNames.length]; // 버튼 이름 길이만큼 버튼 생성

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(buttonNames[i]); // 배열에 저장해놓은 인덱스 순으로 버튼에 이름 넣기

            buttons[i].setFont(new Font("Serif", Font.PLAIN, 16)); // 버튼 폰트 설정
            buttons[i].setBorderPainted(false); // 버튼 테두리 없애기
            buttonPanel.add(buttons[i]); // 버튼 패널에 버튼 추가하기

            //-------------버튼으로 액션받기--------------------------------------------------------------------------

            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {// 버튼 텍스트 받아와서 케이스별로 모드 수행
                String mode = e.getActionCommand(); // 버튼의 이름 mode 변수에 넣기

                // 선 그리기
                switch (mode) {
                    case "FreeLine : (  ︴)" -> {
                        LineMode = false;
                        CircleMode = false;
                        RectangleMode = false;
                        PatternMode = false;
                        FreeLineMode = true;
                    }
                    case "Line : ( ╱ )" -> {
                        LineMode = true;
                        CircleMode = false;
                        RectangleMode = false;
                        PatternMode = false;
                        FreeLineMode = false;
                    }
                    // 원 그리기
                    case "Circle : ( 〇 )" -> {
                        LineMode = false;
                        CircleMode = true;
                        RectangleMode = false;
                        PatternMode = false;
                        FreeLineMode = false;
                    }
                    // 사각형 그리기
                    case "Rectangle : ( ⎕ )" -> {
                        LineMode = false;
                        CircleMode = false;
                        RectangleMode = true;
                        PatternMode = false;
                        FreeLineMode = false;
                    }
                    // 텍스트박스 띄우기
                    case "Text" -> {
                        LineMode = false;
                        CircleMode = false;
                        RectangleMode = false;
                        PatternMode = true;
                        FreeLineMode = false;
                    }
                    // 모두 지우기
                    case "❌ Clear ❌" -> {
                        LineMode = false;
                        CircleMode = false;
                        RectangleMode = false;
                        PatternMode = false;
                        FreeLineMode = false;
                        box.clear(); // 저장한 도형들 클리어
                        canvasPanel.repaint();
                    }
                    // 색상 설정하기
                    case "Color : ( 🌈 )" -> {
                        LineMode = false;
                        CircleMode = false;
                        RectangleMode = false;
                        PatternMode = false;
                        FreeLineMode = false;
                        // 컬러츄저다이얼로그를 띄우고 / 현재 색상을 색상창에서 받아온 값으로 설정
                        currentColor = JColorChooser.showDialog(null, "Choose the Color", currentColor);

                    }
                    case "Thickness : (⠐•● )" -> {
                        LineMode = false;
                        CircleMode = false;
                        RectangleMode = false;
                        PatternMode = false;
                        FreeLineMode = false;

                        // 두께 선택
                        String input = JOptionPane.showInputDialog("Enter line thickness");
                            currentThickness = Float.parseFloat(input);
                    }
                    case "SAVE" -> saveShapes();
                    case "LOAD" -> loadShapes();
                    default -> {
                        LineMode = false;
                        CircleMode = false;
                        RectangleMode = false;
                        PatternMode = false;
                        FreeLineMode = false;
                    }
                } // 스위치 조건문
                }  //이벤트 퍼폼
            }); // 액션리스너
        } // 버튼 설정 for문 끝

        // 강조할 버튼의 폰트 조정
        buttons[0].setFont(new Font("Serif", Font.BOLD, 23));
        buttons[5].setFont(new Font("Serif", Font.BOLD, 23));
        buttons[9].setFont(new Font("Serif", Font.BOLD, 23));
        buttons[13].setFont(new Font("Serif", Font.BOLD, 20));
        buttons[14].setFont(new Font("Serif", Font.BOLD, 20));
    }

    //--------캔버스에 그림그리기---------------------------------------------------------------------------

    // 내부 구현
    // 그리기
    public class CanvasPanel extends JPanel {

        int startX, startY, endX, endY; // 좌표 생성

        public CanvasPanel() { // constructor
            // 마우스 리스너-그림그리기------------------------------------------------------------------------
            addMouseListener(new MouseAdapter() {

                // 마우스를 누를 때-------------------------------------------------------------------------
                @Override
                public void mousePressed(MouseEvent e) { //마우스를 누르고 있을 때
                    startX = e.getX(); // x좌표 받아오기
                    startY = e.getY(); // y좌표 받아오기
                }

                // 마우스를 놓았을 때--------------------------------------------------------------------------
                @Override
                public void mouseReleased(MouseEvent e) { //마우스를 떼어냈을 때
                    endX = e.getX(); // x좌표 받아오기
                    endY = e.getY(); // y좌표 받아오기

                    Shape shape = null; // 도형을 다 그리고나면(마우스를 뗐을 때)box 리스트에 추가


                    // 마우스를 뗐을 때 기존에 그렸던 도형 다시 그리기-------------------------------------------------
                    if (FreeLineMode) {

                    } //FreeLineMode

                    else if (LineMode) {
                        shape = new Line2D.Double(startX, startY, endX, endY);
                    } else if (CircleMode) {
                        shape = new Ellipse2D.Double(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
                    } else if (RectangleMode) {
                        shape = new Rectangle2D.Double(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
                    }
                    if (shape != null) {
                        box.add(new Property(shape, currentColor, currentThickness)); // 현재 색상과 두께로 도형 추가
                    }

                    repaint();
                }
            });
            // 마우스 모션 리스너
            addMouseMotionListener(new MouseAdapter() {
                // 드래그 중 -----------------------------------------------------------------------
                @Override
                public void mouseDragged(MouseEvent e) {
                    endX = e.getX();
                    endY = e.getY();

                    if (FreeLineMode) {
                        freeLinePoints.add(new Point(endX, endY));
                    }
                    repaint();
                }
            });
        }
        //그림그리기 --------------------------------------------------------------------------------
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(currentThickness));

            for (Property coloredShape : box) {
                g2.setColor(coloredShape.getColor());
                g2.setStroke(new BasicStroke(coloredShape.getThickness()));
                g2.draw(coloredShape.getShape());
            }

            g2.setColor(currentColor); // 현재 색상 설정
            g2.setStroke(new BasicStroke(currentThickness)); // 현재 두께 설정

            // 모드 구현-------------------------------------------------------------------------------
            if (LineMode) {
                g2.drawLine(startX, startY, endX, endY);
            }
            else if (CircleMode) {
                g2.drawOval(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
            }
            else if (RectangleMode) {
                g2.drawRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
            }
            else if (FreeLineMode) {
                if (!freeLinePoints.isEmpty()) {
                    Path2D path = new Path2D.Double();

                    path.moveTo(freeLinePoints.get(0).x, freeLinePoints.get(0).y);
                    for (Point p : freeLinePoints) {
                        path.lineTo(p.x, p.y);
                    }
                    g2.draw(path);
                }
            } else if (PatternMode) { //일단 별모양
                int[] x2 = { 210, 175, 60, 150, 110, 210, 310, 270, 360, 245, 210 };
                int[] y2 = { 60, 160, 160, 225, 340, 270, 340, 225, 160, 160, 60 };
                g.drawPolygon(x2, y2, 10);
            }
        }
    }

    // 클래스에 속성 저장하기 (그린 거, 색, 굵기)
        static class Property {
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
            //선일때
            if (shape instanceof Line2D) {
                Line2D line = (Line2D) shape;
                return String.format("Line %.1f %.1f %.1f %.1f %d %d %d %.1f\n", line.getX1(), line.getY1(), line.getX2(), line.getY2(), color.getRed(), color.getGreen(), color.getBlue(), thickness);
            }
            //원일때
            else if (shape instanceof Ellipse2D) {
                Ellipse2D ellipse = (Ellipse2D) shape;
                return String.format("Circle %.1f %.1f %.1f %.1f %d %d %d %.1f\n", ellipse.getX(), ellipse.getY(), ellipse.getWidth(), ellipse.getHeight(), color.getRed(), color.getGreen(), color.getBlue(), thickness);
            }
            //사각형일때
            else if (shape instanceof Rectangle2D) {
                Rectangle2D rectangle = (Rectangle2D) shape;
                return String.format("Rectangle %.1f %.1f %.1f %.1f %d %d %d %.1f\n", rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), color.getRed(), color.getGreen(), color.getBlue(), thickness);
            }
            //프리라인일때
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
                case "FreeLine" -> {
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

    //-----------저장/로드-------------------------------------------------------------------------------------

    private void saveShapes() {
        try (PrintWriter out = new PrintWriter(new FileWriter("shapes.txt"))) {
            for (Property shape : box) {
                out.print(shape.toFileString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadShapes() {
        box.clear();
        try (BufferedReader in = new BufferedReader(new FileReader("shapes.txt"))) {
            String line;
            while ((line = in.readLine()) != null) {
                box.add(Property.fromFileString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        canvasPanel.repaint(); // canvasPanel을 사용하여 repaint 호출
    }
}
