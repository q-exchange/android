package top.biduo.exchange.serivce;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import top.biduo.exchange.R;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import top.biduo.exchange.ui.chat.ChatActivity;
import top.biduo.exchange.app.MyApplication;
import top.biduo.exchange.entity.ChatEntity;
import top.biduo.exchange.entity.ChatEvent;
import top.biduo.exchange.entity.ChatTable;
import top.biduo.exchange.entity.ChatTipEvent;
import top.biduo.exchange.entity.OrderDetial;
import top.biduo.exchange.socket.GroupSocket;
import top.biduo.exchange.socket.HeartSocket;
import top.biduo.exchange.socket.ISocket;
import top.biduo.exchange.socket.SocketFactory;
import top.biduo.exchange.utils.SharedPreferenceInstance;
import top.biduo.exchange.utils.WonderfulDatabaseUtils;
import top.biduo.exchange.utils.WonderfulLogUtils;


/**
 * Created by Administrator on 2018/4/19 0019.
 */

public class MyService extends Service {

    private Gson gson = new Gson();
    private boolean hasNew = false;
    private WonderfulDatabaseUtils databaseUtils;
    private List<ChatTable> list;
    private List<ChatTable> findByOrderList;
    private GroupSocket groupSocket;
    private HeartSocket heartSocket;
    String userSignature="";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void setUserSignature(){
        userSignature=System.currentTimeMillis()+"";
        loginSocket();
    }

