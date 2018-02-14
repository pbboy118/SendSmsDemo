package ttzg.com.smssenderview;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SendSmsTimer {

    private static SendSmsTimer pinstance;
    List<UpdateListener> mListeners = new ArrayList<UpdateListener>();
    private int left = 0;
    private TimerTask mTimerTask = null;
    private Timer timer;

    public static synchronized SendSmsTimer getPInstance() {
        if (pinstance == null) {
            pinstance = new SendSmsTimer();
        }
        return pinstance;
    }


    public void stop() {
        left = 0;
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        mListeners.clear();
        pinstance = null;
    }

    public void start() {
        if (left > 0) {
            return;
        } else {
            if (mTimerTask == null) {
                mTimerTask = new TimerTask() {
                    @Override
                    public void run() {
                        if (left > 0) {
                            left = left - 1;
                        }
                        dispatchUpdate();
                    }
                };
                timer = new Timer();
                timer.schedule(mTimerTask, 0, 1000);
            }
                left = 10;

            dispatchUpdate();
        }
    }

    private void dispatchUpdate() {
        for (UpdateListener listener : mListeners) {
            listener.onUpdate(left);
        }
    }

    public void addListener(UpdateListener listener) {
        mListeners.add(listener);
        dispatchUpdate();
    }

    public void removeListener(UpdateListener listener) {
        mListeners.remove(listener);
    }

    public boolean isStarted() {
        return left > 0;
    }

    public interface UpdateListener {
        void onUpdate(int left);
    }

    public void reset() {
        left = 0;
    }

}
