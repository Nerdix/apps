package playground.plantgame;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.os.Build;

public class StartActivity extends ActionBarActivity {

	Button start_btn;
	EditText nrs;
	final static int MAX_NRS = 68;
	final static int MIN_NRS = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		start_btn = (Button) findViewById(R.id.start_game);
		nrs = (EditText) findViewById(R.id.nr_to_fill);
		start_btn.setOnClickListener(new OnClickListener() {  
            public void onClick(View v) {  
                Intent itn = new Intent(v.getContext(), GameActivity.class);
                String tmp = nrs.getText().toString();
                int value = 0;
                if(!tmp.equals("")){
                	 value = Integer.parseInt(tmp);
                }
                else{
                	 value = 0;
                }
                if((value<=MAX_NRS)&&(value >=MIN_NRS)){
                	itn.putExtra("nr_plants",value);
                }else{
                	itn.putExtra("nr_plants",MAX_NRS);
                	System.out.println("incorrect number of plants, set to max value");
                }
                startActivity(itn);
            }  
        });  
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
			View rootView = inflater.inflate(R.layout.fragment_start,
					container, false);
			return rootView;
		}
	}

}
