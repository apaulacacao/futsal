package jp.tsunashima.yokohama.kazunari.matsuo;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SetMemberActivity extends ActionBarActivity {

	public static final int MEMBER_NUM_MIN = 6;		// メンバー最小数
	public static final int MEMBER_NUM_MAX = 8;		// メンバー最大数

	int				mMemberNum = MEMBER_NUM_MIN;				// メンバー数
	boolean			mFirstFlg = false;							// 初回フラグ
	String			mFileName = "futsal_member.txt";			// 保存ファイル名
	String[]		mMember = new String[MEMBER_NUM_MAX];		// メンバー名
	TextView[]		mText = new TextView[MEMBER_NUM_MAX];		// テキストビュー
	LinearLayout[]	mLayout = new LinearLayout[MEMBER_NUM_MAX];	// レイアウト
	EditText[]		mEditText = new EditText[MEMBER_NUM_MAX];	// エディットテキスト

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.set_member);

		LinearLayout layout_top = (LinearLayout)findViewById(R.id.LinearLayoutTop);

		layout_top.setOrientation(LinearLayout.VERTICAL);

		int[][] resId = {
				{ R.id.ids_set_member_text1, R.string.str_set_member_text1 },
				{ R.id.ids_set_member_text2, R.string.str_set_member_text2 },
				{ R.id.ids_set_member_text3, R.string.str_set_member_text3 },
				{ R.id.ids_set_member_text4, R.string.str_set_member_text4 },
				{ R.id.ids_set_member_text5, R.string.str_set_member_text5 },
				{ R.id.ids_set_member_text6, R.string.str_set_member_text6 },
				{ R.id.ids_set_member_text7, R.string.str_set_member_text7 },
				{ R.id.ids_set_member_text8, R.string.str_set_member_text8 },
		};

		int[] resIdEdit = {
				R.id.ids_set_member_edit1,
				R.id.ids_set_member_edit2,
				R.id.ids_set_member_edit3,
				R.id.ids_set_member_edit4,
				R.id.ids_set_member_edit5,
				R.id.ids_set_member_edit6,
				R.id.ids_set_member_edit7,
				R.id.ids_set_member_edit8,
		};

		// 画面を動的に生成
		for(int i = 0; i < MEMBER_NUM_MAX; i++)
		{
			// レイアウトを作成
			mLayout[i] = new LinearLayout(this);

			// テキストビューを生成
			mText[i] = new TextView(this);
			// リソースID設定
			mText[i].setId(resId[i][0]);
			// テキストを設定
			mText[i].setText(resId[i][1]);
			// 色を設定
			mText[i].setTextColor(Color.WHITE);
			// サイズを設定
			mText[i].setTextSize(15);

			// エディットテキストを生成
			mEditText[i] = new EditText(this);
			// リソースID設定
			mEditText[i].setId(resIdEdit[i]);
			// サイズを設定
			mEditText[i].setTextSize(15);
			// 文字色を設定
			mEditText[i].setTextColor(Color.BLACK);
			// 背景色を設定
			mEditText[i].setBackgroundColor(Color.WHITE);
			// 幅を設定
			mEditText[i].setWidth(500);
			mEditText[i].setHeight(100);

			// レイアウトにテキストを追加
			mLayout[i].addView(mText[i]);
			// レイアウトにエディットテキストを追加
			mLayout[i].addView(mEditText[i]);
			// トップレイアウトにレイアウトを追加
			layout_top.addView(mLayout[i]);

			LayoutParams lp = mLayout[i].getLayoutParams();
			MarginLayoutParams mlp = (MarginLayoutParams)lp;
			mlp.setMargins(mlp.leftMargin, 10, mlp.rightMargin, 10);
			//マージンを設定
			mLayout[i].setLayoutParams(mlp);
		}

		File file = this.getFileStreamPath(mFileName);

		// ファイルが存在する場合
		if (file.exists()) {
			// ファイル読み込み
			read_member();

			for(int i = 0; i < mMemberNum; i++) {
				mEditText[i].setText(mMember[i]);
			}
		}

		// spinner設定
		setSpinner();

		// ビューの可視性切り替え
		chgVisible();

		// ボタンを生成
		Button button1 = new Button(this);
		// リソースID設定
		button1.setId(R.id.ids_set_member_button1);
		// 名前を設定
		button1.setText("保存");
		// テキスト色を設定
		button1.setTextColor(Color.BLACK);
		// 背景色を設定
		button1.setBackgroundColor(Color.GRAY);
		// レイアウトトップにボタンを追加
		layout_top.addView(button1);

		LayoutParams lp_b1 = button1.getLayoutParams();

		MarginLayoutParams mlp_b1 = (MarginLayoutParams)lp_b1;
		mlp_b1.setMargins(mlp_b1.leftMargin, 24, mlp_b1.rightMargin, 24);
		//マージンを設定
		button1.setLayoutParams(mlp_b1);

		// ボタン押下時の処理
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {

				// 入力内容保存が成功した場合
				if(write_member()) {
					// メイン画面を起動
					Intent intent = new Intent(SetMemberActivity.this, MainActivity.class);
									intent.setClassName("jp.tsunashima.yokohama.kazunari.matsuo",
										"jp.tsunashima.yokohama.kazunari.matsuo.MainActivity");
					startActivity(intent);
				}
			}
		});

		// 戻るボタン作成
		Button button2 = new Button(this);
		// 名前を設定
		button2.setText("戻る");
		// テキスト色を設定
		button2.setTextColor(Color.BLACK);
		// 背景色を設定
		button2.setBackgroundColor(Color.GRAY);

		// ボタン押下時の処理
		button2.setOnClickListener(new OnClickListener() {
							public void onClick(View view) {
								// メイン画面を起動
								Intent intent = new Intent(SetMemberActivity.this, MainActivity.class);
										intent.setClassName("jp.tsunashima.yokohama.kazunari.matsuo",
																"jp.tsunashima.yokohama.kazunari.matsuo.MainActivity");
								startActivity(intent);

							}
						});
		// レイアウトにボタンを追加
		layout_top.addView(button2);
	}

	// 入力内容保存
	private boolean write_member() {
		boolean ret = true;

		// エディットテキストの内容を取得
		for(int i = 0; i < mMemberNum; i++ ) {
			mMember[i] = mEditText[i].getText().toString();
		}

		// 入力されていないエディットテキストがある場合
		if(!chkInputEdit()) {
			// アラートダイアログ生成
			AlertDialog.Builder alertDialog=new AlertDialog.Builder(SetMemberActivity.this);
			alertDialog.setMessage("全てのメンバーを入力してください");  //内容(メッセージ)設定
			// OK(肯定的な)ボタンの設定
			alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {

				}
			});
			// アラートダイアログ表示
			alertDialog.show();

			ret = false;

			return ret;
		}

		// 保存
		OutputStream out = null;
		PrintWriter writer = null;
		try{
			out = this.openFileOutput(mFileName, Context.MODE_PRIVATE);
			writer = new PrintWriter(new OutputStreamWriter(out,"UTF-8"));

			// 人数書き込み
			writer.println(mMemberNum);

			// メンバー名書き込み
			for(int i = 0; i < mMemberNum; i++) {
				writer.println(mMember[i]);
			}

			writer.close();
			out.close();
		}catch(Exception e){
			Toast.makeText(this, "File save error!", Toast.LENGTH_LONG).show();
		}

		return ret;
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

	// エディットテキストの入力チェック
	private boolean chkInputEdit() {
		boolean ret = true;
		for(int i = 0; i < mMemberNum; i++) {
			if(mEditText[i].getText().toString().equals("")) {
				ret = false;
				break;
			}
		}

		return ret;
	}

	private void setSpinner() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// アイテムを追加
		adapter.add("入力人数：6人");
		adapter.add("入力人数：7人");
		adapter.add("入力人数：8人");

		Spinner spinner = (Spinner) findViewById(R.id.spinner);

		// アダプターを設定
		spinner.setAdapter(adapter);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
				Spinner spinner = (Spinner) parent;
				// 選択されたアイテムを取得
				String item = (String) spinner.getSelectedItem();

				// 初回は行わない
				if(mFirstFlg != false) {
					mMemberNum = getInputNum(item);
				} else {
					spinner.setSelection(mMemberNum - 6);
				}

				mFirstFlg = true;

				// ビューの可視性切り替え
				chgVisible();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	// ビューの可視性切り替え
	private void chgVisible() {
		if(mMemberNum == 6) {
			// ビューを非表示
			mText[6].setVisibility(View.GONE);
			mEditText[6].setVisibility(View.GONE);
			mText[7].setVisibility(View.GONE);
			mEditText[7].setVisibility(View.GONE);
		} else if(mMemberNum == 7) {
			// ビューを表示
			mText[6].setVisibility(View.VISIBLE);
			mEditText[6].setVisibility(View.VISIBLE);
			// ビューを非表示
			mText[7].setVisibility(View.GONE);
			mEditText[7].setVisibility(View.GONE);
		} else if(mMemberNum == 8) {
			// ビューを表示
			mText[6].setVisibility(View.VISIBLE);
			mEditText[6].setVisibility(View.VISIBLE);
			mText[7].setVisibility(View.VISIBLE);
			mEditText[7].setVisibility(View.VISIBLE);
		} else {
			// ありえない
		}
	}

	private int getInputNum(String item) {
		int ret = 6;

		if(item.equals("入力人数：6人")) {
			ret = 6;
		} else if(item.equals("入力人数：7人")) {
			ret = 7;
		} else if(item.equals("入力人数：8人")) {
			ret = 8;
		} else {
			// ありえない
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