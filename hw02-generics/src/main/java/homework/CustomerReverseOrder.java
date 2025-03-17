package homework;

import java.util.Deque;
import java.util.LinkedList;

@SuppressWarnings("java:S1135")
public class CustomerReverseOrder {

    // todo: 2. надо реализовать методы этого класса
    // надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"
    private final Deque<Customer> customersQueue = new LinkedList<>();

    public void add(Customer customer) {
        customersQueue.add(customer);
    }

    public Customer take() {
        return customersQueue.pollLast(); // это "заглушка, чтобы скомилировать"
    }
}
