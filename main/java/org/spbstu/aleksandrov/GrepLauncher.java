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
import java.io.IOException;
import java.util.List;

public class GrepLauncher {

    @Argument(required = true, metaVar = "GrepRequest")
    private String grepRequest;

    @Option(name = "-v", metaVar = "InvertConditionFlag")
    private boolean invert;

    @Option(name = "-i", metaVar = "IgnoreRegisterFlag")
    private boolean register;

    @Option(name = "-r", metaVar = "RegexFlag")
    private boolean regex;

    @Argument(metaVar = "CombinationToSearch", index = 1)
    private String word;

    @Argument(required = true, metaVar = "InputName", index = 2)
    private String inputFileName;

    public static void main(String[] args){
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
        if (!grepRequest.equals("grep")) {
            System.err.print("No grep request was found");
            return;
        }
        Grep grep = new Grep(inputFileName);
        List<String> result;
        try {
            result = grep.grep(invert, register, regex, word);
        } catch (IOException e) {
            System.err.print(e.getMessage());
            return;
        }
        for (String line : result) {
            System.out.println(line);
        }
    }

}
