package ex_02_processScheduling;


public class Scheduling {
	
	/**
	 * @author ������
	 * 
	 */

	public int processNum;
	Process arrayProcess[]=new Process[10];
	
	public Scheduling(String[] info) {
		
		// ���̵�����
		processNum = Integer.parseInt(info[1]);
		
		// ѭ����ֵ
		for (int i = 0; i < processNum; i++) {
			arrayProcess[i]=new Process(Integer.parseInt(info[2+i*4]), 
										Integer.parseInt(info[3+i*4]), 
										Integer.parseInt(info[4+i*4]), 
										Integer.parseInt(info[5+i*4]));
		}
	}
	
	// Just For Test : ��ʾÿһ��Process ����Ϣ
	public void showInformationOfProcesses() {
		
		for (int i = 0; i < processNum; i++) {
		   	 System.out.println(this.arrayProcess[i].processNo);
		   	 System.out.println(this.arrayProcess[i].arrivalTime);
		   	 System.out.println(this.arrayProcess[i].cpuBurstTime);
		   	 System.out.println(this.arrayProcess[i].priority);
		   	 System.out.println(this.arrayProcess[i].isArrived);
		   	 System.out.println(this.arrayProcess[i].isWaiting);
		   	 System.out.println(this.arrayProcess[i].isExecuting);
		   	 System.out.println(this.arrayProcess[i].waitTime);
		   	 System.out.println(this.arrayProcess[i].executeTime);
		   	 System.out.println(this.arrayProcess[i].getCompletionTime());

		}
   	 System.out.println();
	}
	
	// ��⵱ǰ�㷨�Ƿ�������
	public boolean isFininshed() {
		boolean judgement=true;
		for (int i = 0; i < processNum; i++) {
			// describe:����н��̵� cpuBurstTime ��Ϊ0 ,  ���� false
			if(this.arrayProcess[i].cpuBurstTime!=0) {
				judgement=false;
				break;
			}
			else continue;
		}
		
		// test
//		System.out.println(); 
		// test
//		System.out.println("isFininshed"); 
		
		return judgement;


	}
	
	// ����������
	public void isArrivedCheck(int currentTime) {
		for (int i = 0; i < processNum; i++) {
			Process currentProcess=this.arrayProcess[i];
			// �������Ѿ������������
			if(currentProcess.arrivalTime<=currentTime) {
				// ����������ȴ�
				currentProcess.isWaiting=true;	
				// �ų��Ѿ���ɵ��߳�
				if(currentProcess.cpuBurstTime==0) {
					currentProcess.isWaiting=false;	

				}
			}
			
		}
	}
	// ÿ����������
	public void executePerSeconds(int currentTime) {
		for (int i = 0; i < processNum; i++) {
			Process currentProcess=this.arrayProcess[i];
			// ���̵ȴ� & ������
			if(currentProcess.isWaiting&&(!currentProcess.isExecuting&&currentProcess.cpuBurstTime>=0)) {
				// ���ӵȴ�ʱ��
				currentProcess.waitTime+=1;
//				// test
//				System.out.println(currentProcess.processNo+" "+currentProcess.waitTime+" "+currentProcess.executeTime);
				
			}
			// �������� & ���ȴ�
			if(!currentProcess.isWaiting&&currentProcess.isExecuting) {
				// ���� ����ʱ��
				currentProcess.executeTime+=1;				
//				// test 
//				System.out.println(currentProcess.processNo+" "+currentProcess.waitTime+" "+currentProcess.executeTime);
				// cpuBurstTime -1 s
				currentProcess.cpuBurstTime--;
				// �����ʱ�� ���е��߳���Ϣ
				System.out.println(currentTime+"		"+currentProcess.processNo); // �ո���� \t\t

				
			}
			
		}
	}
	
