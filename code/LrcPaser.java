package Music.code;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LrcPaser {
    private int currentTime;
    private String currentContent;
    private int i = 0;
    public String title;
    public String singer;
    public String album;
    public Map<Integer, Integer> maps = new HashMap<Integer, Integer>();
    public LinkedList<String> lyricsContent = new LinkedList<>();

    public LrcPaser(String path) throws FileNotFoundException, IOException {
        InputStream in = new FileInputStream(path);
        InputStreamReader inr = new InputStreamReader(in, "GBK");
        BufferedReader reader = new BufferedReader(inr);
        String line;
        while ((line = reader.readLine()) != null) {
            parser(line);
        }
    }

    private void parser(String str) {
        if (str.startsWith("[ti:")) {
            title = str.substring(4, str.length() - 1);
        } else if (str.startsWith("[ar:")) {
            singer = str.substring(4, str.length() - 1);
        } else if (str.startsWith("[al:")) {
            album = str.substring(4, str.length() - 1);
        } else {
            String reg = "\\[(\\d{2}:\\d{2}\\.\\d{2})\\]";
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) {
                //String msg = matcher.group();
                //int start = matcher.start();
                //int end = matcher.end();
                //int groupCount = matcher.groupCount();
                currentTime = strToInt(matcher.group(1));
                String[] content = pattern.split(str);
                if (content.length != 0) {
                    currentContent = content[content.length - 1];
                }
                lyricsContent.add(currentContent);
                maps.put(currentTime, i);
                i++;
            }
        }
    }

    private int strToInt(String str) {
        String[] s = str.split(":");
        int min = Integer.parseInt(s[0]);
        String[] ss = s[1].split("\\.");
        int sec = Integer.parseInt(ss[0]);
        int mill = Integer.parseInt(ss[1]);
        return min * 60 + sec + (mill > 50 ? 1 : 0);
    }
}
