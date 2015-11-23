package jp.tsunashima.yokohama.kazunari.matsuo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button button1 = (Button)findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// メンバー設定画面を起動
				Intent intent = new Intent(MainActivity.this, SetMemberActivity.class);
				intent.setClassName("jp.tsunashima.yokohama.kazunari.matsuo",
									"jp.tsunashima.yokohama.kazunari.matsuo.SetMemberActivity");
				startActivity(intent);
			}
		});

		Button button2 = (Button)findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 一覧作成画面を起動
				Intent intent = new Intent(MainActivity.this, MemberListActivity.class);
				intent.setClassName("jp.tsunashima.yokohama.kazunari.matsuo",
									"jp.tsunashima.yokohama.kazunari.matsuo.MemberListActivity");
				startActivity(intent);
			}
		});

		Button button3 = (Button)findViewById(R.id.button3);
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// アプリ終了
				MainActivity.this.moveTaskToBack(true);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
