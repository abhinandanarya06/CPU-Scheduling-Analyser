## Java Classes

* **Process** *(extends Comparable)* : for storing and comparing processes

* **Scheduler** : General class of all scheduling algorithm. It contains general method which freely applies to all inherited scheduling algorithms classes

    - **FCFS** *(extends Scheduler)* : First come first serve scheduling algorithm that only rearrange processes in queue according to its property

    - **NonPreemptivePriority** *(extends Scheduler)* : Non-Preemptive Priority - It also only rearrange processes in queue according to priority set in given processes.
        - **NonPreemptiveSJF** *(extends NonPreemptivePriority)* : Non-Preemptive Shortest job first - It only rearrange processes according to Non-Preemptive Priority set to CPU Burst

    - **PreemptivePriority** *(extends Scheduler)* : Preemptive Priority - It rearrange and slice processes in queue according to priority and preemption on arrival of higher priority process.
        - **PreemptiveSJF** *(extends PreemptivePriority)* - It rearrange and slice processes in queue just like PreemptivePriority + early set priority of each process to corresponding CPU Burst.
    - **RR** *(extends Scheduler)* : Round Robin Scheduling - It rearrange and slice processes in queue according to given time quantum and FCFS policy.

* **SchedulerAnalyser** *(has all objects of above classes)* : Creates all objects of above classes and perform analysis(collects analysis parameters)


## Java Classes Testing 
1. To perform test for the java classes, please remove **package** statement in each java file so that it perform without any error.
2. Compile and run any scheduling class you want as follows
```
    javac <JAVA_FILE>.java
    java <JAVA_CLASS>
```
