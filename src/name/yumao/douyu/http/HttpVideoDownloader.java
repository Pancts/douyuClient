//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package name.yumao.douyu.http;

import java.awt.Button;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JTextField;

import name.yumao.douyu.DouyuAssistantMain;
import name.yumao.douyu.http.Config;
import name.yumao.douyu.http.DownloaderThead;
import name.yumao.douyu.http.HttpClientFactory;
import name.yumao.douyu.mina.LoginMinaThread;
import name.yumao.douyu.vo.DouyuApiDataVo;
import name.yumao.douyu.vo.DouyuApiServersVo;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

public class HttpVideoDownloader {
    
	
	
	//static CloseableHttpClient httpclient;
	
	//private boolean isDownloading = false;

	static {


		

		
	}


    public static void download(JTextField inNum, JButton butnSure, String url, int time) throws ClientProtocolException, IOException {
       
    	time=Config.SO_TIMEOUT / 1000;
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(time * 1000)
				.setConnectionRequestTimeout(time * 1000).setSocketTimeout(time * 1000).build();
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();

		CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(connManager).setDefaultRequestConfig(requestConfig)
				.build();
    	
    	HttpResponse response = null;
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36");
        httpget.setHeader("Cache-Control", "no-cache");	
        httpget.setHeader("Pragma", "no-cache");	

        try {
        	
   
        	
          
            response = httpclient.execute(httpget);
            if(response.getStatusLine().getStatusCode() == 200) {
                DownloaderThead.setTime();
                
               // File file = new File(filePath + ".flv");
              
                HttpEntity entity = response.getEntity();
                
                
                if(entity != null   ) {
                	
                	 // System.out.println("entity " + entity.getContentLength() );
                      
                	 
                      down(inNum,butnSure, response, entity, httpget);
                }
            }
        } finally {
            httpget.abort();
            if(response != null) {
                EntityUtils.consumeQuietly(response.getEntity());
            }
//            
//            try{
//            	File ass = new File(file.getPath().replace("flv", "ass") );
//	            if( file.length() < 1024  * 100   ) {
//	       
//	            	
//	            	file.delete();
//	            	
//	            	while(ass.exists() ) {
//	            		ass.delete() ;
//	            		Thread.sleep(1000L);
//	            	}
//	
//	            }
//            }catch(Exception e){
//            	e.printStackTrace();
//            	System.out.println("删除多余文件失败" +  file.getName() );
//            }

        }

    }

    private static void down(JTextField inNum,JButton butnSure, HttpResponse response, HttpEntity entity, HttpGet httpget) throws IOException, FileNotFoundException {
        long downloadedSize = 0L;
        InputStream is = entity.getContent();
        FileOutputStream fileout=null;
        byte[] buffer = new byte[2048000];
        boolean ch = false;
        
        File file = getDownFileName(inNum);
       
      

        try {
            Date ex = new Date();
           // Date ex2 = new Date();
            int ch1;
            long time = 0 ;
            while((ch1 = is.read(buffer)) != -1 && ch1 > 0) {
            
            	  
            		if(!file.getParentFile().exists()) {
            			file.getParentFile().mkdirs();
            		}
	           		
	        		if( fileout == null){
	        			fileout =new FileOutputStream(file);
	        		} 
	        		
	        		if(!ch) {
	        			 String a = file.getPath();
	        			 loginMina(inNum, butnSure, a.replace("flv", "ass") );
	        			 ch =true;
	        		}
	 
            	   
	                fileout.write(buffer, 0, ch1);
	                downloadedSize += (long)ch1;
	                Date now = new Date();
	                if((now.getTime() - ex.getTime()) / 1000L >= 10L) {
	                    System.out.println(now + " 正在下载房间：" + inNum.getText() + "，已下载大小: " + downloadedSize / 1048576L + "m");
	                    ex = now;
	                    time = time + 10;
	                    
	                    
	                    if(Config.TIMEOUT != -1 && time >= (long)(60 * Config.TIMEOUT) ) {
	                    	httpget.abort();
		                      if(response != null) {
		                          EntityUtils.consumeQuietly(response.getEntity());
		                      }
		                     // DouyuAssistantMain.e.post("end");
	                    }
	                }

            }
        } catch (IOException var16) {
        	var16.printStackTrace();
            throw var16;
        } finally {
        	if(fileout != null) {
                fileout.flush();
                fileout.close();
        	}
            is.close();
            DouyuAssistantMain.e.post("end" + inNum.getText());
        }

    }

	private static File getDownFileName(JTextField inNum) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd^HH-mm-ss");
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		
		//Paths.get("record", inNum.getText(),simpleDateFormat2.format(calendar.getTime() )).toFile().mkdirs();
		Path path = Paths.get("record", inNum.getText(),
				simpleDateFormat2.format(calendar.getTime()),
				simpleDateFormat.format(calendar.getTime()) + ".flv");
		
		File file =path.toFile() ;
		return file;
	}

    private static void loginMina(JTextField inNum, JButton butnSure, String filePath) {
        try {
        	
               LoginMinaThread e = new LoginMinaThread(filePath , inNum, butnSure);
            Thread loginMinaThread = new Thread(e);
            loginMinaThread.start();
            
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }
}
