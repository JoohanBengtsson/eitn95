import java.util.*;
import java.io.*;

class State extends GlobalSimulation {

	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQueue1 = 0, numberInQueue2 = 0, accumulated = 0, noMeasurements = 0, noRejects = 0;

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
		if (numberInQueue1 == 10) {
			noRejects++;
			return;
		}
		if (this.numberInQueue1 == 0) {
			insertEvent(GlobalSimulation.ARRIVAL2, time + 2 * slump.nextDouble());
		}
		numberInQueue1++;
		insertEvent(GlobalSimulation.ARRIVAL1, time + 2.5 * slump.nextDouble());
	}

	private void arrivalTo2() {
		if (numberInQueue2 == 0) {
			insertEvent(READY, time + 2);
		}
		numberInQueue2++;
		insertEvent(GlobalSimulation.ARRIVAL2, time + 2.5 * slump.nextDouble());
	}

	private void ready() {
		numberInQueue2--;
		if (numberInQueue2 > 0) {
			insertEvent(READY, time + 2 * slump.nextDouble());
		}

	}

	private void measure() {
		accumulated = accumulated + numberInQueue1 + numberInQueue2;
		noMeasurements++;
		insertEvent(MEASURE, time + slump.nextDouble() * 10);
	}
}