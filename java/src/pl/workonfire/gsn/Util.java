package pl.workonfire.gsn;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import static org.fusesource.jansi.Ansi.Color;
import static org.fusesource.jansi.Ansi.ansi;

public abstract class Util {

    public static void colorPrint(boolean bright, Color color, String string) {
        if (bright) System.out.println(ansi().fgBright(color).a(string).reset());
        else System.out.println(ansi().fg(color).a(string).reset());
    }

    public static List<String> getLines(File file) throws IOException {
        return Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
    }
}