	//	1)  FCFS�㷨��
	public void FCFS() {
		// ��ʱ���
		int    timeCounter=0;
		
		double sumWaitingTime=0;
		double averageWaitingTime=0;
		double sumCompletionTime=0;
		double averageCompletionTime=0;
		
		System.out.println();
		System.out.println("****	FCFS �㷨	****");
		System.out.println();
		System.out.println("ʱ��		�������"); // �ո���� \t\t
		System.out.println("----------------------");
		
		// ���ȿ�ʼ
		while(!isFininshed()) {
			timeCounter++;
			isArrivedCheck(timeCounter);
			
			// �������� Note: Core Algorithm	!!!!
			// ȷ����ǰӦ�����еĶ��߳�
			int timeAtFirst=9999999;
			int beExecuted=-1;
			for (int i = 0; i < processNum; i++) {
				Process currentProcess=this.arrayProcess[i];
				// ֻ�� ���ڵȴ� �� û����� ���̲߳ſ��Ա�����
				if(currentProcess.isWaiting&&currentProcess.cpuBurstTime!=0) {
					if(currentProcess.arrivalTime<=timeAtFirst) {
						beExecuted=i;
						// ���±��ʱ��
						timeAtFirst=currentProcess.arrivalTime;
					}
				}
				// ��ʱȡ�����е� ���̵� isExecuting =True
				currentProcess.isExecuting=false;
			}
			// �����յ� i 
			// ��Ӧ���̣߳����� Ϊ
			// isExecuting =True
			// isWaiting=false
			this.arrayProcess[beExecuted].isExecuting=true;
			this.arrayProcess[beExecuted].isWaiting=false;
			// ������ Finished ������

			
			executePerSeconds(timeCounter);
		}
				
		
		// ���̽���
		System.out.println("----------------------");

		
		// ����ƽ��ʱ��
		for (int i = 0; i < processNum; i++) {
			sumCompletionTime=sumCompletionTime+this.arrayProcess[i].getCompletionTime();
			sumWaitingTime=sumWaitingTime+this.arrayProcess[i].waitTime;
//			//test
//			System.out.println(this.arrayProcess[i].processNo+" "+this.arrayProcess[i].waitTime+" "+this.arrayProcess[i].executeTime);

		}
		averageWaitingTime=sumWaitingTime/this.processNum;
		averageCompletionTime=sumCompletionTime/this.processNum;
		
		// ������
		System.out.print("ƽ���ȴ�ʱ�� ��");
		System.out.println(averageWaitingTime+" s");
		System.out.print("ƽ����ת�ȴ�ʱ�� ��");
		System.out.println(averageCompletionTime+" s");




	}
	
