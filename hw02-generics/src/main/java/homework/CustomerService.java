package homework;

import java.util.*;

@SuppressWarnings("java:S1135")
public class CustomerService {

    // todo: 3. надо реализовать методы этого класса
    // важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private final NavigableMap<Customer, String> customerStringMap;

    public CustomerService() {
        this.customerStringMap = new TreeMap<>(Comparator.comparingLong(Customer::getScores));
    }

    public Map.Entry<Customer, String> getSmallest() {
        // Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        return copyEntryOrNull(customerStringMap.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return copyEntryOrNull(customerStringMap.higherEntry(customer)); // это "заглушка, чтобы скомилировать"
    }

    public void add(Customer customer, String data) {
        customerStringMap.put(new Customer(customer), data);
    }

    private Map.Entry<Customer, String> copyEntryOrNull(Map.Entry<Customer, String> entry) {
        return Optional.ofNullable(entry)
                .map(c -> Map.entry(new Customer(c.getKey()), c.getValue()))
                .orElse(null);
    }
}
