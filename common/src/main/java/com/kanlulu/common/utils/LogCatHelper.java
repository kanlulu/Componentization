package com.kanlulu.common.utils;

import com.kanlulu.common.base.BaseApplication;
import com.umeng.analytics.MobclickAgent;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Administrator
 * <p>
 * log打印日志保存
 */
public class LogCatHelper {
    private static LogCatHelper instance = null;
    private int appid;//应用pid
    private Thread logThread;
    private boolean isFinish = false;
    private StringBuffer sbLog;
    private boolean isCanStart = false;
    private String splitTag;

    /**
     * @return
     */
    public static LogCatHelper getInstance() {
        if (instance == null) {
            instance = new LogCatHelper();
        }
        return instance;
    }

    private LogCatHelper() {
        appid = android.os.Process.myPid();
    }

    /**
     * 启动log日志保存
     *
     */
    public void start(String splitTag) {
        this.splitTag = splitTag;
        if (logThread == null) {
            logThread = new Thread(new LogRunnable(appid));
        }
        logThread.start();
    }

    /**
     * 提交日志
     */
    public  void upSesentimeLog() {
        try {
            LogCatHelper.getInstance().stop();
            StringBuffer bufferLog = LogCatHelper.getInstance().getLog();
            if (bufferLog != null) {
                String[] logs = bufferLog.toString().split(splitTag);
                String upLastLog = logs[logs.length - 1];
                MobclickAgent.reportError(BaseApplication.getInstance(), upLastLog);
            }
        } catch (Exception e) {
            MobclickAgent.reportError(BaseApplication.getInstance(), e.getMessage());
        }
    }

    /**
     * 关闭线程 释放资源
     */
    public  void releaseAll() {
        try {
            if (!LogCatHelper.getInstance().isFinish) {
                LogCatHelper.getInstance().stop();
            }
            LogCatHelper.getInstance().release();
        } catch (Exception e) {
            MobclickAgent.reportError(BaseApplication.getInstance(), e.getMessage());
        }
    }

    public void stop() {
        if (logThread != null) {
            isFinish = true;
        }
    }

    public StringBuffer getLog() {
        if (sbLog != null && sbLog.length() > 0) {
            return sbLog;
        } else {
            return null;
        }
    }

    public void release() {
        if (instance != null) {
            instance = null;
            sbLog = null;
            logThread = null;
        }
    }

    private class LogRunnable implements Runnable {

        private Process mProcess;
        private BufferedReader mReader;
        private String cmds;
        private String mPid;

        public LogRunnable(int pid) {
            this.mPid = "" + pid;
            cmds = "logcat *:v | grep \"(" + mPid + ")\"";
        }

        @Override
        public void run() {
            try {
                mProcess = Runtime.getRuntime().exec(cmds);
                mReader = new BufferedReader(new InputStreamReader(mProcess.getInputStream()), 1024);
                sbLog = new StringBuffer();
                String line;
                while ((line = mReader.readLine()) != null && !isFinish) {
                    if (line.length() == 0) {
                        continue;
                    }
                    if (line.contains("=开始活体识别=")) {
                        isCanStart = true;
                    }

                    if (isCanStart) {
                        sbLog.append(line).append("\r\n");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (mProcess != null) {
                    mProcess.destroy();
                    mProcess = null;
                }
                try {
                    if (mReader != null) {
                        mReader.close();
                        mReader = null;
                    }
                } catch (Exception e2) {
                    LogUtils.e("debug", e2.getMessage());
                }
                isCanStart = false;
            }
        }
    }

}

