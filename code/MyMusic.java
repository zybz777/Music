package Music.code;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class MyMusic {
    LinkedList<Music> musiclist = new LinkedList<Music>();
    Timer musictime; // 定时器
    boolean has_stop = true; // 停止
    boolean has_suspend = false; // 暂停
    int playtime = 0; // 当前歌曲播放的时间
    boolean TimerOpen = false;
    boolean TimingPlay = false;
    int number = 0; // 当前播放的歌曲序号
    int playState = 0;// 0为循环播放，1为单曲循环，2为随机播放
    int nameNumber = 0;// 添加名字
    int lyricsID = 0;// 当前播放的歌词的ID号
    boolean setTimePlay = false;

    private int totaltime = 0;
    private int timing = 0;

    public MyMusic() {
        musictime = new Timer();
        musictime.schedule(task, 0, 1000);
    }

    public void setPlayState(int playState) {
        this.playState = playState;
    }

    public void addmusic(String path) {
        musiclist.add(new Music(path));
        musiclist.get(nameNumber).SetName(path);
        nameNumber++;
    }

    public void addlyrics(int id, String path) {
        musiclist.get(id).add_lrc(path);
    }

    public void setTime(int time) {
        timing = time;
        TimingPlay = true;
        setTimePlay = false;
    }

    public int getNowMusicTime() { // 当前播放的歌曲总时长
        return musiclist.get(number).getTime();
    }

    public int getMusicTime(int id) { // 指定ID号的歌曲总时长
        return musiclist.get(id).getTime();
    }

    public int getPlaytime() { // 当前歌曲已播放的时长
        return playtime;
    }

    public String getNowMusicName() {
        return musiclist.get(number).getName();
    }

    public String getMusicName(int id) {
        return musiclist.get(id).getName();
    }

    public LinkedList<String> getlyrics() {
        return musiclist.get(number).lyricsContent;
    }

    public int getlyricsID() {
        return lyricsID;
    }

    public void start() {
        if (!has_suspend && has_stop) {
            TimerOpen = true;
            has_stop = false;
            musiclist.get(number).play();
        } else if (!has_stop && !has_suspend) {
            musiclist.get(number).suspense();
            TimerOpen = false;
            has_suspend = true;
        } else if (!has_stop && has_suspend) {
            musiclist.get(number).continues();
            TimerOpen = true;
            has_suspend = false;
        }

    }

    public void stop() {
        musiclist.get(number).stop();
        has_stop = true;
        has_suspend = false;
        TimerOpen = false;
        playtime = 0;
        lyricsID = 0;// 新增
    }

    public void nextmusic() {
        switch (playState) {
            case 0: {
                stop();
                number = number + 1 == musiclist.size() ? 0 : number + 1;
                start();
                has_stop = false;
                has_suspend = false;
                break;
            }
            case 1: {
                stop();
                start();
                has_stop = false;
                has_suspend = false;
                break;
            }
            case 2: {
                RandomPlayMusic();
                break;
            }
            default:
                break;
        }
    }

    public void lastmusic() {
        switch (playState) {
            case 0: {
                stop();
                number = number - 1 == -1 ? musiclist.size() - 1 : number - 1;
                start();
                has_stop = false;
                has_suspend = false;
                break;
            }
            case 1: {
                stop();
                start();
                has_stop = false;
                has_suspend = false;
                break;
            }
            case 2: {
                RandomPlayMusic();
                break;
            }
            default:
                break;
        }
    }

    public void RandomPlayMusic() {
        int temp;
        Random rand = new Random();
        stop();
        while ((temp = rand.nextInt(musiclist.size())) == number) {
        }
        number = temp;
        start();
        has_stop = false;
        has_suspend = false;
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (TimerOpen) {
                if (musiclist.get(number).getID(playtime) != null)
                    lyricsID = musiclist.get(number).getID(playtime);
                playtime++;
                if (!musiclist.get(number).isalive()) {
                    nextmusic();
                    playtime = 0;
                }
            }
            if (TimingPlay) {
                totaltime++;
                if (totaltime >= timing) {
                    stop();
                    TimingPlay = false;
                    totaltime = 0;
                    timing = 0;
                    setTimePlay = true;// 定时播放已完成
                }
            }
        }
    };

    // 定时播放完成信号 true
    public boolean isFinishedSetTimePlay() {
        boolean finished = setTimePlay;
        setTimePlay = false;
        return finished;
    }

    // 播放列表选择播放歌曲
    public void playSelectedMusic(int index) {
        stop();
        this.number = index;
        start();
    }

    // 播放列表选择播放歌曲的歌词
    public LinkedList<String> getlyrics(int index) {
        this.number = index;
        return musiclist.get(number).lyricsContent;
    }
}

class Music {
    String name = "";
    double time;
    Map<Integer, Integer> lyrics = new HashMap<Integer, Integer>();
    LinkedList<String> lyricsContent;
    Musicplayer player;
    String path;

    public Music(String path) {
        this.player = new Musicplayer(path);
        this.path = path;
        Clip clip = null;
        AudioInputStream inputStream = null;
        try {
            inputStream = AudioSystem.getAudioInputStream(new File(path));
            clip = AudioSystem.getClip();
            clip.open(inputStream);
            time = clip.getMicrosecondLength() / 1000000D;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (clip != null) {
                clip.close();
            }
        }
    }

    public void add_lrc(String path) {
        try {
            LrcPaser lrcPaser = new LrcPaser(path);
            lyrics = lrcPaser.maps;
            lyricsContent = lrcPaser.lyricsContent;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void play() {
        this.player.start(false);
    }

    public void stop() {
        this.player.close();
        this.player = new Musicplayer(path);
    }

    public void suspense() {
        this.player.stop();
    }

    public void continues() {
        this.player.continues();
    }

    public boolean isalive() {
        return player.is_stop();
    }

    public int getTime() {
        return (int) time;
    }

    // 取得名字
    public String SetName(String path) {
        String[] string1 = path.split("/");
        String[] string2 = string1[1].split("[.]");
         return name = string2[0];
    }

    // 输出名字
    public String getName() {
        return name;
    }

    public Integer getID(int time) {
        if (lyrics.get(time) != null)
            return lyrics.get(time);
        else
            return null;
    }
}
