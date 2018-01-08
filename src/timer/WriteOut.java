package timer;

import java.io.*;

public class WriteOut {
	public long TJstartTime;
	public long TSstartTime;
	public long TJendTime;
	public long TSendTime;
	
	public void writeTofileSearch(){
		try{
			FileOutputStream out = new FileOutputStream("/home/ubuntu/log.txt",true);
			
			PrintStream p = new PrintStream(out);
			
			p.print(this.TSendTime-this.TSstartTime+"\n");
			
			//p.print(this.TJendTime-this.TJstartTime+"\n");
			
			p.close();
			out.close();
		} catch(Exception e){
			System.out.println("error");
		}
	}
	
	public void writeTofileJdbc(){
		try{
			FileOutputStream out = new FileOutputStream("/home/ubuntu/logJdbc.txt",true);
			
			PrintStream p = new PrintStream(out);
			
			p.print(this.TJendTime-this.TJstartTime+"\n");
			
			//p.print(this.TJendTime-this.TJstartTime+"\n");
			
			p.close();
			out.close();
		} catch(Exception e){
			System.out.println("error");
		}
	}
}
