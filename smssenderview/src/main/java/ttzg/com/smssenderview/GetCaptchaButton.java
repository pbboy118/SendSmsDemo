package ttzg.com.smssenderview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

public class GetCaptchaButton extends android.support.v7.widget.AppCompatTextView implements View.OnClickListener, SendSmsTimer.UpdateListener {

    private SendSmsTimer mSendSmsTimer;
    private String mPhone;
    private String mcheckCode = "";
    private Activity activity;
    private String mType;
    private int mLeft;
    private String mToken;
    private Paint paint;
    private boolean needOutSms = false;
    private String partyid, uid;
    private String adminpartyId;
    public final static int ACTION_CODE = 9999;

    public GetCaptchaButton(Context context) {
        this(context, null);
        //init();
    }

    public GetCaptchaButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        //init();
    }

    public GetCaptchaButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
        update();
    }


    public void setActivity(Activity activity) {
        this.activity = activity;
    }


    private void init() {
        if (isInEditMode()) {
            return;
        }
        paint = new Paint();
        setOnClickListener(this);
        mSendSmsTimer = SendSmsTimer.getPInstance();
        if (mSendSmsTimer != null)
            mSendSmsTimer.addListener(this);
    }

    @Override
    public void onClick(View v) {
        sendMsg();
    }

    public void sendMsg() {
        //if (isEnabled() && mPhone.length() == 11) {
            sendMsg(getPhone());


            if (outSmsListener != null)
                outSmsListener.needOutSendSms(true);
        //}
    }

    public void startTimer() {
        mSendSmsTimer.start();
    }

    public void stopTimer() {
        if (mSendSmsTimer != null) {
            mSendSmsTimer.stop();
            mSendSmsTimer = null;
        }
    }

    public void sendMsg(final String mPhone) {

        if (mSendSmsTimer.isStarted()) {
            return;
        }

        startTimer();
    }

    public int getRequestWath() {
        return ACTION_CODE;
    }

    @Override
    public void onUpdate(int left) {

        mLeft = left;
        post(new Runnable() {
            @Override
            public void run() {
                update();
            }
        });
    }

    private void update() {
        if (needOutSms) {
            setText("短信已发出");
            return;
        }
        if (mLeft > 0) {
            setEnabled(false);
            setText(getContext().getString(R.string.sms_send_wait, mLeft));
            setTextColor(getResources().getColor(R.color.color_useless_gray));
        } else {
            ///if (!TextUtils.isEmpty(mPhone) && mPhone.length() == 11) {
                setEnabled(true);
                setTextColor(getResources().getColor(R.color.color_f47_red));
//            } else {
//                setTextColor(getResources().getColor(R.color.color_useless_gray));
//                setEnabled(false);
//            }

            setText(getContext().getString(R.string.sms_get_code));

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isInEditMode()) {
            return;
        }
        if (mSendSmsTimer != null) {
            mSendSmsTimer.removeListener(this);
        }
    }

    private OutSmsListener outSmsListener;

    public void setOutSmsListener(OutSmsListener outSendSMSListener) {
        this.outSmsListener = outSendSMSListener;
    }

    public interface OutSmsListener {
        void needOutSendSms(boolean needed);
    }

}
