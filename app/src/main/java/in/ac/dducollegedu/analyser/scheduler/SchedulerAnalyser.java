package in.ac.dducollegedu.analyser.scheduler;

import java.util.Scanner;

public class SchedulerAnalyser {
    public String calculateFor(String algo, Process[] testProcesses, int timeQuantum) {
        Scheduler scheduler;
        if (algo == "fcfs") {
            scheduler = new FCFS (testProcesses);
        } else if (algo == "npsjf") {
            scheduler = new NonPreemptiveSJF (testProcesses);
        } else if (algo == "psjf") {
            scheduler = new PreemptiveSJF(testProcesses);
        } else if (algo == "pp") {
            scheduler = new PreemptivePriority(testProcesses);
        } else if (algo == "npp") {
            scheduler = new NonPreemptivePriority(testProcesses);
        } else if (algo == "rr") {
            scheduler = new RR(timeQuantum, testProcesses);
        } else {
            return "";
        }
        String output = "";
        output += scheduler.retGanttChart();
        output += "\nTurnaround Time : ";
        for (Process p: scheduler.processes) {
            output += String.format("(P%d = %d), ", p.pid, scheduler.turnaroundTimeOf(p.pid));
        }
        output += "\nAvg. Turnaround Time : " + scheduler.avgTurnaroundTime();

        output += "\n\nWait Time : ";
        for (Process p: scheduler.processes) {
            output += String.format("(P%d = %d), ", p.pid, scheduler.waitTimeOf(p.pid));
        }
        output += "\nAvg. Waiting Time : " + scheduler.avgWaitTime();

        output += "\n\nResponse Time : ";
        for (Process p: scheduler.processes) {
            output += String.format("(P%d = %d), ", p.pid, scheduler.responseTimeOf(p.pid));
        }
        output += "\nAvg. Response Time : " + scheduler.avgResponseTime();
        output += "\n";
        return output;
    }
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Time Quantum value : ");
        int timeQuantum = in.nextInt();
        System.out.print("How many processes are there ? : ");
        int numOfProcesses = in.nextInt();
        Process givenProcesses[] = new Process[numOfProcesses];
        for (int i=0; i < numOfProcesses; i++) {
            int arrivalTime, cpuBurst, priority;
            System.out.printf("Enter details of P%d : (arrivalTime, cpuBurst, priority) = ", i+1);
            arrivalTime = in.nextInt();
            cpuBurst = in.nextInt();
            priority = in.nextInt();
            givenProcesses[i] = new Process();
            givenProcesses[i].pid = i+1;
            givenProcesses[i].set(arrivalTime, priority, cpuBurst);
        }
        SchedulerAnalyser analyser = new SchedulerAnalyser();
        String[] algos = {"fcfs", "npsjf", "psjf", "npp", "pp", "rr"};
        for (String algo: algos) {
            System.out.println("*******     " + algo + "     *******");
            System.out.println(analyser.calculateFor(algo, givenProcesses, timeQuantum));
        }
        in.close();
    }
}
