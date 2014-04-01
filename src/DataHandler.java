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
		dataList.add(fileData);         //将一组图片的信息添加到ArrayList中
		
		//flag为false表示现在没有处理数据的线程在运行，所以创建线程并置flag为true
		if(flag == false){
			flag = true;
			logger.info("Flag is : "+flag+" , Create a new thread...");
			new Thread(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(!dataList.isEmpty()){   //判断存储图片信息的ArrayList是否为空，不为空继续下载       
						ImageGroup ImageFile = dataList.get(0);      //为空则跳出循环，置flag为false,线程结束
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
