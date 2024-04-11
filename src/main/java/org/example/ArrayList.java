package org.example;

import java.util.Arrays;

/**
 * Класс ArrayList представляет собой список, реализованный с помощью массива.
 * Он предоставляет методы для добавления, удаления, получения и замены элементов в списке, а так же для сортировки и очистки списка.
 * param <T> тип элементов коллекции.
 */
public class ArrayList<T> implements List<T> {
    /**
     * Фиксированный первоначальный размер массива
     */
    private final static int ARRAY_CAPACITY = 10;
    /**
     * Количество свободных ячеек, при недостатке который происходит увеличение размера массива в 1.5 раза
     */
    private final static int RESERVE_SIZE = 2;
    private T[] array;
    /**
     * Размер массива
     */
    private int size = 0;

    /**
     * Конструктор по умолчанию
     */
    public ArrayList() {
        array = (T[]) new Object[ARRAY_CAPACITY];
    }

    /**
     * Конструктор с заданной длиной массива.
     *
     * @param capacity первоначальный размер массива
     */
    public ArrayList(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException("Capacity must be greater than 0");
        array = (T[]) new Object[capacity];
    }

    /**
     * Добавление элемента.
     *
     * @param element добавляемый элемент
     */
    @Override
    public void add(T element) {
        expansionCapacity();
        array[size++] = element;
    }

    /**
     * Добавление элемента по индексу.
     *
     * @param element добавляемый элемент.
     * @param index   место вставки элемента.
     */
    @Override
    public void add(int index, T element) {
        checkIndex(index);
        expansionCapacity();
        for (int i = size; i > index; i--) {
            array[i] = array[i - 1];
        }
        array[index] = element;
        size++;
    }

    /**
     * Возвращение элемента по индексу.
     *
     * @param index индекс возвращаемого элемента.
     * @return T тип возвращаемого элемента.
     */
    @Override
    public T get(int index) {
        checkIndex(index);
        return array[index];
    }

    /**
     * Удаление элемента по индексу.
     *
     * @param index индекс удаляемого элемента
     */
    @Override
    public void remove(int index) {
        checkIndex(index);
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        size--;
        array[size] = null;
        reductionCapacity();
    }

    /**
     * Замена элемента по индексу
     *
     * @param index   место замены элемента
     * @param element значение элемента
     */
    @Override
    public void set(int index, T element) {
        checkIndex(index);
        array[index] = element;
    }

    /**
     * Очистка списка, заполняя весь список "null" и устанавливая размер в 0. Создаётся копия с начальной емкостью.
     */
    public void clear() {
        Arrays.fill(array, 0, size, null);
        size = 0;
        array = Arrays.copyOf(array, ARRAY_CAPACITY);
    }

    /**
     * Размер массива.
     *
     * @return возвращает размер массива.
     */
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(array, size));
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Invalid index: " + index);
    }

    private void expansionCapacity() {
        if (array.length - size <= RESERVE_SIZE)
            array = Arrays.copyOf(array, (int) (array.length * 1.5));
    }

    private void reductionCapacity() {
        if (size < array.length / 2)
            array = Arrays.copyOf(array, (int) (array.length / 1.5));
    }
}
