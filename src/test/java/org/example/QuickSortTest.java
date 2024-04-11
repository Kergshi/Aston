package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Класс для тестирования сотрировки списка с использованием класса QuickSort.
 * Он содержит методы  для проверки корректности сотрировки списка с использованием QuickSort.
 */
class QuickSortTest {
    private ArrayList<Integer> arrayList;

    /**
     * Заполняем списов перед каждым тестом
     */
    @BeforeEach
    void setUp() {
        arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(5);
        arrayList.add(2);
        arrayList.add(6);
        arrayList.add(4);
        arrayList.add(3);
    }

    /**
     * Проверяем корректность сортировки списка с использованием QuickSort
     */
    @Test
    void testSort() {
        QuickSort.sort(arrayList);
        assertEquals("[1, 2, 3, 4, 5, 6]", arrayList.toString());
    }

    /**
     * Проверяем успешную сортировку списка с использованием QuickSort и переданным компаратором
     */
    @Test
    void testSortWithComparator() {
        QuickSort.sort(arrayList, Comparator.naturalOrder());
        assertEquals("[1, 2, 3, 4, 5, 6]", arrayList.toString());
    }
}