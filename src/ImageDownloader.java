import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class ImageDownloader {

	//final static String IMAGE_DIR = "D:\\meinvPicture";
	final private static int TIME_OUT = 5000;
	private String ImageDir;
	private String url;
	
	static Logger logger = Logger.getLogger(ImageDownloader.class.getName());
	
	public ImageDownloader(String ImageDir , String url){
		this.ImageDir = ImageDir;
		this.url =url;
	}
	
	public void download() throws Exception {
		//String fileName = url.substring(url.lastIndexOf("/"));//图片文件名
		//String filePath = IMAGE_DIR + "\\" + fileName;
		BufferedOutputStream out = null;
		byte[] bit = getByte(url);
		//logger.info("I have got the picture byte stream.");
		if (bit.length > 0) {
			try {
				out = new BufferedOutputStream(new FileOutputStream(ImageDir));//写到文件filePath中
				out.write(bit);
				out.flush();
				// log.info("Create File success! [" + filePath + "]");
			} finally {
				if (out != null)
					out.close();
			}
		}
	}

	/**
	 * 获取图片字节流
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public byte[] getByte(String url) throws Exception {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(url);// HTTP Get请求(the same to POST)
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(TIME_OUT).setConnectTimeout(TIME_OUT).build();// 设置请求和传输超时时间
			httpGet.setConfig(requestConfig);
			httpClient.execute(httpGet);// 执行请求
			CloseableHttpResponse response = httpClient.execute(httpGet);
			try {
				//判断数据是否正常返回
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						return EntityUtils.toByteArray(entity); //得到字节流
					}
				}
			} finally {
				response.close();
			}
		} finally {
			httpClient.close();
		}
		return new byte[0];
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filePath = "D:\\meinvPicture";
		String url = "http://p.aimm-img.com/uploads/allimg/140320/2-140320152154.jpg";
		String ImageDir = filePath+"\\"+1+url.substring(url.lastIndexOf("."));
		//String ImageDir = filePath+"\\"+url.substring(url.lastIndexOf("/"));
	    ImageDownloader test = new ImageDownloader(ImageDir,url);
		try {
			test.download();
			System.out.println("The picture is in your file !");
		} catch (Exception e) {
			System.out.println("Something has been run error !");
		}
	}
}
