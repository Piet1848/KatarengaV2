import java.util.ArrayList;

public class AutoPlay {
	private int[] input;
	private int[][][] calculation;  //[Layer][startKnoten][zielKnoten]
	private Storedge store;
	
	public AutoPlay() {
		store = new Storedge();
		getCalculationData();
	}
	
	public int[] zug() {
		return null;
	}

	private void getCalculationData() {
		calculation = store.getCalc();
	}
	
	private void saveCalculationData() {
		store.save(calculation);
	}
}
