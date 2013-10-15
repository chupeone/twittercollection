package example;

import java.io.File;

public class mainymain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		File file=new File("C:\\Users\\Juan\\Dropbox\\MonitoredbyTwitterAggregator\\2013-5-5-14.txt");
		File file=new File("C:\\Users\\Juan\\Desktop\\officemovebuffer_eustonone.txt");

		ParseFromFile pff=new ParseFromFile();
		pff.Parse(file);
//		int num=30;
//		System.out.println(String.format("%04d", num));
	}

}
