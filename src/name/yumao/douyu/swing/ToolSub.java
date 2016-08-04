//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package name.yumao.douyu.swing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import org.apache.log4j.Logger;

public class ToolSub {
    private Date initTime;
    private File file;
    private BufferedWriter bufferedWriter;
    private Random random;
    private static Logger logger = Logger.getLogger(ToolSub.class);

    public ToolSub(String filePath) throws Exception {
        logger.info("Sub:初始化字幕转储进程");
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        this.initTime = calendar.getTime();
        this.file = new File(filePath);
        this.file.getParentFile().mkdirs();
        this.bufferedWriter = new BufferedWriter(new FileWriter(this.file));
        this.random = new Random();
        this.bufferedWriter.write("[Script Info]");
        this.bufferedWriter.newLine();
        this.bufferedWriter.write("; // 此字幕由斗鱼TV录制助手生成");
        this.bufferedWriter.newLine();
        this.bufferedWriter.write("; // 欢迎关注Teemo的腾讯微博");
        this.bufferedWriter.newLine();
        this.bufferedWriter.write("; // http://t.qq.com/reusu123");
        this.bufferedWriter.newLine();
        this.bufferedWriter.write("Title:DouyuAssiatantSub");
        this.bufferedWriter.newLine();
        this.bufferedWriter.write("Original Script:DouyuAssiatantV3.x");
        this.bufferedWriter.newLine();
        this.bufferedWriter.write("Synch Point:0");
        this.bufferedWriter.newLine();
        this.bufferedWriter.write("ScriptType:v4.00");
        this.bufferedWriter.newLine();
        this.bufferedWriter.write("Collisions:Normal");
        this.bufferedWriter.newLine();
        this.bufferedWriter.write("PlayResX:1280");
        this.bufferedWriter.newLine();
        this.bufferedWriter.write("PlayResY:720");
        this.bufferedWriter.newLine();
        this.bufferedWriter.write("Timer:100.0000");
        this.bufferedWriter.newLine();
        this.bufferedWriter.newLine();
        this.bufferedWriter.write("[V4+ Styles]");
        this.bufferedWriter.newLine();
        this.bufferedWriter.write("Format: Name, Fontname, Fontsize, PrimaryColour, SecondaryColour, OutlineColour, BackColour, Bold, Italic, Underline, StrikeOut, ScaleX, ScaleY, Spacing, Angle, BorderStyle, Outline, Shadow, Alignment, MarginL, MarginR, MarginV, Encoding");
        this.bufferedWriter.newLine();
        this.bufferedWriter.write("Style: Default,微软雅黑,25,&H00FFFFFF,&H00000000,&H00000000,&H00000000,-1,0,0,0,100,100,0,0.00,1,1,0,2,30,30,10,134");
        this.bufferedWriter.newLine();
        this.bufferedWriter.newLine();
        this.bufferedWriter.write("[Events]");
        this.bufferedWriter.newLine();
        this.bufferedWriter.write("Format: Layer, Start, End, Style, Actor, MarginL, MarginR, MarginV, Effect, Text");
        this.bufferedWriter.flush();
    }

    public void addSubString(String str) throws Exception {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        Long startTime = Long.valueOf(calendar.getTime().getTime() - this.initTime.getTime());
        startTime = Long.valueOf(startTime.longValue() + 0L);
        Long endTime = Long.valueOf(startTime.longValue() + 10000L);
        String randomNum = String.valueOf(this.random.nextInt(720));
        String startDate = String.format("%02d:%02d:%02d.%02d", new Object[]{Long.valueOf(startTime.longValue() % 86400000L / 3600000L), Long.valueOf(startTime.longValue() % 3600000L / 60000L), Long.valueOf(startTime.longValue() % 60000L / 1000L), Long.valueOf(startTime.longValue() / 10L % 100L)});
        String endDate = String.format("%02d:%02d:%02d.%02d", new Object[]{Long.valueOf(endTime.longValue() % 86400000L / 3600000L), Long.valueOf(endTime.longValue() % 3600000L / 60000L), Long.valueOf(endTime.longValue() % 60000L / 1000L), Long.valueOf(endTime.longValue() / 10L % 100L)});
        if(str.startsWith("=")) {
            str = " " + str;
        }

        this.bufferedWriter.newLine();
        this.bufferedWriter.write("Dialogue: 0," + startDate + "," + endDate + ",*Default,NTP,0000,0000,0000,,{\\move(1280," + randomNum + ",0," + randomNum + ")}" + str);
        this.bufferedWriter.flush();
    }

    public void close() throws Exception {
        if(this.bufferedWriter != null) {
            this.bufferedWriter.flush();
            this.bufferedWriter.close();
            this.bufferedWriter = null;
        }

        logger.info("*******************************" + this.bufferedWriter);
    }
}
