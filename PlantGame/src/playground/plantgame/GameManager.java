package playground.plantgame;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.content.Context;

public class GameManager {

	Context ctx;
	ArrayList<QuestionObj> questions;
	ArrayList<String[]> data;
	int score;
	int index;
	int last_index;
	int size;
	Random r;

	public GameManager(Context ctx) {
		score = 0;
		this.ctx = ctx;
		data = initData();
		r = new Random();
	}

	public QuestionObj startContinuosGame(int nrofQ) {
		questions = new ArrayList<QuestionObj>(nrofQ);
		size = nrofQ;
		for (String[] s : data) {
			QuestionObj tmp = new QuestionObj(s[0], s[1]);
			questions.add(tmp);
		}
		index = 0;
		return questions.get(0);
	}

	public QuestionObj startRandomGame(int nrofQ) {
		questions = new ArrayList<QuestionObj>(nrofQ);
		size = nrofQ;
		int count = 0;
		for (String[] s : data) {
			if (count == nrofQ) {
				break;
			} else {
				QuestionObj tmp = new QuestionObj(s[0], s[1]);
				questions.add(tmp);
				count++;
			}
		}
		Collections.shuffle(questions);
		index = 0;
		return questions.get(0);
	}

	public QuestionObj getCurrentQuestion() {
		return questions.get(index);
	}

	public QuestionObj getNextQuestion() {

		if (index < size - 1) {
			index++;
		} else {
			index = 0;
			Collections.shuffle(questions);
		}
		System.out.println("" + index);
		return questions.get(index);
	}

	public QuestionObj getPrefQuestion() {
		if (index == 0) {
			index = size - 1;
		} else {
			index--;
		}

		return questions.get(index);
	}

	public String check_german(String user_try) {
		if (user_try.equals(questions.get(index).getName_german())) {
			return "";
		} else {
			return questions.get(index).getName_german();
		}
	}

	public String check_bot(String user_try) {
		if (user_try.equals(questions.get(index).getName_bot())) {
			return "";
		} else {
			return questions.get(index).getName_bot();
		}
	}

	public ArrayList<String[]> initData() {
		ArrayList<String[]> myList = null;
		try {

			InputStream inputStream = ctx.getResources().openRawResource(
					R.raw.baum_file);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			int i;
			i = inputStream.read();
			while (i != -1) {
				byteArrayOutputStream.write(i);
				i = inputStream.read();
			}
			inputStream.close();
			String tmp = byteArrayOutputStream.toString();
			String data[] = tmp.replace("\n", "").replace("\r", "").split(";");
			myList = new ArrayList<String[]>(data.length);
			for (String s : data) {
				myList.add(s.split(":"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myList;
	}

	public void setAnswered(boolean value) {
		questions.get(index).setAnswered(value);
	}

	public boolean isAnswered() {
		return questions.get(index).isAnswered();
	}

	public void setLastResult_ger(boolean value) {
		questions.get(index).setLast_result_ger(value);
	}

	public void setLastResult_bot(boolean value) {
		questions.get(index).setLast_result_bot(value);
	}

	public boolean getLastResult_bot() {
		return questions.get(index).isLast_result_bot();
	}

	public boolean getLastResult_ger() {
		return questions.get(index).isLast_result_ger();
	}
}
