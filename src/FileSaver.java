import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

public class FileSaver {

	private ImageGroup ImageFile;
	final private String IMAGE_DIR = "E:\\meinvPicture";
	public static int num = 0;
	
	static Logger logger = Logger.getLogger(FileSaver.class.getName());
	
	public FileSaver(ImageGroup ImageFile){
		this.ImageFile = ImageFile;
	} 
	
	public void save(){
		   //��ArrayList��ͷ����ȡһ��ͼƬ��Ϣ,����ArrayList����ɾ��
		String name = ImageFile.getName();
		String type = ImageFile.getType();
		List<String> urlList = ImageFile.getUrlList();
		
		String filePath = getDir(type,name);
		for(int i=0;i<urlList.size();i++){
			final String url = urlList.get(i);
			String ImageFormat = url.substring(url.lastIndexOf("."));
			final String ImageDir = filePath+"\\"+i+ImageFormat;
			logger.info(ImageDir+" is downloading...");
			ThreadPool.execute(new Runnable() {
				@Override
				public void run() {
				try {
					num++;
					new ImageDownloader(ImageDir,url).download();
					num--;
					logger.info(ImageDir+" has been downloaded.Downloading threads = "+num);
				} catch (Exception e) {
					logger.info(e.toString());
					num--;
					logger.info("*********** "+ImageDir+" has been downloaded error.Downloading threads = "+num);
					//Thread.interrupted();
				}
				}
			});
		}
	}
	
	//�õ�����ͼƬ�Ĵ洢·��
	private String getDir(String type,String name) {
		String typePath = IMAGE_DIR + "\\" + type;
		File typeDir = new File(typePath);                    //�õ�����ͼƬ����type��·����û�оʹ���
		if (!typeDir.exists()) {
			typeDir.mkdir();
			}
		String filePath = typeDir+"\\"+name;
		File fileDir = new File(filePath);                   //�õ�����ͼƬ���ļ�·��
		if(!fileDir.exists()){
			fileDir.mkdir();
		}
		logger.info(filePath+" has been existed.");
		return filePath;
	}
}
