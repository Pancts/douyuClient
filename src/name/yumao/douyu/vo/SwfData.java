package name.yumao.douyu.vo;



/**
 * Created by kaisy on 2015/11/20.
 */
public class SwfData {


    /**
     * error : 0
     * data : {"inNA":0,"room_id":"632578","rtmp_cdn":"tct","rtmp_live":"632578rWLYRrgGEF.flv?wsAuth=306be3478803f2b5089c0191847eb704&token=web-0-632578-21e45bdb98c245868500655798a1d432&logo=0&expire=0","rtmp_url":"http://tc-tct.douyucdn.cn/dyliveflv3","rateSwitch":0}
     */

    private int error;
    /**
     * inNA : 0
     * room_id : 632578
     * rtmp_cdn : tct
     * rtmp_live : 632578rWLYRrgGEF.flv?wsAuth=306be3478803f2b5089c0191847eb704&token=web-0-632578-21e45bdb98c245868500655798a1d432&logo=0&expire=0
     * rtmp_url : http://tc-tct.douyucdn.cn/dyliveflv3
     * rateSwitch : 0
     */

    private DataBean data;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int inNA;
        private String room_id;
        private String rtmp_cdn;
        private String rtmp_live;
        private String rtmp_url;
        private int rateSwitch;

        public int getInNA() {
            return inNA;
        }

        public void setInNA(int inNA) {
            this.inNA = inNA;
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getRtmp_cdn() {
            return rtmp_cdn;
        }

        public void setRtmp_cdn(String rtmp_cdn) {
            this.rtmp_cdn = rtmp_cdn;
        }

        public String getRtmp_live() {
            return rtmp_live;
        }

        public void setRtmp_live(String rtmp_live) {
            this.rtmp_live = rtmp_live;
        }

        public String getRtmp_url() {
            return rtmp_url;
        }

        public void setRtmp_url(String rtmp_url) {
            this.rtmp_url = rtmp_url;
        }

        public int getRateSwitch() {
            return rateSwitch;
        }

        public void setRateSwitch(int rateSwitch) {
            this.rateSwitch = rateSwitch;
        }
    }
}