	//	2)        ʱ��ƬΪ5��RR��
	public void RR_quantum_5() {
		// ��ʱ���
		int    timeCounter=0;
		
		double sumWaitingTime=0;
		double averageWaitingTime=0;
		double sumCompletionTime=0;
		double averageCompletionTime=0;
		
		// ʱ��Ƭ
		int quantum=5;
		
		System.out.println();
		System.out.println("****	RR_quantum_5 �㷨	****");
		System.out.println();
		System.out.println("ʱ��		�������"); // �ո���� \t\t
		System.out.println("----------------------");
		
		// ���ȿ�ʼ
		timeCounter++;
		int tip=-1; // ��ǰ���б��λ
		while(!isFininshed()) {
			isArrivedCheck(timeCounter);
			
			// �������� Note: Core Algorithm	!!!!
			
			//  �ڵ�ǰ�����߳��д� ���λ ��ʼ,Ѱ����һ�� isWaiting ���� isExecuting ��process
			while(true) {
				
				// next
				tip++;
				
				// �������鷶ΧҪ��������
				if(tip>=this.processNum) tip=0;
				
				if(arrayProcess[tip].isWaiting&&!arrayProcess[tip].isExecuting)
				{
					// test
//					System.out.println("Get Process"+arrayProcess[tip].processNo);
					break;
				}
				else {
					// ����Ѱ��
					// test
//					System.out.println(" Finding...........");
					continue;
				}
			}
			
			// RR quantun=5 ѭ��
			for(int i=0;i<quantum;i++) {
				// ��鵽��
				isArrivedCheck(timeCounter);
				// �ı� Ӧ�����е� ���� ��״̬
				this.arrayProcess[tip].isWaiting=false;
				this.arrayProcess[tip].isExecuting=true;
				
				// ����
				executePerSeconds(timeCounter);
				// ʱ�����
				timeCounter++;
				
				// �����ǰΪ �����ѭ���� �������е��̵߳�״̬
				if (i==quantum-1) {
					this.arrayProcess[tip].isWaiting=true;
					this.arrayProcess[tip].isExecuting=false;
				}
				// �����ǰ���̵� CPU burst Time ������ Ӧ��������ֹ��ǰѭ��
				if (this.arrayProcess[tip].cpuBurstTime==0) {
					break;
				}			
			}	
		}		
		// ������ Finished ������
				
		
		// ���̽���
		System.out.println("----------------------");

		
		// ����ƽ��ʱ��
		for (int i = 0; i < processNum; i++) {
			sumCompletionTime=sumCompletionTime+this.arrayProcess[i].getCompletionTime();
			sumWaitingTime=sumWaitingTime+this.arrayProcess[i].waitTime;
//			//test
//			System.out.println(this.arrayProcess[i].processNo+" "+this.arrayProcess[i].waitTime+" "+this.arrayProcess[i].executeTime);

		}
		averageWaitingTime=sumWaitingTime/this.processNum;
		averageCompletionTime=sumCompletionTime/this.processNum;
		
		// ������
		System.out.print("ƽ���ȴ�ʱ�� ��");
		System.out.println(averageWaitingTime+" s");
		System.out.print("ƽ����ת�ȴ�ʱ�� ��");
		System.out.println(averageCompletionTime+" s");




	}

	//	3)        ����ռ��SJF��ͬ�������£����С�Ľ������ȣ���
	public void SJF_NonPreemptive() {
		// ��ʱ���
		int    timeCounter=0;
		
		double sumWaitingTime=0;
		double averageWaitingTime=0;
		double sumCompletionTime=0;
		double averageCompletionTime=0;
		
		System.out.println();
		System.out.println("****	SJF_NonPreemptive �㷨	****");
		System.out.println();
		System.out.println("ʱ��		�������"); // �ո���� \t\t
		System.out.println("----------------------");
		
		// ���ȿ�ʼ
		timeCounter=1;
		int tempCpuBurstTime,tip,tempProcessNo;
			
		isArrivedCheck(timeCounter);
		while(!isFininshed()) {
			// Ѱ�ұ����е��߳�
			tempCpuBurstTime=99999;
			tempProcessNo=-1;
			tip=-1;
			for (int i = 0; i < processNum; i++) {
				// test
//				System.out.println("Find Process............");
				// �ڵ�ǰ����isWaiting�� cpuBurstTime>=0 �Ľ�����
				if(this.arrayProcess[i].isWaiting&&this.arrayProcess[i].cpuBurstTime>=0) {
					// Ѱ�� cpuBurstTime ���ٵ�
					if (this.arrayProcess[i].cpuBurstTime<tempCpuBurstTime) {
						tempCpuBurstTime=this.arrayProcess[i].cpuBurstTime;
						tempProcessNo=this.arrayProcess[i].processNo;
						tip=i;
						continue;
					}
					// ��ͬ������
					// ���С������
					else if(this.arrayProcess[i].cpuBurstTime==tempCpuBurstTime&&this.arrayProcess[i].processNo<tempProcessNo) {
						tempCpuBurstTime=this.arrayProcess[i].cpuBurstTime;
						tempProcessNo=this.arrayProcess[i].processNo;
						tip=i;
						continue;
					}
					else {
						continue;
					}
				}
			}
			// test
//			System.out.println("Found Process: "+this.arrayProcess[tip].processNo);


			// ִ�и��߳�ֱ�����߳����
			while (true) {
				isArrivedCheck(timeCounter);
				// �����߳�״̬�仯
				this.arrayProcess[tip].isWaiting=false;
				this.arrayProcess[tip].isExecuting=true;
				executePerSeconds(timeCounter);
//				test
//				System.out.print("Current Process Remian Time: "+this.arrayProcess[tip].cpuBurstTime);
//				System.out.println("	isExe"+this.arrayProcess[tip].isExecuting);

				timeCounter++;
				if (this.arrayProcess[tip].cpuBurstTime<=0) {
					this.arrayProcess[tip].isExecuting=false;
					break;
				}
			}
			
		}
				
		
		// ���̽���
		System.out.println("----------------------");

		
		// ����ƽ��ʱ��
		for (int i = 0; i < processNum; i++) {
			sumCompletionTime=sumCompletionTime+this.arrayProcess[i].getCompletionTime();
			sumWaitingTime=sumWaitingTime+this.arrayProcess[i].waitTime;
//			//test
//			System.out.println(this.arrayProcess[i].processNo+" "+this.arrayProcess[i].waitTime+" "+this.arrayProcess[i].executeTime);

		}
		averageWaitingTime=sumWaitingTime/this.processNum;
		averageCompletionTime=sumCompletionTime/this.processNum;
		
		// ������
		System.out.print("ƽ���ȴ�ʱ�� ��");
		System.out.println(averageWaitingTime+" s");
		System.out.print("ƽ����ת�ȴ�ʱ�� ��");
		System.out.println(averageCompletionTime+" s");




	}

