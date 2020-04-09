import java.util.*;
import java.io.*;

class State extends GlobalSimulation {

	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int arrived = 0, numberInQueue1 = 0, numberInQueue2 = 0, accumulated = 0, accumulated2 = 0, noMeasurements = 0, noRejects = 0;
	public double percReject = 0; //int?
	private double constQ1 = 5;
	
	Random slump = new Random(); // This is just a random number generator

	// The following method is called by the main program each time a new event has
	// been fetched
	// from the event list in the main loop.
	public void treatEvent(Event x) {
		switch (x.eventType) {
		case GlobalSimulation.ARRIVAL1:
			arrivalTo1();
			break;
		case GlobalSimulation.ARRIVAL2:
			arrivalTo2();
			break;
		case READY:
			ready();
			break;
		case MEASURE:
			measure();
			break;
		}
	}

	// The following methods defines what should be done when an event takes place.
	// This could
	// have been placed in the case in treatEvent, but often it is simpler to write
	// a method if
	// things are getting more complicated than this.

	private void arrivalTo1() {
		arrived += 1;
		if (numberInQueue1 == 10) {
			noRejects += 1;
			insertEvent(GlobalSimulation.ARRIVAL1, time + constQ1);
			return;
		}
		if (this.numberInQueue1 == 0) {
			insertEvent(GlobalSimulation.ARRIVAL2, time + getNextExp(2.1));
		}
		numberInQueue1++;
		insertEvent(GlobalSimulation.ARRIVAL1, time + constQ1);
	} //fixa numberInQueue1 

	private void arrivalTo2() {
		numberInQueue1--;
		if (numberInQueue2 == 0) {
			insertEvent(READY, time + 2);
		}
		numberInQueue2++;
		insertEvent(GlobalSimulation.ARRIVAL2, time + getNextExp(2.1));
	}
	
	private void ready() {
		if (numberInQueue2 > 0) {
			numberInQueue2--;
			insertEvent(READY, time + 2);
		}
	}

	private void measure() {
		accumulated2 += numberInQueue2;
		accumulated = accumulated + numberInQueue1 + numberInQueue2;
		noMeasurements++;
		insertEvent(MEASURE, time + getNextExp(5)); //tänk på warm up time 
	}
	
	public double getNextExp(double lambda) {
	    return Math.log(1-slump.nextDouble())/(-lambda);
	}
}