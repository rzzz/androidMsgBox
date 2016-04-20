package com.example.msgbox.func;

import com.example.msgbox.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class ShowDescriptionActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_description);
        String content = null;
        
        Intent intent = getIntent();
        if(intent!=null){
        	Bundle bundle = intent.getBundleExtra("android.intent.extra.rssItem");
        	if(bundle==null){
        		content = "不好意思程序出错啦";
        	}else{
        		content = bundle.getString("title") + "\n\n"
						+ bundle.getString("pubdate") + "\n\n"
						+ bundle.getString("description") + "\n\n"
						+ "--详细信息请访问以下网址:\n" + bundle.getString("link");
        	}
        }else{
        	content = "不好意思程序出错啦";
        }
        
        TextView contentText = (TextView) this.findViewById(R.id.descontent);
        contentText.setText(content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }

}
