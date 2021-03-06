package com.example.dell.paomadeng.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dell.paomadeng.R;

public class MainActivity extends AppCompatActivity {
    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private NetworkChangeReceiver networkChangeReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);//获取实例
        Button button = (Button) findViewById(R.id.but1);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
/*
                Intent intent = new Intent("com.example.dell.paomadeng.BroadCastReceiver");
                sendOrderedBroadcast(intent,null);
*/
                Intent intent = new Intent("com.example.dell.paomadeng.BroadCastReceiver");
                localBroadcastManager.sendBroadcast(intent);//发送本地广播
            }
        });
        intentFilter = new IntentFilter();//IntentFilter是意图过滤器，一般定义在清单文件manifest.xml中
        intentFilter.addAction("com.example.dell.paomadeng.BroadCastReceiver");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);//注册本地广播监听器
        /*
        * 广播接收器通过过滤器中的action、data和category来筛选广播
        * 上面添加的android.net.conn.CONNECTIVITY_CHANGE是一个action
        * 当网络状态发生变化的时候，系统发出的就是一条值为android.net.conn.CONNECTIVITY_CHANGE的action
        * 创建一个NetworkChangeReceiver的实例
        * 调用registereReceiver()方法进行注册，将NetworkChangeReceiver的实例和IntentFilter的实例传进去
        * 这样NetworkChangeReceiver就会收到所有值为android.net.conn.CONNECTIVITY_CHANGE的广播，也就实现了监听网络变化的功能
        */
        /*networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,intentFilter);
*/

    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        /*unregisterReceiver(networkChangeReceiver);*/

    }


    class LocalReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"received local broadcast",Toast.LENGTH_SHORT).show();
        }
    }
}
class NetworkChangeReceiver extends BroadcastReceiver{//广播接收器，动态注册
    @Override
    public void onReceive(Context context, Intent intent)
        /*
        重写onReceive方法，当网络状态发生变化的时候，onReceive方法就会被执行，这里重写只是简单的使用Toast显示一段文本信息
        */
    {
        ConnectivityManager connectionManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connectionManager.getActiveNetworkInfo();
        if(networkinfo != null && networkinfo.isAvailable())
        {
            Toast.makeText(context,"network is available",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context,"network is unavailable",Toast.LENGTH_SHORT).show();
        }
        /*Toast.makeText(context,"network changes",Toast.LENGTH_SHORT).show();*/
    }

}
