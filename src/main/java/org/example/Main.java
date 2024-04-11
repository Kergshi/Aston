package org.example;

/**
 * Главный класс приложения (пример использования ArrayList и QuickSort).
 */
public class Main {

    /**
     * Точка входа  в программу.
     *
     * @param args аргументы командной строки(не используются)
     */
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(2);
        numbers.add(1);
        numbers.add(3);
        numbers.add(4);
        numbers.add(3, 5);
        numbers.add(9);
        numbers.add(7);
        numbers.add(8);
        numbers.add(6);

        System.out.println("Список до сортировки: " + numbers);
        QuickSort.sort(numbers);
        System.out.println("Список после сортировки: " + numbers);

        System.out.println("Элементы под индексом 3: " + numbers.get(3));
        System.out.println("Размер списка: " + numbers.size());

        System.out.println("Элемент под индексом 4: " + numbers.get(4));
        numbers.remove(3);
        System.out.println("Размер списка после удаления элемента под индексом 3: " + numbers.size());
        System.out.println("Элемент под индексом 3: " + numbers.get(3));

        numbers.clear();
        System.out.println("Размер списка после очистки: " + numbers.size());
    }
}