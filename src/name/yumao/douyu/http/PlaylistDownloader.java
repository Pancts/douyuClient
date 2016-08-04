package name.yumao.douyu.http;
/*
 * Copyright (c) Christopher A Longo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */



import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import name.yumao.douyu.vo.DouyuApiDataVo;



public class PlaylistDownloader {
   // private static URL url;
  //  private static List<String> playlist;
  //  private Crypto crypto;
	private static Logger logger = Logger.getLogger(PlaylistDownloader.class);	
    private static String EXT_X_KEY = "#EXT-X-KEY";
    private static final String BANDWIDTH = "BANDWIDTH";
    static String   outFile="";
    static String roomnum = "";
    private static Set<URL>  sublist = new HashSet<URL>();
    static ConcurrentLinkedQueue<URL> basket = new ConcurrentLinkedQueue<URL>();

//    public PlaylistDownloader(String playlistUrl) throws MalformedURLException {
//        this.url = new URL(playlistUrl);
//        this.playlist = new ArrayList<String>();
//    }


//    public static void download(String outfile)  {
//    	URL down = null;
//    	try {
//    		 down = basket.poll();
//    		 System.out.println("down:" + down);
//			downloadInternal( down, outfile);
//		} catch (IOException e) {
//			System.out.println("down0" + down.toString() + "error");
//			e.printStackTrace();
//		}
//       
 //   }
    
    
	private static String getPath() {

		Calendar calendar = Calendar.getInstance(TimeZone
				.getTimeZone("GMT+08:00"));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(
				"yyyy-MM-dd^HH-mm-ss");
		String path = "record" + File.separator + roomnum
				+ File.separator + simpleDateFormat.format(calendar.getTime());
		String pathfile = path + File.separator
				+ simpleDateFormat2.format(calendar.getTime());

		return pathfile + ".mp5";
	}
	
    private static  void downloadInternal(URL segmentUrl) {

		FileOutputStream out = null;
		InputStream is = null;
		try {
			byte[] buffer = new byte[512 * 1024];

			is = segmentUrl.openStream();
			
			
			if(outFile.equals("") ){
//				    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
//						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("^yyyy-MM-dd^HH-mm-ss");
//			//	
//					    outFile = "record"+File.separator+num+File.separator+simpleDateFormat.format(calendar.getTime());
				outFile = getPath();
			}

			File file = new File(outFile);

			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}

			Date now = new Date();
			System.out.println(now + " 正在下载房间：" + roomnum + "，已下载大小: "
					+ file.length() / (1024 * 1024L) + "m");

			// System.out.println("Downloading size:"+file.length() / (1024 *
			// 1024L) +"m\r");

		
			if (file.length() > 1024 * 1024L  * Config.SIZE) {
				outFile = getPath();
				file = new File(outFile);
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}

			}
			out = new FileOutputStream(outFile, file.exists());

			// System.out.println("正在下载:"+segmentUrl+"\r");

			int read;

