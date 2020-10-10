package in.ac.dducollegedu.analyser.scheduler;

public class Process implements Comparable<Process> {
    public int pid;
    int arrivalTime; // in miliseconds
    int priority; // lowest number have highest priority
    int cpuBurst; // in miliseconds

    /**
     * set() sets values and properties
     * of Process class object
     */
    public void set(int arrivalTime, 
                    int priority,
                    int cpuBurst    ) {
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        this.cpuBurst = cpuBurst;
    }

    /**
     * compareTo(Process cp) : is used for further usage on setting priority of
     * processes according to various properties following all types of schedulers
     * and sorting them.
     */
    public int compareTo(Process cp) {
        return (this.priority - cp.priority);
    }

    /**
     * copy copies array of Process class object
     * @param p
     * @return copy of array 'p'
     */
    public static Process[] copy(Process[] p) {
        Process[] ret = new Process[p.length];
        for (Process process: p) {
            ret[process.pid-1] = new Process();
            ret[process.pid-1].pid = process.pid;
            ret[process.pid-1].set(process.arrivalTime, process.priority, process.cpuBurst);
        }
        return ret;
    }
}
