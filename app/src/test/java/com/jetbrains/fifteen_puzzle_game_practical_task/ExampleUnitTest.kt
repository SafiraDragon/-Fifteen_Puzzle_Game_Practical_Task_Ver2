package com.jetbrains.fifteen_puzzle_game_practical_task

import org.junit.Test
import org.junit.Assert.*

class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    // Тест для створення фінального стану
    @Test
    fun testCreateFinalState() {
        val finalState = createFinalState(16)
        val expected = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
        assertArrayEquals(expected, finalState)
    }

    // Тест для підрахунку інверсій
    @Test
    fun testCountInversions() {
        val state = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 14, 16)
        val inversions = countInversions(state, 16)
        assertEquals(1, inversions)
    }

    // Тест для перевірки коректності клітинки
    @Test
    fun testValidateCell() {
        val state = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 14, 16)
        assertTrue(validateCell(state, 15))
        assertFalse(validateCell(state, 20))
    }

    // Тест для переміщення клітинки
    @Test
    fun testMoveTile() {
        val state = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16, 14)
        assertTrue(moveTile(state, 15, 16))
        assertArrayEquals(
            byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 16, 15, 14),
            state
        )
    }

    // Тест для ініціалізації гри
    @Test
    fun testInitializeGameState() {
        val finalState = createFinalState(16)
        val state = initializeGameState(finalState)
        assertEquals(finalState.size, state.size)
        assertNotEquals(finalState.toList(), state.toList()) // Стан має бути перемішаним
    }
}
