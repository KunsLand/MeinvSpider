package diao.si.common;
import java.io.File;


public class Configure {
	
	public final static String FILE_ROOT = "E:\\meinvPicture\\";
	
	public final static String INVALID_CHARSET = "[\\\\/:*?\"<>|]";
	
	static{
		File file = new File(FILE_ROOT);
		if(!file.exists())
			file.mkdirs();
	}
}
