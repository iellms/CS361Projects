package proj04BittingEllmerWang;

import org.fxmisc.richtext.CodeArea;

public class DirtyableCodeArea extends CodeArea {

    private final int contentHash;

    public DirtyableCodeArea() {
        super();
        this.contentHash = 0;
    }

    public DirtyableCodeArea(String s) {
        super();
        this.appendText(s);
        this.contentHash = s.hashCode();
    }

    public boolean isDirty() {
        String currentText = this.getText();
        int currentHash = currentText.hashCode();

        return currentHash == this.contentHash;
    }


}
