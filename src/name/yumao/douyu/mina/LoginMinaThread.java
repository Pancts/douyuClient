package name.yumao.douyu.mina;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JTextField;

import name.yumao.douyu.http.HttpClientFromDouyu;
import name.yumao.douyu.mina.factory.DouyuCodecFactory;
import name.yumao.douyu.utils.MD5Util;
import name.yumao.douyu.utils.SttDecoder;
import name.yumao.douyu.utils.SttEncoder;
import name.yumao.douyu.vo.DouyuApiDataVo;
import name.yumao.douyu.vo.DouyuApiServersVo;

import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.google.common.eventbus.EventBus;

public class LoginMinaThread implements Runnable{
	private static Logger logger = Logger.getLogger(LoginMinaThread.class);
	private String Server1_Host;
	private int Server1_Port;
	private String filepath;
	private JTextField inNum;
	private JButton butnSure;
	private SttEncoder sttEncoder;
	private Properties properties;
	
	  // EventBus eventBus ;
	public LoginMinaThread(String filepath,JTextField inNum,JButton butnSure/*,   EventBus eventBus*/ ){
		//拉起进程同时随机选择登陆服务器

		//初始化
		this.inNum = inNum;
		this.butnSure = butnSure;
		this.sttEncoder = new SttEncoder();
		this.filepath = filepath;
		//this.eventBus = eventBus;
		//设置配置文件读取路径
		try {
			InputStream inputStream=new BufferedInputStream(new FileInputStream("conf"+File.separator+"auth.properties"));
			this.properties = new Properties();
			properties.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void run() { 
		try{	
					if(inNum.getText().equals("")){
						inNum.setEditable(true);
						butnSure.setEnabled(true);
						logger.info("无效的房间名or房间号！");
					}else{
						
					  	DouyuApiDataVo api = HttpClientFromDouyu.QueryDouyuDownloadUrl(inNum.getText() );
						DouyuApiServersVo loginServerVo = api.getServers().get((int)(Math.random()*api.getServers().size()));
						Server1_Host = loginServerVo.getIp();
						Server1_Port = Integer.parseInt(loginServerVo.getPort());
						logger.info("随机选择登陆服务器 " + Server1_Host + ":" + Server1_Port);
						
						IoConnector connector = new NioSocketConnector();
						DefaultIoFilterChainBuilder chain = connector.getFilterChain();
						chain.addLast("codec", new ProtocolCodecFilter(new DouyuCodecFactory()));
						connector.setHandler(new LoginMinaHandler(filepath,inNum,butnSure,properties));
						IoSession session = null;
						
							ConnectFuture future = connector.connect(new InetSocketAddress(Server1_Host,Server1_Port));
							future.awaitUninterruptibly();
							session=future.getSession();
							SttDecoder sttDecoder = new SttDecoder();
							sttDecoder.Parse(inNum.getText());
							if(sttDecoder.GetItem("type")!=null){
								session.write(inNum.getText());
								inNum.setText(sttDecoder.GetItem("roomid"));
							}else{
								//发送匿名登录信息
								Calendar calendar=Calendar.getInstance();
								Date date=calendar.getTime();
								long nowTime = date.getTime()/1000;
								sttEncoder.Clear();
								sttEncoder.AddItem("type","loginreq");
								if(Boolean.parseBoolean(properties.getProperty("Auth_Login"))){
									
									sttEncoder.AddItem("nickname",new String(Base64.getDecoder().decode(properties.getProperty("Auth_User")),"UTF-8") );
									sttEncoder.AddItem("password",MD5Util.MD5(new String(Base64.getDecoder().decode(properties.getProperty("Auth_Pwd")),"UTF-8")));
								}else{
									sttEncoder.AddItem("username","");
									sttEncoder.AddItem("password","");
								}
								String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
								String timestamp = Long.toString(nowTime);
								sttEncoder.AddItem("roomid",inNum.getText());
								sttEncoder.AddItem("ct","2");
								sttEncoder.AddItem("devid",uuid);	
								sttEncoder.AddItem("ver","20150526");	
								sttEncoder.AddItem("rt",timestamp);
								sttEncoder.AddItem("vk", MD5Util.MD5(timestamp + "7oE9nPEG9xXV69phU31FYCLUagKeYtsF" + uuid));
								session.write(sttEncoder.GetResualt());
							}

					}
				
		}catch (Exception e) {
			logger.info("登录服务器通讯失败 "+e.toString());
		}
	}
	
}
