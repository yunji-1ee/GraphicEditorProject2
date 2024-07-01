import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GraphicEditor editor = new GraphicEditor();
            editor.UI();
        });
    }
}
