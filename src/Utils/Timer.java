package Utils;

public class Timer {
	private long startTime;
	
	public Timer(){
		this.start();
	}
	public void start(){
		startTime = System.currentTimeMillis();
	}
	public long getTime(){
		return (System.currentTimeMillis()-startTime)/((long)1000);
	}
	public void reset(){
		startTime=0;
	}
	public void set(long time){
		startTime=time;
	}
}
