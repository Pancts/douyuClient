package name.yumao.douyu.utils;

//import java.net.URLDecoder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import name.yumao.douyu.http.DownloaderThead;
import name.yumao.douyu.vo.ContentServerVo;
import name.yumao.douyu.vo.DouyuApiDataVo;
import name.yumao.douyu.vo.DouyuApiServersVo;
import name.yumao.douyu.vo.DouyuApiVo;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
//import name.yumao.douyu.vo.DownloadUrlVo;
//import name.yumao.douyu.vo.LoginServerVo;
import com.google.gson.reflect.TypeToken;

public class ServerUtils {
	
	private static Logger logger = Logger.getLogger(DownloaderThead.class);	
	
	public static String QueryRoomId(String roomApiStr){
		  Gson gson = new Gson();
		  DouyuApiVo roomApiVo = gson.fromJson(roomApiStr, DouyuApiVo.class);
		  
		  logger.debug(roomApiVo);
		  
		  return roomApiVo.getData().getRoom_id();
	} 
	
	
	public static List<DouyuApiServersVo> QueryLoginServerList(String roomApiStr){
		  Gson gson = new Gson();
		  DouyuApiVo roomApiVo = gson.fromJson(roomApiStr, DouyuApiVo.class);
		  
		  logger.debug(roomApiVo);
		  
		  return roomApiVo.getData().getServers();
	} 
	

	public static DouyuApiDataVo QueryDouyuDownloadUrl(String roomApiStr) throws Exception {

		  Gson gson = new Gson();
		  DouyuApiVo roomApiVo = gson.fromJson(roomApiStr, DouyuApiVo.class);
		  
		  logger.debug(roomApiVo);
		  
		  return roomApiVo.getData() ; //.getRtmp_url() + "/" + roomApiVo.getData().getRtmp_live();
	} 
	

	
	
	
	public static List<ContentServerVo> QueryContentServerList(String serverList){
		String serverListJson = "[{\"" + serverList.replaceAll("@S/", "\"},{\"").replaceAll("@A=", "\":\"").replaceAll("@S", "\",\"") + "\"}]";
		serverListJson = serverListJson.substring(0,serverListJson.indexOf(",{\"\"}")) + "]";
		List<ContentServerVo> contentServerList = new ArrayList<ContentServerVo>();
		
		Gson gson = new Gson();
		contentServerList = gson.fromJson(serverListJson, new TypeToken<List<ContentServerVo>>(){}.getType());
		logger.debug(contentServerList);
		return contentServerList;
	}
	
	
	
	public static String getCpuIdMd5() {  
    String serial = ""; 
	    try {  
	        Process process = Runtime.getRuntime().exec(  
	        new String[] { "wmic", "cpu", "get", "ProcessorId" });  
	        process.getOutputStream().close();  
	        Scanner sc = new Scanner(process.getInputStream());  
	        sc.next();  
	        serial = sc.next();  
	    } catch (IOException e) {  
    }  
    return MD5Util.MD5(serial).toUpperCase();
}  

}
