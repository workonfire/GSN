package pl.workonfire.gsn

import org.apache.commons.lang3.StringUtils
import org.fusesource.jansi.Ansi
import org.fusesource.jansi.Ansi.Color.*
import org.fusesource.jansi.AnsiConsole
import java.io.File
import java.io.IOException
import java.util.Random
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

object GSN {
    private const val version = "1.0.2"
    private const val author = "workonfire"

    private fun String.colorPrint(bright: Boolean, color: Ansi.Color?) {
        println(
            if (bright)
                Ansi.ansi().fgBright(color).a(this).reset()
            else
                Ansi.ansi().fg(color).a(this).reset()
        )
    }

    fun run() {
        val howManyTimes: Int
        val maxLength: Int
        val saveToFile: Boolean
        val appendNumber: Boolean
        val stripPolishChars: Boolean
        val adjectives: List<String>
        val nouns: List<String>

        AnsiConsole.systemInstall()

        "         (        )  ".colorPrint(false, WHITE)
        " (       )\\ )  ( /(  ".colorPrint(true, YELLOW)
        " )\\ )   (()/(  )\\()) ".colorPrint(false, YELLOW)
        "(()/(    /(_))((_)\\  ".colorPrint(true, RED)
        " /(_))_ (_))   _((_) ".colorPrint(false, RED)
        "(_)) __|/ __| | \\| | ".colorPrint(false, RED)
        "  | (_ |\\__ \\ | .` | ".colorPrint(false, RED)
        "   \\___||___/ |_|\\_| \n".colorPrint(false, RED)

        println("Generator Spierdolonych Nicków v$version")
        "by $author\n".colorPrint(true, YELLOW)

        while (true) {
            print("Ilość nicków do wygenerowania: ")
            howManyTimes = readLine()?.toInt() ?: 0
            if (howManyTimes == 69) {
                "▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄".colorPrint(true, RED)
                "██ ███ █▄▄ ██▄██ ██ █ ▄▀▄ ██".colorPrint(true, YELLOW)
                "██ █ █ █▀▄███ ▄█ ██ █ █▄█ ██".colorPrint(true, GREEN)
                "██▄▀▄▀▄█▄▄▄█▄▄▄██▄▄▄█▄███▄██".colorPrint(true, BLUE)
                "▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀".colorPrint(true, MAGENTA)
            }
            print("Maksymalna ilość znaków: ")
            maxLength = readLine()?.toInt() ?: 0
            "UWAGA: Jeśli jakieś nicki będą przekraczały ustalony limit znaków, zostaną one przycięte."
                .colorPrint(false, RED)
            print("Zapisać wynik do pliku? (y/n) ")
            saveToFile = readLine()?.toLowerCase() == "y"
            print("Doklejać losową liczbę? (y/n) ")
            appendNumber = readLine()?.toLowerCase() == "y"
            print("Usunąć polskie znaki? (y/n) ")
            stripPolishChars = readLine()?.toLowerCase() == "y"
            break
        }
        try {
            adjectives = File("dictionaries/adjectives.txt").readLines(charset = Charsets.UTF_8)
            nouns = File("dictionaries/nouns.txt").readLines(charset = Charsets.UTF_8)
        } catch (exception: IOException) {
            "Wystąpił problem podczas próby otworzenia słowników.".colorPrint(false, RED)
            exitProcess(1)
        }
        val generatedNicknames: MutableList<String> = ArrayList()
        println("Generowanie nicków...\n")
        for (i in 1 until howManyTimes) {
            val randomAdjectiveIndex = Random().nextInt(adjectives.size)
            val adjective = adjectives[randomAdjectiveIndex]
            val randomNounIndex = Random().nextInt(nouns.size)
            var noun = nouns[randomNounIndex]
            noun = if (Random().nextBoolean())
                "_$noun"
            else
                noun.substring(0, 1).toUpperCase() + noun.substring(1)
            var generatedNick = adjective + noun
            if (appendNumber) generatedNick += Random().nextInt(100) + 1
            if (generatedNick.length > maxLength) generatedNick = generatedNick.substring(0, maxLength)
            if (stripPolishChars) generatedNick = StringUtils.stripAccents(generatedNick)
            generatedNicknames.add(generatedNick.replace("\\s+".toRegex(), ""))
        }
        for (nick in generatedNicknames) ">> $nick".colorPrint(true, CYAN)
        "\nGotowe.".colorPrint(false, GREEN)
        if (saveToFile) {
            try {
                File("output.txt").printWriter().use { out ->
                    generatedNicknames.forEach {
                        out.println(it)
                    }
                }
                "Zapisano nicki do pliku.".colorPrint(false, GREEN)
            } catch (exception: IOException) {
                "Wystąpił problem podczas zapisywania pliku.".colorPrint(false, RED)
            }
        }
        println("By zakończyć pracę programu, naciśnij Enter.")
        readLine()
        AnsiConsole.systemUninstall()
    }
}

fun main() {
    GSN.run()
}