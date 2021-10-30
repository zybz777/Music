package Music.code;

import java.io.File;
import java.util.LinkedList;

public class ReadFIle {  //读文件类
    private String path;
    public ReadFIle(String path) {  //输入一个文件夹的路径
        this.path = path;
    }
    public LinkedList<String> getFileName() {
        File file = new File(path);
        String[] filelist = file.list();
        LinkedList<String> pathlist = new LinkedList<>();
        for(String s : filelist) {
            pathlist.add(path + "/" + s);
        }
        return pathlist; //返回的是一个链表，里面是文件夹里的所有文件路径，顺序是文件夹的里的顺序，通常是偶数歌词文件，奇数音频文件
    }
    public static void main(String[] args) { //测试程序
        ReadFIle readFIle = new ReadFIle("JAVA\\Music\\songs");
        LinkedList<String> s1 = readFIle.getFileName();
        for (String s : s1) {
            System.out.println(s);
            String playName = s.substring(0, s.lastIndexOf("."));
            System.out.println(playName);
        }
    }
}
