from colorama import init, deinit, Fore
from random import choice, randint
from unidecode import unidecode
import platform
import os

__VERSION__ = '1.0.2'
__AUTHOR__ = 'workonfire'


def color_print(color, text):
    init(autoreset=True)
    print(color + text)
    deinit()


def main():
    if platform.system() == 'Windows':
        os.system('title GSN')

    color_print(Fore.WHITE, "         (        )  ")
    color_print(Fore.LIGHTYELLOW_EX, " (       )\\ )  ( /(  ")
    color_print(Fore.YELLOW, " )\\ )   (()/(  )\\()) ")
    color_print(Fore.LIGHTRED_EX, "(()/(    /(_))((_)\\  ")
    color_print(Fore.RED, " /(_))_ (_))   _((_) ")
    color_print(Fore.RED, "(_)) __|/ __| | \\| | ")
    color_print(Fore.RED, "  | (_ |\\__ \\ | .` | ")
    color_print(Fore.RED, "   \\___||___/ |_|\\_| ")
    print(f"\nGenerator Spierdolonych Nicków v{__VERSION__}")
    color_print(Fore.LIGHTYELLOW_EX, f"by {__AUTHOR__}\n")

    adjectives_path = "dictionaries/adjectives.txt"
    nouns_path = "dictionaries/nouns.txt"

    if platform.system() == 'Linux' and os.path.isdir('/opt/gsn'):
        adjectives_path = "/opt/gsn/dictionaries/adjectives.txt"
        nouns_path = "/opt/gsn/dictionaries/nouns.txt"

    output_file = os.path.dirname(os.path.abspath(__file__))
    output_file += '\\output.txt' if platform.system() == 'Windows' else '/output.txt'

    while True:
        try:
            how_many_times = int(input("Ilość nicków do wygenerowania: "))

            if how_many_times == 69:
                color_print(Fore.LIGHTRED_EX, "▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄")
                color_print(Fore.LIGHTYELLOW_EX, "██ ███ █▄▄ ██▄██ ██ █ ▄▀▄ ██")
                color_print(Fore.LIGHTGREEN_EX, "██ █ █ █▀▄███ ▄█ ██ █ █▄█ ██")
                color_print(Fore.LIGHTBLUE_EX, "██▄▀▄▀▄█▄▄▄█▄▄▄██▄▄▄█▄███▄██")
                color_print(Fore.LIGHTMAGENTA_EX, "▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀")

            max_length = int(input("Maksymalna ilość znaków: "))
            color_print(Fore.RED,
                        "UWAGA: Jeśli jakieś nicki będą przekraczały ustalony limit znaków, zostaną one przycięte.")
            save_to_file = input("Zapisać wynik do pliku? (y/n) ").lower().rstrip() == 'y'
            generate_number = input("Doklejać losową liczbę? (y/n) ").lower().rstrip() == 'y'
            strip_polish_chars = input("Usunąć polskie znaki? (y/n) ").lower().rstrip() == 'y'
            break
        except ValueError:
            color_print(Fore.RED, "Podaj poprawne wartości.")

    with open(adjectives_path, encoding='utf-8') as adjectives_file:
        adjectives = [line.rstrip() for line in adjectives_file.readlines()]
    with open(nouns_path, encoding='utf-8') as nouns_file:
        nouns = [line.rstrip() for line in nouns_file.readlines()]

    generated_nicknames = []
    print("Generowanie nicków...\n")

    for i in range(how_many_times):
        adjective = choice(adjectives)
        noun = choice(nouns)
        noun = '_' + noun if choice([True, False]) else noun.title()
        generated_nick = adjective + noun
        if generate_number:
            generated_nick += str(randint(1, 100))
        if len(generated_nick) > max_length:
            generated_nick = generated_nick[:max_length]
        if strip_polish_chars:
            generated_nick = unidecode(generated_nick)
        generated_nicknames.append(generated_nick)

    for nick in generated_nicknames:
        color_print(Fore.LIGHTCYAN_EX, "> " + nick)

    color_print(Fore.LIGHTGREEN_EX, "\nGotowe.")

    if save_to_file:
        generated_nicknames = "\n".join(generated_nicknames)
        with open(output_file, 'a', encoding='utf-8') as log_file:
            log_file.write(generated_nicknames)
        color_print(Fore.LIGHTGREEN_EX, "Zapisano nicki do pliku.")

    if platform.system() == 'Windows':
        print("By zakończyć pracę programu, naciśnij dowolny klawisz.")
        os.system('pause >nul')


if __name__ == '__main__':
    try:
        main()
    except KeyboardInterrupt:
        pass
