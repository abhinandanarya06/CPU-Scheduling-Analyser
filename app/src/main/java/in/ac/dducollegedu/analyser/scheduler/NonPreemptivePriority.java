package in.ac.dducollegedu.analyser.scheduler;

import java.util.Arrays;
import java.util.Scanner;

public class NonPreemptivePriority extends Scheduler {
    protected void rearrangeQueueOnPriority() {
        /*
         * First setting priority according to
         * arrival time of processes
         * And then sorting it on this priority
         */
        for (Process p: queue) {
            p.priority += p.arrivalTime * 1000;
        }
        Arrays.sort(queue);
        for (Process p: queue) {
            p.priority -= p.arrivalTime * 1000;
        }
        /*
         * Partial sorting it on given priority
         *  with handling arrival time cover
         */
        int i = 0;
        while (i < queue.length) { // Partial sorting started
            int arrivalCover = queue[i].arrivalTime + queue[i].cpuBurst;
            int tosorti = i+1;
            while (i < queue.length && arrivalCover >= queue[i].arrivalTime) i++;
            Arrays.sort(queue, tosorti, i);
        }
        /*
         * Setting the real allocate time to arrival time of
         * each processes so that it can be used to print
         * Gantt Chart of the queue
         */
        int arrivalTime = 0;
        for (Process p: queue) {
            arrivalTime = Math.max(arrivalTime, p.arrivalTime);
            p.set(arrivalTime, p.arrivalTime, p.cpuBurst);
            arrivalTime += p.cpuBurst;
        }
    }
    public NonPreemptivePriority() {}
    public NonPreemptivePriority(Process[] init) {
        this.queue = Process.copy(init);
        /*
         * Cloning init to processes array
         */

        rearrangeQueueOnPriority();
        this.processes = Process.copy(init);
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("How many processes are there ? : ");
        int numOfProcesses = in.nextInt();
        Process[] givenProcesses = new Process[numOfProcesses];
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
        NonPreemptivePriority scheduler = new NonPreemptivePriority(givenProcesses);
        System.out.println("\n****** Process Scheduling Analysis for Non-Preemptive SJF Algorithm *******");
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
