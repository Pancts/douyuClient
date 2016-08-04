//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package name.yumao.douyu.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import name.yumao.douyu.utils.MD5Util;
import name.yumao.douyu.utils.ServerUtils;
import name.yumao.douyu.vo.DouyuApiDataVo;
import name.yumao.douyu.vo.DouyuApiServersVo;
import name.yumao.douyu.vo.DouyuApiVo;
import name.yumao.douyu.vo.Mdouy;
import name.yumao.douyu.vo.SwfData;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class HttpClientFromDouyu {
    private static Logger logger = Logger.getLogger(HttpClientFromDouyu.class);

    public HttpClientFromDouyu() {
    }

    public static String QueryDouyuRoomNum(String roomName) {
        String roomNum = "";
        HttpEntity entity = null;
        HttpGet get = new HttpGet("http://www.douyutv.com/api/client/room/" + roomNum );

        String var7;
        try {
            get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36");
            HttpResponse e = HttpClientFactory.getHttpClient().execute(get);
            if(e.getStatusLine().getStatusCode() == 200) {
                entity = e.getEntity();
                String htmlEntity = EntityUtils.toString(entity);
                roomNum = ServerUtils.QueryRoomId(htmlEntity);
            }

            return roomNum;
        } catch (Exception var10) {
            var7 = roomNum;
        } finally {
            get.abort();
            if(entity != null) {
                EntityUtils.consumeQuietly(entity);
            }

        }

        return var7;
    }

    public static List<DouyuApiServersVo> QueryLoginServer(String roomNum) {
    	List<DouyuApiServersVo> roomApiServersVo = new ArrayList<DouyuApiServersVo>();
        HttpEntity entity = null;
        HttpGet get = new HttpGet("http://www.douyutv.com/api/client/room/" + roomNum );

   
        try { 
        	
        	
            get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36");
            HttpResponse e = HttpClientFactory.getHttpClient().execute(get);
            if(e.getStatusLine().getStatusCode() == 200) {

          
                entity = e.getEntity();
                if(entity!= null) {
                	String htmlEntity = EntityUtils.toString(entity);
                    roomApiServersVo = ServerUtils.QueryLoginServerList(htmlEntity);
                }
            }

            return roomApiServersVo;
        } catch (Exception var10) {
        	roomApiServersVo = new ArrayList<DouyuApiServersVo>();
        } finally {
            get.abort();
            if(entity != null) {
                EntityUtils.consumeQuietly(entity);
            }

        }

        return roomApiServersVo;
    }
    


    public static List<String> getList(String url) throws Exception {
    	List<String> list = new ArrayList<String >();
        HttpEntity entity = null;
        HttpGet get = new HttpGet(url );

        try { 
        	
            get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36");
            HttpResponse e = HttpClientFactory.getHttpClient().execute(get);
            if(e.getStatusLine().getStatusCode() == 200) {
                entity = e.getEntity();
                if(entity!= null) {
                
                	   BufferedReader reader = null;

                        reader = new BufferedReader(new InputStreamReader(entity.getContent()));


                       String line;
                 

                       while ((line = reader.readLine()) != null) {
                       	if (line.length() > 0 && !line.startsWith("#")) {
                             
                               if (!line.startsWith("http")) {
                                   String baseUrl = getBaseUrl(url);
                                   list.add(baseUrl + line) ;
                               } else {
                            	   list.add(line) ;
                               }

                          }
                        
                       }
                 	}
                }
           

        } catch (Exception var10) {
        	throw new Exception(var10);
        } finally {
            get.abort();
            if(entity != null) {
                EntityUtils.consumeQuietly(entity);
            }

        }

        return list;
    }
    

	public  static String getBaseUrl(String urlString) {
	  //  String urlString = url.toString();
	    int index = urlString.lastIndexOf('/');
	    return urlString.substring(0, ++index);
	}
            
	public static DouyuApiDataVo QueryDouyuDownloadUrl(String roomNum) {
        DouyuApiDataVo vo = new DouyuApiDataVo();
        HttpEntity entity = null;
        HttpGet get = null;

     
        try {
        	
        	
        	//http://www.douyu.com/swf_api/room/141371?cdn=&nofan=yes&_t=24487156&sign=c2c0e502375e6425e32a6d40929d70cd
    		String API_SECRET = "bLFlashflowlad92";
    		String time = Long.valueOf(System.currentTimeMillis() / (60 * 1000) )+"";
        	//http://capi.douyucdn.cn/api/v1/room/145201?aid=android1&client_sys=android&ne=1&support_pwd=1&time=1469155020&auth=b6f26683a970c5b0e524c47516b6c4f1
            //String e = "room/" + roomNum + "?aid=android1&client_sys=android&ne=1&support_pwd=1&time=" + ;
            String auth = MD5Util.MD5(roomNum + API_SECRET + time);
            String apiurl = "http://www.douyu.com/swf_api/room/"+roomNum+"?cdn=&nofan=yes&_t="+time+"&sign="+auth;
            get = new HttpGet(apiurl);
            get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36");
            HttpResponse response = HttpClientFactory.getHttpClient().execute(get);
            if(response.getStatusLine().getStatusCode() == 200) {
                entity = response.getEntity();
                String htmlEntity = EntityUtils.toString(entity);
                vo = ServerUtils.QueryDouyuDownloadUrl(htmlEntity);
            }

           // logger.info(vo);
            return vo;
        } catch (Exception var13) {
        	vo =  new DouyuApiDataVo();
        } finally {
            get.abort();
            if(entity != null) {
                EntityUtils.consumeQuietly(entity);
            }

        }

        return vo;
    }
	
	
	  public static String  getswf(String num) {
		  HttpEntity httpEntity =null;
		 HttpPost post = new HttpPost("http://www.douyu.com/lapi/live/getPlay/"+num);
		 //did=64416AD9E882213CBE160FB795AB90EC&cdn=&sign=f31b29f6ed27b6f088c9adc9fe2c08aa&rate=0&tt=24490882
		 String s = "A12Svb&%1UUmf@hC";
		 String time = Long.valueOf(System.currentTimeMillis() / (60 * 1000) )+"";
		 String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
		 String sign = MD5Util.MD5(num+uuid+s+time);
		  List <NameValuePair> params = new ArrayList<NameValuePair>();  
	        params.add(new BasicNameValuePair("did", uuid));  
	        params.add(new BasicNameValuePair("cdn", ""));  
	        params.add(new BasicNameValuePair("sign", sign)); 
	        params.add(new BasicNameValuePair("rate", "0")); 
	        params.add(new BasicNameValuePair("tt", time)); 
	        
	        try {
				post.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
	
	            HttpResponse httpResponse = HttpClientFactory.getHttpClient().execute(post);  
	            if(httpResponse.getStatusLine().getStatusCode() == 200)  
	            {  
	                 httpEntity = httpResponse.getEntity();  
	                String result = EntityUtils.toString(httpEntity);//取出应答字符串 
	                //System.out.println(result);
	                Gson gson = new Gson();
		      		 SwfData swfData = gson.fromJson(result, SwfData.class);
		      		  
		      		  logger.info(swfData);
		      		  return swfData.getData().getRtmp_url()+"/"+swfData.getData().getRtmp_live() ;//
	            }  else {
	            	  logger.info( EntityUtils.toString(httpEntity));
	            } 
            
			} catch (Exception e) {
				logger.debug(e.getMessage() );
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} finally {
					post.abort();
	              if(httpEntity != null) {
	                  EntityUtils.consumeQuietly(httpEntity);
	              }

			}  
	        
	        
	        return "";
	  }
    
    public static String  getHTML5DownUrl(String num) {
        	Mdouy vo = new Mdouy();
          HttpEntity entity = null;
          HttpGet get = null;

       
          try {
          	
        	  String apiurl = "http://m.douyu.com/html5/live?roomId="+num;
              get = new HttpGet(apiurl);
              get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36");
              HttpResponse response = HttpClientFactory.getHttpClient().execute(get);
              if(response.getStatusLine().getStatusCode() == 200) {
                  entity = response.getEntity();
                  String htmlEntity = EntityUtils.toString(entity);
        		  Gson gson = new Gson();
        		  Mdouy Mdouy = gson.fromJson(htmlEntity, Mdouy.class);
        		  
        		  logger.debug(Mdouy);
        		  String url = Mdouy.getData().getHls_url();
        		  return  "http://tc-tct.douyucdn.cn/dyliveflv3/"+url.substring(url.indexOf("live")+"live/".length(),url.indexOf("_550") )+".flv";//
              }

              //logger.info(vo);
              return "";
          } catch (Exception var13) {
        	  return "";
          } finally {
              get.abort();
              if(entity != null) {
                  EntityUtils.consumeQuietly(entity);
              }

          }

         // return "";
    }
}
