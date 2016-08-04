package name.yumao.douyu.vo;

import java.io.Serializable;


public class DouyuApiVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 12072122814258076L;
	private String error;
	private DouyuApiDataVo data;
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public DouyuApiDataVo getData() {
		return data;
	}
	public void setData(DouyuApiDataVo data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "DouyuApiVo [error=" + error + ", data=" + data + "]";
	}
	
	
	
}
