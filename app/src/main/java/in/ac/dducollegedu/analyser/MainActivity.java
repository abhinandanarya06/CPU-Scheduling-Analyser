package in.ac.dducollegedu.analyser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.ac.dducollegedu.analyser.databinding.ActivityMainBinding;
import in.ac.dducollegedu.analyser.scheduler.SchedulerAnalyser;
import in.ac.dducollegedu.analyser.scheduler.Process;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding; // Using data Binding feature
    int processNo; // Keep track of process no being added to input
    ArrayList<Process> testProcesses; // Keep Input for further calculation
    SchedulerAnalyser analyser; // Object for analysing scheduling algorithm
    String table; // formatted string for output it on inputs TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * Initialise MainActivity with Data Binding to access
         * activity views efficiently
         */
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        /**
         * Initialising process calculation parameter : testProcesses, processNo, analyser
         *         and  output format string parameter : table
         */
        testProcesses = new ArrayList<Process>();
        processNo = 1;
        table = "";
        analyser = new SchedulerAnalyser();
        /**
         * Declaring onClick Function for addInput button so that
         * it add process data to required table and then update
         * the data table text view
         */
        binding.addInputs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Getting Input EditText Views with explicite smaller
                 * namespace
                 */
                EditText arrivalB, burstB, priorityB;
                arrivalB = binding.arrivalInput;
                burstB = binding.burstInput;
                priorityB = binding.priorityInput;
                /**
                 * Getting arrival time, burst time, priority of process
                 * from EditText View above initialised
                 *
                 * if Inputs given are invalid, Generate a toast for assumption.
                 */
                int arrivalTime, burstTime, priority;
                arrivalTime = burstTime = priority = 0;
                try {
                    arrivalTime = Integer.parseInt(arrivalB.getText().toString());
                    burstTime = Integer.parseInt(burstB.getText().toString());
                    priority = Integer.parseInt(priorityB.getText().toString());
                } catch(Exception e) {
                    Toast.makeText(MainActivity.this, "Assuming blank entries to be 0", Toast.LENGTH_SHORT).show();
                }
                /**
                 * Creating new Process and setting above given inputs to
                 * the parameter of the new process
                 *
                 * then add it to process list
                 */
                Process toAdd = new Process();
                toAdd.pid = processNo;
                toAdd.set(arrivalTime, priority, burstTime);
                testProcesses.add(toAdd);
                /**
                 * Adding formatted output string to table
                 */
                table += String.format("P%d \t\t\t\t\t\t\t\t %d \t\t\t\t\t\t\t\t\t\t %d \t\t\t\t\t\t\t\t %d\n",
                        processNo, arrivalTime, burstTime, priority);
                processNo++;
                /**
                 * Setting output table to appropriate TextView inputs
                 */
                binding.inputs.setText(table);
            }
        });
        /**
         * Defining onClick function for calculate button so that it
         * first calculate analysis parameter from given input in testProcesses
         * and then update the data to each analysis output text view of
         * each process scheduling algorithm
         */
        binding.calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Getting time quantum input from EditText View timeQuantum
                 *
                 * if given time quantum is invalid (<= 0), show a toast assuming
                 * time quantum to be 1
                 */
                int timeQuantum = 1;
                try {
                    timeQuantum = Integer.parseInt(binding.timeQuantum.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Assuming time quantum to be 1", Toast.LENGTH_SHORT).show();
                }
                /**
                 * Iterating over all algorithms analysis and calculate analysis paramter
                 * for each algorithms then set the output analysis to appropriate TextView
                 *
                 * Hence Output Analysis is shown
                 */
                String[] algos = {"fcfs", "npsjf", "psjf", "npp", "pp", "rr"};
                TextView[] outputs = {binding.fcfsa, binding.npsjfa, binding.psjfa,
                        binding.nppa, binding.ppa, binding.rra};
                Process[] toTest = new Process[testProcesses.size()];
                testProcesses.toArray(toTest);
                for (int i=0; i < algos.length; i++) {
                    TextView toSet = outputs[i];
                    String algo = algos[i];
                    String analysis = "";
                    if (timeQuantum == 0 && algo == "rr") {
                        analysis = "In case of time quantum 0, Infinite preemption will occur and ready queue will never be empty";
                    } else {
                        try {
                            analysis = analyser.calculateFor(algo, toTest, timeQuantum);
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Error in Calculation occured !", Toast.LENGTH_SHORT).show();
                        }
                    }
                    toSet.setText(analysis);
                }
                /**
                 * Hiding Keyboard so that user can see the scroll view of analysis output
                 */
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                try {
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error in Keyboard occured !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}