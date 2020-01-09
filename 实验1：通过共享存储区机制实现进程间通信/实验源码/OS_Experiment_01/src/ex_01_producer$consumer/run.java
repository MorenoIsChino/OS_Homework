package ex_01_producer$consumer;

public class run {
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int[] bufferList=new int[16];		// bufferList:����16����������
											// 0 : empty; 1~32: ��0������full
		int[] commodityStatus=new int[32]; //  commodityStatus:�ܼ�����32����Ʒ�����ﱣ�����ǵ�״̬��
										   //  0 : none; 1: produced; 2: consumed;
		

		Producer producer=new Producer(bufferList, commodityStatus);
		Consumer consumer=new Consumer(bufferList, commodityStatus);
		
		Thread threadOfProducer=new Thread(producer);
		Thread threadOfConsumer=new Thread(consumer);
		
		while(checkCommodityStatus(commodityStatus)) {
			// tips:�����߳�
			threadOfProducer.start();
			threadOfConsumer.start();
		}		
	}
	
	// Tips: ��鵱ǰ32����Ʒ�Ƿ�ȫ����������������
	public static boolean checkCommodityStatus(int[] status) {
		
		 int judge;
		 		 judge=0;
		 for (int i = 0; i < status.length; i++) {
			if(status[i]==2) {
				judge=i+1;
			}
			else {
				break;
			}
		}
		 if(judge==status.length)return false;
		 else return true;
			}
}
