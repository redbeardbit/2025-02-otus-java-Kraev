package ru.otus.tests.kit.engine;

import static ru.otus.tests.kit.reflection.ReflectionSupport.invokeMethod;

import java.util.ArrayList;
import java.util.List;
import ru.otus.tests.kit.reflection.ReflectionException;

public class TestFlow {

    private final Object testedObject;

    private final List<TestTask> tasks;

    public TestFlow(Object testedObject, List<TestTask> tasks) {
        this.testedObject = testedObject;
        this.tasks = tasks;
    }

    public List<TestTask> getTasks() {
        return tasks;
    }

    private void runMethod(MethodType methodType) {
        List<TestTask> metodTasks =
                getTasks().stream().filter(t -> t.getMethodType() == methodType).toList();

        if (!metodTasks.isEmpty()) {
            for (TestTask test : metodTasks) {
                try {
                    invokeMethod(test.getMethod(), testedObject);
                    test.setState(Status.SUCCESSFUL);
                } catch (ReflectionException e) {
                    test.setState(Status.FAILED);
                }
            }
        }
    }

    private List<TestResult> getFlowRunResults() {
        List<TestResult> results = new ArrayList<>();

        getTasks().stream()
                .filter(t -> t.getMethodType() == MethodType.TEST)
                .forEach(task -> results.add(new TestResult(task.getDisplayName(), task.getState())));

        return results;
    }

    public List<TestResult> run() {

        runMethod(MethodType.BEFORE);
        runMethod(MethodType.TEST);
        runMethod(MethodType.AFTER);

        return getFlowRunResults();
    }
}
