package in.ac.dducollegedu.analyser.scheduler;

public class Process implements Comparable<Process> {
    public int pid;
    int arrivalTime; // in miliseconds
    int priority; // lowest number have highest priority
    int cpuBurst; // in miliseconds

    public void set(int arrivalTime, 
                    int priority,
                    int cpuBurst    ) {
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        this.cpuBurst = cpuBurst;
    }

    /*
     * compareTo(Process cp) : is used for further usage on setting priority of
     * processes according to various properties following all types of schedulers
     * and sorting them.
     */
    public int compareTo(Process cp) {
        return (this.priority - cp.priority);
    }
}
