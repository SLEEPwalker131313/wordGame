package tryanko.test.com.wordgame

enum class StringConstantEnum(val text: String) {
    DEFAULT_PLAYER_1_NAME_STRING_CONSTANT("Player 1"),
    DEFAULT_PLAYER_2_NAME_STRING_CONSTANT("Player 2"),
    FIELD_SIZE_STRING_CONSTANT("Размер поля: "),
    TIME_LIMIT_STRING_CONSTANT("Ограничение по времени: "),
    PICK_START_WORD_STRING_CONSTANT("Слово: "),
    START_WORD_STRING_CONSTANT("случайное"),
    START_GAME_STRING_CONSTANT("Начать игру"),
    UNCORRECT_WORD_LENGTH_STRING_CONSTANT("Некорректная длина стартового слова")
}