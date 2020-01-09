package ex_01_producer$consumer;

public class run {
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int[] bufferList=new int[16];		// bufferList:代表16个共享缓冲区
											// 0 : empty; 1~32: 非0整数，full
		int[] commodityStatus=new int[32]; //  commodityStatus:总计生成32件商品，这里保存它们的状态，
										   //  0 : none; 1: produced; 2: consumed;
		

		Producer producer=new Producer(bufferList, commodityStatus);
		Consumer consumer=new Consumer(bufferList, commodityStatus);
		
		Thread threadOfProducer=new Thread(producer);
		Thread threadOfConsumer=new Thread(consumer);
		
		while(checkCommodityStatus(commodityStatus)) {
			// tips:开启线程
			threadOfProducer.start();
			threadOfConsumer.start();
		}		
	}
	
	// Tips: 检查当前32件商品是否全部都被生产、消费
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
