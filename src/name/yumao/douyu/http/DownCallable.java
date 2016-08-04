package name.yumao.douyu.http;

import java.util.concurrent.Callable;

public class DownCallable implements Callable<String> {
	
	private  PlaylistDownloader33333 downloader ; // = new ConcurrentLinkedQueue<String>();
	 public DownCallable(PlaylistDownloader33333 downloader ) {
		this.downloader  = downloader;
	}

	@Override
	public String call() {
	
	     while(true) { 
	    	 
	  		try {
	    	// System.out.println("downloader");
	  			downloader.down();
				Thread.sleep(500);
	  		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     }
	}

}
