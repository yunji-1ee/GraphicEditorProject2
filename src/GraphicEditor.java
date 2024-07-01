import java.awt.*; //그래픽관련 클래스
import java.awt.event.*; //이벤트 처리
import javax.swing.*; //스윙 GUI
import java.util.ArrayList; //어레이리스트 사용

//메인 클래스 & UI 구성 -프로그램 정의
public class GraphicEditor {
    public boolean LineMode = false; //선 그리기
    public boolean CircleMode = false; //원 그리기
    public boolean RectangleMode = false; //사각형 그리기
    public boolean PatternMode = false; //도장찍기
    public boolean FreeLineMode = false; //자유곡선
    public boolean EraserMode = false; //지우개
    public boolean StyleMode = false; // 일단 보류했음
    public float currentThickness = 2.0F; // 굵기 실수형으로 조절
    public Color currentColor = Color.BLACK; // 기본 색상 검정으로 설정

    public ArrayList<Property> box = new ArrayList<>(); // 도형 저장하기 (그릴 때마다 초기화되지 않도록)
    public ArrayList<Point> freeLinePoints = new ArrayList<>(); // 프리라인을 만들기 위한 점들 저장할 곳

    public CanvasPanel canvasPanel; // CanvasPanel 인스턴스를 멤버 변수로 추가


    //UI설정 초기화
    public void UI() {
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
        canvasPanel = new CanvasPanel(this); // 그림 그릴용 캔버스 패널 생성
        canvasPanel.setBounds(250, 0, 950, 800); // 캔버스 패널 좌표 설정
        canvasPanel.setBackground(Color.WHITE); // 흰색 도화지로
        frame.add(canvasPanel); // 프레임에 캔버스 패널 추가하기

        frame.setVisible(true); // 프레임 보이게 설정 = 창 생성

        //---------------버튼세팅-----------------------------------------------------------------------

        // 버튼들 이름 배열에 정해놓기
        String[] buttonNames = {
                "[Draw]", "FreeLine : ( \uD83D\uDD8A )", "Line : ( ╱ )", "Circle : ( 〇 )", "Rectangle : ( ⎕ )", // 5
                "[Property]", "Color : ( 🌈 )", "Thickness : (⠐•● )", "Style", // 4
                "[More]", "Pattern", "Eraser ✖", "✖ Clear ✖", "LOAD", "SAVE" // 5
        };

        JButton[] buttons = new JButton[buttonNames.length]; // 버튼 이름 길이만큼 버튼 생성

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(buttonNames[i]); // 배열에 저장해놓은 인덱스 순으로 버튼에 이름 넣기

            buttons[i].setFont(new Font("Serif", Font.PLAIN, 16)); // 버튼 폰트 설정
            buttons[i].setBorderPainted(false); // 버튼 테두리 없애기
            buttonPanel.add(buttons[i]); // 버튼 패널에 버튼 추가하기

            //-------------버튼으로 액션받기--------------------------------------------------------------------------
            //버튼이 클릭되면 어떻게 할건지
            buttons[i].addActionListener(new ActionListener() { //버튼 클릭되면 실행
                @Override
                public void actionPerformed(ActionEvent e) { // 버튼 텍스트 받아와서 케이스별로 모드 수행
                    String mode = e.getActionCommand(); // 버튼의 이름 mode 변수에 넣기

                    // 선 그리기
                    switch (mode) {
                        case "FreeLine : ( \uD83D\uDD8A )" -> {
                            LineMode = false;
                            CircleMode = false;
                            RectangleMode = false;
                            PatternMode = false;
                            FreeLineMode = true;
                            EraserMode = false;
                            StyleMode = false;
                        }
                        case "Line : ( ╱ )" -> {
                            LineMode = true;
                            CircleMode = false;
                            RectangleMode = false;
                            PatternMode = false;
                            FreeLineMode = false;
                            EraserMode = false;
                            StyleMode = false;
                        }
                        // 원 그리기
                        case "Circle : ( 〇 )" -> {
                            LineMode = false;
                            CircleMode = true;
                            RectangleMode = false;
                            PatternMode = false;
                            FreeLineMode = false;
                            EraserMode = false;
                            StyleMode = false;
                        }
                        // 사각형 그리기
                        case "Rectangle : ( ⎕ )" -> {
                            LineMode = false;
                            CircleMode = false;
                            RectangleMode = true;
                            PatternMode = false;
                            FreeLineMode = false;
                            EraserMode = false;
                            StyleMode = false;
                        }
                        // 패턴 모드
                        case "Pattern" -> {
                            LineMode = false;
                            CircleMode = false;
                            RectangleMode = false;
                            PatternMode = true;
                            FreeLineMode = false;
                            EraserMode = false;
                            StyleMode = false;
                        }
                        // 모두 지우기
                        case "✖ Clear ✖" -> {
                            LineMode = false;
                            CircleMode = false;
                            RectangleMode = false;
                            PatternMode = false;
                            FreeLineMode = false;
                            EraserMode = false;
                            StyleMode = false;

                            box.clear(); // 저장한 도형들 클리어
                            canvasPanel.repaint(); //화면 새로고침
                        }
                        // 색상 설정하기
                        case "Color : ( 🌈 )" -> {
                            LineMode = false;
                            CircleMode = false;
                            RectangleMode = false;
                            PatternMode = false;
                            FreeLineMode = false;
                            EraserMode = false;
                            StyleMode = false;
                            // 컬러츄저다이얼로그를 띄우고 / 현재 색상을 색상창에서 받아온 값으로 설정
                            currentColor = JColorChooser.showDialog(null, "Choose the Color", currentColor);
                        }
                        case "Thickness : (⠐•● )" -> {
                            LineMode = false;
                            CircleMode = false;
                            RectangleMode = false;
                            PatternMode = false;
                            FreeLineMode = false;
                            EraserMode = false;
                            StyleMode = false;
                            // 두께를 제이옵션으로 입력받아서 설정
                            String input = JOptionPane.showInputDialog("Enter line thickness");
                            currentThickness = Float.parseFloat(input);
                        }
                        case "Eraser ✖" -> {
                            LineMode = false;
                            CircleMode = false;
                            RectangleMode = false;
                            PatternMode = false;
                            FreeLineMode = false;
                            EraserMode = true;
                            StyleMode = false;
                        }
                        case "Style" -> {
                        }
                        case "SAVE" -> FileManager.saveShapes(box);
                        case "LOAD" -> {
                            box = FileManager.loadShapes();
                            canvasPanel.repaint();
                        }
                        default -> {
                            LineMode = false;
                            CircleMode = false;
                            RectangleMode = false;
                            PatternMode = false;
                            FreeLineMode = false;
                            EraserMode = false;
                            StyleMode = false;
                        }
                    } // 스위치 조건문
                }  // 이벤트 퍼폼
            }); // 액션리스너
        } // 버튼 설정 for문 끝

        // 강조할 버튼의 폰트 조정
        buttons[0].setFont(new Font("Serif", Font.BOLD, 23));
        buttons[5].setFont(new Font("Serif", Font.BOLD, 23));
        buttons[9].setFont(new Font("Serif", Font.BOLD, 23));
        buttons[13].setFont(new Font("Serif", Font.BOLD, 20));
        buttons[14].setFont(new Font("Serif", Font.BOLD, 20));
    }
}
