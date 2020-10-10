package in.ac.dducollegedu.analyser.scheduler;

public class Scheduler {
    Process[] queue; // Ordered as Gantt chart
    Process[] processes;

    protected int waitTimeOf(int pid) {
        int waitTime = 0;
        int nastartTime = processes[pid-1].arrivalTime;
        for (Process p: queue) {
            if (p.pid == pid) {
                waitTime += p.arrivalTime - nastartTime;
                nastartTime = p.arrivalTime + p.cpuBurst;
            }
        }
        return waitTime;
    }
    protected int turnaroundTimeOf(int pid) {
        int cpuBurst = 0;
        for (Process p: processes) {
            if (p.pid == pid) {
                cpuBurst += p.cpuBurst;
                break;
            }
        }
        return waitTimeOf(pid) + cpuBurst;
    }
    protected int responseTimeOf(int pid) {
        int responseTime = 0;
        for (Process p: queue) {
            if (p.pid == pid) {
                responseTime = p.arrivalTime;
                break;
            }
        }
        return responseTime - processes[pid-1].arrivalTime;
    }
    public double avgWaitTime() {
        int sumOfAllWaitTimes = 0;
        for (Process p: processes) {
            sumOfAllWaitTimes += waitTimeOf(p.pid);
        }
        return (double) sumOfAllWaitTimes / processes.length;
    }
    public double avgTurnaroundTime() {
        int sumOfAllTurnarounds = 0;
        for (Process p: processes) {
            sumOfAllTurnarounds += turnaroundTimeOf(p.pid);
        }
        return (double) sumOfAllTurnarounds / processes.length;
    }
    public double avgResponseTime() {
        int sumOfAllResponses = 0;
        for (Process p: processes) {
            sumOfAllResponses += responseTimeOf(p.pid);
        }
        return (double) sumOfAllResponses / processes.length;
    }
    public String retGanttChart() {
        String output = "Gantt Chart : ";
        int finalTime = 0;
        for (Process p: queue) {
            if (p.arrivalTime > finalTime) {
                output += String.format(" IDEL (%d - %d), ", finalTime, p.arrivalTime);
            }
            finalTime = p.arrivalTime + p.cpuBurst;
            output += String.format(" P%d (%d - %d), ", p.pid, p.arrivalTime, finalTime);
        }
        output += "\n";
        return output;
    }
}
