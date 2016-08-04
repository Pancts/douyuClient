//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package name.yumao.douyu.http;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import javax.swing.JButton;
import javax.swing.JTextField;
import name.yumao.douyu.http.HttpClientFromDouyu;
import name.yumao.douyu.http.HttpVideoDownloader;
import name.yumao.douyu.vo.DouyuApiDataVo;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

public class DownloaderThead implements Runnable {
	private static Logger logger = Logger.getLogger(DownloaderThead.class);
	private JTextField inNum;
	private JButton butnSure;
	private Calendar calendar;
	private static int time = 1;
	private static boolean closed = false;

	public DownloaderThead(JTextField inNum) {
		logger.info("Sub:初始化录像进程");
		this.inNum = inNum;
	}

	public static synchronized int getTime() {
		return time > 5 ? time : time++;
	}

	public static synchronized void setclosed(boolean b) {
		closed = b;
	}

	public static synchronized boolean getclosed(boolean b) {
		return closed;
	}

	public static synchronized void setTime() {
		time = 1;
	}

	public void run() {
		
		
		System.out.println("开始：" + this.inNum.getText());
		
		while (true) {

			try {
				
				
//				DouyuApiDataVo api = HttpClientFromDouyu.QueryDouyuDownloadUrl(this.inNum.getText());
//
//				String online = api.getOnline();
//				
//				if (online != null && !online.equals("0")) {

					String vo = HttpClientFromDouyu.getswf(this.inNum.getText());

					if (!vo.equals(""))

					{
//						VideoDownloader down = new VideoDownloader();
//						down.addDownloadTask(this.inNum.getText(), vo);
						
						HttpVideoDownloader.download(inNum, butnSure, vo,time);
					}

//				}

			} catch (Exception e) {
				logger.debug(e.getMessage()  );
				//e.printStackTrace();
			}


			try {
				Thread.sleep((long) (500 * getTime()));
				logger.debug(Integer.valueOf(time));
			} catch (Exception var7) {
				var7.printStackTrace();
			}
		}
	}


}
