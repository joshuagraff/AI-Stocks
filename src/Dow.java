import java.util.HashMap;


public class Dow {
	private String date;
	private HashMap<String, Float> stocks;
	private float change;
	
	public Dow(String _date, float _change){
		setDate(_date);
		setChange(_change);
		stocks = new HashMap<String, Float>();
	}
	
	public void addStock(String stockName, float changeToday){
		stocks.put(stockName, changeToday);
	}

	public float getChange() {
		return change;
	}

	public void setChange(float change) {
		this.change = change;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Dow on " + date + " change="
				+ change + ", stocks=" + stocks;
	}
	
	
}
