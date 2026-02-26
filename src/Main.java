import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        for (int N = 100; N < 3101; N+=200) {
            task(3000, 10, N, 10);
        }
    }

    public static void task(double simulationTime, int iterations, int N, int delay) {
        long avgTimeSim1 = 0;
        for (int oi = 1; oi < iterations; oi++) {
            Element.resetNextId();
            Create creator = new Create("CREATOR", delay);

            Process prevProcessor = new Process("PROCESSOR0", delay);
            creator.addNextElement(prevProcessor);

            ArrayList<Process> listOfProcesses = new ArrayList<>();
            listOfProcesses.add(prevProcessor);

            for (int i = 1; i < N; i++) {
                Process process = new Process("PROCESSOR" + i, delay);
                listOfProcesses.add(process);
                prevProcessor.addNextElement(process);

                prevProcessor = process;
            }
            Model model = new Model(new ArrayList<>(){{ add(creator); addAll(listOfProcesses); }});
            avgTimeSim1 += measureTime(() -> model.simulate(simulationTime));
        }

        long avgTimeSim2 = 0;
        for (int oi = 1; oi < iterations; oi++) {
            Element.resetNextId();
            Create creator = new Create("CREATOR", delay);

            Process prevProcessor = new Process("PROCESSOR0", delay);
            creator.addNextElement(prevProcessor);

            ArrayList<Process> listOfProcesses = new ArrayList<>();
            listOfProcesses.add(prevProcessor);

            for (int i = 1; i < N; i++) {
                Process process = new Process("PROCESSOR" + i, delay);
                listOfProcesses.add(process);
            }

            for (int i = 0; i < listOfProcesses.size()/2; i++) {

                Process current = listOfProcesses.get(i);

                int leftChild = 2 * i + 1;
                int rightChild = 2 * i + 2;

                if (leftChild < listOfProcesses.size()) {
                    current.addNextElement(listOfProcesses.get(leftChild), 0.5);
                }
                if (rightChild < listOfProcesses.size()) {
                    current.addNextElement(listOfProcesses.get(rightChild), 0.5);
                }
            }

            Model model = new Model(new ArrayList<>(){{ add(creator); addAll(listOfProcesses); }});
            avgTimeSim2 += measureTime(() -> model.simulate(simulationTime));
        }

        System.out.println("При N=" + N);
        System.out.println("Час симуляції №1: " + (avgTimeSim1 /(double)iterations));
        System.out.println("Час симуляції №2: " + (avgTimeSim2 /(double)iterations));
        System.out.println("----------------------------------");
    }

    private static long measureTime(Runnable runnable) {
        long startTime = System.currentTimeMillis();
        runnable.run();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}