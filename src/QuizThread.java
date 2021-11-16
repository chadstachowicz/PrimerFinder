import java.util.Random;

public class QuizThread implements Runnable {
	private PrimerFinder ui;
	private boolean exit = false;
	
	public QuizThread(PrimerFinder pf) {
		this.ui = pf;
	}
	//syncronized needed to wait / notify.
	@Override
	public synchronized void run() {
	    
	    //do a while loop checking time and for a wrong answer
		while (!exit) {
				
		}

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
