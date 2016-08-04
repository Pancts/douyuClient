package name.yumao.douyu;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.JTextField;

import name.yumao.douyu.http.DownloaderThead;
import name.yumao.douyu.http.HttpClientFromDouyu;
import name.yumao.douyu.utils.MD5Util;




public class Test {
	public static void main(String[] args) {
//		BeeperControl c= new BeeperControl();
//		c.beepForAnHour();
		///System.out.println(HttpClientFromDouyu.QueryDouyuDownloadUrl("69618") );
		
		
		
		
	 	//http://capi.douyucdn.cn/api/v1/room/145201?aid=android1&client_sys=android&ne=1&support_pwd=1&time=1469155020&auth=b6f26683a970c5b0e524c47516b6c4f1
		//  http://capi.douyucdn.cn/api/v1/room/424559?aid=android1&client_sys=android&ne=1&support_pwd=1&time=1469226600&auth=fb382bc8e1ee510f2d71949cc8979c1f
		
		//String e = "room/" + "145201" + "?aid=android1&client_sys=android&ne=1&support_pwd=1&time=" + Long.valueOf(System.currentTimeMillis() / 1000L);
     
		
		//http://www.douyu.com/swf_api/room/141371?cdn=&nofan=yes&_t=24487156&sign=c2c0e502375e6425e32a6d40929d70cd
		//	API_SECRET = u'bLFlashflowlad92'
		//def get_room_info(roomid):
		//    API_URL = "http://www.douyutv.com/swf_api/room/{0}?cdn={1}&nofan=yes&_t={2}&sign={3}"
		//    API_SECRET = u'bLFlashflowlad92'
		////    ts = int(time.time()/60)
		  //  sign = hashlib.md5(("{0}{1}{2}".format(roomid, API_SECRET, ts)).encode("utf-8")).hexdigest()
		//	String date = "http://capi.douyucdn.cn/api/v1/room/145201?aid=android1&client_sys=android&ne=1&support_pwd=1&time=1469155020";
		//http://www.douyu.com/swf_api/room/624981?cdn=&nofan=yes&_t=24487213&sign=8fff4cda3585857c81635fc35a1f80ef
		
		
		//http://api.douyutv.com/api/v1/room/67373?aid=dytool2&time=1469268061&auth=5b4f468f925dc3f60128524efeec2b07
		String auth = MD5Util.MD5("room/67373?aid=dytool2&time=1469268061");
        System.out.println(auth );

        
        
    	final ExecutorService executorService = Executors.newCachedThreadPool();
    	
    	
    	executorService.submit(new Runnable() {
			
			@Override
			public void run() {
				
				while(true) {
					System.out.println("111111111111111111111111111111111111111111");
				
					try {
						Thread.sleep(1000L);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					
					}
				}
			}
		});
    	
    	
    	Future a = 	executorService.submit(new Runnable() {
			
			@Override
			public void run() {
				
					System.out.println("2222222222222222222222222222");
				
					try {
						Thread.sleep(10000L);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					}
					
					
				
						
			}
		});
   
    	
        
 //       http://www.douyu.com/swf_api/room/141371?cdn=&nofan=yes&_t=24487156&sign=
        
      

		
		
		//c2c0e502375e6425e32a6d40929d70cd
       // c2c0e502375e6425e32a6d40929d70cd
   //     http://www.douyu.com/swf_api/room/16789 ?cdn=&nofan=yes&_t=1469231194&sign=7b19d693ff486de6fb6d07e424f980a8      
        
      //  System.out.println(auth);
//        b6f26683a970c5b0e524c47516b6c4f1
//        591ce7ce3d98b9dda0660edcdbd2bae0
        //String apiurl = "http://capi.douyucdn.cn/api/v1" + e + "&auth=" + auth;

		
		
		
		
		
		
	}
	
	static class BeeperControl{
		private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
		
		public void beepForAnHour(){
			JTextField inNum=new JTextField(10);
			inNum.setText("4809");
			HttpClientFromDouyu.QueryLoginServer("4809");
			
			
			
			
			
			
			
//			final Runnable beeper=new DownloaderThead(inNum);
//			
//			
//			final ScheduledFuture<?>beeperHandle=scheduler.scheduleAtFixedRate(beeper,0,30,TimeUnit.SECONDS);
//			
//			
//			scheduler.schedule(new Runnable(){
//			public void run(){beeperHandle.cancel(true);}
//			},31,TimeUnit.SECONDS);
		}
	}

}

