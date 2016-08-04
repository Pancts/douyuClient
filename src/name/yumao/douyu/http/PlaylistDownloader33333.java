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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JButton;
import javax.swing.JTextField;

import name.yumao.douyu.mina.LoginMinaThread;
import name.yumao.douyu.vo.DouyuApiDataVo;
import name.yumao.douyu.vo.DouyuApiServersVo;

import org.apache.log4j.Logger;

import com.google.common.eventbus.EventBus;

public class PlaylistDownloader33333 {
	// private static URL url;
	// private static List<String> playlist;
	// private Crypto crypto;
	private static Logger logger = Logger.getLogger(PlaylistDownloader33333.class);
	private static String EXT_X_KEY = "#EXT-X-KEY";
	private static final String BANDWIDTH = "BANDWIDTH";
	private  String playlisturl ="" ; 
	private EventBus eventBus;


	public String getPlaylisturl() {
		return playlisturl;
	}

	private ConcurrentSkipListSet<String> sublist = new ConcurrentSkipListSet<String>();
	private ConcurrentLinkedQueue<String> basket = new ConcurrentLinkedQueue<String>();
	private String outFile = "a.ts";

	JTextField inNum;
	JButton butnSure;

	ExecutorService service = Executors.newCachedThreadPool();

	public PlaylistDownloader33333(JTextField inNum,EventBus eventBus) {
		this.inNum = inNum;
		butnSure = new JButton();
		outFile = getPath();
		this.eventBus = eventBus;
	}

	public static void main(String[] args) {
		JTextField inNum = new JTextField(10);
		inNum.setText("212689");
	    EventBus eventBus = new EventBus();
		new PlaylistDownloader33333(inNum,eventBus).go();
	}

	private String getPath() {

		Calendar calendar = Calendar.getInstance(TimeZone
				.getTimeZone("GMT+08:00"));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(
				"yyyy-MM-dd^HH-mm-ss");
		String path = "record" + File.separator + this.inNum.getText()
				+ File.separator + simpleDateFormat.format(calendar.getTime());
		String pathfile = path + File.separator
				+ simpleDateFormat2.format(calendar.getTime());

		return pathfile + ".ts";
	}

