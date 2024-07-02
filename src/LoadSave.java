import java.io.*;
import java.util.ArrayList;

public class LoadSave {


    public static void saveShapes(ArrayList<Property> box) {  //모양을 저장하는 메소드
        try (PrintWriter out = new PrintWriter(new FileWriter("shapes.txt"))) {
            //PrintWriter FileWriter 도형정보를 텍스트로 저장
            for (Property shape : box) {
                out.print(shape.toFileString()); //메소드 불러오기(Property에 있음) - 각 객체의 정보를 문자열로 저장하고 파일로 저장하기
            }
        } catch (IOException ex) { //트라이캐치문
            throw new RuntimeException(ex);
        }
    }
    public static ArrayList<Property> loadShapes() { //저장된 모양을 불러내는 메소드
        ArrayList<Property> box = new ArrayList<>(); //저장한 도형리스트 반환

        try (BufferedReader in = new BufferedReader(new FileReader("shapes.txt"))) { //FileReader로 파일정보 텍스트로 불러오기
            String line;
            while ((line = in.readLine()) != null) { //파일 한 줄씩 읽기
                box.add(Property.fromFileString(line));  //메소드 불러오기(Property에 있음) - 문자열을 프라펄티 객체로 바꾸고 도형저장 리스트에 추가하기
            }
        } catch (IOException ex) { //트라이 캐치
            throw new RuntimeException(ex);
        }
        return box; //저장한 도형 반환하기
    }
}
