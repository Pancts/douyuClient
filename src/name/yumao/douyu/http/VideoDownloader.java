package name.yumao.douyu.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import name.yumao.douyu.mina.LoginMinaThread;
import name.yumao.douyu.vo.DouyuApiDataVo;
import name.yumao.douyu.vo.DouyuApiServersVo;

public class VideoDownloader {
	static final Logger logger = LoggerFactory.getLogger(VideoDownloader.class);

	ExecutorService executorService = Executors.newCachedThreadPool();
	CloseableHttpClient httpclient;
	
	private boolean isDownloading = false;

	
	public  VideoDownloader() {
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10 * 1000)
				.setConnectionRequestTimeout(10 * 1000).setSocketTimeout(10 * 1000).build();
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();

		httpclient = HttpClients.custom().setConnectionManager(connManager).setDefaultRequestConfig(requestConfig)
				.build();
		

		
	}
//
//	@PreDestroy
//	public void destory() throws IOException {
//		httpclient.close();
//		executorService.shutdown();
//	}
	
	public synchronized void setDownloading(boolean d) {
		 isDownloading =d;
	}
	
	public synchronized boolean isDownloading() {
		return isDownloading;
	}

	public void addDownloadTask(final String room, final String url) {
		
		setDownloading(true);
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd^hh-mm-ss");
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		
		Paths.get("record", room,simpleDateFormat2.format(calendar.getTime() )).toFile().mkdirs();
		Path path = Paths.get("record", room,
				simpleDateFormat2.format(calendar.getTime()),
				simpleDateFormat.format(calendar.getTime()) + ".flv");
		// Path path2 = Paths.get("video", room,
		// simpleDateFormat.format(new Date())+".ass" );
		final File flvFile = path.toFile();
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				

				long downloadedSize = 0;
				logger.info("开始下载房间：{}, url:{}", room, url);
				System.out.println("开始下载房间：" + room + "，url: " + url);
				
				try (CloseableHttpResponse response = httpclient.execute(new HttpGet(url))) {

					logger.info(response.getStatusLine().getStatusCode() + "");
					if (response.getStatusLine().getStatusCode() == 200) {
						
						
						HttpEntity entity = response.getEntity();
						if (entity != null) {
							InputStream inputStream = entity.getContent();
	
//
							loginMina(room,flvFile.getPath().replace("flv", "ass") );

							try (FileOutputStream outstream = new FileOutputStream(flvFile);) {
								byte[] buffer = new byte[128 * 1024];
								int size = 0;

								Date last = new Date();
								long time = 0 ;
								while ((size = inputStream.read(buffer)) != -1) {
									outstream.write(buffer, 0, size);
									downloadedSize += size;
									Date now = new Date();
									if (((now.getTime() - last.getTime()) / 1000) >= 5) {
										logger.info("开始下载房间：{}, 已下载大小:{}m", room, downloadedSize/ (1024* 1024) );
										System.out.println("正在下载房间：" + room + "，已下载大小: " + downloadedSize /(1024* 1024)+"m");
										last = now;
										time = time + 5;
//										
					                    if(Config.TIMEOUT != -1 &&  time >= Config.TIMEOUT ) {
					                    	  httpclient.close();
						                      if(response != null) {
						                          EntityUtils.consumeQuietly(response.getEntity());
						                      }
					                    }
									
									}
								}
							}
						}
					}
				} catch (Throwable t) {
					logger.error("房间下载出错！room:" + room, t);
					System.out.println("下载房间出错！：" + room + "，url: " + url);
					//setDownloading(false);
				} finally {
//					manager.returnDownloadPermit(room);
					setDownloading(false);
					logger.info("结束下载房间：{}, url:{}", room, url);
					System.out.println("结束下载房间：" + room + "，url: " + url);
					try {
						httpclient.close();
						executorService.shutdown();
	
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
			}
		});
		
		
//		
//        	LoginMinaThread e = new LoginMinaThread(flvFile.getPath().replace("flv", "ass") , loginServerList, inNum, butnSure);
//		executorService.submit(e);
	}
	
	  private static void loginMina(String num,String filePath) {
		  
		  
		  
	        try {
	        	
	        	JTextField inNum = new JTextField(num);
	        	JButton butnSure = new JButton();
	        	
	        	
//	        	//第三方弹幕协议服务器地址
//	        	 static final String hostName = "openbarrage.douyutv.com";
//	        	
//	        	//第三方弹幕协议服务器端口
//	        	 static final int port = 8601;
	        	
	        	//DouyuApiDataVo api = HttpClientFromDouyu.QueryDouyuDownloadUrl(num);
	            LoginMinaThread e = new LoginMinaThread(filePath, inNum, butnSure);
	            Thread loginMinaThread = new Thread(e);
	            loginMinaThread.start();
	        } catch (Exception var6) {
	            var6.printStackTrace();
	        }

	    }

}
