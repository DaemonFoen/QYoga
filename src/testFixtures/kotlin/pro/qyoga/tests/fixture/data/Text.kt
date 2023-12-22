package pro.qyoga.tests.fixture.data

import kotlin.random.Random

val lowerCaseCyrillicLetters = ('а'..'я').toList()
val upperCaseCyrillicLetters = ('А'..'Я').toList()
val cyrillicLetters = lowerCaseCyrillicLetters + upperCaseCyrillicLetters

val lowerCaseLatinLetters = ('a'..'z').toList()

val imageExtensions = listOf("png", "jpeg", "gif")

fun randomWord(letters: List<Char>, minLength: Int = 1, maxLength: Int = 12) =
    buildString {
        val length = Random.nextInt(minLength, maxLength)
        repeat(length) {
            append(letters[Random.nextInt(letters.size)])
        }
        check(this.length in minLength..maxLength)
    }

fun randomCyrillicWord(minLength: Int = 1, maxLength: Int = 12) =
    randomWord(cyrillicLetters, minLength, maxLength)

fun randomLatinWord(minLength: Int = 1, maxLength: Int = 12) =
    randomWord(lowerCaseLatinLetters, minLength, maxLength)

fun randomSentence(minWords: Int = 3, maxWords: Int = 20): String {
    val length = Random.nextInt(minWords, maxWords)
    val sentence = (1..length).joinToString(". ") {
        randomCyrillicWord()
    }
    check(sentence.isNotEmpty())
    return sentence
}

fun randomEmail(): String =
    buildString {
        append(randomWord(lowerCaseLatinLetters, 4, 8))
        append("@")
        append(randomWord(lowerCaseLatinLetters, 4, 8))
        append(".")
        append(randomWord(lowerCaseLatinLetters, 2, 3))
    }


fun randomFileName(extension: () -> String = { randomLatinWord(1, 3) }) =
    randomLatinWord(3, 9) + "." + extension()

fun randomPassword() = randomLatinWord(minLength = 8)