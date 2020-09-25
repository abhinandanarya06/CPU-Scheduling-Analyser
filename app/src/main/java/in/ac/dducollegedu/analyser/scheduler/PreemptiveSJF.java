package in.ac.dducollegedu.analyser.scheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class PreemptiveSJF extends PreemptivePriority {
    protected void rearrangeQueueOnPriority() {
        /*
         * Setting priority so that it is 
         * Sorting on basis of arrival time as first priority 
         * And external priority as second priority
         */
        for (Process p: processes) {
            p.priority += p.arrivalTime * 1000;
        }
        Arrays.sort(processes);
        /*
         * Recovering the external priority by
         * reversing (subtracting) the process
         */
        for (Process p: processes) {
            p.priority -= p.arrivalTime * 1000;
        }
        ArrayList<Process> queue = new ArrayList<Process>();
        /*
         * Firstly filling processes in the queue upto
         * which preemption will occur - Stage 1 ordering
         */
        int lastArrival = 0;
        for (int i=0; i < processes.length; i++) {
            Process p = processes[i];
            int j = i+1;
            while (j < processes.length && processes[j].priority >= p.priority) j++;
            if (j >= processes.length) break;
            Process ahead = processes[j];
            Process toAdd = new Process();
            toAdd.pid = p.pid;
            lastArrival = Math.max(lastArrival, p.arrivalTime);
            int cpuBurst = Math.min(p.cpuBurst, ahead.arrivalTime - lastArrival);
            toAdd.set(lastArrival, p.cpuBurst, cpuBurst);
            queue.add(toAdd);
            p.cpuBurst -= cpuBurst;
            p.priority = p.cpuBurst;
            lastArrival += cpuBurst;
        }
        /*
         * Then filling processes in the queue without slicing of processes
         * as preemption stage has been filled. Remaining stage can be sorted and
         * added in the queue following Non-Preemptive Priority afterward.
         */
        Arrays.sort(processes);
        for (Process p: processes) {
            if (p.cpuBurst > 0) {
                Process toAdd = new Process();
                toAdd.pid = p.pid;
                toAdd.set(lastArrival, p.priority, p.cpuBurst);
                queue.add(toAdd);
                lastArrival += p.cpuBurst;
            }
        }
        // Assigning the ArrayList type to class member (Process Array type)
        this.queue = new Process[queue.size()];
        queue.toArray(this.queue);
    }
    public PreemptiveSJF(Process[] init) {
        super();
        processes = copy(init);
        for (Process p: processes) {
            p.priority = p.cpuBurst;
        }
        rearrangeQueueOnPriority();
        processes = copy(init);
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("How many processes are there ? : ");
        int numOfProcesses = in.nextInt();
        Process[] givenProcesses = new Process[numOfProcesses];
        for (int i=0; i < numOfProcesses; i++) {
            int arrivalTime, cpuBurst;
            System.out.printf("Enter details of P%d : (arrivalTime, cpuBurst) = ", i+1);
            arrivalTime = in.nextInt();
            cpuBurst = in.nextInt();
            givenProcesses[i] = new Process();
            givenProcesses[i].pid = i+1;
            givenProcesses[i].set(arrivalTime, 0, cpuBurst);
        }
        PreemptiveSJF scheduler = new PreemptiveSJF(givenProcesses);
        System.out.println("\n****** Process Scheduling Analysis for Preemptive SJF Algorithm *******");
        System.out.print(scheduler.retGanttChart());
        /*
         * Printing wait times of each processes
         * And then printing avg. wait time of the algorithm
         */
        System.out.print("Wait times : ");
        for (Process p: scheduler.processes) {
            System.out.printf("(P%d = %d), ", p.pid, scheduler.waitTimeOf(p.pid));
        }
        System.out.printf("Avg. Waiting Time : %f\n\n", scheduler.avgWaitTime());
        /*
         * Printing turnaround times of each processes
         * And then printing avg. turnaround time of the algorithm
         */
        System.out.print("Turnaround times : ");
        for (Process p: scheduler.processes) {
            System.out.printf("(P%d = %d), ", p.pid, scheduler.turnaroundTimeOf(p.pid));
        }
        System.out.printf("Avg. Turnaround Time : %f\n\n", scheduler.avgTurnaroundTime());
        /*
         * Printing response times of each processes
         * And then printing avg. response time of the algorithm
         */
        System.out.print("Response times : ");
        for (Process p: scheduler.processes) {
            System.out.printf("(P%d = %d), ", p.pid, scheduler.responseTimeOf(p.pid));
        }
        System.out.printf("Avg. Response Time : %f\n\n", scheduler.avgResponseTime()); // Because it is non - preemptive
        in.close();
    }
}
