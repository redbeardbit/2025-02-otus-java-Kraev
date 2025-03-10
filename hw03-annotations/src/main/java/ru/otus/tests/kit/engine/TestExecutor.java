package ru.otus.tests.kit.engine;

import ru.otus.tests.kit.annotations.After;
import ru.otus.tests.kit.annotations.Before;
import ru.otus.tests.kit.annotations.Test;
import ru.otus.tests.kit.preconditions.Preconditions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static ru.otus.tests.kit.reflection.ReflectionSupport.*;

public class TestExecutor {

    private List<TestTask> getTestTasks(Class<?> testClass) {
        List<TestTask> tests = new ArrayList<>();

        for (Method method : getDeclaredMethods(testClass, Before.class)) {
            tests.add(new TestTask(getAnnotationDisplayNameValue(method), method, MethodType.BEFORE));
        }
        for (Method method : getDeclaredMethods(testClass, Test.class)) {
            tests.add(new TestTask(getAnnotationDisplayNameValue(method), method, MethodType.TEST));
        }
        for (Method method : getDeclaredMethods(testClass, After.class)) {
            tests.add(new TestTask(getAnnotationDisplayNameValue(method), method, MethodType.AFTER));
        }

        return tests;
    }

    private TestFlow getTestFlow(Class<?> testClass) {
        Object testObject = newInstance(testClass);
        List<TestTask> tests = getTestTasks(testClass);

        Preconditions.notNull(testObject, "Test object must not be null");
        Preconditions.notEmpty(tests, "Test must not be empty");

        return new TestFlow(testObject, tests);
    }

    public void run(Class<?>... testClasses) {
        List<TestResult> results = new ArrayList<>();

        System.out.println("=".repeat(80));
        System.out.println("Test run:");

        for (Class<?> testClass : testClasses) {
            results.addAll(getTestFlow(testClass).run());
        }

        printReport(results);
    }

    private void printReport(List<TestResult> results) {

        System.out.println("=".repeat(80));
        System.out.println("Test report:");
        System.out.printf("Total number of tests: %d, are successful: %d\n", results.size(), results.stream().filter(r -> Status.SUCCESSFUL.equals(r.status())).count());

        System.out.println("=".repeat(80));
        System.out.println("Test Details:");
        results.forEach(r -> System.out.printf("Test name: [%s], status: %s%n", r.testName(), r.status()));
    }
}