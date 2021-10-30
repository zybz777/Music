package Music.code;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.util.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyExtendsJFrame extends JFrame implements ActionListener, MouseListener {
    // 窗口类
    JLabel background;// 背景控件
    JLabel background1;// 背景控件
    JLabel background2;// 背景控件
    JLabel background3;// 背景控件
    JLabel background4;// 背景控件
    JLabel singer1;// 背景控件
    JLabel singer2;// 背景控件
    JButton buttonlogo;
    JButton buttonPlay;// 播放 按钮
    JButton buttonStop;// 停止播放
    JButton buttonNext;// 下一首 按钮
    JButton buttonLast;// 上一首 按钮
    JButton buttonOpen;// 打开文件 按钮
    JButton buttonOrderPlay;// 顺序播放 按钮
    JButton buttonSinglePlay;// 单曲循环 按钮
    JButton buttonRandomPlay;// 随机播放 按钮
    JButton buttonSetTimePlay1;// 定时关闭 按钮
    JButton buttonSetTimePlay2;// 定时关闭 按钮 15min
    JButton buttonSetTimePlay3;// 定时关闭 按钮 30min
    JButton buttonSetTimePlay4;// 定时关闭 按钮 45min
    JButton buttonSetTimePlay5;// 定时关闭 按钮 60min
    // 歌词相关
    JTextPane textLyrics;// 歌词控件
    int LyriceNUm = 12;
    String[] lastLyric = new String[LyriceNUm];// 保存12行歌词组
    int lyricsCount = 0;
    String nowLyrics = "";

    JLabel playTime;// 播放进度条控件
    JList<String> listPlayFile;// 播放列表控件
    LinkedList<String> Lyrics = new LinkedList<>();// 歌词
    Timer nTimer;// 定时器对象
    JTextArea textarea;// 歌名控件
    JTextArea runtime;// 当前歌曲时间
    JTextArea textarea1;// 歌手名
    JTextArea textSetTime;// 定时播放
    JTextArea root;// 目录
    JProgressBar progressBar;// 进度条
    int currentProgress = 0;
    int saveNumber = 0;// 保存的歌曲数量
    MyMusic music = new MyMusic();// 方法都在Mymusic类里
    String ingTime;
    String allTime;
    Font fontType1 = new Font("Dialog", 0, 20);// 歌词
    Font fontType2 = new Font("Dialog", 0, 16);// 播放列表
    Font fontType3 = new Font("Dialog", 0, 12);// 播放列表
    Color colorType1 = new Color(255, 192, 0);
    Color colorType2 = new Color(21, 21, 21);
    Boolean isReadFile = false;// 判断文件是否抓取

    public MyExtendsJFrame() {
        setTitle("嘿心音乐");// 软件名
        setBounds(300, 50, 1200, 700); // 设置窗口大小
        setLayout(null);// 空布局
        init(); // 添加控件的操作封装成一个函数
        setVisible(true);// 放在添加组件后面执行
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    void init() {// 添加控件
        // 设置背景图片
        Icon img = new ImageIcon("JAVA\\Music\\pictures\\播放列表顶部.png");
        background = new JLabel(img);// 设置背景图片
        background.setBounds(0, 0, 300, 100);// 设置背景大小
        getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));// 背景图片控件置于最底层
        ((JPanel) getContentPane()).setOpaque(false); // 控件透明

        Icon img0 = new ImageIcon("JAVA\\Music\\pictures\\底部功能栏.png"); // 创建图标对象
        background1 = new JLabel(img0);// 设置背景图片
        background1.setBounds(0, 500, 1200, 200);// 设置背景控件大小
        getLayeredPane().add(background1, new Integer(Integer.MIN_VALUE + 1));// 背景图片控件置于最底层
        ((JPanel) getContentPane()).setOpaque(false); // 控件透明

        Icon img1 = new ImageIcon("JAVA\\Music\\pictures\\播放列表.png"); // 创建图标对象
        background2 = new JLabel(img1);// 设置背景图片
        background2.setBounds(0, 100, 300, 400);// 设置背景控件大小
        getLayeredPane().add(background2, new Integer(Integer.MIN_VALUE + 1));// 背景图片控件置于最底层
        ((JPanel) getContentPane()).setOpaque(false); // 控件透明

        Icon img2 = new ImageIcon("JAVA\\Music\\pictures\\歌词板.png"); // 创建图标对象
        background3 = new JLabel(img2);// 设置背景图片
        background3.setBounds(300, 0, 900, 500);// 设置背景控件大小
        getLayeredPane().add(background3, new Integer(Integer.MIN_VALUE + 1));// 背景图片控件置于最底层
        ((JPanel) getContentPane()).setOpaque(false); // 控件透明

        Icon img3 = new ImageIcon("JAVA\\Music\\pictures\\目录.png"); // 创建图标对象
        background4 = new JLabel(img3);// 设置背景图片
        background4.setBounds(0, 100, 300, 25);// 设置背景控件大小
        getLayeredPane().add(background4, new Integer(Integer.MIN_VALUE + 2));// 背景图片控件置于最底层
        ((JPanel) getContentPane()).setOpaque(false); // 控件透明

        // 目录
        root = new JTextArea("目  录");
        root.setBounds(120, 105, 300, 25);// 设置背景控件大小
        root.setFont(fontType2);// 字体大小
        root.setForeground(colorType2);// 字体颜色
        root.setOpaque(false);
        root.setEditable(false);
        add(root);

        // 当前歌曲播放时间 控件
        runtime = new JTextArea("00:00" + " / " + "00:00");
        runtime.setBounds(800, 600, 200, 30);
        runtime.setFont(fontType3);// 字体大小
        runtime.setForeground(colorType1);// 字体颜色
        runtime.setOpaque(false);
        add(runtime);

        // 歌曲名 控件
        textarea = new JTextArea("歌曲信息");
        textarea.setBounds(290, 600, 200, 25);
        textarea.setFont(fontType3);// 字体大小
        textarea.setForeground(colorType1);// 字体颜色

        textarea.setOpaque(false);
        add(textarea);

        // logo_batton
        buttonlogo = new JButton();// 添加 打开 按钮
        buttonlogo.setBounds(0, 5, 300, 100); // 设置播放按钮大小
        Icon logo = new ImageIcon("JAVA\\Music\\pictures\\logo.png");// 创建播放图标对象
        buttonlogo.setIcon(logo); // 设置播放图标
        buttonlogo.setBorderPainted(false);
        add(buttonlogo);// 添加播放按钮至窗口中

        // 播放 控件
        buttonPlay = new JButton();// 添加 播放 按钮
        buttonPlay.setBounds(100, 550, 70, 70); // 设置播放按钮大小
        Icon icon = new ImageIcon("JAVA\\Music\\pictures\\3.png");// 创建播放图标对象
        buttonPlay.setIcon(icon); // 设置播放图标
        buttonPlay.setBorderPainted(false);
        buttonPlay.addActionListener(this);// 添加单机关联事件
        add(buttonPlay);// 添加播放按钮至窗口中
        buttonPlay.setOpaque(false); // 控件透明

        // 暂停 控件
        buttonStop = new JButton();// 添加 播放 按钮
        buttonStop.setBounds(100, 550, 70, 70); // 设置播放按钮大小
        Icon iconstop = new ImageIcon("JAVA\\Music\\pictures\\4.png");// 创建播放图标对象
        buttonStop.setIcon(iconstop); // 设置播放图标
        buttonStop.setBorderPainted(false);
        buttonStop.addActionListener(this);// 添加单机关联事件
        add(buttonStop);// 添加播放按钮至窗口中

        // 下一首 控件
        buttonNext = new JButton();// 添加 下一首 按钮
        buttonNext.setBounds(180, 570, 50, 50); // 设置播放按钮大小
        Icon icon1 = new ImageIcon("JAVA\\Music\\pictures\\5.png");// 创建播放图标对象
        buttonNext.setIcon(icon1); // 设置播放图标
        buttonNext.setBorderPainted(false);
        buttonNext.addActionListener(this);
        add(buttonNext);// 添加播放按钮至窗口中

        // 上一首 控件
        buttonLast = new JButton();// 添加 上一首 按钮
        buttonLast.setBounds(40, 570, 50, 50); // 设置播放按钮大小
        Icon icon2 = new ImageIcon("JAVA\\Music\\pictures\\2.png");// 创建播放图标对象
        buttonLast.setIcon(icon2); // 设置播放图标
        buttonLast.setBorderPainted(false);
        buttonLast.addActionListener(this);
        add(buttonLast);// 添加播放按钮至窗口中

        // 打开文件 按钮
        buttonOpen = new JButton();// 添加 打开 按钮
        buttonOpen.setBounds(900, 550, 60, 60); // 设置播放按钮大小
        Icon icon3 = new ImageIcon("JAVA\\Music\\pictures\\6.png");// 创建播放图标对象
        buttonOpen.setIcon(icon3); // 设置播放图标
        buttonOpen.setBorderPainted(false);
        buttonOpen.addActionListener(this);
        add(buttonOpen);// 添加播放按钮至窗口中

        // 顺序播放 按钮
        buttonOrderPlay = new JButton();// 添加 顺序播放 按钮
        buttonOrderPlay.setBounds(980, 550, 60, 60); // 设置播放按钮大小
        Icon icon4 = new ImageIcon("JAVA\\Music\\pictures\\7.png");// 创建播放图标对象
        buttonOrderPlay.setIcon(icon4); // 设置播放图标
        buttonOrderPlay.setBorderPainted(false);
        buttonOrderPlay.addActionListener(this);
        add(buttonOrderPlay);// 添加播放按钮至窗口中

        // 单曲循环 按钮
        buttonSinglePlay = new JButton();// 添加 单曲循环播放 按钮
        buttonSinglePlay.setBounds(980, 550, 60, 60); // 设置播放按钮大小
        Icon icon5 = new ImageIcon("JAVA\\Music\\pictures\\8.png");// 创建播放图标对象
        buttonSinglePlay.setIcon(icon5); // 设置播放图标
        buttonSinglePlay.setBorderPainted(false);
        buttonSinglePlay.addActionListener(this);
        add(buttonSinglePlay);// 添加播放按钮至窗口中

        // 随机播放 按钮
        buttonRandomPlay = new JButton(); // 添加 随机播放 按钮
        buttonRandomPlay.setBounds(980, 550, 60, 60); // 设置播放按钮大小
        Icon icon6 = new ImageIcon("JAVA\\Music\\pictures\\9.png");// 创建播放图标对象
        buttonRandomPlay.setIcon(icon6); // 设置播放图标
        buttonRandomPlay.setBorderPainted(false);
        buttonRandomPlay.addActionListener(this);
        add(buttonRandomPlay);// 添加播放按钮至窗口中

        // 定时播放
        textSetTime = new JTextArea("定时播放"); //
        textSetTime.setBounds(1065, 610, 60, 60);
        textSetTime.setFont(fontType3);// 字体大小
        textSetTime.setForeground(colorType1);// 字体颜色
        textSetTime.setOpaque(false);
        add(textSetTime);

        // 定时功能 关闭 控件
        buttonSetTimePlay1 = new JButton(); // 添加 随机播放 按钮
        buttonSetTimePlay1.setBounds(1060, 550, 60, 60); // 设置播放按钮大小
        Icon icon7 = new ImageIcon("JAVA\\Music\\pictures\\12.png");// 创建播放图标对象
        buttonSetTimePlay1.setIcon(icon7); // 设置播放图标
        buttonSetTimePlay1.setBorderPainted(false);
        buttonSetTimePlay1.addActionListener(this);
        add(buttonSetTimePlay1);// 添加播放按钮至窗口中

        // 定时功能30s 开启 控件
        buttonSetTimePlay2 = new JButton(); // 添加 随机播放 按钮
        buttonSetTimePlay2.setBounds(1060, 550, 60, 60); // 设置播放按钮大小
        Icon icon8 = new ImageIcon("JAVA\\Music\\pictures\\13.png");// 创建播放图标对象
        buttonSetTimePlay2.setIcon(icon8); // 设置播放图标
        buttonSetTimePlay2.setBorderPainted(false);
        buttonSetTimePlay2.addActionListener(this);
        add(buttonSetTimePlay2);// 添加播放按钮至窗口中

        // 定时功能1min 开启 控件
        buttonSetTimePlay3 = new JButton(); // 添加 随机播放 按钮
        buttonSetTimePlay3.setBounds(1060, 550, 60, 60); // 设置播放按钮大小
        buttonSetTimePlay3.setIcon(icon8); // 设置播放图标
        buttonSetTimePlay3.setBorderPainted(false);
        buttonSetTimePlay3.addActionListener(this);
        add(buttonSetTimePlay3);// 添加播放按钮至窗口中

        // 定时功能5min 开启 控件
        buttonSetTimePlay4 = new JButton(); // 添加 随机播放 按钮
        buttonSetTimePlay4.setBounds(1060, 550, 60, 60); // 设置播放按钮大小
        buttonSetTimePlay4.setIcon(icon8); // 设置播放图标
        buttonSetTimePlay4.setBorderPainted(false);
        buttonSetTimePlay4.addActionListener(this);
        add(buttonSetTimePlay4);// 添加播放按钮至窗口中

        // 定时功能10min 开启 控件
        buttonSetTimePlay5 = new JButton(); // 添加 随机播放 按钮
        buttonSetTimePlay5.setBounds(1060, 550, 60, 60); // 设置播放按钮大小
        buttonSetTimePlay5.setIcon(icon8); // 设置播放图标
        buttonSetTimePlay5.setBorderPainted(false);
        buttonSetTimePlay5.addActionListener(this);
        add(buttonSetTimePlay5);// 添加播放按钮至窗口中

        // 播放列表 控件
        listPlayFile = new JList<String>(); // 创建播放列表
        listPlayFile.setBounds(80, 150, 200, 400); // 设置播放列表大小
        listPlayFile.setOpaque(false);// 设置播放列表透明
        listPlayFile.setBackground(new Color(0, 0, 0, 0));// 设置播放列表背景颜色
        listPlayFile.setFont(fontType2);// 字体大小
        listPlayFile.setForeground(colorType1);// 字体颜色
        add(listPlayFile); // 添加播放列表至窗口中
        listPlayFile.addMouseListener(this);// 添加播放列表的双击事件关联

        // 歌词 控件
        textLyrics = new JTextPane(); // 创建歌词控件
        textLyrics.setBounds(500, 100, 400, 500);// 设置歌词控件大小
        textLyrics.setForeground(Color.white);// 歌词控件字体颜色
        textLyrics.setFont(fontType1);// 字体大小
        textLyrics.setForeground(colorType1);// 字体颜色
        textLyrics.setOpaque(false);// 歌词控件透明
        add(textLyrics); // 添加歌词控件至窗口中
        textLyrics.setText("点击播放列表，选择歌曲进行播放 \n");// 歌词控件添加文字

        // 进度条 控件
        progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(100); // 进度最大值和最小值
        progressBar.setValue(currentProgress);// 设置当前进度值
        progressBar.setBounds(280, 580, 600, 7);
        add(progressBar);
        progressBar.setVisible(true);

    }

    // 获取播放歌曲时间
    public String getTime(int MusicTime) {
        int TimeMin = 0;
        int TimeSec = 0;
        TimeMin = MusicTime / 60;
        TimeSec = MusicTime % 60;
        String allTimeMin;
        String allTimeSec;
        if (TimeMin < 10) {
            allTimeMin = "0" + TimeMin;
        } else {
            allTimeMin = "" + TimeMin;
        }
        if (TimeSec < 10) {
            allTimeSec = "0" + TimeSec;
        } else {
            allTimeSec = "" + TimeSec;
        }
        return allTimeMin + ":" + allTimeSec;
    }

    // 获取播放时间百分比
    public int getPercentTime(int MusicTime, int allTime) {
        return (100 * MusicTime) / allTime;
    }

    int id = -1;

    // 展示歌词
    public void SetLyrcis() {
        String lyrcis = Lyrics.get(music.getlyricsID());
        if (id != music.getlyricsID()) {
            lastLyric[lyricsCount++] = lyrcis;
            id = music.getlyricsID();
            nowLyrics = nowLyrics + "\n" + lastLyric[lyricsCount - 1];
        }
        textLyrics.setText(nowLyrics);
        if (lyricsCount == LyriceNUm) {
            lyricsCount = 0;
            nowLyrics = " ";
        }
    }

    // 清空歌词
    public void cleanLyrcis() {
        id = -1;
        for (int i = 0; i < LyriceNUm; i++) {
            lastLyric[i] = " ";
        }
        nowLyrics = " ";
        lyricsCount = 0;
    }

    // 实时变化更新
    public void run() {
        new javax.swing.Timer(100, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // 展示歌曲时间
                runtime.setText(getTime(music.getPlaytime()) + " / " + getTime(music.getNowMusicTime()));
                // 进度条展示
                currentProgress = getPercentTime(music.getPlaytime(), music.getNowMusicTime());
                progressBar.setValue(currentProgress);
                // 歌词展示
                SetLyrcis();
                // 定时播放回复
                if (music.isFinishedSetTimePlay()) {
                    buttonPlay.setVisible(true);
                    cleanLyrcis();
                    textLyrics.setText(null);
                    buttonSetTimePlay1.setVisible(true);
                    buttonSetTimePlay2.setVisible(false);
                    buttonSetTimePlay3.setVisible(false);
                    buttonSetTimePlay4.setVisible(false);
                    buttonSetTimePlay5.setVisible(false);
                    textSetTime.setText("定时播放"); //
                }
            }
        }).start();
    }

    // 绑定功能
    @Override
    public void actionPerformed(ActionEvent e) {
        // 点击播放按钮
        if (e.getSource() == buttonPlay) {
            System.out.println("播放");
            buttonPlay.setVisible(false);// 歌曲播放中，显示暂停按钮
            music.start();
            runtime.setText(getTime(music.getPlaytime()) + " / " + getTime(music.getNowMusicTime()));// 展示歌曲时间
            textarea.setText(music.getNowMusicName());// 展示歌曲信息
            Lyrics = music.getlyrics();// 得到全部歌词
            run();
        }
        // 点击暂停按钮
        if (e.getSource() == buttonStop) {
            System.out.println("暂停");
            music.start();// 停止键，关闭当前歌曲
            buttonPlay.setVisible(true);// 歌曲暂停播放，显示播放按钮
            textarea.setText(music.getNowMusicName());// 展示歌曲信息
            run();
        }
        // 点击上一首
        if ((e.getSource() == buttonLast)) {
            System.out.println("上一首");
            music.stop();// 当前歌曲停止播放
            music.lastmusic();// 上一首歌曲
            buttonPlay.setVisible(false);// 歌曲播放中，显示暂停按钮
            runtime.setText(getTime(music.getPlaytime()) + " / " + getTime(music.getNowMusicTime()));// 展示歌曲时间
            textarea.setText(music.getNowMusicName());// 展示歌曲信息
            // 重置歌词区域
            cleanLyrcis();
            textLyrics.setText(null);
            // 得到新歌词
            Lyrics = music.getlyrics();
            run();
        }
        // 点击下一首
        if (e.getSource() == buttonNext) {
            System.out.println("下一首");
            music.stop();// 当前歌曲停止播放
            music.nextmusic();
            buttonPlay.setVisible(false);// 歌曲播放中，显示暂停按钮
            runtime.setText(getTime(music.getPlaytime()) + " / " + getTime(music.getNowMusicTime()));// 展示歌曲时间
            textarea.setText(music.getNowMusicName());// 展示歌曲信息
            // 重置歌词区域
            cleanLyrcis();
            textLyrics.setText(null);
            // 得到新歌词
            Lyrics = music.getlyrics();
            run();
        }
        // 点击打开文件
        if (e.getSource() == buttonOpen) {
            System.out.println("打开文件");
            FileDialog openFile = new FileDialog(this, "打开文件");// 创建打开文件对话框
            openFile.setVisible(true); // 对话框可见
            // String playFileName = openFile.getFile();// 获取打开的文件名包括后缀

            String playFileDirectory1 = openFile.getDirectory();// 获取打开的文件路径

            System.out.println(playFileDirectory1);

            ReadFIle readFIle = new ReadFIle(playFileDirectory1);
            LinkedList<String> s1 = readFIle.getFileName();
            if (!isReadFile) {
                for (String s : s1) {
                    if (s.contains("wav")) {
                        music.addmusic(s);// 添加歌曲，将wav歌曲地址输入，可添加多首
                        String playName = s.substring(0, s.lastIndexOf("."));
                        music.addlyrics(saveNumber, playName + ".lrc");
                        saveNumber++;
                        System.out.println("添加成功");
                    }
                }
                isReadFile = true;// 文件抓取完成
            }
            // 播放列表显示
            Vector<String> vt = new Vector<String>(); // 创建Vector对象，通过add方法添加多行
            for (int k = 0; k < music.musiclist.size(); k++) {
                int j = k + 1;
                vt.add(j + ". " + music.musiclist.get(k).name);
            }
            listPlayFile.setListData(vt);// 播放列表展示

        }
        // 单曲播放
        if (e.getSource() == buttonOrderPlay) {
            System.out.println("单曲播放");
            buttonOrderPlay.setVisible(false);
            buttonSinglePlay.setVisible(true);
            buttonRandomPlay.setVisible(false);
            music.setPlayState(1);
        }
        // 随机循环
        if (e.getSource() == buttonSinglePlay) {
            System.out.println("随机循环");
            buttonOrderPlay.setVisible(false);
            buttonSinglePlay.setVisible(false);
            buttonRandomPlay.setVisible(true);
            music.setPlayState(2);
        }
        // 顺序播放
        if (e.getSource() == buttonRandomPlay) {
            System.out.println("顺序播放");
            buttonOrderPlay.setVisible(true);
            buttonSinglePlay.setVisible(false);
            buttonRandomPlay.setVisible(false);
            music.setPlayState(0);
        }
        // 定时播放 30s
        if (e.getSource() == buttonSetTimePlay1) {
            System.out.println("开启定时播放30s");
            textSetTime.setText("30秒");
            buttonSetTimePlay1.setVisible(false);
            buttonSetTimePlay2.setVisible(true);
            buttonSetTimePlay3.setVisible(false);
            buttonSetTimePlay4.setVisible(false);
            buttonSetTimePlay5.setVisible(false);
            music.setTime(30);

        }
        // 定时播放 1min
        if (e.getSource() == buttonSetTimePlay2) {
            System.out.println("开启定时播放1min");
            textSetTime.setText("1分钟");
            buttonSetTimePlay1.setVisible(false);
            buttonSetTimePlay2.setVisible(false);
            buttonSetTimePlay3.setVisible(true);
            buttonSetTimePlay4.setVisible(false);
            buttonSetTimePlay5.setVisible(false);
            music.setTime(60);
        }
        // 定时播放 5min
        if (e.getSource() == buttonSetTimePlay3) {
            System.out.println("开启定时播放5min");
            textSetTime.setText("5分钟");
            buttonSetTimePlay1.setVisible(false);
            buttonSetTimePlay2.setVisible(false);
            buttonSetTimePlay3.setVisible(false);
            buttonSetTimePlay4.setVisible(true);
            buttonSetTimePlay5.setVisible(false);
            music.setTime(300);
        }
        // 定时播放 10min
        if (e.getSource() == buttonSetTimePlay4) {
            System.out.println("开启定时播放10min");
            textSetTime.setText("10分钟");
            buttonSetTimePlay1.setVisible(false);
            buttonSetTimePlay2.setVisible(false);
            buttonSetTimePlay3.setVisible(false);
            buttonSetTimePlay4.setVisible(false);
            buttonSetTimePlay5.setVisible(true);
            music.setTime(600);
        }
        // 定时播放 off
        if (e.getSource() == buttonSetTimePlay5) {
            System.out.println("关闭定时播放");
            textSetTime.setText("定时播放");
            buttonSetTimePlay1.setVisible(true);
            buttonSetTimePlay2.setVisible(false);
            buttonSetTimePlay3.setVisible(false);
            buttonSetTimePlay4.setVisible(false);
            buttonSetTimePlay5.setVisible(false);
            music.setTime(music.getNowMusicTime());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    // 点击鼠标
    @Override
    public void mouseClicked(MouseEvent e) {
        // 如表连续点击两次
        if (e.getClickCount() == 2) {
            if (e.getSource() == listPlayFile) { // 在播放列表里双击
                int index = listPlayFile.getSelectedIndex();
                System.out.println("播放");
                music.playSelectedMusic(index);// 音乐播放
                buttonPlay.setVisible(false);// 歌曲播放中，显示暂停按钮
                runtime.setText(getTime(music.getPlaytime()) + " / " + getTime(music.getNowMusicTime()));// 展示歌曲时间
                textarea.setText(music.getNowMusicName());// 展示歌曲信息
                // 重置歌词区域
                cleanLyrcis();
                textLyrics.setText(null);
                // 得到新歌词
                Lyrics = music.getlyrics(index);
                run();
            }
        }
    }

}
