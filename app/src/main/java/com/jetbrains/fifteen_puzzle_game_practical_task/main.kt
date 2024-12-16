package com.jetbrains.fifteen_puzzle_game_practical_task

fun main() {
    val empty: Byte = 16
    val finalState = createFinalState(empty)
    val state = initializeGameState(finalState)

    // Головний цикл гри
    while (!state.contentEquals(finalState)) {
        // Вивід стану гри
        printState(state, empty)

        // Читання та перевірка відповіді
        print("Enter the cell to move or 'q' to exit: ")
        val response = readln()
        if (response.lowercase() == "q") break

        val cell = response.toByteOrNull()
        if (cell == null || !validateCell(state, cell)) {
            println("Invalid input: '$response'. Try again.")
            continue
        }

        if (!moveTile(state, cell, empty)) {
            println("This number '$cell' cannot be moved.")
        }
    }

    // Перевірка перемоги
    if (state.contentEquals(finalState)) {
        println("Congratulations, you win!")
        printState(state, empty)
    } else {
        println("Goodbye!")
    }
}

// Створення фінального стану
fun createFinalState(empty: Byte): ByteArray {
    return ByteArray(empty.toInt()) { (it + 1).toByte() }
}

// Ініціалізація гри
fun initializeGameState(finalState: ByteArray): ByteArray {
    val state = finalState.clone().also { it.shuffle() }
    if (countInversions(state, 16) % 2 == 1) {
        // Виправляємо стан, якщо інверсій непарна кількість
        val last = state.size - 1
        state[last] = state[last - 1].also { state[last - 1] = state[last] }
    }
    return state
}

// Підрахунок інверсій
fun countInversions(state: ByteArray, empty: Byte): Int {
    var inversions = 0
    for (i in state.indices) {
        for (j in i + 1 until state.size) {
            if (state[i] > state[j] && state[i] != empty && state[j] != empty) {
                inversions++
            }
        }
    }
    return inversions
}

// Перевірка клітинки
fun validateCell(state: ByteArray, cell: Byte): Boolean {
    return cell in 1..15 && state.contains(cell)
}

// Переміщення клітинки
fun moveTile(state: ByteArray, cell: Byte, empty: Byte): Boolean {
    val index = state.indexOf(cell)
    val emptyIndex = state.indexOf(empty)
    val rowDiff = kotlin.math.abs(index / 4 - emptyIndex / 4)
    val colDiff = kotlin.math.abs(index % 4 - emptyIndex % 4)

    return if (rowDiff + colDiff == 1) {
        state[index] = empty.also { state[emptyIndex] = cell }
        true
    } else {
        false
    }
}

// Вивід стану гри
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