	//	4)        ����ռ��SRTF��ͬ�������£����С�Ľ������ȣ���
	public void SRTF_Preemptive() {
		// ��ʱ���
		int    timeCounter=0;
		
		double sumWaitingTime=0;
		double averageWaitingTime=0;
		double sumCompletionTime=0;
		double averageCompletionTime=0;
		
		System.out.println();
		System.out.println("****	SRTF_Preemptive �㷨	****");
		System.out.println();
		System.out.println("ʱ��		�������"); // �ո���� \t\t
		System.out.println("----------------------");
		
		// ���ȿ�ʼ
		int tempCPUBurstTime;
		int tempProcessNo;
		int tipOfLeastCPUBrustTimeProcess;
		while(!isFininshed()) {

			timeCounter++;
			isArrivedCheck(timeCounter);
			
			// �������� Note: Core Algorithm	!!!!
			
			// Ѱ�� isWaiting �Ľ����� CPU burst Time ��С��
			tempCPUBurstTime=99999;
			tempProcessNo=-1;
			tipOfLeastCPUBrustTimeProcess=-1;
			for(int i=0;i<this.processNum;i++) {
				if(this.arrayProcess[i].isWaiting) {
					if(this.arrayProcess[i].cpuBurstTime<tempCPUBurstTime) {
						tempCPUBurstTime=this.arrayProcess[i].cpuBurstTime;
						tempProcessNo=this.arrayProcess[i].processNo;
						tipOfLeastCPUBrustTimeProcess=i;
					}
					else if(this.arrayProcess[i].cpuBurstTime==tempCPUBurstTime&&this.arrayProcess[i].processNo>tempProcessNo) {
						// ��ͬ������
						// ���С������						
						tempCPUBurstTime=this.arrayProcess[i].cpuBurstTime;
						tempProcessNo=this.arrayProcess[i].processNo;
						tipOfLeastCPUBrustTimeProcess=i;
					}
				}
			}
			// ���õ�ǰ��Ҫ���е��̵߳�״̬
			this.arrayProcess[tipOfLeastCPUBrustTimeProcess].isWaiting=false;
			this.arrayProcess[tipOfLeastCPUBrustTimeProcess].isExecuting=true;
			// ������ Finished ������

			executePerSeconds(timeCounter);
			// tset
			// ��ӡ�����̵߳�  processNo cpuBurstTime isWaiting
//			for(int i=0;i<this.processNum;i++) {
//				System.out.print(this.arrayProcess[i].processNo+"_"+this.arrayProcess[i].cpuBurstTime+"_"+this.arrayProcess[i].isWaiting+" **** ");
//			}
//			System.out.println();
			// ȥ������״̬ 
			this.arrayProcess[tipOfLeastCPUBrustTimeProcess].isExecuting=false;
		
		}
				
		
		// ���̽���
		System.out.println("----------------------");

		
		// ����ƽ��ʱ��
		for (int i = 0; i < processNum; i++) {
			sumCompletionTime=sumCompletionTime+this.arrayProcess[i].getCompletionTime();
			sumWaitingTime=sumWaitingTime+this.arrayProcess[i].waitTime;
//			//test
//			System.out.println(this.arrayProcess[i].processNo+" "+this.arrayProcess[i].waitTime+" "+this.arrayProcess[i].executeTime);

		}
		averageWaitingTime=sumWaitingTime/this.processNum;
		averageCompletionTime=sumCompletionTime/this.processNum;
		
		// ������
		System.out.print("ƽ���ȴ�ʱ�� ��");
		System.out.println(averageWaitingTime+" s");
		System.out.print("ƽ����ת�ȴ�ʱ�� ��");
		System.out.println(averageCompletionTime+" s");




	}

