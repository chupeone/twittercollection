package example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import twitter4j.Status;

public class Logging {
	String[] statusproblems;
	ConfigurationVariables confv=new ConfigurationVariables();
	public void Logging()
	{
		this.statusproblems[0]="problema 1";
		this.statusproblems[1]="problema 2";
		this.statusproblems[2]="problema 3";
		this.statusproblems[3]="problema 4";
		this.statusproblems[4]="problema 5";
		this.statusproblems[5]="problema 6";
	}
	public void invalidRawStatus(String rawStatus,int problem)
	{
		try {
			Calendar time=Calendar.getInstance();
			
		File file = new File(confv.getLogDir()+time.get(Calendar.YEAR)+"-"+time.get(Calendar.MONTH)+"-"+time.get(Calendar.DATE)+".txt");
        FileWriter fw;

		fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(rawStatus);
		bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void logException(String exceptionToLog,int problem)
	{
		System.out.println(exceptionToLog);
		try {
			Calendar time=Calendar.getInstance();
			
		File file = new File(confv.getLogExeptionDir()+time.get(Calendar.YEAR)+"-"+time.get(Calendar.MONTH)+"-"+time.get(Calendar.DATE)+".txt");
        FileWriter fw;

		fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);

		bw.write(exceptionToLog+ System.getProperty( "line.separator" ));
		bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void logTerribleException(String exceptionToLog,int problem)
	{
		try {
			Calendar time=Calendar.getInstance();
			
		File file = new File(confv.getTerribleLogExeptionDir()+time.get(Calendar.YEAR)+"-"+time.get(Calendar.MONTH)+"-"+time.get(Calendar.DATE)+".txt");
        FileWriter fw;

		fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(exceptionToLog+ System.getProperty( "line.separator" ));
		bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
