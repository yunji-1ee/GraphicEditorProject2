//--------import------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.*;

// ---------초기설정-----------------------------------------------------------------------
public class GraphicEditor {
    private boolean LineMode = false;
    private boolean CircleMode = false;
    private boolean RectangleMode = false;
    private boolean TextBoxMode = false;
    private Color currentColor = Color.BLACK; // 기본 색상 검정으로 설정

    ArrayList<ColoredShape> box = new ArrayList<>(); // 도형 저장하기 (그릴 때마다 초기화되지 않도록)

    public GraphicEditor() { // constructor
        // 프레임 설정
        JFrame frame = new JFrame("Graphic Editor ( ◡̉̈ )\u200B");
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setSize(1200, 800);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 버튼 패널 설정

        JPanel buttonPanel = new JPanel(); //버튼패널생성
        buttonPanel.setLayout(new GridLayout(15, 1, 5, 5)); //버튼패널 레이아웃 설정
        buttonPanel.setBounds(0, 0, 250, 770);  //프레임 내 버튼패널 좌표
        buttonPanel.setBackground(new Color(230, 230, 220)); //버튼패널 배경색 rgb로 설정
        frame.add(buttonPanel); //프레임에 버튼패널 넣기

        // 캔버스 패널 설정
        CanvasPanel canvasPanel = new CanvasPanel(); //그림그릴용 캔버스패널 생성
        canvasPanel.setBounds(250, 0, 950, 800); //캔버스패널 좌표 설정
        canvasPanel.setBackground(Color.WHITE); //흰색 도화지로,,
        frame.add(canvasPanel); //프레임에 캔버스패널 추가하기

        frame.setVisible(true); // **** 프레임 보이게 = 창 생성

    //---------------버튼세팅-----------------------------------------------------------------------

        // 버튼들 이름 배열에 정해놓기
        String[] buttonNames = {
                "[Draw]", "FreeLine : ( ︴)", "Line : ( ╱ )", "Circle : ( 〇 )", "Rectangle : ( ⎕ )", // 5
                "[Property]", "Color : ( 🌈  )", "Thickness : (⠐•● )", "Style", // 4
                "[More]", "Text", "Eraser", "** Clear **", "LOAD", "SAVE" // 5
        };


        JButton[] buttons = new JButton[buttonNames.length]; // 버튼이름 길이만큼 버튼 생성

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(buttonNames[i]);  //배열에 저장해놓은 인텍스 순으로 버튼에 이름넣기

            buttons[i].setFont(new Font("Serif", Font.PLAIN, 16)); //버튼 폰트 설정
            buttons[i].setBorderPainted(false);//버튼색깔 죽이기 -> 배경색 지정해서 이뿌게 보이게 하려고,,
            buttonPanel.add(buttons[i]); //버튼패널에 버튼 추가하기


    //-------------버튼으로 액션받기--------------------------------------------------------------------------

            buttons[i].addActionListener(new ActionListener() {
                @Override

                public void actionPerformed(ActionEvent e) { // 버튼 텍스트 받아와서 케이스별로  모드 수행
                    String mode = e.getActionCommand(); //버튼의 이름 mode 변수에 넣기

                    // 선 그리기
                    switch (mode) {
                        case "Line : ( ╱ )" -> {
                            LineMode = true;
                            CircleMode = false;
                            RectangleMode = false;
                            TextBoxMode = false;  //버튼 이름이 다음과 같다면,.
                        }
                        // 원 그리기
                        case "Circle : ( 〇 )" -> {
                            LineMode = false;
                            CircleMode = true;
                            RectangleMode = false;
                            TextBoxMode = false;
                        }
                        // 사각형 그리기
                        case "Rectangle : ( ⎕ )" -> {
                            LineMode = false;
                            CircleMode = false;
                            RectangleMode = true;
                            TextBoxMode = false;
                        }
                        // 텍스트박스 띄우기
                        case "Text" -> {
                            LineMode = false;
                            CircleMode = false;
                            RectangleMode = false;
                            TextBoxMode = true;
                        }
                        // 모두 지우기
                        case "** Clear **" -> {
                            LineMode = false;
                            CircleMode = false;
                            RectangleMode = false;
                            TextBoxMode = false;

                            box.clear();
                            canvasPanel.repaint();
                        }
                        //색상 설정하기
                        case "Color : ( 🌈  )" -> {
                            LineMode = false;
                            CircleMode = false;
                            RectangleMode = false;
                            TextBoxMode = false;

                            // 현재 색상을 색깔창에서 선택한 색으로 설정
                            currentColor = JColorChooser.showDialog(null, "색상 선택", currentColor);
                        }
                        default -> {
                            LineMode = false;
                            CircleMode = false;
                            RectangleMode = false;
                            TextBoxMode = false;
                        }
                    } //스위치 조건문
                } //이벤트 액션퍼폼
            }); //액션리스너
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
    //그리기
    public class CanvasPanel extends JPanel {

        int startX, startY, endX, endY; // 좌표생성

        public CanvasPanel() { // constructor
            // 마우스 리스너
            addMouseListener(new MouseAdapter() {

                // 마우스를 누를 때
                @Override
                public void mousePressed(MouseEvent e) {
                    startX = e.getX(); // x좌표 받아오기
                    startY = e.getY(); // y좌표 받아오기
                }

                // 마우스 놓았을 때
                @Override
                public void mouseReleased(MouseEvent e) {
                    endX = e.getX(); // x좌표 받아오기
                    endY = e.getY(); // y좌표 받아오기

                    Shape shape = null; // 도형을 리스트에 추가


                    //도형 다시그리기
                    if (LineMode) {

                    } else if (CircleMode) {
                        shape = new Ellipse2D.Double(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
                    } else if (RectangleMode) {
                        shape = new Rectangle2D.Double(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
                    }

                    if (shape != null) {
                        box.add(new ColoredShape(shape, currentColor)); // 현재 색상으로 도형 추가
                    }

                    repaint();
                }
            });

            // 마우스 모션 리스너
            addMouseMotionListener(new MouseAdapter() {
                // 드래그 중
                @Override
                public void mouseDragged(MouseEvent e) {
                    endX = e.getX();
                    endY = e.getY();
                    repaint();
                }
            });
        }

        // 모드 구현
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            // 그린 도형 유지하기 - 모두 다시 그리기
            for (ColoredShape coloredShape : box) {

                g2.setColor(coloredShape.getColor());
                g2.draw(coloredShape.getShape());
            }
            g2.setColor(currentColor); // 현재 색상 설정

            // 도형 그리기
            if (LineMode) {
                g2.drawLine(startX, startY, endX, endY);
            }
            else if (CircleMode) {
                g2.drawOval(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
            }
            else if (RectangleMode) {
                g2.drawRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
            }
            else if (TextBoxMode) {
                int[] x2 = { 210, 175, 60, 150, 110, 210, 310, 270, 360, 245, 210 };
                int[] y2 = { 60, 160, 160, 225, 340, 270, 340, 225, 160, 160, 60 };
                g.drawPolygon(x2, y2, 10);
            }
        }
    }
    // 색상과 도형 을 함께 저장할 클래스
    class ColoredShape {
        private Shape shape;
        private Color color;
      //  private Thickness thickness;

        public ColoredShape(Shape shape, Color color // ,Thickness thickness
                             ) {
             this.shape = shape;
             this.color = color;
     //       this.thickness = thickness;
        }

        public Shape getShape() {
            return shape;
        }

        public Color getColor() {
            return color;
        }
       // public  Thickness getThickness(){
        //    return thickness;
       // }
    }
}