    private void loginSocket() {
        String body="";
        try {
            body=new JSONObject().put("username_",userSignature).put("passwd_","d2f7575c5ea7c237725037a267c560f1").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("body",body);
        heartSocket.sendRequest(
                ISocket.CMD.SOCKET_LOGIN, body.getBytes(), new ISocket.TCPCallback() {
                    @Override
                    public void dataSuccess(ISocket.CMD cmd, String response) {
                        WonderfulLogUtils.logi(cmd + "loginSocket成功回执", response);
                        mHandler.postDelayed(r, 10000);
                        startGroup();
                    }

                    @Override
                    public void dataFail(int code, ISocket.CMD cmd, String errorInfo) {
                        WonderfulLogUtils.logi(cmd + "loginSocket失败回执", errorInfo);
                    }
                });

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (groupSocket == null) {
            groupSocket = (GroupSocket) SocketFactory.produceSocket(ISocket.GROUP);
        }
        if (heartSocket == null) {
            heartSocket = (HeartSocket) SocketFactory.produceSocket(ISocket.HEART);
        }
        setUserSignature();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startGroup() {
        groupSocket.sendRequest(
                ISocket.CMD.SUBSCRIBE_GROUP_CHAT, buildGetBodyJson().toString().getBytes(), new ISocket.TCPCallback() {
                    @Override
                    public void dataSuccess(ISocket.CMD cmd, String response) {
                        WonderfulLogUtils.logi(cmd + "groupSocket订阅成功回执", response);
                        //thumbSuccess(cmd, response);
                        if (cmd == ISocket.CMD.PUSH_GROUP_CHAT) {
                            startAlarm(getApplicationContext());
                            showNotice(response);
                            storageData(response);
                            ChatEvent chatEvent = new ChatEvent();
                            chatEvent.setResonpce(response);
                            chatEvent.setType("left");
                            chatEvent.setCmd(cmd);
                            EventBus.getDefault().post(chatEvent);
                        }
                        /*if (cmd == ISocket.CMD.PUSH_CHAT) {         //测试环境
                            startAlarm(getApplicationContext());
                            storageData(response);
                            ChatEvent chatEvent = new ChatEvent();
                            chatEvent.setResonpce(response);
                            chatEvent.setType("left");
                            chatEvent.setCmd(cmd);
                            EventBus.getDefault().post(chatEvent);
                        }*/
                    }

                    @Override
                    public void dataFail(int code, ISocket.CMD cmd, String errorInfo) {
                        WonderfulLogUtils.logi(cmd + "groupSocket订阅失败回执", errorInfo);
                    }
                });
    }

    private void showNotice(String response) {
        if (!isAppOnForeground()) {
            ChatEntity chatEntity = gson.fromJson(response, ChatEntity.class);
            Context context = getApplicationContext();
            int id = Integer.valueOf(chatEntity.getUidFrom());
            Intent intent = new Intent(context, ChatActivity.class);
            OrderDetial orderDetial = new OrderDetial();
            orderDetial.setOrderSn(chatEntity.getOrderId());
            orderDetial.setMyId(chatEntity.getUidTo());
            orderDetial.setHisId(chatEntity.getUidFrom());
            orderDetial.setOtherSide(chatEntity.getNameFrom());
            intent.putExtra("orderDetial", orderDetial);

            String channel_01 = "my_channel_01";
            String name = "biduo";
            NotificationManager   notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Notification notification = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(channel_01, name, NotificationManager.IMPORTANCE_LOW);
                notificationManager.createNotificationChannel(mChannel);
                notification = new Notification.Builder(this)
                        .setChannelId(channel_01)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(chatEntity.getNameFrom() + ": " + chatEntity.getContent())
                        .setAutoCancel(true)
                        .setOngoing(false)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT))
                        .setSmallIcon(R.mipmap.ic_launcher).build();
            } else {
                  notification = new NotificationCompat.Builder(context)
                        /**设置通知左边的大图标**/
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_cm_logo))
                        /**设置通知右边的小图标**/
                        .setSmallIcon(R.mipmap.ic_cm_logo)
                        /**通知首次出现在通知栏，带上升动画效果的**/
                        //.setTicker("应用名")
                        /**设置通知的标题**/
                        .setContentTitle(getString(R.string.app_name))
                        /**设置通知的内容**/
                        .setContentText(chatEntity.getNameFrom() + ": " + chatEntity.getContent())
                        /**通知产生的时间，会在通知信息里显示**/
                        .setWhen(System.currentTimeMillis())
                        /**设置该通知优先级**/
                        .setPriority(Notification.PRIORITY_DEFAULT)
                        /**设置这个标志当用户单击面板就可以让通知将自动取消**/
                        .setAutoCancel(true)
                        /**设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)**/
                        .setOngoing(false)
                        /**向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：Notification.DEFAULT_ALL就是3种全部提醒**/
                        //.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)
                        .setContentIntent(PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT))
                        .build();
            }
            /**发起通知**/
            notificationManager.notify(id, notification);
        }
    }

    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName) && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    private JSONObject buildGetBodyJson() {
        JSONObject obj = new JSONObject();
        try {
            //obj.put("orderId", orderDetial.getOrderSn());
            //obj.put("uid", orderDetial.getMyId());
            if (MyApplication.getApp().getCurrentUser() == null) {
                obj.put("uid", 0);
            } else {
                obj.put("uid", MyApplication.getApp().getCurrentUser().getId());
            }
            WonderfulLogUtils.logi("MyService    uid  ", String.valueOf(obj.get("uid")));
            return obj;
        } catch (Exception ex) {
            return null;
        }
    }

    Handler mHandler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            //每隔30s循环执行run方法
            startHeart();
            mHandler.postDelayed(this, 30000);
        }
    };

    private void startHeart() {
        heartSocket.sendRequest(
                ISocket.CMD.HEART_BEAT,null, new ISocket.TCPCallback() {
                    @Override
                    public void dataSuccess(ISocket.CMD cmd, String response) {
                        WonderfulLogUtils.logi(cmd + "  订阅成功回执", response);
                        //thumbSuccess(cmd, response);
                    }

                    @Override
                    public void dataFail(int code, ISocket.CMD cmd, String errorInfo) {
                        WonderfulLogUtils.logi(cmd + "  订阅失败回执", errorInfo);
                    }
                });
    }

    private void storageData(String response) {

        ChatEntity chatEntity = gson.fromJson(response, ChatEntity.class);
        if (chatEntity == null) {
            return;
        }
        hasNew = true;
        SharedPreferenceInstance.getInstance().saveHasNew(hasNew);
        databaseUtils = new WonderfulDatabaseUtils();
        list = databaseUtils.findAll();
        if (list == null || list.size() == 0) {
            ChatTable table = new ChatTable();
            table.setContent(chatEntity.getContent());
            table.setFromAvatar(chatEntity.getFromAvatar());
            table.setNameFrom(chatEntity.getNameFrom());
            table.setNameTo(chatEntity.getNameTo());
            table.setUidFrom(chatEntity.getUidFrom());
            table.setUidTo(chatEntity.getUidTo());
            table.setOrderId(chatEntity.getOrderId());
            table.setRead(false);
            table.setHasNew(true);
            table.setSendTimeStr(chatEntity.getSendTimeStr());
            table.setSendTime(chatEntity.getSendTime());
            databaseUtils.saveChat(table);
            ChatTipEvent tipEvent = new ChatTipEvent();
            tipEvent.setHasNew(hasNew);
            tipEvent.setOrderId(chatEntity.getOrderId());
            EventBus.getDefault().post(tipEvent);
            return;
        }
        ChatTable table = new ChatTable();
        for (int i = 0; i < list.size(); i++) {
            if (chatEntity.getOrderId().equals(list.get(i).getOrderId())) {
                findByOrderList = databaseUtils.findByOrder(chatEntity.getOrderId());
                table = findByOrderList.get(findByOrderList.size() - 1);
                table.setRead(false);
                table.setHasNew(true);
                table.setContent(chatEntity.getContent());
                table.setSendTimeStr(chatEntity.getSendTimeStr());
                table.setSendTime(chatEntity.getSendTime());
                //WonderfulLogUtils.logi("MyService","  content  "+chatEntity.getContent());
                databaseUtils.update(table);
                ChatTipEvent tipEvent = new ChatTipEvent();
                tipEvent.setHasNew(hasNew);
                tipEvent.setOrderId(chatEntity.getOrderId());
                EventBus.getDefault().post(tipEvent);
                return;
            }
        }
        table.setContent(chatEntity.getContent());
        table.setFromAvatar(chatEntity.getFromAvatar());
        table.setNameFrom(chatEntity.getNameFrom());
        table.setNameTo(chatEntity.getNameTo());
        table.setUidFrom(chatEntity.getUidFrom());
        table.setUidTo(chatEntity.getUidTo());
        table.setOrderId(chatEntity.getOrderId());
        table.setRead(false);
        table.setHasNew(true);
        table.setSendTimeStr(chatEntity.getSendTimeStr());
        table.setSendTime(chatEntity.getSendTime());
        databaseUtils.saveChat(table);
        ChatTipEvent tipEvent = new ChatTipEvent();
        tipEvent.setHasNew(hasNew);
        tipEvent.setOrderId(chatEntity.getOrderId());
        EventBus.getDefault().post(tipEvent);
    }

    //消息提示音
    private static void startAlarm(Context context) {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (notification == null) {
            return;
        }
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();
    }

}
