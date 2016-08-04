package name.yumao.douyu.vo;



/**
 * Created by kaisy on 2015/11/20.
 */
public class Mdouy{


    /**
     * error : 0
     * msg : ok
     * data : {"room_id":"432854","tag_name":"数码","room_src":"http://rpic.douyucdn.cn/z1607/22/16/432854_160722162121.jpg","room_name":"淘宝造物节：科技、艺术、原创大集会","show_status":"1","online":2256,"nickname":"科技专用直播间6","hls_url":"http://hls3.douyutv.com/live/432854r2cshzswSV_550/playlist.m3u8?wsSecret=ea58c6c5064dfcd1ce2dea1f8ef9405e&wsTime=1469172585","is_pass_player":0,"is_ticket":0,"storeLink":""}
     */

    private int error;
    private String msg;
    /**
     * room_id : 432854
     * tag_name : 数码
     * room_src : http://rpic.douyucdn.cn/z1607/22/16/432854_160722162121.jpg
     * room_name : 淘宝造物节：科技、艺术、原创大集会
     * show_status : 1
     * online : 2256
     * nickname : 科技专用直播间6
     * hls_url : http://hls3.douyutv.com/live/432854r2cshzswSV_550/playlist.m3u8?wsSecret=ea58c6c5064dfcd1ce2dea1f8ef9405e&wsTime=1469172585
     * is_pass_player : 0
     * is_ticket : 0
     * storeLink :
     */

    private DataBean data;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String room_id;
        private String tag_name;
        private String room_src;
        private String room_name;
        private String show_status;
        private int online;
        private String nickname;
        private String hls_url;
        private int is_pass_player;
        private int is_ticket;
        private String storeLink;

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getTag_name() {
            return tag_name;
        }

        public void setTag_name(String tag_name) {
            this.tag_name = tag_name;
        }

        public String getRoom_src() {
            return room_src;
        }

        public void setRoom_src(String room_src) {
            this.room_src = room_src;
        }

        public String getRoom_name() {
            return room_name;
        }

        public void setRoom_name(String room_name) {
            this.room_name = room_name;
        }

        public String getShow_status() {
            return show_status;
        }

        public void setShow_status(String show_status) {
            this.show_status = show_status;
        }

        public int getOnline() {
            return online;
        }

        public void setOnline(int online) {
            this.online = online;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHls_url() {
            return hls_url;
        }

        public void setHls_url(String hls_url) {
            this.hls_url = hls_url;
        }

        public int getIs_pass_player() {
            return is_pass_player;
        }

        public void setIs_pass_player(int is_pass_player) {
            this.is_pass_player = is_pass_player;
        }

        public int getIs_ticket() {
            return is_ticket;
        }

        public void setIs_ticket(int is_ticket) {
            this.is_ticket = is_ticket;
        }

        public String getStoreLink() {
            return storeLink;
        }

        public void setStoreLink(String storeLink) {
            this.storeLink = storeLink;
        }
    }
}
