import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GraphicEditor {
    private boolean LineMode = false;
    private boolean PolyLineMode = false;
    private boolean CircleMode = false;
    private boolean RectangleMode = false;
    private boolean ColorMode = false;
    private boolean ThicknessMode = false;
    private boolean StyleMode = false;
    private boolean Mode1 = false;
    private boolean Mode2= false;
    private boolean Mode3 = false;
    private boolean Load= false;
    private boolean Save = false;


    public static void main(String[] args) {
        new GraphicEditor();
    }
//화면
    public GraphicEditor() {
        JFrame frame = new JFrame("그림판");
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setSize(1200, 800);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(17, 1, 5, 5));
        buttonPanel.setBounds(0, 0, 250, 860);
        buttonPanel.setBackground(new Color(230, 230, 220));
        frame.add(buttonPanel);

        String[] buttonNames = {
                "[Draw]", "Line", "PolyLine", "Circle", "Rectangle",
                "[Property]", "Color", "Thickness", "Style",
                "[More]", "1", "2", "3", "LOAD", "SAVE"
        };

        JButton[] buttons = new JButton[buttonNames.length];

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(buttonNames[i]);
            buttonPanel.add(buttons[i]);
            buttons[i].setFont(new Font("Serif", Font.PLAIN, 16));
            buttons[i].setBorderPainted(false);
            buttons[i].setOpaque(false);

            // 버튼 텍스트를 통해 드로잉 모드 설정
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton sourceButton = (JButton) e.getSource();

                    //선 그리기
                    if (sourceButton.getText().equals("Line")) {
                        LineMode = true;
                        PolyLineMode = false;
                        CircleMode = false;
                        RectangleMode = false;
                        ColorMode = false;
                        ThicknessMode = false;
                        StyleMode = false;
                        Mode1 = false;
                        Mode2= false;
                        Mode3 = false;
                        Load = false;
                        Save = false;
                    }
                    //곡선 그리기
                    else if (sourceButton.getText().equals("Polyline")) {
                        LineMode = false;
                        PolyLineMode = true;
                        CircleMode = false;
                        RectangleMode = false;
                        ColorMode = false;
                        ThicknessMode = false;
                        StyleMode = false;
                        Mode1 = false;
                        Mode2= false;
                        Mode3 = false;
                        Load = false;
                        Save = false;
                    }
                    //원 그리기
                    else if (sourceButton.getText().equals("Circle")) {
                        LineMode = false;
                        PolyLineMode = false;
                        CircleMode = true;
                        RectangleMode = false;
                        ColorMode = false;
                        ThicknessMode = false;
                        StyleMode = false;
                        Mode1 = false;
                        Mode2= false;
                        Mode3 = false;
                        Load = false;
                        Save = false;
                    }
                    //사각형 그리기
                    else if (sourceButton.getText().equals("Rectangle")) {
                        LineMode = false;
                        PolyLineMode = false;
                        CircleMode = false;
                        RectangleMode = true;
                        ColorMode = false;
                        ThicknessMode = false;
                        StyleMode = false;
                        Mode1 = false;
                        Mode2= false;
                        Mode3 = false;
                        Load = false;
                        Save = false;
                    }
                    //색 선택
                    else if (sourceButton.getText().equals("Color")) {
                        LineMode = false;
                        PolyLineMode = false;
                        CircleMode = false;
                        RectangleMode = false;
                        ColorMode = true;
                        ThicknessMode = false;
                        StyleMode = false;
                        Mode1 = false;
                        Mode2= false;
                        Mode3 = false;
                        Load = false;
                        Save = false;
                    }
                    //두께 선택
                    else if (sourceButton.getText().equals("Thickness")) {
                        LineMode = false;
                        PolyLineMode = false;
                        CircleMode = false;
                        RectangleMode = false;
                        ColorMode = false;
                        ThicknessMode = true;
                        StyleMode = false;
                        Mode1 = false;
                        Mode2= false;
                        Mode3 = false;
                        Load = false;
                        Save = false;
                    }
                    //1
                    else if (sourceButton.getText().equals("1")) {
                        LineMode = false;
                        PolyLineMode = false;
                        CircleMode = false;
                        RectangleMode = false;
                        ColorMode = false;
                        ThicknessMode = false;
                        StyleMode = false;
                        Mode1 = true;
                        Mode2= false;
                        Mode3 = false;
                        Load = false;
                        Save = false;
                    }
                    //2
                    else if (sourceButton.getText().equals("2")) {
                        LineMode = false;
                        PolyLineMode = false;
                        CircleMode = false;
                        RectangleMode = false;
                        ColorMode = false;
                        ThicknessMode = false;
                        StyleMode = false;
                        Mode1 = false;
                        Mode2= true;
                        Mode3 = false;
                        Load = false;
                        Save = false;
                    }
                    //3
                    else if (sourceButton.getText().equals("3")) {
                        LineMode = false;
                        PolyLineMode = false;
                        CircleMode = false;
                        RectangleMode = false;
                        ColorMode = false;
                        ThicknessMode = false;
                        StyleMode = false;
                        Mode1 = false;
                        Mode2= false;
                        Mode3 = true;
                        Load = false;
                        Save = false;

                    }
                    else if (sourceButton.getText().equals("Load")) {
                        LineMode = false;
                        PolyLineMode = false;
                        CircleMode = false;
                        RectangleMode = false;
                        ColorMode = false;
                        ThicknessMode = false;
                        StyleMode = false;
                        Mode1 = false;
                        Mode2= false;
                        Mode3 = false;
                        Load = true;
                        Save = false;

                    }
                    else if (sourceButton.getText().equals("Save")) {
                        LineMode = false;
                        PolyLineMode = false;
                        CircleMode = false;
                        RectangleMode = false;
                        ColorMode = false;
                        ThicknessMode = false;
                        StyleMode = false;
                        Mode1 = false;
                        Mode2= false;
                        Mode3 = false;
                        Load = false;
                        Save = true;

                    }
                } //액션퍼폼
            }); //액션리스너
        } //버튼
        //화면 꾸미기
        buttons[0].setFont(new Font("Serif", Font.BOLD, 23));
        buttons[5].setFont(new Font("Serif", Font.BOLD, 23));
        buttons[9].setFont(new Font("Serif", Font.BOLD, 23));

        CanvasPanel canvasPanel = new CanvasPanel();
        canvasPanel.setBounds(250, 0, 950, 800);
        canvasPanel.setBackground(Color.WHITE);
        frame.add(canvasPanel);

        frame.setVisible(true);
    }


//내부구현
    private class CanvasPanel extends JPanel {

        private int startX, startY, endX, endY; //좌표설정

        public CanvasPanel() {
            setLayout(null);


        //마우스 리스너

            addMouseListener(new MouseAdapter() {

                //마우스를 누를 때
                @Override
                public void mousePressed(MouseEvent e) {

                    if (LineMode) {
                    //    drawing = true; //현재 상태 트루
                        startX = e.getX(); //x좌표 받아오기
                        startY = e.getY(); //Y좌표 받아오기
                   // else if(){

                  //  }

                    }
                }
                //마우스 놓았을 때
                @Override
                public void mouseReleased(MouseEvent e) {

                    repaint();
                }
            });

    //마우스 모션 리스너
            addMouseMotionListener(new MouseAdapter() {

                //드래그 중일 때 마우스 움직이는대로 좌표값 받아와서 화면에 띄우기
                @Override
                public void mouseDragged(MouseEvent e) {

                        endX = e.getX();
                        endY = e.getY();
                        repaint();
                }
            });
        }
        //초기화
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
                g.drawLine(startX, startY, endX, endY);
               // g.drawRect(startX,startY,endX,endY);
        }


    }
}
