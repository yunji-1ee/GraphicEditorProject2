import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;
import javax.swing.*;

public class GraphicEditor {
    private boolean LineMode = false;
    private boolean CircleMode = false;
    private boolean RectangleMode = false;

    public static void main(String[] args) {
        new GraphicEditor();
    }

    public GraphicEditor() {
        // 프레임 설정
        JFrame frame = new JFrame("그림판");
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setSize(1200, 800);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 버튼 패널 설정
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(17, 1, 5, 5));
        buttonPanel.setBounds(0, 0, 250, 860);
        buttonPanel.setBackground(new Color(230, 230, 220));
        frame.add(buttonPanel);

        // 버튼 이름 배열
        String[] buttonNames = {
                "[Draw]", "FreeLine", "Line", "Circle", "Rectangle",
                "[Property]", "Color", "Thickness", "Style",
                "[More]", "1", "2", "3", "LOAD", "SAVE"
        };

        JButton[] buttons = new JButton[buttonNames.length];

        // 버튼 생성 및 이벤트 리스너 추가
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(buttonNames[i]);
            buttonPanel.add(buttons[i]);
            buttons[i].setFont(new Font("Serif", Font.PLAIN, 16));
            buttons[i].setBorderPainted(false);
            buttons[i].setOpaque(false);

            // 버튼 텍스트로 모드 세팅
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton sourceButton = (JButton) e.getSource();

                    // 선 그리기
                    if (sourceButton.getText().equals("Line")) {
                        LineMode = true;
                        CircleMode = false;
                        RectangleMode = false;
                    }
                    // 원 그리기
                    else if (sourceButton.getText().equals("Circle")) {
                        LineMode = false;
                        CircleMode = true;
                        RectangleMode = false;

                    }
                    // 사각형 그리기
                    else if (sourceButton.getText().equals("Rectangle")) {
                        LineMode = false;
                        CircleMode = false;
                        RectangleMode = true;

                    }
                    else {
                        LineMode = false;
                        CircleMode = false;
                        RectangleMode = false;
                    }
                }
            });
        }

        // 강조할 버튼의 폰트 조정
        buttons[0].setFont(new Font("Serif", Font.BOLD, 23));
        buttons[5].setFont(new Font("Serif", Font.BOLD, 23));
        buttons[9].setFont(new Font("Serif", Font.BOLD, 23));

        // 캔버스 패널 설정
        CanvasPanel canvasPanel = new CanvasPanel();
        canvasPanel.setBounds(250, 0, 950, 800);
        canvasPanel.setBackground(Color.WHITE);
        frame.add(canvasPanel);

        frame.setVisible(true);
    }

    // 내부 구현
    private class CanvasPanel extends JPanel {
        private int startX, startY, endX, endY; // 좌표 설정
        private boolean drawing = false; // 그리기 상태

        public CanvasPanel() {
            setLayout(null);

            // 마우스 리스너
            addMouseListener(new MouseAdapter() {
                // 마우스를 누를 때
                @Override
                public void mousePressed(MouseEvent e) {
                        startX = e.getX(); // x좌표 받아오기
                        startY = e.getY(); // y좌표 받아오기
                        drawing = true; // 그리기 상태 true
                }

                // 마우스 놓았을 때
                @Override
                public void mouseReleased(MouseEvent e) {
                    if (drawing) {
                        endX = e.getX(); // x좌표 받아오기
                        endY = e.getY(); // y좌표 받아오기
                        drawing = false; // 그리기 상태 false
                        repaint(); // 화면 갱신
                    }
                }
            });

            // 마우스 모션 리스너
            addMouseMotionListener(new MouseAdapter() {
                // 드래그 중일 때 마우스 움직이는 대로 좌표값 받아와서 화면에 띄우기
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (drawing) {
                        endX = e.getX();
                        endY = e.getY();
                        repaint();
                    }
                }
            });
        }

        // 모드 구현
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2;
            g2 = (Graphics2D)g;
            {
                // 선 그리기
                if (LineMode) {
                    g.drawLine(startX, startY, endX, endY);
                }
                // 원 그리기
                else if (CircleMode) {
                     g.drawOval(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX-startX), Math.abs(endY-startY));
                }
                //사각형 그리기
                else if (RectangleMode){
                     g.drawRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX-startX), Math.abs(endY-startY));

                }
            }
        }
    }
}

