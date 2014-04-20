package cc.aimm;

public class PicClawerManager extends Thread{
	private String url;
	private PicClawer pc;
	
	public PicClawerManager(String category){
		this.url = "http://www.95mm.com/category/"+category;
		this.pc = new PicClawer(url);
	}
	
	public void run(){
		pc.task();
	}

	public static void main(String[] args) {
		String[] categories = { "qingchun", "xinggan", "mingxing", "mote",
				"wangluo", "siwa" };
//		new PicClawerManager(categories[0]).start();
		for(int i=0; i<categories.length;i++)
			new PicClawerManager(categories[i]).start();
	}
}
