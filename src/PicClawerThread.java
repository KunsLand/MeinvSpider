
public class PicClawerThread extends Thread {
	private String url;
	private PicClawer pc;
	
	public PicClawerThread(String category, PicClawer pc){
		this.url = "http://www.95mm.com/category/"+category;
		this.pc = pc;
	}
	
	public void run(){
		pc.task(url);
	}

	public static void main(String[] args) {
		PicClawer pc = new PicClawer();
		String[] categories = { "qingchun", "xinggan", "mingxing", "mote",
				"wangluo", "siwa" };
//		new PicClawerThread(categories[3], pc).start();
		for(int i=0; i<categories.length;i++)
			new PicClawerThread(categories[i], pc).start();
	}
	
}
