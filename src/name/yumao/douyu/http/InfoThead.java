package name.yumao.douyu.http;

import java.io.File;

import javax.swing.JTextField;

import name.yumao.douyu.utils.SetSystemProperty;

import org.apache.log4j.Logger;

public class InfoThead implements Runnable{
	private static Logger logger = Logger.getLogger(InfoThead.class);	
	private JTextField inNum;
	boolean write = false;
	public InfoThead(JTextField inNum,boolean write){
		logger.info("Sub:读写配置");
		this.inNum = inNum;
		this.write = write;


	}
	
	
	
	@Override
	public void run() {
		
		try {
			
			if(write){
				SetSystemProperty.writeProperties("room", inNum.getText() );
			} else {
				inNum.setText( SetSystemProperty.readValue("conf"+File.separator+"update.properties" , "room") );
			}
			
		  Config.TIMEOUT = Integer.parseInt(SetSystemProperty.readValue("conf"+File.separator+"update.properties" , "Timeout"));
		  Config.CONNECTION_TIMEOUT = Integer.parseInt(SetSystemProperty.readValue("conf"+File.separator+"update.properties" , "CONNECTION_TIMEOUT").trim() );
		  Config.SO_TIMEOUT = Integer.parseInt(SetSystemProperty.readValue("conf"+File.separator+"update.properties" , "SO_TIMEOUT").trim() );
		  Config.CONNECTIONREQUESTTIMEOUT = Integer.parseInt(SetSystemProperty.readValue("conf"+File.separator+"update.properties" , "CONNECTIONREQUESTTIMEOUT").trim() );
		  logger.info(Config.TIMEOUT + ":"+Config.CONNECTION_TIMEOUT +":"+ Config.SO_TIMEOUT);
		  
		  

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
