import java.util.*;
import java.io.*;

class State extends GlobalSimulation {
	

	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int arrived = 0, servedCustomers = 0, numberInQueue1 = 0, numberInQueue2 = 0, noMeasurements = 0, noRejects = 0;
	private double percReject;
	private LinkedList<Integer> _noQ1 = new LinkedList<Integer>();
	private LinkedList<Integer> _noQ2 = new LinkedList<Integer>();
	private double constQ1 = 1, meanServ1 = 1/2.1, meanServ2 = 2;

	Random slump = new Random(); // This is just a random number generator

	// The following method is called by the main program each time a new event has
	// been fetched
	// from the event list in the main loop.
	public void treatEvent(Event x) {
		switch (x.eventType) {
		case GlobalSimulation.ARRIVAL1:
			arrivalTo1();
			break;
		case GlobalSimulation.DEPT1:
			departureFrom1();
			break;
		case GlobalSimulation.DEPT2:
			departureFrom2();
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
		if (numberInQueue1 < 10) {
			this.numberInQueue1++;
		} else {
			noRejects += 1;
		}
		if (this.numberInQueue1 == 1) {
			insertEvent(GlobalSimulation.DEPT1, time + getNextExp(meanServ1));
		}
		insertEvent(GlobalSimulation.ARRIVAL1, time + constQ1);
	}

	private void departureFrom1() {
		this.numberInQueue1--;
		this.numberInQueue2++;
		if (numberInQueue2 == 1) {
			insertEvent(DEPT2, time + this.meanServ2);
		}
		if (this.numberInQueue1 > 0) {
			insertEvent(GlobalSimulation.DEPT1, time + getNextExp(meanServ1));
		}
	}

	private void departureFrom2() {
		servedCustomers++;
		numberInQueue2--;
		if (numberInQueue2 > 0) {
			insertEvent(DEPT2, time + this.meanServ2);
		}
	}

	private void measure() {
		_noQ1.add(numberInQueue1);
		_noQ2.add(numberInQueue2);
		noMeasurements++;
		insertEvent(MEASURE, time + getNextExp(0.2)); // tänk på warm up time
	}
	
	public double getNextExp(double lambda) {
		return Math.log(1 - slump.nextDouble()) / (-lambda);
	}
	
	public int getAvgNoQ1(){
		int acc1 = 0;
		int i = 0;
		while(_noQ1.size() > 0) {
			acc1 += _noQ1.poll();
			i++;
		}
		return acc1/i;
	}
	
	public int getAvgNoQ2(){
		int acc2 = 0;
		int i = 0;
		while(_noQ2.size() > 0) {
			acc2 += _noQ2.poll();
			i++;
		}
		return acc2/i;
	}
	
}