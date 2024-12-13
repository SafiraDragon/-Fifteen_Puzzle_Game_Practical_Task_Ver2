package com.jetbrains.fifteen_puzzle_game_practical_task

fun main() {
    // Ініціалізація гри
    val empty: Byte = 16
    val finalState = ByteArray(empty.toInt()) { (it + 1).toByte() }
    val state: ByteArray = finalState.clone().also { it.shuffle() }

    // Перевірка інверсій для забезпечення розв'язності
    var inversion = 0
    val last = state.size - 1
    for (i in 0 until last) {
        for (j in i + 1 until state.size) {
            if (state[i] > state[j] && state[i] != empty && state[j] != empty) inversion++
        }
    }
    println("Inversion: $inversion")
    if (inversion % 2 == 1) {
        println("First state: ${state.joinToString(separator = " ")}")
        // Поміняємо два останні елементи місцями
        state[last] = state[last - 1].also { state[last - 1] = state[last] }
        println("Updated state: ${state.joinToString(separator = " ")}")
    }

    // Головний цикл гри
    while (!state.contentEquals(finalState)) {
        // Вивід стану гри
        printState(state, empty)

        // Читання та перевірка відповіді
        print("Enter the cell to move or 'q' to exit: ")
        val response = readln()
        if (response.lowercase() == "q") break
        val cell = response.toByteOrNull() ?: -1
        if (cell !in 1..15) {
            println("Invalid input: '$response'. Try again.")
            continue
        }

        // Знаходимо індекс клітинки
        val ixBase = state.indexOf(cell)
        if (ixBase == -1) {
            println("Cell '$cell' not found on the board.")
            continue
        }

        val iRowBase = ixBase / 4
        val iColBase = ixBase % 4
        var isMove = false

        // Оновлення стану
        for (i in listOf(-1, 1)) {
            // Перевірка по колонках
            val iCol = iColBase + i
            var ix = iRowBase * 4 + iCol
            if (iCol in 0..3 && state[ix] == empty) {
                state[ixBase] = state[ix].also { state[ix] = state[ixBase] }
                isMove = true
                break
            }
            // Перевірка по рядках
            val iRow = iRowBase + i
            ix = iRow * 4 + iColBase
            if (iRow in 0..3 && state[ix] == empty) {
                state[ixBase] = state[ix].also { state[ix] = state[ixBase] }
                isMove = true
                break
            }
        }
        if (!isMove) println("This number '$cell' cannot be moved.")
    }

    // Перевірка перемоги
    if (state.contentEquals(finalState)) {
        println("Congratulations, you win!")
        printState(state, empty)
    } else {
        println("Goodbye!")
    }
}

// Функція для виводу стану гри
fun printState(state: ByteArray, empty: Byte) {
    println("-".repeat(19))
    for (iRow in 0..3) {
        print("|")
        for (iCol in 0..3) {
            val ix = iRow * 4 + iCol
            val cell = if (state[ix] == empty) "" else state[ix].toString()
            print("%4s".format(cell))
        }
        println(" |")
    }
    println("-".repeat(19))
}
