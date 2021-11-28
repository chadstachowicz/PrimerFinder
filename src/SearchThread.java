import java.util.List;
import java.util.Map;
import java.util.Random;

public class SearchThread implements Runnable {
	private PrimerFinder ui;
	private boolean exit = false;
	private int minPrim;
	private int maxPrim;
	private int minGcFun;
	private int maxGcFun;
	private int minTmFun;
	private int maxTmFun;
	private int minBpFun;
	private int maxBpFun;
	
	public SearchThread(PrimerFinder pf, int minPrim, int maxPrim, int minGcFun, int maxGcFun, int minTmFun, int maxTmFun, int minBpFun, int maxBpFun ) {
		this.ui = pf;
		this.minPrim = minPrim;
		this.maxPrim = maxPrim;
		this.minGcFun = minGcFun;
		this.maxGcFun = maxGcFun;
		this.minTmFun = minTmFun;
		this.maxTmFun = maxTmFun;
		this.minBpFun = minBpFun;
		this.maxBpFun = maxBpFun;
	}
	private Object[][] makeNewArray(Object[][] original, Object row[]) {
		Object[][] newArray = new Object[original.length+1][5];
		for(int i = 0; i < original.length; i++) {
			newArray[i] = original[i];
		}
		newArray[original.length] = row;
		return newArray;
		
	}
	//syncronized needed to wait / notify.
	@Override
	public synchronized void run() {
		System.out.println("test");
		DNA dna = new DNA(ui.enterSequence.getText());
		List<Map<String , String>> primers = dna.findPrimers(minPrim, maxPrim, minGcFun, maxGcFun, minTmFun, maxTmFun );
		List<Map<String , String>> rev_primers = dna.findReversePrimers(primers,minBpFun,maxBpFun,minPrim, maxPrim, minGcFun, maxGcFun, minTmFun, maxTmFun);
	    int i = 0;
		for (Map<String, String> map : primers) {

	    		Object[] row = {i+1,map.get("seq"),map.get("length"),map.get("start"),map.get("temp"),map.get("gc_ratio")};
	    		ui.forwardData = makeNewArray(ui.forwardData,row);
	    		i++;
	    }
		i = 0;
	    for (Map<String, String> map2 : rev_primers) {
	    
	    		Object[] row2 = {i+1,map2.get("seq"),map2.get("length"),map2.get("start"),map2.get("temp"),map2.get("gc_ratio")};
	    		ui.reverseData = makeNewArray(ui.reverseData,row2);
	    		i++;
	    }
	    ui.buildResultsFrame();
		ui.findPrimers.setEnabled(true);

	}
	
	public void stop()
    {
        exit = true;
    }
	
	public synchronized void checkAnswer()
    {
        this.notifyAll();
    }
	
	
}
