

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JTextField;

import name.yumao.douyu.http.DownloaderThead;
import name.yumao.douyu.http.HttpClientFromDouyu;
import name.yumao.douyu.http.PlaylistDownloader;
import name.yumao.douyu.utils.MD5Util;

public class Test {
	public static void main(String[] args) {
	
	//	String url = "http://hls3.douyutv.com/live/67373rmT3brricfJ_550/playlist.m3u8?wsSecret=765bb8a6e85fafde806a8e1a9ab9d9c3&wsTime=1469201748";
		//String url = HttpClientFromDouyu.getHTML5DownUrl("762484");
		//System.out.println(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() );
//		String did="64416AD9E882213CBE160FB795AB90EC";
//				String time = "24490882";
//		String num = "616322";		
//		 String s = "A12Svb&%1UUmf@hC";
//				//sign=f31b29f6ed27b6f088c9adc9fe2c08aa&rate=0&
//		
//		String sign = MD5Util.MD5(num+did+s+time);
//		System.out.println(sign);
		
		Map  map= new HashMap();
		
		Iterator<Map.Entry<Object, Object>> entries = map.entrySet().iterator();

		while (entries.hasNext()) {  
			  
		    Map.Entry<Object, Object> entry = entries.next();  
		  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		  
		}  
		
		
	System.out.print("111111111111111111"+ HttpClientFromDouyu.QueryDouyuDownloadUrl("453751").getOnline() );
		
	}
}
