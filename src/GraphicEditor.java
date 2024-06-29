import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GraphicEditor {
    private boolean drawLineMode = false;

    public static void main(String[] args) {
        new GraphicEditor();
    }

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
                "[Draw]", "line", "polyLine", "circle", "rectangle",
                "[Property]", "color", "thickness", "style",
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
                    if (sourceButton.getText().equals("line")) {
                        drawLineMode = true; // 라인 모드 활성화
                    } else {
                        drawLineMode = false; // 다른 버튼 클릭 시 라인 모드 비활성화
                    }
                }
            });
        }
        buttons[0].setFont(new Font("Serif", Font.BOLD, 23));
        buttons[5].setFont(new Font("Serif", Font.BOLD, 23));
        buttons[9].setFont(new Font("Serif", Font.BOLD, 23));

        CanvasPanel canvasPanel = new CanvasPanel();
        canvasPanel.setBounds(250, 0, 950, 800);
        canvasPanel.setBackground(Color.WHITE);
        frame.add(canvasPanel);

        frame.setVisible(true);
    }

    private class CanvasPanel extends JPanel {
        private int startX, startY, endX, endY;
        private boolean drawing = false;

        public CanvasPanel() {
            setLayout(null);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (drawLineMode) {
                        startX = e.getX();
                        startY = e.getY();
                        drawing = true;
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (drawing) {
                        drawing = false;
                        repaint();
                    }
                }
            });

            addMouseMotionListener(new MouseAdapter() {
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

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (!drawing) {
                g.drawLine(startX, startY, endX, endY);
            }
        }
    }
}
