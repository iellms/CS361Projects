/*
 * File: proj04BittingEllmerWang.Main.java
 * Names: Caleb Bitting, Ian Ellmer, Baron Wang
 * Class: CS361
 * Project 3
 * Date: 2/15/2022
 */

package proj04BittingEllmerWang;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import keywordDemo.JavaKeywordsDemo;
import org.fxmisc.richtext.CodeArea;


import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Main Class for loading the fxml file and building the stage
 *
 * @author (Caleb Bitting, Ian Ellmer, Baron Wang)
 */
public class Main extends Application {

    @FXML
    private CodeArea codeArea;


    /*
     * syntax highlighting code in progress
     */

    // keyword identification
    private static final String[] KEYWORDS = new String[]{
            "abstract", "assert", "boolean", "break", "byte",
            "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else",
            "enum", "extends", "final", "finally", "float",
            "for", "goto", "if", "implements", "import",
            "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super",
            "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "var", "void", "volatile", "while"
    };
    // digit identification
    private static final String[] DIGITS = new String[]{
            "0","1","2","3","4","5","6","7","8","9"
    };

    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String INTEGER_PATTERN = "\\b(" + String.join("|", DIGITS) + ")\\b";

    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<INTEGER>" + INTEGER_PATTERN + ")"
    );


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));




        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        Controller controller = fxmlLoader.getController();
        primaryStage.setTitle("Baron, Caleb, and Ian's Project 4");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                // exit method triggered when user tries to click on the red dot on top corner
                controller.handleExitMenuItem(event);
            }
        });
    }
}
