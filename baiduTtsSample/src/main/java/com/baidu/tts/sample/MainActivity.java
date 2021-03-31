/**
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tts.sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizeBag;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.SynthesizerTool;
import com.baidu.tts.client.TtsMode;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author liweigao 2015年9月15日
 */
public class MainActivity extends Activity implements OnClickListener, SpeechSynthesizerListener {
    private Button mSpeak, mPause, mResume, mStop, mSynthesize, mPlay, mBatchSpeak, mNextActivity;
    private EditText mInput;
    private TextView mShowText;

    private SpeechSynthesizer mSpeechSynthesizer;
    private String mSampleDirPath;
    private static final String SAMPLE_DIR_NAME = "baiduTTS";
    private static final String SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";
    private static final String SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male.dat";
    private static final String TEXT_MODEL_NAME = "bd_etts_text.dat";
    private static final String LICENSE_FILE_NAME = "temp_license";

    private static final int PRINT = 0;
    private static final int UI_CHANGE_INPUT_TEXT_SELECTION = 1;
    private static final int UI_CHANGE_SYNTHES_TEXT_SELECTION = 2;
    private static final String TAG = "MainActivity";

    /*
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialEnv();
        initialView();
        initialTts();
    }

    private void initialEnv() {
        if (mSampleDirPath == null) {
            String sdcardPath = Environment.getExternalStorageDirectory().toString();
            mSampleDirPath = sdcardPath + "/" + SAMPLE_DIR_NAME;
        }
        makeDir(mSampleDirPath);
        copyFromAssetsToSdcard(false, SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, TEXT_MODEL_NAME, mSampleDirPath + "/" + TEXT_MODEL_NAME);
        copyFromAssetsToSdcard(false, LICENSE_FILE_NAME, mSampleDirPath + "/" + LICENSE_FILE_NAME);
    }

    private void makeDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 将sample工程需要的资源文件拷贝到SD卡中使用（授权文件为临时授权文件，请注册正式授权）
     * 
     * @param isCover 是否覆盖已存在的目标文件
     * @param source
     * @param dest
     */
    private void copyFromAssetsToSdcard(boolean isCover, String source, String dest) {
        File file = new File(dest);
        if (isCover || (!isCover && !file.exists())) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = getResources().getAssets().open(source);
                String path = dest;
                fos = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = is.read(buffer, 0, 1024)) >= 0) {
                    fos.write(buffer, 0, size);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initialView() {
        this.mSpeak = (Button) this.findViewById(R.id.speak);
        this.mSpeak.setOnClickListener(this);
        this.mPause = (Button) this.findViewById(R.id.pause);
        this.mPause.setOnClickListener(this);
        this.mResume = (Button) this.findViewById(R.id.resume);
        this.mResume.setOnClickListener(this);
        this.mStop = (Button) this.findViewById(R.id.stop);
        this.mStop.setOnClickListener(this);
        this.mSynthesize = (Button) this.findViewById(R.id.synthesize);
        this.mSynthesize.setOnClickListener(this);
        this.mPlay = (Button) this.findViewById(R.id.play);
        this.mPlay.setOnClickListener(this);
        this.mBatchSpeak = (Button) this.findViewById(R.id.batchSpeak);
        this.mBatchSpeak.setOnClickListener(this);
        this.mNextActivity = (Button) this.findViewById(R.id.nextActivity);
        this.mNextActivity.setOnClickListener(this);
        this.mInput = (EditText) this.findViewById(R.id.input);
        this.mShowText = (TextView) this.findViewById(R.id.showText);
        this.mShowText.setMovementMethod(new ScrollingMovementMethod());
    }

    private void initialTts() {
        // 是否开启LOG开关，默认为关闭
        LoggerProxy.printable(true);
        this.mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        this.mSpeechSynthesizer.setContext(this);
        this.mSpeechSynthesizer.setSpeechSynthesizerListener(this);

        // 设置PID
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_PRODUCT_ID, "1");
        // 发音人（在线引擎），可用参数为0,1,2,3。。。（服务器端会动态增加，各值含义参考文档，以文档说明为准。0--普通女声，1--普通男声，2--特别男声，3--情感男声。。。）
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");

        //  URL设置,默认URL为https://tts.baidu.com/text2audio
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_URL, "https://tts.baidu.com/text2audio");

        // 初始化tts
        mSpeechSynthesizer.initTts(TtsMode.ONLINE);

    }

    /* 
     * 
     */
    @Override
    protected void onDestroy() {
        this.mSpeechSynthesizer.release();
        super.onDestroy();
    }

    /*
     * @param v
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.speak:
                speak();
                break;
            case R.id.pause:
                pause();
                break;
            case R.id.resume:
                resume();
                break;
            case R.id.stop:
                stop();
                break;
            case R.id.synthesize:
                synthesize();
                break;
            case R.id.batchSpeak:
                batchSpeak();
                break;
            case R.id.nextActivity:
                break;

            default:
                break;
        }
    }

    /**
     * 打印引擎so库版本号及基本信息和model文件的基本信息
     */
    private void printEngineInfo() {
        toPrint("EngineVersioin=" + SynthesizerTool.getEngineVersion());
        toPrint("EngineInfo=" + SynthesizerTool.getEngineInfo());
        String textModelInfo = SynthesizerTool.getModelInfo(mSampleDirPath + "/" + TEXT_MODEL_NAME);
        toPrint("textModelInfo=" + textModelInfo);
        String speechModelInfo = SynthesizerTool.getModelInfo(mSampleDirPath + "/" + SPEECH_FEMALE_MODEL_NAME);
        toPrint("speechModelInfo=" + speechModelInfo);
    }


    private void speak() {
        String text = this.mInput.getText().toString();
        //需要合成的文本text的长度不能超过1024个GBK字节。
        if (TextUtils.isEmpty(mInput.getText())) {
            text = "欢迎使用百度语音合成SDK,百度语音为你提供支持。";
            mInput.setText(text);
        }
        int result = this.mSpeechSynthesizer.speak(text);
        if (result < 0) {
            toPrint("error,please look up error code in doc or URL:http://yuyin.baidu.com/docs/tts/122 ");
        }
    }

    private void pause() {
        this.mSpeechSynthesizer.pause();
    }

    private void resume() {
        this.mSpeechSynthesizer.resume();
    }

    private void stop() {
        this.mSpeechSynthesizer.stop();
    }

    private void synthesize() {
        String text = this.mInput.getText().toString();
        //需要合成的文本text的长度不能超过1024个GBK字节。
        int result = this.mSpeechSynthesizer.synthesize(text);
        if (result < 0) {
            toPrint("error,please look up error code in doc or URL:http://yuyin.baidu.com/docs/tts/122 ");
        }
    }

    private void batchSpeak() {
        List<SpeechSynthesizeBag> bags = new ArrayList<SpeechSynthesizeBag>();
        bags.add(getSpeechSynthesizeBag("123456", "0"));
        bags.add(getSpeechSynthesizeBag("你好", "1"));
        bags.add(getSpeechSynthesizeBag("使用百度语音合成SDK", "2"));
        bags.add(getSpeechSynthesizeBag("hello", "3"));
        bags.add(getSpeechSynthesizeBag("这是一个demo工程", "4"));
        int result = this.mSpeechSynthesizer.batchSpeak(bags);
        if (result < 0) {
            toPrint("error,please look up error code in doc or URL:http://yuyin.baidu.com/docs/tts/122 ");
        }
    }

    private SpeechSynthesizeBag getSpeechSynthesizeBag(String text, String utteranceId) {
        SpeechSynthesizeBag speechSynthesizeBag = new SpeechSynthesizeBag();
        //需要合成的文本text的长度不能超过1024个GBK字节。
        speechSynthesizeBag.setText(text);
        speechSynthesizeBag.setUtteranceId(utteranceId);
        return speechSynthesizeBag;
    }

    /*
     * @param arg0
     */
    @Override
    public void onSynthesizeStart(String utteranceId) {
        toPrint("onSynthesizeStart utteranceId=" + utteranceId);
    }

    /**
     * 合成数据和进度的回调接口，分多次回调
     *
     * @param utteranceId
     * @param data 合成的音频数据。该音频数据是采样率为16K，2字节精度，单声道的pcm数据。
     * @param progress 文本按字符划分的进度，比如:你好啊 进度是0-3
     * @param engineType 引擎类型，0: online engine 1：offline engine
     */
    @Override
    public void onSynthesizeDataArrived(String utteranceId, byte[] data, int progress, int engineType) {
        // toPrint("onSynthesizeDataArrived");
        mHandler.sendMessage(mHandler.obtainMessage(UI_CHANGE_SYNTHES_TEXT_SELECTION, progress, 0));
    }

    /**
     * 合成正常结束，每句合成正常结束都会回调，如果过程中出错，则回调onError，不再回调此接口
     *
     * @param utteranceId
     */
    @Override
    public void onSynthesizeFinish(String utteranceId) {
        toPrint("onSynthesizeFinish utteranceId=" + utteranceId);
    }

    /**
     * 播放开始，每句播放开始都会回调
     *
     * @param utteranceId
     */
    @Override
    public void onSpeechStart(String utteranceId) {
        toPrint("onSpeechStart utteranceId=" + utteranceId);
    }

    /**
     * 播放进度回调接口，分多次回调
     *
     * @param utteranceId
     * @param progress 文本按字符划分的进度，比如:你好啊 进度是0-3
     */
    @Override
    public void onSpeechProgressChanged(String utteranceId, int progress) {
        // toPrint("onSpeechProgressChanged");
        mHandler.sendMessage(mHandler.obtainMessage(UI_CHANGE_INPUT_TEXT_SELECTION, progress, 0));
    }

    /**
     * 播放正常结束，每句播放正常结束都会回调，如果过程中出错，则回调onError,不再回调此接口
     *
     * @param utteranceId
     */
    @Override
    public void onSpeechFinish(String utteranceId) {
        toPrint("onSpeechFinish utteranceId=" + utteranceId);
    }

    /**
     * 当合成或者播放过程中出错时回调此接口
     *
     * @param utteranceId
     * @param error 包含错误码和错误信息
     */
    @Override
    public void onError(String utteranceId, SpeechError error) {
        toPrint("onError error=" + "(" + error.code + ")" + error.description + "--utteranceId=" + utteranceId);
    }

    private Handler mHandler = new Handler() {

        /*
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case PRINT:
                    print(msg);
                    break;
                case UI_CHANGE_INPUT_TEXT_SELECTION:
                    if (msg.arg1 <= mInput.getText().length()) {
                        mInput.setSelection(0,msg.arg1);
                    }
                    break;
                case UI_CHANGE_SYNTHES_TEXT_SELECTION:
                    SpannableString colorfulText = new SpannableString(mInput.getText().toString());
                    if (msg.arg1 <= colorfulText.toString().length()) {
                        colorfulText.setSpan(new ForegroundColorSpan(Color.GRAY), 0, msg.arg1, Spannable
                                .SPAN_EXCLUSIVE_EXCLUSIVE);
                        mInput.setText(colorfulText);
                    }
                    break;
                default:
                    break;
            }
        }

    };

    private void toPrint(String str) {
        Message msg = Message.obtain();
        msg.obj = str;
        this.mHandler.sendMessage(msg);
    }

    private void print(Message msg) {
        String message = (String) msg.obj;
        if (message != null) {
            Log.w(TAG, message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            scrollLog(message);
        }
    }

    private void scrollLog(String message) {
        Spannable colorMessage = new SpannableString(message + "\n");
        colorMessage.setSpan(new ForegroundColorSpan(0xff0000ff), 0, message.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mShowText.append(colorMessage);
        Layout layout = mShowText.getLayout();
        if (layout != null) {
            int scrollAmount = layout.getLineTop(mShowText.getLineCount()) - mShowText.getHeight();
            if (scrollAmount > 0) {
                mShowText.scrollTo(0, scrollAmount + mShowText.getCompoundPaddingBottom());
            } else {
                mShowText.scrollTo(0, 0);
            }
        }
    }

}
