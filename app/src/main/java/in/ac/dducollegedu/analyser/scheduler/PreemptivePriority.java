package in.ac.dducollegedu.analyser.scheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class PreemptivePriority extends Scheduler {
    protected void rearrangeQueueOnPriority() {
        /*
         * Setting priority so that it is 
         * Sorting on basis of arrival time as first priority
         * And external priority as second priority
         */
        for (Process p : processes) {
            p.priority += p.arrivalTime * 1000;
        }
        Arrays.sort(processes);
        /*
         * Recovering the external priority by
         * reversing (subtracting) the process
         */
        int totalBurst = 0;
        for (Process p : processes) {
            p.priority -= p.arrivalTime * 1000;
            totalBurst += p.cpuBurst;
        }
        ArrayList<Process> queue = new ArrayList<>();

        // stores how much time has been passed (total of all cpu
        // burst that has been executed
        int curBurst = 0;

        // for handing arrival of current process to be added to the queue
        int curArrival = processes[0].arrivalTime;

        while (curBurst <= totalBurst) {
            /*
             * Selecting the process having highest priority
             * and arrived at certain time curArrival
             */
            Process toadd = null;
            int priority = (int) 1e9;
            for (Process p : processes) {
                if (priority > p.priority && curArrival >= p.arrivalTime && p.cpuBurst > 0) {
                    priority = p.cpuBurst;
                    toadd = p;
                }
            }
            if (toadd == null) break;
            /*
             * Taking the next process which can preempt
             * the selected process
             */
            Process next = null;
            for (Process p : processes) {
                if (toadd.priority > p.priority && curArrival < p.arrivalTime) {
                    next = p;
                    break;
                }
            }
            /*
             * Preparing the values of the new process to
             * be added to the queue
             */
            int cpuBurst;
            int arrivalTime;
            if (next == null) {
                cpuBurst = toadd.cpuBurst;
                arrivalTime = curArrival + cpuBurst;
            } else {
                cpuBurst = Math.min(next.arrivalTime - toadd.arrivalTime, toadd.cpuBurst);
                arrivalTime = Math.min(curArrival + cpuBurst, next.arrivalTime);
            }
            /*
             * Adding the selected process to the queue
             * with slicing based on preemption
             */
            cpuBurst = Math.min(arrivalTime - curArrival, cpuBurst);
            Process toAdd = new Process();
            toAdd.pid = toadd.pid;
            toAdd.set(curArrival, priority, cpuBurst);
            queue.add(toAdd);
            toadd.cpuBurst -= cpuBurst;
            curArrival = arrivalTime;
            curBurst += cpuBurst;
        }

        // Assigning the ArrayList type to class member (Process Array type)
        this.queue = new Process[queue.size()];
        queue.toArray(this.queue);
    }
    public PreemptivePriority() {}
    public PreemptivePriority(Process[] init) {
        this.processes = Process.copy(init);
        rearrangeQueueOnPriority();
        processes = Process.copy(init);
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
        PreemptivePriority scheduler = new PreemptivePriority(givenProcesses);
        System.out.println("\n****** Process Scheduling Analysis for Preemptive Priority Algorithm *******");
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
