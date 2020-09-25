package in.ac.dducollegedu.analyser.scheduler;

import java.util.Scanner;

public class RR extends Scheduler {
    int timeQuantum; // Maximum Preemption Interval
    
    public RR(int timeQuantum, Process[] init) {
        this.processes = copy(init);
        this.timeQuantum = timeQuantum;
        /*
         * Finding required length/size of queue in
         * Round Robin Algorithm and store it in processQueueLenght.
         */
        int queueLenght = 0;
        for (Process p: processes) {
            queueLenght += Math.ceil((double) p.cpuBurst / timeQuantum);
        }

        /*
         * Initialise queue with necessary lenght/size
         * And reordering and slicing the process
         * according to time quantum and arrival time.
         */
        queue = new Process[queueLenght];
        int curSeat, pNo, curArrival;
        curSeat = pNo = curArrival = 0;
        while (curSeat < queueLenght) {
            int lenght = 0;
            for (Process p: processes) {
                if (curArrival >= p.arrivalTime) lenght++;
            }
            if (lenght == 0) lenght = 1;
            int toAssign = pNo % lenght;
            if (processes[toAssign].cpuBurst > 0) {
                int timeReq = Math.min(processes[toAssign].cpuBurst, timeQuantum);
                processes[toAssign].cpuBurst -= timeReq;
                queue[curSeat] = new Process();
                queue[curSeat].pid = processes[toAssign].pid;
                curArrival = Math.max(curArrival, processes[toAssign].arrivalTime);
                queue[curSeat].set(curArrival, curArrival, timeReq);
                curArrival += timeReq;
                curSeat++;
            }
            pNo++;
        }
        this.processes = copy(init);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner (System.in);
        System.out.print("Enter Time Quantum value : ");
        int timeQuantum = in.nextInt();
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
        RR scheduler = new RR(timeQuantum, givenProcesses);
        System.out.println("****** Process Scheduling Analysis for Round Robin Algorithm *******");
        System.out.print(scheduler.retGanttChart());
        /*
         * Printing wait times of each processes
         * And then printing avg. wait time of the algorithm
         */
        for (Process p: scheduler.processes) {
            System.out.printf("Wait time for P%d : %d\n", p.pid, scheduler.waitTimeOf(p.pid));
        }
        System.out.printf("Avg. Waiting Time : %f\n\n", scheduler.avgWaitTime());
        /*
         * Printing turnaround times of each processes
         * And then printing avg. turnaround time of the algorithm
         */
        for (Process p: scheduler.processes) {
            System.out.printf("Turnaround time for P%d : %d\n", p.pid, scheduler.turnaroundTimeOf(p.pid));
        }
        System.out.printf("Avg. Turnaround Time : %f\n\n", scheduler.avgTurnaroundTime());
        /*
         * Printing response times of each processes
         * And then printing avg. response time of the algorithm
         */
        for (Process p: scheduler.processes) {
            System.out.printf("Response time for P%d : %d\n", p.pid, scheduler.responseTimeOf(p.pid));
        }
        System.out.printf("Avg. Response Time : %f\n\n", scheduler.avgResponseTime()); // Because it is non - preemptive
        in.close();
    }
}