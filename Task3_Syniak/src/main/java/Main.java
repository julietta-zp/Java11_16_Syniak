import xyz.hedo.xmlAnalyzer.controller.Controller;

public class Main {

    public static void main(String... args){

        Controller controller = new Controller();
        controller.readFile("notes.xml");
    }
}
