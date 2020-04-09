import java.util.*;
import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static void main(String[] args) throws IOException {
		Event actEvent;
		State actState = new State(); // The state that should be used
		// Some events must be put in the event list at the beginning
		insertEvent(GlobalSimulation.ARRIVAL1, 0);
		insertEvent(MEASURE, 200);

		// The main simulation loop
		while (actState.noMeasurements < 1000) {
			actEvent = eventList.fetchEvent();
			time = actEvent.eventTime;
			actState.treatEvent(actEvent);
		}
		
		// Printing the result of the simulation, in this case a mean value
		System.out.println("N1 = " + actState.numberInQueue1 + "\nN2 = " + actState.numberInQueue2);
		System.out
				.println("Average amount of customers in Q2 were: " + actState.accumulated2 / actState.noMeasurements);
		System.out.println("Average in the whole system: " + 1.0 * actState.accumulated / actState.noMeasurements);
		System.out.println("Number of rejects were " + actState.noRejects
				+ " and the total amount of customers during the run were " + actState.arrived);
		actState.percReject = (double) actState.noRejects / (actState.arrived);
		System.out.println("Thus, the average percentage of rejection was: "
				+ 100 * actState.percReject + "%");
	}
}