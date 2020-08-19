package pl.workonfire.gsn;

import org.apache.commons.lang3.StringUtils;
import org.fusesource.jansi.AnsiConsole;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static org.fusesource.jansi.Ansi.Color.*;

public class Main {
    private static final String VERSION = "1.0.2";
    private static final String AUTHOR = "workonfire";

    public static void main(String[] args) {
        AnsiConsole.systemInstall();

        int howManyTimes;
        int maxLength;
        boolean saveToFile;
        boolean appendNumber;
        boolean stripPolishChars;

        List<String> adjectives = new ArrayList<>();
        List<String> nouns = new ArrayList<>();

        Util.colorPrint(false, WHITE, "         (        )  ");
        Util.colorPrint(true, YELLOW, " (       )\\ )  ( /(  ");
        Util.colorPrint(false, YELLOW, " )\\ )   (()/(  )\\()) ");
        Util.colorPrint(false, RED, "(()/(    /(_))((_)\\  ");
        Util.colorPrint(false, RED, " /(_))_ (_))   _((_) ");
        Util.colorPrint(false, RED, "(_)) __|/ __| | \\| | ");
        Util.colorPrint(false, RED, "  | (_ |\\__ \\ | .` | ");
        Util.colorPrint(false, RED, "   \\___||___/ |_|\\_| ");

        System.out.println("\nGenerator Spierdolonych Nicków v" + VERSION);
        Util.colorPrint(true, YELLOW, "by " + AUTHOR + "\n");

        while (true) {
            try {
                System.out.print("Ilość nicków do wygenerowania: ");
                howManyTimes = new Scanner(System.in).nextInt();

                System.out.print("Maksymalna ilość znaków: ");
                maxLength = new Scanner(System.in).nextInt();
                Util.colorPrint(false, RED, "UWAGA: Jeśli jakieś nicki będą przekraczały ustalony limit " +
                        "znaków, zostaną one przycięte.");

                System.out.print("Zapisać wynik do pliku? (y/n) ");
                saveToFile = new Scanner(System.in).next().equalsIgnoreCase("y");

                System.out.print("Doklejać losową liczbę? (y/n) ");
                appendNumber = new Scanner(System.in).next().equalsIgnoreCase("y");

                System.out.print("Usunąć polskie znaki? (y/n) ");
                stripPolishChars = new Scanner(System.in).next().equalsIgnoreCase("y");

                break;
            }
            catch (InputMismatchException exception) {
                Util.colorPrint(false, RED, "Podaj poprawne wartości.");
            }
        }

        try {
            adjectives = Util.getLines(new File("dictionaries/adjectives.txt"));
            nouns = Util.getLines(new File("dictionaries/nouns.txt"));
        }
        catch (IOException exception) {
            Util.colorPrint(false, RED, "Wystąpił problem podczas próby otworzenia słowników.");
            System.exit(1);
        }

        List<String> generatedNicknames = new ArrayList<>();
        System.out.println("Generowanie nicków...\n");

        for (int i = 1; i < howManyTimes; ++i) {
            int randomAdjectiveIndex = new Random().nextInt(adjectives.size());
            String adjective = adjectives.get(randomAdjectiveIndex);

            int randomNounIndex = new Random().nextInt(nouns.size());
            String noun = nouns.get(randomNounIndex);
            noun = new Random().nextBoolean() ? '_' + noun : noun.substring(0, 1).toUpperCase() + noun.substring(1);

            String generatedNick = adjective + noun;

            if (appendNumber) generatedNick += new Random().nextInt(100) + 1;
            if (generatedNick.length() > maxLength) generatedNick = generatedNick.substring(0, maxLength);
            if (stripPolishChars) generatedNick = StringUtils.stripAccents(generatedNick);

            generatedNicknames.add(generatedNick.replaceAll("\\s+", ""));
        }

        for (String nick : generatedNicknames) Util.colorPrint(true, CYAN, ">> " +  nick);

        Util.colorPrint(false, GREEN, "\nGotowe.");

        if (saveToFile) {
            File outputFile = new File("output.txt");
            try {
                outputFile.createNewFile();
                FileWriter fileWriter = new FileWriter(outputFile, true);
                fileWriter.write(String.join("\n", generatedNicknames));
                fileWriter.close();
                Util.colorPrint(false, GREEN, "Zapisano nicki do pliku.");
            }
            catch (IOException exception) {
                Util.colorPrint(false, RED, "Wystąpił problem podczas zapisywania pliku.");
            }
        }

        System.out.println("By zakończyć pracę programu, naciśnij Enter.");
        try {
            System.in.read();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }

        AnsiConsole.systemUninstall();
    }
}
