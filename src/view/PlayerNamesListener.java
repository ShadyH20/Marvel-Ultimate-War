package view;

import java.io.IOException;

public interface PlayerNamesListener {
    void onPlayerNamesConfirmed(String p1Name, String p2Name) throws IOException;
}
