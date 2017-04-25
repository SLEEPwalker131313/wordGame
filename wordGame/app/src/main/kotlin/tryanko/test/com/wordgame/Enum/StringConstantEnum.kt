package tryanko.test.com.wordgame.Enum

enum class StringConstantEnum(val text: String) {
    DEFAULT_PLAYER_1_NAME_STRING_CONSTANT("Player 1"),
    DEFAULT_PLAYER_2_NAME_STRING_CONSTANT("Player 2"),
    FIELD_SIZE_STRING_CONSTANT("Размер поля: "),
    TIME_LIMIT_STRING_CONSTANT("Ограничение по времени: "),
    PICK_START_WORD_STRING_CONSTANT("Слово: "),
    START_WORD_STRING_CONSTANT("случайное"),
    START_GAME_STRING_CONSTANT("Начать игру"),
    UNCORRECT_WORD_LENGTH_STRING_CONSTANT("Некорректная длина стартового слова"),
    USE_NEW_SYMBOL_STRING_CONSTANT("Вы должны использовать в новом слове символ, добавленный на этом ходу"),
    WORD_NOT_FOUND_STRING_CONSTANT("Слово не найдено в словаре!"),
    WORD_WAS_USED_EARLIER_STRING_CONSTANT("Слово уже было использовано!"),
    PASS_STRING_CONSTANT("Пропустить ход"),
    SHOW_WORD_LIST_STRING_CONSTANT("Посмотреть список слов"),
    RULES_WORD_CONSTANT("Правила игры"),
    MAIN_RULES_TEXT_STRING_CONSTANT("" +
            "Игровое поле представляет" +
            " собой квадратную таблицу, ячейки/клетки " +
            "центральной строки которой содержат по одной букве, а " +
            "строка целиком — произвольное n-буквенное нарицательное" +
            " имя существительное в именительном падеже и единственном" +
            " числе (множественном числе, если слово не имеет " +
            "единственного числа). Размеры поля, расположение и " +
            "длина слова могут варьироваться, тем не менее количество" +
            " пустых клеток в начале игры должно быть чётным, чтобы " +
            "у обоих игроков было одинаковое количество ходов/слов." +
            "Во время своего хода игрок может добавить букву в клетку, примыкающую по " +
            "вертикали/горизонталик заполненной клетке таким образом, чтобы получалась неразрывная " +
            "и несамопересекающаяся прямоугольная ломаная («змейка») из клетки с добавленной " +
            "буквой и других заполненных клеток, представляющая собой слово " +
            "(соответствующее описанным выше требованиям), или пропустить ход."),
    RULES_TITLE_TEXT_STRING_CONSTANT("В течение игры должны соблюдаться также следующие правила: "),
    RULE_1_STRING_CONSTANT("Игроки ходят по очереди."),
    RULE_2_STRING_CONSTANT("Каждая клетка содержит только одну букву, каждая буква в составленном слове приносит игроку одно очко."),
    RULE_3_STRING_CONSTANT("Слово должно содержаться хотя бы в одном толковом или энциклопедическом словаре, при " +
            "этом запрещаются аббревиатуры, слова с уменьшительно-ласкательными (банька, дочурка, ножик)" +
            "и размерно-оценочными суффиксами (домина, зверюга, ножища), если такие слова не имеют " +
            "специального значения, а также слова, не входящие в состав русского литературного языка," +
            "то есть вульгаризмы, диалектизмы и жаргонизмы (имеющие словарные пометы вульг., диал.," +
            "обл., жарг. и аналогичные, например, сокращённые названия соответствующих регионов). "),
    RULE_4_STRING_CONSTANT("Слова в одной игре повторяться не могут, даже если это омонимы."),
    RULE_5_STRING_CONSTANT("Игра заканчивается тогда, когда либо заполнены все клетки, либо невозможно составить " +
            "очередное слово согласно указанным выше правилам. Выигрывает тот игрок, который наберёт " +
            "большее количество очков, кроме случая ничьи после троекратного пропуска хода обоими игроками."),
    CLOSE_STRING_CONSTANT("Закрыть"),
    SCORE_DEFAULT_STRING_CONSTANT("0")
}