package ex_01_producer$consumer;

public class Producer implements Runnable {
	
	public int[] p_bufferList;
	public int[] p_commodityStatus;
	// constructor
	public Producer(int[] bufferList,int[] commodityStatus) {
		 p_bufferList=bufferList;
		 p_commodityStatus=commodityStatus;

	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int position=0;
		
		for (int i = 0; i < p_commodityStatus.length; i++) {
			
		// Tips:检查可用的buffer位置
			while(true) {
				position=check(p_bufferList);
				if(position!=-1) {
					break;
				}
				else {
					System.out.println("!!!!!There isn't empty buffer area,Producer Sleep 0.5 seconds.");
					try {
						// Tips:线程睡眠
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
//						e.printStackTrace();
					}
					continue;
				}
			}
		// "product_i+1" 给buffer中填充 1~32的整数，代表商品名，而且非 0 数也使得consumer不能使用
			p_bufferList[position]=i+1;

			System.out.println("Produce product_"+(i+1)+" into "+(position+1)+"st buffer block");
			
		//  update ：更新 commodityStatus[]
		//  0->1
			p_commodityStatus[i]=1;
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}
	}
	
	public int check(int[] bufferList) {
		int len=bufferList.length;
		int result; 
		result=-1;

		for(int i=0;i<len;i++) {
			if(bufferList[i]==0) {
				result=i;
				break;
			}
			else {
				continue;
			}
		}
		if(result!=-1) {
			// describe:当找到可用缓冲区时，首先将缓冲区下的整数赋值为非零的无意义整数，使得consumer线程不能使用该缓冲区
			bufferList[result]=9999;
		}
		return result;
	}
	
	public void output(int position,int[] commodityStatus) {
		commodityStatus[position-1]=1;
	}

}
