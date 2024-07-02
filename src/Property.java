import java.awt.*; //그래픽 색상
import java.awt.geom.*; //2D 그래픽처리
import java.awt.geom.Path2D; //awt.geom에서 1,2,3차 곡선으로 작성된 패스를 나타낼 수 있다. -> 자유곡선 때
import java.awt.geom.PathIterator; //Shape인터페이스를 구현하는 객체를 한 번에 1세그먼트 단위로 경계패스를 꺼냐는 호출이 가능해짐


//도형 속성 클래스 --------------------------------------------------------------------------------
public class Property {
    private final Shape shape;  //도형 객체 저장
    private final Color color; //도형 색상 저장
    private final float thickness; //도형 두께정보 저장


    //Property 클래스의 생성자 - 초기화
    public Property(Shape shape, Color color, float thickness) {
        this.shape = shape;
        this.color = color;
        this.thickness = thickness;

    }
    //도형 반환
    public Shape getShape() {
        return shape;
    }
    //색상 변환
    public Color getColor() {
        return color;
    }
    //두께 반환
    public float getThickness() {
        return thickness;
    }

    //저장할 때 사용하는 메소드 : 도형정보 파일용 문자열로 변환-----------------------------------------------------------------------------------------

    public String toFileString() {
        // 선일 때
        if (shape instanceof Line2D) { //참조변수가 참조하고 있는 인스턴스의 타입이라면 트루 아니면 펄스
            Line2D line = (Line2D) shape;

            return String.format("Line %.1f %.1f %.1f %.1f " +  // 앞에 4개는 라인을 그릴 때 필요한 좌표
                                "%d %d %d " + // 그다음 3개 색상 RGB
                                "%.1f\n", // 두께
                                line.getX1(), line.getY1(), line.getX2(), line.getY2(),
                                color.getRed(), color.getGreen(), color.getBlue(),
                                thickness);
        }
        // 원일 때
        else if (shape instanceof Ellipse2D) {
            Ellipse2D ellipse = (Ellipse2D) shape;

            return String.format("Circle %.1f %.1f " + //시작좌표 x,y
                                "%.1f %.1f" + // 넓이 높이
                                " %d %d %d " + //알지비
                                "%.1f\n", //두께
                                ellipse.getX(), ellipse.getY(),
                                ellipse.getWidth(), ellipse.getHeight(),
                                color.getRed(), color.getGreen(), color.getBlue(),
                                thickness);
        }
        // 사각형일 때
        else if (shape instanceof Rectangle2D) {
            Rectangle2D rectangle = (Rectangle2D) shape;

            return String.format("Rectangle %.1f %.1f " + //시작좌표 x,y
                                "%.1f %.1f " + // 넓이 높이
                                "%d %d %d " + //색상
                                "%.1f\n", //두께
                                rectangle.getX(), rectangle.getY(),
                                rectangle.getWidth(), rectangle.getHeight(),
                                color.getRed(), color.getGreen(), color.getBlue(),
                                thickness);
        }
        // 프리라인일 때
        else if (shape instanceof Path2D) {
            Path2D path = (Path2D) shape;
            //자유곡선 점들의 패스를 저장하기 위한 스트링빌더
            StringBuilder pathData = new StringBuilder("FreeLine");

            for (PathIterator pathIterator = path.getPathIterator(null); !pathIterator.isDone(); pathIterator.next()) {
                double[] coords = new double[6];
                int type = pathIterator.currentSegment(coords);
                pathData.append(" ").append(type).append(" ").append(coords[0]).append(" ").append(coords[1]);
            }

            return String.format("%s %d %d %d %.1f\n",
                    pathData, color.getRed(), color.getGreen(), color.getBlue(),
                    thickness);
        }
        return "";
    }

//로드할 때 쓰는 메소드 : 문자로 변환했던 도형정보 가져오기 ---------------------------------------------------------------------------------------------

    public static Property fromFileString(String fileString) {

        String[] parts = fileString.split(" "); //스페이스 기준으로 자르기

        String type = parts[0]; //어떤 도형인지 타입으로 받기
        double x1 = Double.parseDouble(parts[1]); //
        double y1 = Double.parseDouble(parts[2]);
        double x2 = Double.parseDouble(parts[3]); //넓이 = x좌표
        double y2 = Double.parseDouble(parts[4]); //높이 = y좌표
        int r = Integer.parseInt(parts[5]); //색
        int g = Integer.parseInt(parts[6]);
        int b = Integer.parseInt(parts[7]);
        float thickness = Float.parseFloat(parts[8]); //두께
        Color color = new Color(r, g, b); //알지비로 묶기

        Shape shape = null;

        switch (type) { //타입이 ~~면
            case "Line" -> shape = new Line2D.Double(x1, y1, x2, y2); //저장된 선 가지고오기
            case "Circle" -> shape = new Ellipse2D.Double(x1, y1, x2, y2); //저장된 원 가지고오기
            case "Rectangle" -> shape = new Rectangle2D.Double(x1, y1, x2, y2); //저장된 사각형 가지고오기
            case "FreeLine" -> { //끊기지 않도록

                shape = getPath2D(x1, y1, parts);
            }
        }
        return new Property(shape, color, thickness);
    }

    private static Path2D getPath2D(double x1, double y1, String[] parts) {
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
        return path;
    }
}
