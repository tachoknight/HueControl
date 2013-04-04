package tachoknight.wantstobe.anearlyriser;

/**
 * Hello world!
 * 
 */
public class App
{
	public static void main(String[] args)
	{
		HueConfiguration hc = new HueConfiguration("10.0.1.28", "javaRemote", "javaRemote");
		BulbFactory bf = new BulbFactory(hc);

		System.out.println("Hello World!");
	}
}
