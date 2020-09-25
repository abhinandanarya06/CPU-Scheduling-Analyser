package in.ac.dducollegedu.analyser.scheduler;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Scheduling Algorithm : First Come - First Serve
 * Priority : On basis of arrival times of the processes
 * Preemption : No
 */
public class FCFS extends Scheduler {
    /**
     * Constructor of this class first initialise the processes array
     * then sort them on the basis of the scheduling criteria.
     */
    public FCFS(Process[] init) {
        this.queue = copy(init);
        /**
         * Cloning init to processes array
         */
        this.processes = copy(init);
        /**
         * Setting priority on the basis of
         * Arrival times of the processes
         * then sorting them on this priority
         */
        for (int i=0; i < init.length; i++) {
            queue[i].priority = queue[i].arrivalTime;
        }
        Arrays.sort(queue);
        /**
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

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("How many processes are there ? : ");
        int numOfProcesses = in.nextInt();
        Process givenProcesses[] = new Process[numOfProcesses];
        for (int i=0; i < numOfProcesses; i++) {
            int arrivalTime, cpuBurst;
            System.out.printf("Enter details of P%d : (arrivalTime, cpuBurst) = ", i+1);
            arrivalTime = in.nextInt();
            cpuBurst = in.nextInt();
            givenProcesses[i] = new Process();
            givenProcesses[i].pid = i+1;
            givenProcesses[i].set(arrivalTime, 0, cpuBurst);
        }
        FCFS scheduler = new FCFS(givenProcesses);
        System.out.println("****** Process Scheduling Analysis for FCFS Algorithm *******");
        System.out.print(scheduler.retGanttChart());
        /**
         * Printing wait times of each processes
         * And then printing avg. wait time of the algorithm
         */
        System.out.print("Wait times : ");
        for (Process p: scheduler.processes) {
            System.out.printf("(P%d = %d), ", p.pid, scheduler.waitTimeOf(p.pid));
        }
        System.out.printf("Avg. Waiting Time : %f\n\n", scheduler.avgWaitTime());
        /**
         * Printing turnaround times of each processes
         * And then printing avg. turnaround time of the algorithm
         */
        System.out.print("Turnaround times : ");
        for (Process p: scheduler.processes) {
            System.out.printf("(P%d = %d), ", p.pid, scheduler.turnaroundTimeOf(p.pid));
        }
        System.out.printf("Avg. Turnaround Time : %f\n\n", scheduler.avgTurnaroundTime());
        /**
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