	//	5)        ����ռ�����ȼ������㷨�����С��Ϊ�����ȼ�����
	public void PriorityScheduling_NonPreemptive() {
		// ��ʱ���
		int    timeCounter=0;
		
		double sumWaitingTime=0;
		double averageWaitingTime=0;
		double sumCompletionTime=0;
		double averageCompletionTime=0;
		
		System.out.println();
		System.out.println("****	PriorityScheduling_NonPreemptive �㷨	****");
		System.out.println();
		System.out.println("ʱ��		�������"); // �ո���� \t\t
		System.out.println("----------------------");
		
		// ���ȿ�ʼ
		timeCounter=1;
		int tempPriority;
		int tip;
		while(!isFininshed()) {
			isArrivedCheck(timeCounter);
			// Ѱ��δ���������������ȼ���ߵ�
			// isWaiting
			// cpuBurstTime>0
			// priority Highest
			tempPriority=-1;
			tip=-1;
			for (int i = 0; i < processNum; i++) {
				if(this.arrayProcess[i].cpuBurstTime>0&&this.arrayProcess[i].isWaiting) {
					if(arrayProcess[i].priority>tempPriority) {
						tempPriority=arrayProcess[i].priority;
						tip=i;
						continue;
					}
//					else if(arrayProcess[i].priority==tempPriority) {						
//					}
				}
			}
			
			// ���и��߳�ֱ�������
			while(this.arrayProcess[tip].cpuBurstTime>0) {
				// �����߳�״̬
				this.arrayProcess[tip].isWaiting=false;
				this.arrayProcess[tip].isExecuting=true;
				// ���� 1 S
				executePerSeconds(timeCounter);
				
				timeCounter++;
				if(this.arrayProcess[tip].cpuBurstTime<=0) {
					this.arrayProcess[tip].isExecuting=false;
					break;
				}
			}
		}
				
		
		// ���̽���
		System.out.println("----------------------");

		
		// ����ƽ��ʱ��
		for (int i = 0; i < processNum; i++) {
			sumCompletionTime=sumCompletionTime+this.arrayProcess[i].getCompletionTime();
			sumWaitingTime=sumWaitingTime+this.arrayProcess[i].waitTime;
//			//test
//			System.out.println(this.arrayProcess[i].processNo+" "+this.arrayProcess[i].waitTime+" "+this.arrayProcess[i].executeTime);

		}
		averageWaitingTime=sumWaitingTime/this.processNum;
		averageCompletionTime=sumCompletionTime/this.processNum;
		
		// ������
		System.out.print("ƽ���ȴ�ʱ�� ��");
		System.out.println(averageWaitingTime+" s");
		System.out.print("ƽ����ת�ȴ�ʱ�� ��");
		System.out.println(averageCompletionTime+" s");




	}

	
}
