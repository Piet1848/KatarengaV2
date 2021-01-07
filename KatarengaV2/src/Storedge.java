import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Stream;

public class Storedge {
	private Object[] aText;
	private String link = "dl"; //tbd

	public Storedge() {

	}

	public int[][][] getCalc() { //264520 einträge
		if(aText.length == 264520) {
			double[][][] calculation = new double[4][][];
			calculation[0] = new double[130][400];
			calculation[1] = new double[400][400];
			calculation[2] = new double[400][130];
			calculation[3] = new double[130][4];
			openText(link);
			char[] c;
			int i = 0;
			int j = 0;
			int k = 0;
			for(int t = 0; t < 52000; t++) {	//von 130 --> 400
				c = aText[t].toString().toCharArray();
				double value = 0.;
				boolean nachkommer = false;
				for(int x = 0; x < c.length; x++) {
					if(c[x] == '.') {
						nachkommer = true;
					}else {
						value = value*10 + c[x];
						if(nachkommer) {
							value /= 10;
						}
					}
				}
				calculation[i][j][k] = value;
				k++;
				if(k == 400) {
					k = 0;
					j++;
				}
			}
			i++;
			j = 0;
			k = 0;
			for(int t = 52000; t < 212000; t++) { //von 400 --> 400
				c = aText[t].toString().toCharArray();
				double value = 0.;
				boolean nachkommer = false;
				for(int x = 0; x < c.length; x++) {
					if(c[x] == '.') {
						nachkommer = true;
					}else {
						value = value*10 + c[x];
						if(nachkommer) {
							value /= 10;
						}
					}
				}
				calculation[i][j][k] = value;
				k++;
				if(k == 400) {
					k = 0;
					j++;
				}
			}
			i++;
			j = 0;
			k = 0;
			for(int t = 212000; t < 264000; t++) { //von 400 --> 130
				c = aText[t].toString().toCharArray();
				double value = 0.;
				boolean nachkommer = false;
				for(int x = 0; x < c.length; x++) {
					if(c[x] == '.') {
						nachkommer = true;
					}else {
						value = value*10 + c[x];
						if(nachkommer) {
							value /= 10;
						}
					}
				}
				calculation[i][j][k] = value;
				k++;
				if(k == 130) {
					k = 0;
					j++;
				}
			}
			i++;
			j = 0;
			k = 0;
			for(int t = 264000; t < 264520; t++) { //von 400 --> 130
				c = aText[t].toString().toCharArray();
				double value = 0.;
				boolean nachkommer = false;
				for(int x = 0; x < c.length; x++) {
					if(c[x] == '.') {
						nachkommer = true;
					}else {
						value = value*10 + c[x];
						if(nachkommer) {
							value /= 10;
						}
					}
				}
				calculation[i][j][k] = value;
				k++;
				if(k == 4) {
					k = 0;
					j++;
				}
			}
		}else {
			System.out.println("########!!!Falsche länge der Datei!!!############################");
		}
		return null;
	}

	public void save(int[][][] calculation) {
		String text = "";
		for(int i = 0; i < calculation.length; i++) {
			for(int j = 0; j < calculation[i].length; j++) {
				for(int k = 0; k < calculation[i][j].length; k++) {
					text += calculation[i][j][k] + "\n";				//to be tested
				}
			}
		}
	}

	private void saveText(String text) {
		try{
			FileWriter fr = new FileWriter(link);
			BufferedWriter br = new BufferedWriter(fr);
			PrintWriter out = new PrintWriter(br);
			
			out.write(text);
			out.close();
		}

		catch(IOException e){
			System.out.println(e);
		}
	}
	
	public void setZero() {
		String[] text = new String[264520];
		for(int i = 0; i < 264520; i++) {
			text[i] = "0.03";
		}
	}

	private void openText(String link) {
		FileReader fr;
		try {
			fr = new FileReader(link);
			BufferedReader br = new BufferedReader(fr);
			Stream<String> stream = br.lines();
			aText = stream.toArray();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
