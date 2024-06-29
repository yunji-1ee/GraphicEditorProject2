import java.awt.*;
import javax.swing.*;


//-------------------------------------------------------------------------------------

public class GUI {

    public static void main(String[] args) {
        new GUI();
    }

    public GUI() {
        // 프레임 세팅

        JButton lineButton = new JButton();
        JButton polylineButton = new JButton();
        JButton circle = new JButton();

    //프레임
    JFrame frame = new JFrame("그림판");
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setSize(1200, 800);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //버튼패널
    JPanel button_panel = new JPanel();
        // 버튼 패널 설정
        button_panel.setLayout(new GridLayout(17, 1, 5, 5)); // 행, 열, 버튼 간격
        button_panel.setBounds(0, 0, 250, 800);
        button_panel.setBackground(new Color(230, 230, 220));
        frame.add(button_panel);

    //버튼
        String[] button_name = {
                "[Draw]", "line", "polyLone", "circle", "rectangle",
                "[Property]", "color", "thickness", "style", "[More]", "1", "2", "3",
                "LOAD", "SAVE"
        };

    JButton[] bt = new JButton[button_name.length];

        for (int i = 0; i < bt.length; i++) {
            bt[i] = new JButton(button_name[i]);
            button_panel.add(bt[i]);
            bt[i].setFont(new Font("Arial", Font.PLAIN, 16));
            bt[i].setBorderPainted(false);
            bt[i].setOpaque(false);
        }

        // 강조할 버튼의 폰트 조정
        bt[0].setFont(new Font("Arial", Font.BOLD, 23));
        bt[5].setFont(new Font("Arial", Font.BOLD, 23));
        bt[9].setFont(new Font("Arial", Font.BOLD, 23));
        bt[13].setFont(new Font("Arial", Font.BOLD, 23));
        bt[14].setFont(new Font("Arial", Font.BOLD, 23));

        // 사각형
        JButton rectangleButton = new JButton();

    // 캔버스 패널 설정
    JPanel canvas_panel = new JPanel();
        canvas_panel.setLayout(null);
        canvas_panel.setBounds(250, 0, 950, 800);
        canvas_panel.setBackground(Color.WHITE);
        frame.add(canvas_panel);

    // 텍스트 영역 설정
    JTextArea textArea = new JTextArea();
        textArea.setText("텍스트를 입력하시오");
        textArea.setBounds(200, 0, 400, 50);
        canvas_panel.add(textArea);

        // 사각형 그리기 패널 설정
        Tool tool = new Tool();
        tool.setBounds(0, 300, 950, 800);
        tool.setOpaque(false);
        canvas_panel.add(tool);

        frame.setVisible(true);

    } //GUI생성자

    private class Tool extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawRect(400, 100, 150, 150); // 사각형 그리기
            g.drawOval(400,300,150,150); //원 그리기
            g.drawLine(220, 100, 350, 250); // 선 그리기
            g.drawArc(100,100,150,150,90,60); //곡선 그리기
        }
    }
} //GUI 클래스
