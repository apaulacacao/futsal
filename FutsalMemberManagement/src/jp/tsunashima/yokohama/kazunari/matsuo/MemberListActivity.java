package jp.tsunashima.yokohama.kazunari.matsuo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MemberListActivity extends ActionBarActivity{

	int mMemberNum = SetMemberActivity.MEMBER_NUM_MIN;	// メンバー数
	TextView	mText;									// テキストビュー
	String		mFileName = "futsal_member.txt";		// ファイル名
	String[]	mMember;								// メンバー名
	String[]	mMemberShuffle;							// シャッフル後のメンバー名
	String		mResult;								// 画面に表示する一覧文字列

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member_list);

		LinearLayout layout = (LinearLayout)findViewById(R.id.linearlayout1);

		layout.setOrientation(LinearLayout.VERTICAL);

		int[][] resId = {
				{ R.id.ids_name_text1, R.string.str_name_text1 },
			};

		// メンバーを読み込み
		read_member();

		// メンバー生成
		Member[] Member = new Member[mMemberNum];

		for(int i = 0; i < mMemberNum; i++ ) {
			Member[i] = new Member();
		}

		// 配列をシャッフル
		shuffleArray();

		// メンバーの名前を設定
		for(int i = 0; i < mMemberNum; i++) {
			Member[i].setName( mMemberShuffle[i] );
		}

		// リスト作成
		createList(Member);

		// テキストビューを生成
		mText = new TextView(this);
		// リソースID設定
		mText.setId(resId[0][0]);
		// 文字列を設定
		mText.setText(mResult);
		// 色を設定
		mText.setTextColor(Color.WHITE);
		// サイズを設定
		mText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);

		// レイアウトにテキストを追加
		layout.addView( mText);

		// 更新ボタン作成
		Button button1 = new Button(this);
		// 名前を設定
		button1.setText("更新");
		// テキスト色を設定
		button1.setTextColor(Color.BLACK);
		// 背景色を設定
		button1.setBackgroundColor(Color.GRAY);
		// マージンを設定
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(20, 20, 20, 20);
		button1.setLayoutParams(lp);

		// ボタン押下時の処理
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				// メンバー生成
				Member[] Member = new Member[mMemberNum];

				for(int i = 0; i < mMemberNum; i++ ) {
					Member[i] = new Member();
				}

				// 配列をシャッフル
				shuffleArray();

				// 名前を設定
				for(int i = 0; i < mMemberNum; i++) {
					Member[i].setName( mMemberShuffle[i] );
				}

        		mResult = "";

				// リスト作成
				createList(Member);
				// 名前を設定
				mText.setText(mResult);
			}
		});

		// レイアウトにボタンを追加
		layout.addView(button1);

		// 戻るボタン作成
		Button button2 = new Button(this);
		// 名前を設定
		button2.setText("戻る");
		// テキスト色を設定
		button2.setTextColor(Color.BLACK);
		// 背景色を設定
		button2.setBackgroundColor(Color.GRAY);
		// マージンの設定
		lp.setMargins(20, 20, 20, 20);
		button2.setLayoutParams(lp);

		// ボタン押下時の処理
		button2.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						// メイン画面を起動
						Intent intent = new Intent(MemberListActivity.this, MainActivity.class);
						intent.setClassName("jp.tsunashima.yokohama.kazunari.matsuo",
											"jp.tsunashima.yokohama.kazunari.matsuo.MainActivity");
						startActivity(intent);

					}
				});
		// レイアウトにボタンを追加
		layout.addView(button2);
	}

	// メンバー名の配列をシャッフル
	private void shuffleArray(){
		// 配列からListへ変換
		List<String> list = Arrays.asList(mMember);

		// リストの並びをシャッフル
		Collections.shuffle(list);

		// listから配列へ戻す
		mMemberShuffle = (String[])list.toArray(new String[list.size()]);
	}

	// メンバー読み込み
	private void read_member() {
		// ファイルを読み込み
		try {
			// ファイルオープン
			InputStream in = this.openFileInput(mFileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			// 人数読み込み
			String num = reader.readLine();

			if(num.equals("6")) {
				mMemberNum = 6;
			} else if(num.equals("7")){
				mMemberNum = 7;
			} else if(num.equals("8")) {
				mMemberNum = 8;
			} else {
				// ありえない
			}

			mMember = new String[mMemberNum];

			// メンバー名を読み込み
			for(int i = 0; i < mMemberNum; i++ ) {
				mMember[i] = reader.readLine();
			}

			// ファイルクローズ
			reader.close();
			in.close();
		} catch (Exception e) {
			Toast.makeText(this, "File read error!", Toast.LENGTH_LONG).show();
		}
	}

	private class Member {
		String	mName;					// 名前
		int		mPlayCount = 0;			// プレー回数

		// 名前を設定
		public void setName(String name) {
			mName = name;
		}

		// 名前を取得
		public String getName() {
			return mName;
		}

		// プレー回数をインクリメント
		public void incPlayCount() {
			mPlayCount++;
		}

		// プレー回数を取得
		public int getPlayCount() {
			return mPlayCount;
		}
	}

	// リスト作成
	private void createList(Member[] member) {
		int x = 0;
		int y = 0;
		int game_num = 1;

		mResult = "<第1試合>\n";

		while(true){
			if(x == mMemberNum) {
				x = 0;
			}

			// 1試合分ごとにチェック
			if(y == 4) {
				y = 0;

				// 全メンバーのプレー回数をチェックし一致したら終了
				if( checkPlayCount(member) ) {
					break;
				}

				// 改行
				mResult += "\n";
				// 試合数加算
				game_num++;
				mResult += String.format("<第%d試合>\n", game_num);
			}

			// 文字列を追加
			mResult += member[x].getName();
			// 改行
			mResult += "\n";

			// プレー回数をインクリメント
			member[x].incPlayCount();

			x++;
			y++;
		}
	}

	// プレー回数チェック
	private boolean checkPlayCount(Member[] member) {
		boolean ret = true;

		for(int i = 0; i < mMemberNum; i++) {
			if( member[0].getPlayCount() != member[i].getPlayCount() ) {
				ret = false;
				break;
			}
		}

		return ret;
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
