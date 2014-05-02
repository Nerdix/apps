package playground.plantgame;

public class QuestionObj {

	private String name_german;
	private String name_bot;
	private String pic_link;
	private boolean last_result_ger;
	private boolean last_result_bot;
	private boolean answered;

	public QuestionObj(String name_german, String name_bot) {
		this.name_bot = name_bot;
		this.name_german = name_german;
		this.pic_link = name_bot.replace(" ", "_").toLowerCase() + ".JPG";
	}

	public QuestionObj(String name_german, String name_bot, String pic) {
		this.name_bot = name_bot;
		this.name_german = name_german;
		this.pic_link = name_bot + ".jpg";
	}

	public String getName_bot() {
		return name_bot;
	}

	public String getName_german() {
		return name_german;
	}

	public String getPic_link() {
		return pic_link;
	}

	public boolean isAnswered() {
		return answered;
	}

	public void setAnswered(boolean answered) {
		this.answered = answered;
	}

	public boolean isLast_result_ger() {
		return last_result_ger;
	}

	public boolean isLast_result_bot() {
		return last_result_bot;
	}

	public void setLast_result_ger(boolean last_result_ger) {
		this.last_result_ger = last_result_ger;
	}

	public void setLast_result_bot(boolean last_result_bot) {
		this.last_result_bot = last_result_bot;
	}

}
