package org.spbstu.aleksandrov;

/*
Вариант 3 -- grep
Вывод на консоль указанного (в аргументе командной строки) текстового файла с фильтрацией:
    word задаёт слово для поиска (на консоль выводятся только содержащие его строки)
    -r (regex) вместо слова задаёт регулярное выражение для поиска
        (на консоль выводятся только строки, содержащие данное выражение)
    -v инвертирует условие фильтрации (выводится только то, что ему НЕ соответствует)
    -i игнорировать регистр слов
    Command Line: grep [-v] [-i] [-r] word inputname.txt
Кроме самой программы, следует написать автоматические тесты к ней.
*/

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;

public class GrepLauncher {

    @Option(name = "-v", metaVar = "InvertConditionFlag")
    private boolean invert;

    @Option(name = "-i", metaVar = "IgnoreRegisterFlag")
    private boolean register;

    @Option(name = "-r", metaVar = "RegexFlag")
    private boolean regex;

    @Argument(required = true, metaVar = "CombinationToSearch")
    private String word;

    @Argument(metaVar = "InputFile", index = 1)
    private File inputFile;

    public static void main(String[] args) {
        new GrepLauncher().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.print(e.getMessage());
            return;
        }

        if (inputFile == null) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String line = br.readLine();
                while (line != null && !line.equals("")) {
                    if (new Grep(line).grep(invert, register, regex, word)) System.out.println(line);
                    line = br.readLine();
                }
            } catch (IOException e) {
                System.err.print(e.getMessage());
            }
        }

        if (inputFile != null) {
            try (FileReader fr = new FileReader(inputFile)) {
                BufferedReader br = new BufferedReader(fr);
                String line = br.readLine();
                while (line != null) {
                    if (new Grep(line).grep(invert, register, regex, word)) System.out.println(line);
                    line = br.readLine();
                }
            } catch (IOException e) {
                System.err.print(e.getMessage());
            }
        }
    }

}
