import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

 class Memo extends JFrame {
    JTextArea ta; //텍스트필드 -> 캔버스패널
    File f;

    public Memo(String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenu file = new JMenu("파일");

        JMenuItem mOpen = new JMenuItem("열기");
        JMenuItem mNew = new JMenuItem("새파일");
        JMenuItem mSave = new JMenuItem("저장");
        JMenuItem mSaveAs = new JMenuItem("다른이름으로 저장");
        JMenuItem mExit = new JMenuItem("끝내기");

        file.add(mOpen);
        file.add(mNew);
        file.add(mSave);
        file.add(mSaveAs);
        file.addSeparator();
        file.add(mExit);

        JMenuBar mb = new JMenuBar();
        mb.add(file);
        setJMenuBar(mb);

        ta = new JTextArea();
//JScrollPane jsp=new JScrollPane(ta);
        JScrollPane jsp = new JScrollPane();
        jsp.setViewportView(ta);
//add(ta);

        add(jsp);

//새파일
        mNew.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ta.setText("");
                setTitle("제목없음");
            }
        });

//열기 (선택된 파일을 읽어들이기. fileread)
        mOpen.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                if (fc.showOpenDialog(Memo.this) == JFileChooser.CANCEL_OPTION)
                    return; //자신을 불러주는 부모창을 인수로함. Memo자기자신.
                f = fc.getSelectedFile();//선택된 파일
                setTitle(f.getName());//선택된 파일의 이름 가져오기
                fileRead(f);//취소를 눌렀을경우 선택된 파일이 없기때문에 파일 읽어들이는 fileRead오류남
            }
        });

//저장 (기존파일 없음=제목없음이면 새 창을 띄우기)
        mSave.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (getTitle().equals("제목없음")) {
                    JFileChooser fc = new JFileChooser();
                    if (fc.showSaveDialog(Memo.this) == JFileChooser.CANCEL_OPTION)
                        return;
                    f = fc.getSelectedFile();
                    fileSave(f);
                    setTitle(f.getName());
                    mSaveAs.doClick();//mSaveAs가 다시 실행되는 것과 같음.
                } else {//기존파일 있음
                    fileSave(f);
                }
            }
        });

//새이름 저장
        mSaveAs.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                if (fc.showSaveDialog(Memo.this) == JFileChooser.CANCEL_OPTION)
                    return;
                f = fc.getSelectedFile();
                        System.out.println("파일 저장: "+f);
                fileSave(f);
                setTitle(f.getName());
            }
        });

//끝내기
        mExit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setSize(500, 400);
        setVisible(true);

    }

    public static void main(String[] args) {
        new Memo("제목없음");
    }

    //파일 읽기 메소드
    private void fileRead(File f) {
        try {
            FileReader fr = new FileReader(f);
            StringWriter sw = new StringWriter();
            while (true) {
                int ch = fr.read();
                if (ch == -1) break;
                sw.write(ch);
            }
            ta.setText(sw.toString());
            sw.close();
            fr.close();
        } catch (FileNotFoundException n) {
            n.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //파일 저장 메소드
    private void fileSave(File f) {
        try {
            PrintStream ps = new PrintStream(f);
            ps.println(ta.getText());
            ps.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

