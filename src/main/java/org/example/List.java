package org.example;

/**
 * Интерфейс List в Java представляет упорядоченную коллекцию объектов, которая может содержать дубликаты элементов.
 * Этот интерфейс определяет основные операции, которые можно выполнить с коллекцией.
 *
 * @param <T> тип элементов коллекции.
 */
public interface List<T> {
    void add(T element);

    void add(int index, T element);

    T get(int index);

    void remove(int index);

    void set(int index, T element);

    void clear();

    int size();

}
