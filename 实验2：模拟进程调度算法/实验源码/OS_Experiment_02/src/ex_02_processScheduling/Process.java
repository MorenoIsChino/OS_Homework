package ex_02_processScheduling;

public class Process {
	
	/**
	 * @author ΑΊ²©γό
	 * 
	 */
	
	public int processNo;
	public int arrivalTime;
	public int cpuBurstTime;
	public int priority ;
	public boolean isArrived;
	public boolean isWaiting;
	public boolean isExecuting;
	public double waitTime;
	public double executeTime;
	public double completionTime;
	
	
	public Process(int processNo, int arrivalTime,int CpuBurstTime,int priority){
		
		this.processNo=processNo;
		this.arrivalTime=arrivalTime;
		this.cpuBurstTime=CpuBurstTime;
		this.priority=priority;
		this.isArrived=false;
		this.isWaiting=false;
		this.isExecuting=false;
		this.waitTime=0;
		this.executeTime=0;
		this.completionTime=0;
	}
	
	public double getCompletionTime() {
		completionTime=this.waitTime+this.executeTime;
		return completionTime;
	}
	
}
