package name.yumao.douyu;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.JFrame;
import javax.swing.JTextField;

import com.google.common.eventbus.EventBus;

import name.yumao.douyu.http.DownloaderThead;
import name.yumao.douyu.http.InfoThead;
import name.yumao.douyu.swing.Danmu;
import name.yumao.douyu.swing.Download;

public class DouyuAssistantMain extends JFrame{
	private static final long serialVersionUID = 1L;
	public static final EventBus e = new EventBus();
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception{
		
		 ScheduledExecutorService executor = Executors.newScheduledThreadPool(100);  
		
		JTextField inNum2=new JTextField(10);
		InfoThead infoThead = new InfoThead(inNum2,false);
		Thread infoWrite = new Thread(infoThead);
		infoWrite.start();
		
		if(args[0].equals("-content")){
			Danmu danmu = new Danmu();
		}else if(args[0].equals("-download")){
			Download download = new Download();
		}else  if(args[0].equals("-start") ) {
			
			
			int lenght = args.length;
			
			for(int i = 1; i < args.length ;i++) {
//				PlaylistDownloader.go(args[i] );
				JTextField inNum=new JTextField(10);
				inNum.setText(args[i]);
		//		http://www.douyu.com/lapi/live/getPlay/632223
			//	DownloaderThead down = new DownloaderThead(inNum);
				new Thread(new DownloaderThead(inNum) ).start();
//			    JButton butnSure=new JButton("录像");
//			    EventBus eventBus = new EventBus();
//				PlaylistDownloader33333 downloader = new PlaylistDownloader33333(inNum,eventBus);
//				downloader.go();

//				executor.scheduleAtFixedRate(
//						new DownloaderThead(inNum),
//						0,
//						30,
//						TimeUnit.MINUTES);
			    
			}

		}
		else {
			System.out.print("错误的传入参数！");
		}
	}	
}
