package org.spbstu.aleksandrov;

import org.junit.jupiter.api.Test;

import java.io.*;

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
    public void grep() throws FileNotFoundException {
        assertEquals("Come hither, Sleep," + newLine, main(new String[]{"hither", "src/test/resources/input.txt"}));
        assertEquals("But lo! the morning peeps" + newLine +
                        "Lo! to the vault" + newLine,
                main(new String[]{"-i", "lo", "src/test/resources/input.txt"}));
        assertEquals("Come hither, Sleep," + newLine +
                        "And my griefs unfold:" + newLine +
                        "Of paved heaven," + newLine +
                        "With sorrow fraught" + newLine +
                        "My notes are driven:" + newLine +
                        "And with tempests play." + newLine,
                main(new String[]{"-v", "-r", "-i", "\\btHe\\b", "src/test/resources/input.txt"}));
        assertEquals("Argument \"CombinationToSearch\" is required", main(new String[]{}));
        assertEquals("someFile.txt (Не удается найти указанный файл)",
                main(new String[]{"-i", "and", "someFile.txt"}));

        InputStream oldIn = System.in;
        System.setIn(new BufferedInputStream(new FileInputStream("src/test/resources/input.txt")));
        assertEquals("Come hither, Sleep," + newLine, main(new String[]{"-i", "-r", "\\bcomE\\b"}));
        System.setIn(oldIn);
    }
}