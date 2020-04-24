import java.util.*;
import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static void main(String[] args) throws IOException {
		Event actEvent;
		State actState = new State(); // The state that should be used
		// Some events must be put in the event list at the beginning
		insertEvent(GlobalSimulation.ARRIVAL1, 0);
		insertEvent(MEASURE, 100000);

		// The main simulation loop
		while (actState.noMeasurements < 100000) {
			actEvent = eventList.fetchEvent();
			time = actEvent.eventTime;
			actState.treatEvent(actEvent);
		}
		
		// Printing the result of the simulation, in this case a mean value
		System.out.println("Currently there are following customers in the system: \n");
		System.out.println("N1 = " + actState.numberInQueue1 + "\nN2 = " + actState.numberInQueue2);
		System.out.println("Average amount of customers in Q1 were: " + actState.getAvgNoQ1());
		System.out.println("Average amount of customers in Q2 were: " + actState.getAvgNoQ2());
		System.out.println("Thus, the average percentage of rejection was: " + 100 * (double) actState.noRejects/actState.arrived + "%");
	}
}