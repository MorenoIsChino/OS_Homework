package ex_01_producer$consumer;

public class Consumer implements Runnable {
	
	public int[] p_bufferList;
	public int[] p_commodityStatus;
	// constructor
	public Consumer(int[] bufferList,int[] commodityStatus) {
		 p_bufferList=bufferList;
		 p_commodityStatus=commodityStatus;

	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int position=0;
		int productName=0;
		
		for (int i = 0; i < p_commodityStatus.length; i++) {
			
		// Tips:�����õ�bufferλ��
			while(true) {
				position=check(p_bufferList);
				if(position!=-1) {
					break;
				}
				else {
					System.out.println("					!!!!!There isn't full buffer area,Consumer Sleep 0.5 seconds.");
					try {
						// Tips:�߳�˯��
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
//						e.printStackTrace();
					}
					continue;
				}
			}
		// Tips:�����Ʒ��
			productName=p_bufferList[position];
		// Tips:��������buffer��ʹ��product ����ʹ�øû�����
			p_bufferList[position]=0;
			
			System.out.println("					Consume product_"+productName+" from "+(position+1)+"st buffer block");
			
			//  update ������ commodityStatus[]
			//  1->2
			p_commodityStatus[i]=2;
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
			if(bufferList[i]!=0) {
				result=i;
				break;
			}
			else {
				continue;
			}
		}

		return result;
	}
	
	public void output(int position,int[] commodityStatus) {
		commodityStatus[position-1]=2;
	}


}
