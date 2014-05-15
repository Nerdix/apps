package playground.plantgame;

import java.io.InputStream;

import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;


public class GameActivity extends ActionBarActivity {

	private GameManager gm;

	ImageButton back;
	ImageButton forward;
	TextView name_bot;
	TextView name_ger;
	EditText input_ger;
	EditText input_bot;
	ImageSwitcher imageSwitch;
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

		forward = (ImageButton) findViewById(R.id.forward);
		back = (ImageButton) findViewById(R.id.back);
		name_bot = (TextView) findViewById(R.id.name_bot);
		name_ger = (TextView) findViewById(R.id.name_ger);
		input_bot = (EditText) findViewById(R.id.editText_bot);
		input_ger = (EditText) findViewById(R.id.Edit_ger);
		imageSwitch = (ImageSwitcher)findViewById(R.id.imageSwitcher);
		imageSwitch.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
		imageSwitch.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
		imageSwitch.setDrawingCacheEnabled(false);
		imageSwitch.setFactory(new ViewFactory() {

			   @Override
			   public View makeView() {
			      ImageView myView = new ImageView(getApplicationContext());
			      myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			      myView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.
			      MATCH_PARENT,LayoutParams.MATCH_PARENT));
			      return myView;
			       }
			   });
		
		
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
			name_bot.setText(res_bot);
			gm.setLastResult_bot(false);
		}
		if (res_ger.equals("")) {
			// german correct
			name_ger.setText("Richtig");
			gm.setLastResult_ger(true);
		} else {
			//german incorrect
			name_ger.setText(res_ger);
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
			imageSwitch.setImageDrawable(Drawable.createFromStream(is, ""));
			clearFields();
		}
	}
	private void setTextField(){
		boolean lastRes = gm.getLastResult_bot();
		
		if(gm.isAnswered() && lastRes){
			name_bot.setText("Vorher richtig");


		}else if(gm.isAnswered() && !lastRes){
			name_bot.setText("Vorher falsch");

		}else{
			name_bot.setText("");
		}
		
		lastRes = gm.getLastResult_ger();
		if(gm.isAnswered() && lastRes){
			name_ger.setText("Vorher richtig");
		}else if(gm.isAnswered() && !lastRes){
			name_ger.setText("Vorher falsch");
		}else{
			name_ger.setText("");
		}
	}
}
