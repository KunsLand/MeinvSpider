import java.util.ArrayList;

import org.apache.log4j.Logger;


public class DataHandler {

	private ArrayList<ImageGroup> dataList;
	private boolean flag;
	
	static Logger logger = Logger.getLogger(DataHandler.class.getName());
	public DataHandler(){
		dataList = new ArrayList<ImageGroup>();
		flag = false;
	}
	
	public void add(ImageGroup fileData){
		dataList.add(fileData);         //��һ��ͼƬ����Ϣ��ӵ�ArrayList��
		
		//flagΪfalse��ʾ����û�д������ݵ��߳������У����Դ����̲߳���flagΪtrue
		if(flag == false){
			flag = true;
			logger.info("Flag is : "+flag+" , Create a new thread...");
			new Thread(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(!dataList.isEmpty()){   //�жϴ洢ͼƬ��Ϣ��ArrayList�Ƿ�Ϊ�գ���Ϊ�ռ�������       
						ImageGroup ImageFile = dataList.get(0);      //Ϊ��������ѭ������flagΪfalse,�߳̽���
						new FileSaver(ImageFile).save();	
						dataList.remove(0);
						
						try {
							Thread.sleep(2000);
						} catch (Exception e) {
						// TODO: handle exception
						}
					}
					
					flag =false;
					logger.info("Flag is : "+flag+" , the thread is over .");
				}
			}).start();	
		}
	}
}
