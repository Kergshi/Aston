package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Клас для тестирования методов класса ArrayList
 */
class ArrayListTest {
    private ArrayList<Integer> arrayList;

    /**
     * Метод создает и заполняет список перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        arrayList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            arrayList.add(i);
        }
    }

    /**
     * Метод проверяет добавление элемента в список
     */
    @Test
    void testAdd() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            arrayList.add(i);
            assertEquals(i + 1, arrayList.size());
            assertEquals(i, arrayList.get(i));
        }
    }

    /**
     * Метод проверяет добавление элемента по индексу
     */
    @Test
    void testAddByIndex() {
        arrayList.add(0, 100);
        assertEquals(100, arrayList.get(0));
        for (int i = 0; i < 20; i++) {
            assertEquals(i, arrayList.get(i + 1));
        }
    }

    /**
     * Проверяет,что метод add выбрасывает исключение при попытке добавить элемент по отрицательному индексу
     */
    @Test
    void testAddByIndexThrowsException() {
        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () -> arrayList.add(-1, 100));
        assertEquals("Invalid index: -1", exception.getMessage());
    }

    /**
     * Проверяет, что метод get выбрасывает исключение при попытке получить элемент по отрицательному индексу.
     */
    @Test
    void testGetThrowsException() {
        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () -> arrayList.get(-1));
        assertEquals("Invalid index: -1", exception.getMessage());
    }

    /**
     * Проверяет успешное получение элемента по индексу.
     */
    @Test
    void testGet() {
        for (int i = 0; i < 20; i++) {
            assertEquals(i, arrayList.get(i));
        }
    }

    /**
     * Проверяет удаление элемента по индексу
     */
    @Test
    void testRemoveByIndex() {
        arrayList.remove(0);
        for (int i = 0; i < 19; i++) {
            assertEquals(i + 1, arrayList.get(i));
        }
    }

    /**
     * Проверяет,что метод remove выбрасывает исключение при попытке удалить элемент по отрицательному индексу
     */
    @Test
    void testRemoveByIndexThrowsException() {
        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () -> arrayList.remove(-1));
        assertEquals("Invalid index: -1", exception.getMessage());
    }

    /**
     * Проверяет,что метод успешно заменяет элемент в списке
     */
    @Test
    void testSet() {
        arrayList.set(0, 30);
        assertEquals(30, arrayList.get(0));
        for (int i = 1; i < 20; i++) {
            assertEquals(i, arrayList.get(i));
        }
    }

    /**
     * Проверяет,что метод clear очищает список.
     */
    @Test
    void testClear() {
        arrayList.clear();
        assertEquals(0, arrayList.size());
    }

    /**
     * Проверяет,что метод size возвращает корректное значение размера списка
     */
    @Test
    void testSize() {
        assertEquals(20, arrayList.size());
    }

    /**
     * Проверяет,что метод toString возвращает корректную строку.
     */
    @Test
    void testToString() {
        assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]", arrayList.toString());
    }
}