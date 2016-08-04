package name.yumao.douyu.http;

import java.util.concurrent.Callable;

public class GetPlayListCallable implements Callable<String> {

	PlaylistDownloader33333 downloader;
	public GetPlayListCallable(PlaylistDownloader33333 downloader) {
		this.downloader = downloader;
	}
	

	@Override
	public String call() {
		 

  		  while(true){
  			 // System.out.println("getList");
	  			try {
	  				downloader.getList(downloader.getPlaylisturl() );
	  				Thread.sleep(5000);
	  			} catch (Exception e) {
	  				// TODO Auto-generated catch block
	  				e.printStackTrace();
	  			}

		}
  		  
		
	}




}