			while ((read = is.read(buffer)) >= 0) {
				out.write(buffer, 0, read);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(out!=null){
					out.flush();
					out.close();
				}
					
				if(is!=null)
					is.close();
				
			} catch (IOException e) {
				logger.info("下载失败:" + segmentUrl + e.getMessage());
				e.printStackTrace();
			}

		}
	}
    
    

    private static void downloadInternal(URL segmentUrl, String outFile)  {
    	
        FileOutputStream out = null;
        InputStream is = null;
    	try{
        byte[] buffer = new byte[512];

         is =  segmentUrl.openStream();

         
        if (outFile != null) {
            File file = new File(outFile);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
            
            out = new FileOutputStream(outFile, file.exists());
        } else {
            String path = segmentUrl.getPath();
            int pos = path.lastIndexOf('/');
            out = new FileOutputStream(path.substring(++pos), false);
        }

        logger.info("Downloading segment:"+segmentUrl+"\r");

        int read;

        while ((read = is.read(buffer)) >= 0) {
            out.write(buffer, 0, read);
        }


        
    	} catch(Exception e) {
    		e.printStackTrace(); 
    	} finally {
            try {
				is.close();
				out.close();
			} catch (IOException e) {
			
				e.printStackTrace();
			}
            
    		
    	}
    	 
    }

    private static String getBaseUrl(URL url) {
        String urlString = url.toString();
        int index = urlString.lastIndexOf('/');
        return urlString.substring(0, ++index);
    }
    

    
    
    
    
	
	public static void go(/**final String name,*/final String num ) {
		//			PlaylistDownloader loader = new PlaylistDownloader("http://");
				      ExecutorService service = Executors.newCachedThreadPool();
		
		//			  GetList producer = new GetList();
				      
				      roomnum = num;
					      
		//			  Down consumer = new Down();
				     // final	String  id = "";
			          service.execute(new Runnable() {
			        	  public void run()  { 

			        		  while(true){
			      		
			      					

//			      					
//			      					ZhanqiApiVo vo = HttpClientFromZhanqi.QueryZhanqiDownloadUrl(inNum.getText() );

					      					
				      				try {
				      					String url = HttpClientFromDouyu.getHTML5DownUrl(num);
				      					if(!url.equals("") )  {
				      						
				      						
//	
				      						fetchsubPlaylist(new URL(url) ) ;
				      					} else {
				      						logger.info("error");
				      						outFile = getPath();
				      					}
				      					
										
									} catch (MalformedURLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
										
									}
//				      					
			      					
//			      					}
//	
			      					
			      					try {
										Thread.sleep(5000);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
			      					
			      			
			      			}
			        	  } 
		
			          } );
			          
			          
			          service.execute(new Runnable() {
			        	  
			        	  
			        	  public void run()  { 
			        		 
			        		  while(true){
			        			  logger.info("down ......................");
			        			  URL down  = null;
			        			  
			        			     while(true) {
			        					try {
			        			    		 down = basket.poll();
			        			    		 logger.debug("down:" + down);
			        			    		 if(down != null)
			        			    			 downloadInternal( down);
			        						Thread.sleep(500);
			        						
			        				
			        					} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
			        			     }
									
			      				//download("outtest");
			      			}
			        	  } 
		
			          } );
	

	}
    


//    private static void fetchPlaylist(URL url) throws IOException {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
//        boolean isMaster = false;
//        long maxRate = 0L;
//        int maxRateIndex = 0;
//
//        String line;
//        int index = 0;
//        List<String> playlist = new ArrayList<String>();
//        while ((line = reader.readLine()) != null) {
//           playlist.add(line);
//
//            if (line.contains(BANDWIDTH))
//                isMaster = true;
//
//            if (isMaster && line.contains("_1024/index.m3u8")) {
//                try {
//                    //int pos = line.lastIndexOf("=");
////                    long bandwidth = Long.parseLong(StringUtils.substringBetween(line, "BANDWIDTH=", "000")  );
////
////                    maxRate = Math.max(bandwidth, maxRate);
////
////                    if (bandwidth == maxRate)
//                        maxRateIndex = index; // + 1;
//                } catch (NumberFormatException ignore) {
//                	
//                	
//                }
//            }
//
//            index++;
//        }
//        
//        logger.info("maxRate"+ maxRate);
//        
//
//        reader.close();
//
//        if (isMaster) {
//        	logger.debug("Found master playlist, fetching highest stream at "+(maxRate / 1024) +"Kb/s\n" );
//            url = updateUrlForSubPlaylist(url ,playlist.get(maxRateIndex));
//           playlist.clear();
//
//           fetchsubPlaylist(url);
//        }
//        
//        System.out.println(playlist );
//        
//    }

    private static  void fetchsubPlaylist(URL url) {
    	
    logger.info(url);
    	//System.out.println("AAAAAAAAAAAAAAAAAAAA");
    	
//    	try{
//    		throw new Exception("1111111111111");
//    	}catch(Exception e)  {
//    		e.printStackTrace();
//    	}
//    	
    	 BufferedReader reader = null;
    	try{
         reader = new BufferedReader(new InputStreamReader(url.openStream()));


        String line;
  

        while ((line = reader.readLine()) != null) {
        	if (line.length() > 0 && !line.startsWith("#")) {
                URL segmentUrl;

                if (!line.startsWith("http")) {
                    String baseUrl = getBaseUrl(url).replace("_550", "");
                    segmentUrl = new URL(baseUrl + line);
                } else {
                    segmentUrl = new URL(line);
                }
                
                if(!sublist.contains(segmentUrl) ) {
                	sublist.add(segmentUrl) ;
                	basket.offer(segmentUrl);
                	
                	//System.out.println(basket.isEmpty()  +"www" + sublist);
                }
                
        	}
        }

        //reader.close();
        
    	} catch (Exception e){
    		e.printStackTrace();
    	} finally{
    		try {
    			if(reader !=null)
    				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}

    }
    
    
    
    
    private static URL updateUrlForSubPlaylist(URL url, String sub) throws MalformedURLException {
        String newUrl;

        if (!sub.startsWith("http")) {
            newUrl = getBaseUrl(url) + sub;
        } else {
            newUrl = sub;
        }

        return new URL(newUrl);
    }
}
