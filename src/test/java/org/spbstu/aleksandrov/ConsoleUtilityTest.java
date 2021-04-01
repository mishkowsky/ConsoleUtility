package org.spbstu.aleksandrov;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

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
    public void grep() throws FileNotFoundException, URISyntaxException {

        URL url = GrepLauncher.class.getResource("/input.txt");
        File file = new File(url.toURI());

        assertEquals("Come hither, Sleep," + newLine, main(new String[]{"hither", file.getPath()}));
        assertEquals("But lo! the morning peeps" + newLine +
                        "Lo! to the vault" + newLine,
                main(new String[]{"-i", "lo", file.getPath()}));
        assertEquals("Come hither, Sleep," + newLine +
                        "And my griefs unfold:" + newLine +
                        "Of paved heaven," + newLine +
                        "With sorrow fraught" + newLine +
                        "My notes are driven:" + newLine +
                        "And with tempests play." + newLine,
                main(new String[]{"-v", "-r", "-i", "\\btHe\\b", file.getPath()}));
        assertEquals("Argument \"CombinationToSearch\" is required", main(new String[]{}));
        assertEquals("someFile.txt (No such file or directory)",
                main(new String[]{"-i", "and", "someFile.txt"}));

        InputStream oldIn = System.in;
        System.setIn(new BufferedInputStream(new FileInputStream(file.getPath())));
        assertEquals("Come hither, Sleep," + newLine, main(new String[]{"-i", "-r", "\\bcomE\\b"}));
        System.setIn(oldIn);
    }
}