package proj04BittingEllmerWang;

import javafx.beans.value.ChangeListener;

import java.util.HashMap;

public class DirtyTabTracker {

    private final HashMap<String, Integer> tabHashes;
    private final HashMap<String, Boolean> tabDirty;

    public DirtyTabTracker() {
        this.tabHashes = new HashMap<>();
        this.tabDirty = new HashMap<>();
    }

    public void addTab(String tabID, String tabContent) {
        tabHashes.put(tabID, tabContent.hashCode());
    }

    public boolean tabIsDirty(String tabID) {
        return this.tabDirty.get(tabID);
    }

    public ChangeListener<? extends String> dirtyDetector(String tabID) {
        return (ChangeListener<String>) (observable, oldValue, newValue) -> {
            // hash the new text
            int newHash = newValue.hashCode();
            // compare to beginning hash and update accordingly
            if (tabHashes.get(tabID).equals(newHash)) {
                tabDirty.put(tabID, false);
            } else {
                tabDirty.put(tabID, true);
            }

        };
    }
}