	public void getList(String url) {
		try {
			List<String> a = HttpClientFromDouyu.getList(url);
			if (!a.isEmpty()) {
				for (String temp : a) {
					if (!sublist.contains(temp)) {
						basket.add(temp);
						sublist.add(temp);
					}
				}
			}
			// System.out.println(sublist);

		} catch (Exception e1) {
			DouyuApiDataVo vo = HttpClientFromDouyu.QueryDouyuDownloadUrl(inNum
					.getText());
			playlisturl = vo.getHls_url();
			if (playlisturl.length() > 0) {
				File file = new File(outFile);

				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}

				//service.execute(new LoginMinaThread(outFile.replace(".ts",".ass"), vo.getServers(), inNum, butnSure,eventBus));

			}

		}

	}

	public void down() {

		try {
			String data = basket.poll();
			if (data != null) {
				downloadInternal(new URL(data));
			}
		} catch (MalformedURLException e) {
			logger.info("取下载地址失败:" + e.getMessage());
		}
	}

	private void downloadInternal(URL segmentUrl) {

		FileOutputStream out = null;
		InputStream is = null;
		try {
			byte[] buffer = new byte[512 * 1024];

			is = segmentUrl.openStream();

			File file = new File(outFile);

			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}

			Date now = new Date();
			System.out.println(now + " 正在下载房间：" + inNum.getText() + "，已下载大小: "
					+ file.length() / (1024 * 1024L) + "m");

			// System.out.println("Downloading size:"+file.length() / (1024 *
			// 1024L) +"m\r");

			//if (file.length() > 1024 * 1024 * 1024 * 1L * Config.SIZE) {
			if (file.length() > 1024 * 1024 * 10L * Config.SIZE) {
				outFile = getPath();
				file = new File(outFile);
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				eventBus.post(outFile);

			}
			out = new FileOutputStream(outFile, file.exists());

			// System.out.println("正在下载:"+segmentUrl+"\r");

			int read;

			while ((read = is.read(buffer)) >= 0) {
				out.write(buffer, 0, read);
			}

		} catch (Exception e) {
			DouyuApiDataVo vo = HttpClientFromDouyu.QueryDouyuDownloadUrl(inNum.getText());
			playlisturl = vo.getHls_url();
			logger.info("下载失败:" + segmentUrl + e.getMessage());
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

	// private void loginMina(JTextField inNum, JButton butnSure, String
	// filePath,
	// List<DouyuApiServersVo> loginServerList) {
	// try {
	// LoginMinaThread e = new LoginMinaThread(filePath,loginServerList, inNum,
	// butnSure);
	// Thread loginMinaThread = new Thread(e);
	// loginMinaThread.start();
	// } catch (Exception var6) {
	// var6.printStackTrace();
	// }
	//
	// }

	public void go() {
		service.submit(new GetPlayListCallable(this));
		service.submit(new DownCallable(this));

	}

	// private static void fetchPlaylist(URL url) throws IOException {
	// BufferedReader reader = new BufferedReader(new
	// InputStreamReader(url.openStream()));
	// boolean isMaster = false;
	// long maxRate = 0L;
	// int maxRateIndex = 0;
	//
	// String line;
	// int index = 0;
	// List<String> playlist = new ArrayList<String>();
	// while ((line = reader.readLine()) != null) {
	// playlist.add(line);
	//
	// if (line.contains(BANDWIDTH))
	// isMaster = true;
	//
	// if (isMaster && line.contains("_1024/index.m3u8")) {
	// try {
	// //int pos = line.lastIndexOf("=");
	// // long bandwidth = Long.parseLong(StringUtils.substringBetween(line,
	// "BANDWIDTH=", "000") );
	// //
	// // maxRate = Math.max(bandwidth, maxRate);
	// //
	// // if (bandwidth == maxRate)
	// maxRateIndex = index; // + 1;
	// } catch (NumberFormatException ignore) {
	//
	//
	// }
	// }
	//
	// index++;
	// }
	//
	// logger.info("maxRate"+ maxRate);
	//
	//
	// reader.close();
	//
	// if (isMaster) {
	// logger.debug("Found master playlist, fetching highest stream at "+(maxRate
	// / 1024) +"Kb/s\n" );
	// url = updateUrlForSubPlaylist(url ,playlist.get(maxRateIndex));
	// playlist.clear();
	//
	// fetchsubPlaylist(url);
	// }
	//
	// System.out.println(playlist );
	//
	// }

	// private static void fetchsubPlaylist(URL url) {
	//
	//
	// BufferedReader reader = null;
	// try{
	// reader = new BufferedReader(new InputStreamReader(url.openStream()));
	//
	//
	// String line;
	//
	//
	// while ((line = reader.readLine()) != null) {
	// if (line.length() > 0 && !line.startsWith("#")) {
	// URL segmentUrl;
	//
	// if (!line.startsWith("http")) {
	// String baseUrl = getBaseUrl(url);
	// segmentUrl = new URL(baseUrl + line);
	// } else {
	// segmentUrl = new URL(line);
	// }
	// //
	// // if(!sublist.contains(segmentUrl) ) {
	// // sublist.add(segmentUrl) ;
	// // basket.offer(segmentUrl);
	// //
	// // //System.out.println(basket.isEmpty() +"www" + sublist);
	// // }
	//
	// }
	// }
	//
	// //reader.close();
	//
	// } catch (Exception e){
	// e.printStackTrace();
	// } finally{
	// try {
	// if(reader != null)
	// reader.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// }
	//
	//
	//
	//
	// private static URL updateUrlForSubPlaylist(URL url, String sub) throws
	// MalformedURLException {
	// String newUrl;
	//
	// if (!sub.startsWith("http")) {
	// newUrl = getBaseUrl(url) + sub;
	// } else {
	// newUrl = sub;
	// }
	//
	// return new URL(newUrl);
	// }
}
