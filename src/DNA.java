import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DNA {
	
	public String sequence;
	public int sequence_length;
	public String current_primer;
	
	public DNA(String sequence) {
		Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");
		Matcher m = CRLF.matcher(sequence);
		String newSeq = sequence;
		if (m.find()) {
		  newSeq = m.replaceAll("");
		}
		this.sequence = newSeq;
		this.sequence_length = newSeq.length();
	}
	
	public float[] calcGCTemp(String seq)
	{
		this.current_primer = seq;
		float[] results = new float[2];
		int len = seq.length();
		int gCount = countChars('G');
		int cCount = countChars('C');
		int aCount = countChars('A');
		int tCount = countChars('T');
		float temp = calcTemp(aCount,tCount,cCount,gCount);
		results[0] = temp;
		int gc = gCount + cCount;
		float gc_rat = (float) gc/len * 100;
		results[1] = gc_rat;
		return results;
		
	}
	
	
	public int countChars(char letter) {
		int totalChars = 0;
		for (int i = 0; i < this.current_primer.length(); i++) {

        char temp = this.current_primer.charAt(i);
        if (temp == letter)
            totalChars++;
		}
		return totalChars;
	}
	
	public List<Map<String , String>> findPrimers(int primer_min, int primer_max, int gc_min, int gc_max, int temp_min, int temp_max) {
	    List<Map<String , String>> myMap  = new ArrayList<Map<String,String>>();
		int dif = primer_max-primer_min;
		for(int i = 0; i < this.sequence_length; i++) {
			for(int k = 0; k < dif; k++) {
				if(i+primer_min+k >= this.sequence_length) {
					break;
				}
				String seq_check = this.sequence.substring(i, i+ primer_min + k);
				float[] returns = calcGCTemp(seq_check);
				float temp = returns[0];
				float gc_ratio = returns[1];
				if (gc_ratio >= (float)gc_min && gc_ratio <= (float) gc_max) {
					if(temp >= temp_min && temp <= temp_max) {
						Map<String,String> primer = new HashMap<String, String>();
						primer.put("start", Integer.toString(i));
						primer.put("end", Integer.toString(i+primer_min+k));
						primer.put("gc_ratio", Float.toString(gc_ratio));
						primer.put("temp", Float.toString(temp));
						primer.put("length", Integer.toString(primer_min+k));
						primer.put("seq", seq_check);
						myMap.add(primer);
						i = i + primer_max + k;
						k = 0;
					}
				}
			}
		}
		return myMap;
	}
	
	public float calcTemp(int A, int T, int C, int G) {
		float temp = (float)(64.9 + 41*(G+C-16.4)/(A+T+G+C));
		return temp;
	}

}
