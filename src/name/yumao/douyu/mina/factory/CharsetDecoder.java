package name.yumao.douyu.mina.factory;

import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class CharsetDecoder implements ProtocolDecoder{

 
 private final static Logger log = Logger.getLogger(CharsetDecoder.class);  
 private final static Charset charset = Charset.forName("UTF-8");
 private final static AttributeKey leftBuff=new AttributeKey(CharsetDecoder.class, "leftByteBuff");//Creates a Key from a class name and an attribute name. The resulting Key will be stored in the session Map.
 private IoBuffer buff = IoBuffer.allocate(0).setAutoExpand(true);
 @Override
 public synchronized void  decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
   throws Exception {
  // TODO Auto-generated method stub
  
  in.order();
  try{
  IoBuffer iobuffer=null;
  byte[] bytebuf=(byte[])session.getAttribute(leftBuff);//judge if there is data that left;
  if(bytebuf==null){
   iobuffer=in;
  }else{
   iobuffer=IoBuffer.allocate(bytebuf.length+in.remaining());//if there is left data,merge with the new data
   iobuffer.setAutoExpand(true);
   iobuffer.put(bytebuf);
   iobuffer.put(in);
   iobuffer.flip();
  }
  
  while(iobuffer.hasRemaining()){
   iobuffer.mark();
   byte b = iobuffer.get();
   if (b =='\n') {  
    buff.flip();
    byte[] bytes=new byte[buff.limit()];
    buff.get(bytes);
    String message=new String(bytes,charset);
    out.write(message);
    buff.clear();
    
    log.info("message: " + message);  
   }else{
   
       buff.put(b);
   }
  }
  if(buff.hasRemaining()){
	   buff.flip();
	   byte[] leftbyte=new byte[buff.limit()];
	   buff.get(leftbyte);
	   String mes=new String(leftbyte,charset);
	   session.setAttribute(leftBuff, leftbyte);
	   //iobuffer.skip(lengthByte);
   
  }
  }catch (Exception e) {
   e.printStackTrace();// TODO: handle exception
  }
  
   
 }
    public String getValueFromValue(byte[] bytebuf){
     String value=new String(bytebuf,charset);
     return value;
    }
 @Override
 public void dispose(IoSession session) throws Exception {
  // TODO Auto-generated method stub
  log.info("#########dispose#########");     
  log.info(session.getCurrentWriteMessage());
 }

 @Override
 public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1)
   throws Exception {
  // TODO Auto-generated method stub
 }

 
}
