/*
 * File: JavaKeywordAsyncDemo.java
 * Names: Baron Wang, Caleb Bitting, Ian Ellmer
 * Class: CS 361
 * Project 4
 * Date: February 28
 */

package proj04BittingEllmerWang;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.Subscription;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * The JavaKeywordAsyncDemo (JKAD) class
 * from the Git repois made into a helper
 * class to effectuate syntax highlighting.
 *
 * In Main.java, a JKAD call serves as a "colorizer" that
 * will enable syntax coloring of the CodeArea instance
 * passed into the constructor.
 *
 * @author Baron Wang
 */
public class JavaKeywordsAsyncDemo extends Application {

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
            "transient", "try", "var", "void", "volatile", "while" // "var" keyword added to the list
    };
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/"   // for whole text processing (text blocks)
            + "|" + "/\\*[^\\v]*" + "|" + "^\\h*\\*([^\\v]*|/)";  // for visible paragraph processing (line by line)
    // new INTEGER_PATTERN that looks around for decimal point.
    // Inspired from Wiktor Stribiżew's answer on StackOverflow
    private static final String INTEGER_PATTERN = "\\b(?<!\\.)([1-9]\\d*|0)(?!\\.)\\b";

    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
                    + "|(?<INTEGER>" + INTEGER_PATTERN + ")"
    );

    private CodeArea codeArea;
    private ExecutorService executor;

    /**
     * Constructor for JavaKeywordAsyncDemo.
     *
     * The most important thing it does is make
     * a Subscription and invoke
     * computeHighlightingAsync method
     * on the code area. It enables syntax
     * coloring of the code area.
     *
     * @param codeArea code area to be colored
     */
    public JavaKeywordsAsyncDemo(CodeArea codeArea){
        this.codeArea = codeArea;

        executor = Executors.newSingleThreadExecutor();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        Subscription cleanupWhenDone = codeArea.multiPlainChanges()
                .successionEnds(Duration.ofMillis(500))
                .retainLatestUntilLater(executor)
                .supplyTask(this::computeHighlightingAsync)
                .awaitLatest(codeArea.multiPlainChanges())
                .filterMap(t -> {
                    if (t.isSuccess()) {
                        return Optional.of(t.get());
                    } else {
                        t.getFailure().printStackTrace();
                        return Optional.empty();
                    }
                })
                .subscribe(this::applyHighlighting);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while (matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("PAREN") != null ? "paren" :
                                    matcher.group("BRACE") != null ? "brace" :
                                            matcher.group("BRACKET") != null ? "bracket" :
                                                    matcher.group("STRING") != null ? "string" :
                                                            matcher.group("COMMENT") != null ? "comment" :
                                                                matcher.group("INTEGER") != null ? "integer" :
                                                                        null; /* never happens */
            assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    // Empty start method needs to stay because otherwise the class won't work
    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    @Override
    public void stop() {
        executor.shutdown();
    }

    private Task<StyleSpans<Collection<String>>> computeHighlightingAsync() {
        String text = codeArea.getText();
        Task<StyleSpans<Collection<String>>> task = new Task<StyleSpans<Collection<String>>>() {
            @Override
            protected StyleSpans<Collection<String>> call() throws Exception {
                return computeHighlighting(text);
            }
        };
        executor.execute(task);
        return task;
    }

    private void applyHighlighting(StyleSpans<Collection<String>> highlighting) {
        codeArea.setStyleSpans(0, highlighting);
    }
}