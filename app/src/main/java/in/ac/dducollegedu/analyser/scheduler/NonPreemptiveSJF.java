package in.ac.dducollegedu.analyser.scheduler;

import java.util.Scanner;

/*
 * Scheduling Algorithm : Shortest Job First
 * Priority : On basis of CPU Burst time of the processes
 * Preemption : No
 * Other Names : Shortest - next - CPU - burst algorithm
 */
public class NonPreemptiveSJF extends NonPreemptivePriority {
    /*
     * Constructor of this class first initialise the processes array
     * then reorder them on the basis of the scheduling criteria.
     */
    public NonPreemptiveSJF(Process[] init) {
        super();
        this.queue = Process.copy(init);
        /*
         * Cloning init to processes array
         */
        this.processes = Process.copy(init);
        /*
         * Then setting priority according to
         * CPU Burst time of queue
         * And then partial sorting it on this priority
         *  with handling arrival time cover
         */
        for (int i=0; i < init.length; i++) {
            queue[i].priority = queue[i].cpuBurst; // Setting Priority to cpuBurst
        }
        rearrangeQueueOnPriority();

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
        NonPreemptiveSJF scheduler = new NonPreemptiveSJF(givenProcesses);
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