package playground.plantgame;

import java.io.InputStream;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class GameActivity extends ActionBarActivity {

	private GameManager gm;

	Button back;
	Button forward;
	TextView name_bot;
	TextView name_ger;
	ImageView plant;
	EditText input_ger;
	EditText input_bot;
	int sound_false;
	int sound_pos;
	 SoundPool soundPool;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Context ctx = getApplicationContext();
		
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		sound_false = soundPool.load(this, R.raw.zonk, 1);
		sound_pos = soundPool.load(this, R.raw.applause, 1);
		gm = new GameManager(ctx);

		forward = (Button) findViewById(R.id.forward);
		back = (Button) findViewById(R.id.back);
		name_bot = (TextView) findViewById(R.id.name_bot);
		name_ger = (TextView) findViewById(R.id.name_ger);
		input_bot = (EditText) findViewById(R.id.editText_bot);
		input_ger = (EditText) findViewById(R.id.Edit_ger);
		plant = (ImageView) findViewById(R.id.plantView);
		
		Intent intent = getIntent();
		int nr = intent.getIntExtra("nr_plants", 0);
		
		QuestionObj obj = gm.startRandomGame(nr);
		checkOkj(obj);
	}

	public void ButtonOnClick(View v) {
		QuestionObj obj = null;
		switch (v.getId()) {
		case R.id.forward:
			obj = gm.getNextQuestion();
			break;
		case R.id.back:
			obj = gm.getPrefQuestion();
			break;
		case R.id.check:
			check();
			break;
		}
		checkOkj(obj);
	}

	private void check() {
		String res_bot = gm.check_bot(input_bot.getText().toString());
		String res_ger = gm.check_german(input_ger.getText().toString());
		
		if (res_bot.equals("")) {
			// botanic correct
			name_bot.setText("Richtig");
			gm.setLastResult_bot(true);
			
		} else {
			// botanic incorrect
			name_bot.setText("FALSCH! " + res_bot);
			gm.setLastResult_bot(false);
		}
		if (res_ger.equals("")) {
			// german correct
			name_ger.setText("Richtig");
			gm.setLastResult_ger(true);
		} else {
			//german incorrect
			name_ger.setText("FALSCH! " + res_ger);
			gm.setLastResult_ger(false);
		}
		//do sound
		if(res_bot.equals("") && (res_ger.equals(""))){
			soundPool.play(sound_pos, 1.0f, 1.0f, 0, 0, 1.0f);	

		}else{
			soundPool.play(sound_false, 1.0f, 1.0f, 0, 0, 1.0f);
		}
		gm.setAnswered(true);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}

	}

	private void clearFields() {
		setTextField();
		input_bot.setText("");
		input_ger.setText("");
		input_bot.setHint("botanisch");
		input_ger.setHint("deutsch");

	}
	private void checkOkj(QuestionObj obj){
		if (obj != null) {
			System.out.println(obj.getPic_link());
			InputStream is = getClass().getResourceAsStream(
					"/res/drawable-hdpi/" + obj.getPic_link());
			plant.setImageDrawable(Drawable.createFromStream(is, ""));
			
			clearFields();
		}
	}
	private void setTextField(){
		boolean lastRes = gm.getLastResult_bot();
		
		if(gm.isAnswered() && lastRes){
			name_bot.setText("letzter Versuch richtig");
		}else if(gm.isAnswered() && !lastRes){
			name_bot.setText("letzter Versuch falsch");
		}else{
			name_bot.setText("");
		}
		
		lastRes = gm.getLastResult_ger();
		if(gm.isAnswered() && lastRes){
			name_ger.setText("letzter Versuch richtig");
		}else if(gm.isAnswered() && !lastRes){
			name_ger.setText("letzter Versuch falsch");
		}else{
			name_ger.setText("");
		}
	}
}
