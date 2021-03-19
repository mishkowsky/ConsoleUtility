package org.spbstu.aleksandrov;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleUtilityTest {

    String newLine = System.getProperty("line.separator");

    private String main(String[] args) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        PrintStream oldErr = System.err;
        System.setOut(new PrintStream(baos));
        System.setErr(new PrintStream(baos));

        GrepLauncher.main(args);

        System.out.flush();
        System.err.flush();
        System.setOut(oldOut);
        System.setErr(oldErr);
        return (baos.toString());
    }

    @Test
    public void grep() {
        assertEquals("Come hither, Sleep," + newLine, main(new String[]{"grep", "hither", "input\\input.txt"}));
        assertEquals("But lo! the morning peeps" + newLine +
                "Lo! to the vault" + newLine,
                main(new String[]{"grep", "-i", "lo", "input\\input.txt"}));
        assertEquals("Come hither, Sleep," + newLine +
                        "And my griefs unfold:" + newLine + newLine +
                        "Of paved heaven," + newLine +
                        "With sorrow fraught" + newLine +
                        "My notes are driven:" + newLine +
                        "And with tempests play." + newLine,
                main(new String[]{"grep", "-v", "-r", "-i", "\\bthe\\b", "input\\input.txt"}));
        assertEquals("Argument \"GrepRequest\" is required", main(new String[]{}));
        assertEquals("No grep request was found", main(new String[]{"notGrep", "word", "someFile.txt"}));
    }
}