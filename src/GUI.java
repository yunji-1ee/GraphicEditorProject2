import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

import javax.swing.event.*;
import javax.swing.JButton;

/*
<그림판 만들기>
패널 위에 그림그리기

[패널의 두 가지 기능]
1.컴포넌트의 복잡한 배치를 도와준다
2.그림을 그릴 수 있다 --> JPanel을 상속받아야 함

Graphics -> 추상클래스 : 그림그리는데 필요한 여러가지 메소드를 정의

1> 화면 띄우기 v
1-1> 화면에 버튼패널, 캔버스패널 만들고 프레임에 추가하기
2> 버튼패널 안에 버튼 넣기
*/

public class GUI extends JFrame {
    JFrame frame = new JFrame("그림을 그려보아요");
    JPanel canvas_panel = new JPanel();
    JPanel button_panel = new JPanel();
  //  JButton button = new JButton();
    //GraphicDraw draw = new GraphicDraw(); // 연습
    // DrawLine line = new DrawLine();


    public static void main(String[] args) { //메인함수
        new GUI();
    }

    public GUI() { //초기화
        //프레임세팅

        frame.setLayout(null); //좌표값 직접 정하기
        frame.setLocationRelativeTo(null); // 프레임 정중앙에 위치
        frame.setSize(900, 600);
        frame.setResizable(false);//화면크기 조절은? -> 나중에 보고 더 나은 방향으로 설정
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //프레임에 패널 넣기

        //캔버스
        frame.add(canvas_panel);
        canvas_panel.setLayout(null);
        canvas_panel.setBounds(180, 0, 750, 600);
        canvas_panel.setBackground(Color.WHITE);

        // 버튼
        frame.add(button_panel);
        button_panel.setLayout(null);
        button_panel.setBounds(0, 0, 250, 600);
        button_panel.setBackground(new Color(230, 230, 220));

        String[] button_name = { // 버튼이름
                "[Draw]", "• line     ", "• polyLone ", "• circle   ", "• rectangle",
                "[Property]", "• color    ", "• thickness ", "• style    ", "[More]", "1", "2", "3"}; // 버튼이 너무 많아서 panel과 배열로 만들기

        button_panel.setLayout(new GridLayout(15, 1, -50, 0)); // 행과 열, 버튼사이 간격 정하기



        JButton[] bt = new JButton[button_name.length]; // 버튼의 길이 -> 반복문을 통해 이름을 입력하기 위함

        for (int i = 0; i < bt.length; i++) {
            bt[i] = new JButton(button_name[i]);
            button_panel.add(bt[i]); // 다음 내용들을 bt_panel에 추가하기
            bt[i].setSize(-100,10);
            bt[i].setFont(new Font("Arial", Font.PLAIN, 13));

            bt[i].setBorderPainted(false); // 버튼 테두리 없애기
            bt[i].setOpaque(false); // 맥에서 색이 안먹는 것 해결
        }
        bt[0].setFont(new Font("Arial", Font.BOLD, 20));
        bt[5].setFont(new Font("Arial", Font.BOLD, 20));
        bt[9].setFont(new Font("Arial", Font.BOLD, 20));
/*
            button_name[i].setFont(new Font("Arial", Font.PLAIN, 17));

            if (((i >= 4) && (i <= 6)) || ((i >= 8) && (i <= 10)) || ((i >= 12) && (i <= 14)) || (i == 16)|| (i == 17))
                bt[i].addActionListener(new Number()); // 숫자버튼을 누르면
            else if(i==18){
                bt[i].addActionListener(new equal());
            }
            else
                bt[i].addActionListener(new Operator());
        }
        */

       // 화면에 띄우기



    } // GUI class 닫는 괄호
}

        /*
//패널에 그림두기
    class GraphicDraw {
        public void paint(Graphics g) {//그래픽의 객체 g를 이용하여 그림그리는 메소드 호출, 그림그리기


            g.drawLine(100, 100, 200, 200);
            g.drawRect(100, 100, 200, 200); //사각형 그리기
            g.drawOval(100, 100, 200, 200); //원 그리기
            g.drawString("텍스트", 100, 100);
            g.drawArc(100, 100, 200, 200, 180, 180); //곡선 그리기
        }
    }
    */

//마우스로 그림그리기
  //  class DrawLine extends JFrame{}