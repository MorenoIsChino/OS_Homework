package ex_02_processScheduling;


public class Scheduling {
	
	/**
	 * @author 梁博泓
	 * 
	 */

	public int processNum;
	Process arrayProcess[]=new Process[10];
	
	public Scheduling(String[] info) {
		
		// 进程的数量
		processNum = Integer.parseInt(info[1]);
		
		// 循环赋值
		for (int i = 0; i < processNum; i++) {
			arrayProcess[i]=new Process(Integer.parseInt(info[2+i*4]), 
										Integer.parseInt(info[3+i*4]), 
										Integer.parseInt(info[4+i*4]), 
										Integer.parseInt(info[5+i*4]));
		}
	}
	
	// Just For Test : 显示每一个Process 的信息
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
	
	// 检测当前算法是否调度完成
	public boolean isFininshed() {
		boolean judgement=true;
		for (int i = 0; i < processNum; i++) {
			// describe:如果有进程的 cpuBurstTime 不为0 ,  返回 false
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
	
	// 到达情况检查
	public void isArrivedCheck(int currentTime) {
		for (int i = 0; i < processNum; i++) {
			Process currentProcess=this.arrayProcess[i];
			// 当进程已经到达任务队列
			if(currentProcess.arrivalTime<=currentTime) {
				// 到达后立即等待
				currentProcess.isWaiting=true;	
				// 排除已经完成的线程
				if(currentProcess.cpuBurstTime==0) {
					currentProcess.isWaiting=false;	

				}
			}
			
		}
	}
	// 每秒的运行情况
	public void executePerSeconds(int currentTime) {
		for (int i = 0; i < processNum; i++) {
			Process currentProcess=this.arrayProcess[i];
			// 进程等待 & 不运行
			if(currentProcess.isWaiting&&(!currentProcess.isExecuting&&currentProcess.cpuBurstTime>=0)) {
				// 增加等待时间
				currentProcess.waitTime+=1;
//				// test
//				System.out.println(currentProcess.processNo+" "+currentProcess.waitTime+" "+currentProcess.executeTime);
				
			}
			// 进程运行 & 不等待
			if(!currentProcess.isWaiting&&currentProcess.isExecuting) {
				// 增加 运行时间
				currentProcess.executeTime+=1;				
//				// test 
//				System.out.println(currentProcess.processNo+" "+currentProcess.waitTime+" "+currentProcess.executeTime);
				// cpuBurstTime -1 s
				currentProcess.cpuBurstTime--;
				// 输出该时刻 运行的线程信息
				System.out.println(currentTime+"		"+currentProcess.processNo); // 空格距离 \t\t

				
			}
			
		}
	}
	
	//	1)  FCFS算法；
	public void FCFS() {
		// 计时标记
		int    timeCounter=0;
		
		double sumWaitingTime=0;
		double averageWaitingTime=0;
		double sumCompletionTime=0;
		double averageCompletionTime=0;
		
		System.out.println();
		System.out.println("****	FCFS 算法	****");
		System.out.println();
		System.out.println("时刻		进程序号"); // 空格距离 \t\t
		System.out.println("----------------------");
		
		// 调度开始
		while(!isFininshed()) {
			timeCounter++;
			isArrivedCheck(timeCounter);
			
			// ！！！！ Note: Core Algorithm	!!!!
			// 确定当前应当运行的额线程
			int timeAtFirst=9999999;
			int beExecuted=-1;
			for (int i = 0; i < processNum; i++) {
				Process currentProcess=this.arrayProcess[i];
				// 只有 正在等待 且 没有完成 的线程才可以被讨论
				if(currentProcess.isWaiting&&currentProcess.cpuBurstTime!=0) {
					if(currentProcess.arrivalTime<=timeAtFirst) {
						beExecuted=i;
						// 更新标记时间
						timeAtFirst=currentProcess.arrivalTime;
					}
				}
				// 暂时取消所有的 进程的 isExecuting =True
				currentProcess.isExecuting=false;
			}
			// 将最终的 i 
			// 对应的线程，设置 为
			// isExecuting =True
			// isWaiting=false
			this.arrayProcess[beExecuted].isExecuting=true;
			this.arrayProcess[beExecuted].isWaiting=false;
			// ↑↑↑ Finished ↑↑↑

			
			executePerSeconds(timeCounter);
		}
				
		
		// 过程结束
		System.out.println("----------------------");

		
		// 计算平均时间
		for (int i = 0; i < processNum; i++) {
			sumCompletionTime=sumCompletionTime+this.arrayProcess[i].getCompletionTime();
			sumWaitingTime=sumWaitingTime+this.arrayProcess[i].waitTime;
//			//test
//			System.out.println(this.arrayProcess[i].processNo+" "+this.arrayProcess[i].waitTime+" "+this.arrayProcess[i].executeTime);

		}
		averageWaitingTime=sumWaitingTime/this.processNum;
		averageCompletionTime=sumCompletionTime/this.processNum;
		
		// 输出结果
		System.out.print("平均等待时间 ：");
		System.out.println(averageWaitingTime+" s");
		System.out.print("平均周转等待时间 ：");
		System.out.println(averageCompletionTime+" s");




	}
	
	//	2)        时间片为5的RR；
	public void RR_quantum_5() {
		// 计时标记
		int    timeCounter=0;
		
		double sumWaitingTime=0;
		double averageWaitingTime=0;
		double sumCompletionTime=0;
		double averageCompletionTime=0;
		
		// 时间片
		int quantum=5;
		
		System.out.println();
		System.out.println("****	RR_quantum_5 算法	****");
		System.out.println();
		System.out.println("时刻		进程序号"); // 空格距离 \t\t
		System.out.println("----------------------");
		
		// 调度开始
		timeCounter++;
		int tip=-1; // 当前运行标记位
		while(!isFininshed()) {
			isArrivedCheck(timeCounter);
			
			// ！！！！ Note: Core Algorithm	!!!!
			
			//  在当前所有线程中从 标记位 开始,寻找下一个 isWaiting 但不 isExecuting 的process
			while(true) {
				
				// next
				tip++;
				
				// 超出数组范围要重新置零
				if(tip>=this.processNum) tip=0;
				
				if(arrayProcess[tip].isWaiting&&!arrayProcess[tip].isExecuting)
				{
					// test
//					System.out.println("Get Process"+arrayProcess[tip].processNo);
					break;
				}
				else {
					// 继续寻找
					// test
//					System.out.println(" Finding...........");
					continue;
				}
			}
			
			// RR quantun=5 循环
			for(int i=0;i<quantum;i++) {
				// 检查到达
				isArrivedCheck(timeCounter);
				// 改变 应该运行的 进程 的状态
				this.arrayProcess[tip].isWaiting=false;
				this.arrayProcess[tip].isExecuting=true;
				
				// 运行
				executePerSeconds(timeCounter);
				// 时间递增
				timeCounter++;
				
				// 如果当前为 第五次循环， 重置运行的线程的状态
				if (i==quantum-1) {
					this.arrayProcess[tip].isWaiting=true;
					this.arrayProcess[tip].isExecuting=false;
				}
				// 如果当前进程的 CPU burst Time 结束， 应当立即终止当前循环
				if (this.arrayProcess[tip].cpuBurstTime==0) {
					break;
				}			
			}	
		}		
		// ↑↑↑ Finished ↑↑↑
				
		
		// 过程结束
		System.out.println("----------------------");

		
		// 计算平均时间
		for (int i = 0; i < processNum; i++) {
			sumCompletionTime=sumCompletionTime+this.arrayProcess[i].getCompletionTime();
			sumWaitingTime=sumWaitingTime+this.arrayProcess[i].waitTime;
//			//test
//			System.out.println(this.arrayProcess[i].processNo+" "+this.arrayProcess[i].waitTime+" "+this.arrayProcess[i].executeTime);

		}
		averageWaitingTime=sumWaitingTime/this.processNum;
		averageCompletionTime=sumCompletionTime/this.processNum;
		
		// 输出结果
		System.out.print("平均等待时间 ：");
		System.out.println(averageWaitingTime+" s");
		System.out.print("平均周转等待时间 ：");
		System.out.println(averageCompletionTime+" s");




	}

	//	3)        非抢占的SJF（同等条件下，序号小的进程优先）；
	public void SJF_NonPreemptive() {
		// 计时标记
		int    timeCounter=0;
		
		double sumWaitingTime=0;
		double averageWaitingTime=0;
		double sumCompletionTime=0;
		double averageCompletionTime=0;
		
		System.out.println();
		System.out.println("****	SJF_NonPreemptive 算法	****");
		System.out.println();
		System.out.println("时刻		进程序号"); // 空格距离 \t\t
		System.out.println("----------------------");
		
		// 调度开始
		timeCounter=1;
		int tempCpuBurstTime,tip,tempProcessNo;
			
		isArrivedCheck(timeCounter);
		while(!isFininshed()) {
			// 寻找被运行的线程
			tempCpuBurstTime=99999;
			tempProcessNo=-1;
			tip=-1;
			for (int i = 0; i < processNum; i++) {
				// test
//				System.out.println("Find Process............");
				// 在当前所有isWaiting且 cpuBurstTime>=0 的进程中
				if(this.arrayProcess[i].isWaiting&&this.arrayProcess[i].cpuBurstTime>=0) {
					// 寻找 cpuBurstTime 最少的
					if (this.arrayProcess[i].cpuBurstTime<tempCpuBurstTime) {
						tempCpuBurstTime=this.arrayProcess[i].cpuBurstTime;
						tempProcessNo=this.arrayProcess[i].processNo;
						tip=i;
						continue;
					}
					// 相同条件下
					// 序号小的优先
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


			// 执行该线程直到该线程完成
			while (true) {
				isArrivedCheck(timeCounter);
				// 设置线程状态变化
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
				
		
		// 过程结束
		System.out.println("----------------------");

		
		// 计算平均时间
		for (int i = 0; i < processNum; i++) {
			sumCompletionTime=sumCompletionTime+this.arrayProcess[i].getCompletionTime();
			sumWaitingTime=sumWaitingTime+this.arrayProcess[i].waitTime;
//			//test
//			System.out.println(this.arrayProcess[i].processNo+" "+this.arrayProcess[i].waitTime+" "+this.arrayProcess[i].executeTime);

		}
		averageWaitingTime=sumWaitingTime/this.processNum;
		averageCompletionTime=sumCompletionTime/this.processNum;
		
		// 输出结果
		System.out.print("平均等待时间 ：");
		System.out.println(averageWaitingTime+" s");
		System.out.print("平均周转等待时间 ：");
		System.out.println(averageCompletionTime+" s");




	}

	//	4)        可抢占的SRTF（同等条件下，序号小的进程优先）；
	public void SRTF_Preemptive() {
		// 计时标记
		int    timeCounter=0;
		
		double sumWaitingTime=0;
		double averageWaitingTime=0;
		double sumCompletionTime=0;
		double averageCompletionTime=0;
		
		System.out.println();
		System.out.println("****	SRTF_Preemptive 算法	****");
		System.out.println();
		System.out.println("时刻		进程序号"); // 空格距离 \t\t
		System.out.println("----------------------");
		
		// 调度开始
		int tempCPUBurstTime;
		int tempProcessNo;
		int tipOfLeastCPUBrustTimeProcess;
		while(!isFininshed()) {

			timeCounter++;
			isArrivedCheck(timeCounter);
			
			// ！！！！ Note: Core Algorithm	!!!!
			
			// 寻找 isWaiting 的进程中 CPU burst Time 最小的
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
						// 相同条件下
						// 序号小的优先						
						tempCPUBurstTime=this.arrayProcess[i].cpuBurstTime;
						tempProcessNo=this.arrayProcess[i].processNo;
						tipOfLeastCPUBrustTimeProcess=i;
					}
				}
			}
			// 设置当前需要运行的线程的状态
			this.arrayProcess[tipOfLeastCPUBrustTimeProcess].isWaiting=false;
			this.arrayProcess[tipOfLeastCPUBrustTimeProcess].isExecuting=true;
			// ↑↑↑ Finished ↑↑↑

			executePerSeconds(timeCounter);
			// tset
			// 打印所有线程的  processNo cpuBurstTime isWaiting
//			for(int i=0;i<this.processNum;i++) {
//				System.out.print(this.arrayProcess[i].processNo+"_"+this.arrayProcess[i].cpuBurstTime+"_"+this.arrayProcess[i].isWaiting+" **** ");
//			}
//			System.out.println();
			// 去除运行状态 
			this.arrayProcess[tipOfLeastCPUBrustTimeProcess].isExecuting=false;
		
		}
				
		
		// 过程结束
		System.out.println("----------------------");

		
		// 计算平均时间
		for (int i = 0; i < processNum; i++) {
			sumCompletionTime=sumCompletionTime+this.arrayProcess[i].getCompletionTime();
			sumWaitingTime=sumWaitingTime+this.arrayProcess[i].waitTime;
//			//test
//			System.out.println(this.arrayProcess[i].processNo+" "+this.arrayProcess[i].waitTime+" "+this.arrayProcess[i].executeTime);

		}
		averageWaitingTime=sumWaitingTime/this.processNum;
		averageCompletionTime=sumCompletionTime/this.processNum;
		
		// 输出结果
		System.out.print("平均等待时间 ：");
		System.out.println(averageWaitingTime+" s");
		System.out.print("平均周转等待时间 ：");
		System.out.println(averageCompletionTime+" s");




	}

	//	5)        非抢占的优先级调度算法（序号小者为高优先级）。
	public void PriorityScheduling_NonPreemptive() {
		// 计时标记
		int    timeCounter=0;
		
		double sumWaitingTime=0;
		double averageWaitingTime=0;
		double sumCompletionTime=0;
		double averageCompletionTime=0;
		
		System.out.println();
		System.out.println("****	PriorityScheduling_NonPreemptive 算法	****");
		System.out.println();
		System.out.println("时刻		进程序号"); // 空格距离 \t\t
		System.out.println("----------------------");
		
		// 调度开始
		timeCounter=1;
		int tempPriority;
		int tip;
		while(!isFininshed()) {
			isArrivedCheck(timeCounter);
			// 寻找未结束的任务中优先级最高的
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
			
			// 运行该线程直到其结束
			while(this.arrayProcess[tip].cpuBurstTime>0) {
				// 调整线程状态
				this.arrayProcess[tip].isWaiting=false;
				this.arrayProcess[tip].isExecuting=true;
				// 运行 1 S
				executePerSeconds(timeCounter);
				
				timeCounter++;
				if(this.arrayProcess[tip].cpuBurstTime<=0) {
					this.arrayProcess[tip].isExecuting=false;
					break;
				}
			}
		}
				
		
		// 过程结束
		System.out.println("----------------------");

		
		// 计算平均时间
		for (int i = 0; i < processNum; i++) {
			sumCompletionTime=sumCompletionTime+this.arrayProcess[i].getCompletionTime();
			sumWaitingTime=sumWaitingTime+this.arrayProcess[i].waitTime;
//			//test
//			System.out.println(this.arrayProcess[i].processNo+" "+this.arrayProcess[i].waitTime+" "+this.arrayProcess[i].executeTime);

		}
		averageWaitingTime=sumWaitingTime/this.processNum;
		averageCompletionTime=sumCompletionTime/this.processNum;
		
		// 输出结果
		System.out.print("平均等待时间 ：");
		System.out.println(averageWaitingTime+" s");
		System.out.print("平均周转等待时间 ：");
		System.out.println(averageCompletionTime+" s");




	}

	
}
