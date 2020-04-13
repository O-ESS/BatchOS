import java.util.concurrent.atomic.AtomicBoolean;

public class Process extends Thread {
	
	public int processID;
    ProcessState status = ProcessState.New;

    /*flag to break out of the loop simulating the transition
     * from the blocked state to the running state
     */
	AtomicBoolean flag = new AtomicBoolean(false);
	
	public Process(int m) {
		processID = m;
	}

	public void loop(){ //process is blocked
		flag.set(false);
		while (!flag.get()){Thread.onSpinWait();} //will wait until flag is set
	}

	@Override
	public void run() {
		switch(processID)
		{
		case 1:process1();break;
		case 2:process2();break;
		case 3:process3();break;
		case 4:process4();break;
		case 5:process5();break;
		}
	}
	
	private void process1() {
		OperatingSystem.inputMutex.acquire(this);
		//TODO: print semaphore goes here (inc)
		//TODO: read semaphore goes here (inc)

		OperatingSystem.printText("Enter File Name: ");
		OperatingSystem.printText(OperatingSystem.readFile(OperatingSystem.TakeInput()));

		OperatingSystem.inputMutex.release(this);
		//TODO: print semaphore goes here (dec)
		//TODO: read semaphore goes here (dec)

		setProcessState(this,ProcessState.Terminated);
	}
	
	private void process2(){

		OperatingSystem.inputMutex.acquire(this);
		//TODO: print semaphore goes here (inc)

		OperatingSystem.printText("Enter File Name: ");
		String filename= OperatingSystem.TakeInput();
		OperatingSystem.printText("Enter Data: ");
		String data= OperatingSystem.TakeInput();

		OperatingSystem.inputMutex.release(this);
		//TODO: print semaphore goes here (dec)

		OperatingSystem.writeMutex.acquire(this);

		OperatingSystem.writefile(filename,data);

		OperatingSystem.writeMutex.release(this);

		setProcessState(this,ProcessState.Terminated);
	}

	private void process3() {
		int x=0;
		//TODO: print semaphore goes here (inc)

		while (x<301)
		{ 
			OperatingSystem.printText(x+"\n");
			x++;
		}

		//TODO: print semaphore goes here (dec)

		setProcessState(this,ProcessState.Terminated);
	}
	
	private void process4() {
		//TODO: print semaphore goes here (inc)

		int x=500;
		while (x<1001)
		{
			OperatingSystem.printText(x+"\n");
			x++;
		}

		//TODO: print semaphore goes here (dec)

		setProcessState(this,ProcessState.Terminated);
	}

	private void process5(){
		OperatingSystem.inputMutex.acquire(this);
		//TODO: print semaphore goes here (inc)

		OperatingSystem.printText("Enter LowerBound: ");
		String lower= OperatingSystem.TakeInput();
		OperatingSystem.printText("Enter UpperBound: ");
		String upper= OperatingSystem.TakeInput();

		OperatingSystem.inputMutex.release(this);
		//TODO: print semaphore goes here (dec)

		int lowernbr=Integer.parseInt(lower);
		int uppernbr=Integer.parseInt(upper);
		StringBuilder data= new StringBuilder();
		
		while (lowernbr<=uppernbr)
		{
			data.append(lowernbr++).append("\n");
		}

		OperatingSystem.writeMutex.acquire(this);

		OperatingSystem.writefile("P5.txt", data.toString());

		OperatingSystem.writeMutex.release(this);

		setProcessState(this,ProcessState.Terminated);
	}
	
	 public static void setProcessState(Process p, ProcessState s) {
		 p.status=s;
		 if (s == ProcessState.Terminated)
		 {
			 OperatingSystem.ProcessTable.remove(p);
		 }
	}
	 @SuppressWarnings("unused")
	 public static ProcessState getProcessState(Process p) {
		 return p.status;
	}
}
