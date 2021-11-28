import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Map;


public class PrimerFinder extends JFrame {
	private static final long serialVersionUID = -8723649827349837249L;
	public JButton findPrimers = new JButton("Search for Primers");
	private JLabel enterSequenceLabel = new JLabel("<html><u><bold><b1>Sequence</u></bold></b1></html>");
	private JLabel meltingTempLabel = new JLabel("<html><u><bold><b1>Melting Temperature Range (Tm)</u></bold></b1></html>");
	private JLabel gcLabel = new JLabel("<html><u><bold><b1>GC Content Range (Gc)</u></bold></b1></html>");
	private JLabel ampliconRangeLabel = new JLabel("<html><u><bold><b1>Amplicon Length Range (bp)</u></bold></b1></html>");
	private JLabel primerRangeLabel = new JLabel("<html><u><bold><b1>Primer Length Range (bp)</u></bold></b1></html>");
	private JLabel resultsLabel = new JLabel("Below are your search results. First row of forward matches the first row of reverse, etc..");
	public JTextArea enterSequence = new JTextArea();
	private JTextField minTm = new JTextField("40");
	private JTextField maxTm = new JTextField("60");
	private JTextField minBp = new JTextField("50");
	private JTextField maxBp = new JTextField("2000");
	private JTextField minGc = new JTextField("50");
	private JTextField maxGc = new JTextField("60");
	private JTextField minPrimerBp = new JTextField("22");
	private JTextField maxPrimerBp = new JTextField("28");
	private JPanel mainFrame = new JPanel();
	private JPanel resultFrame = new JPanel();
	private JPanel inputFrame = new JPanel();
	private JPanel sequenceFrame = new JPanel();
	private JPanel minmaxLabelFrame = new JPanel();
	private JPanel minmaxLabelFrame2 = new JPanel();
	private JPanel minmaxLabelFrame3 = new JPanel();
	private JPanel minmaxLabelFrame4 = new JPanel();
	private JPanel tmInputFrame = new JPanel();
	private JPanel gcInputFrame = new JPanel();
	private JPanel bpInputFrame = new JPanel();
	private JPanel primerBpInputFrame = new JPanel();
	private JPanel currentError;
	private List<Map<String , String>> primers;
	private List<Map<String , String>> rev_primers;
	//max of 5 primers
	public Object[][] forwardData = {};
	public Object[][] reverseData = {};
	private static PrimerFinder UIPass;

	
	
	public PrimerFinder(String title) {
		super(title);
		setSize(600,1000);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(mainFrame, BorderLayout.CENTER);
		buildMainFrame();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

	
	public static void main(String[] args) {
		PrimerFinder pf = new PrimerFinder("Primer Finder v0.01");
		UIPass = pf;
	}
	
	public void buildMainFrame() {
		mainFrame.setLayout(new GridLayout(3,0));
		sequenceFrame.setLayout(new GridLayout(2,0));
		sequenceFrame.add(enterSequenceLabel);

		JScrollPane scroll = new JScrollPane (enterSequence, 
				   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        sequenceFrame.add(scroll);
        mainFrame.add(sequenceFrame);
		mainFrame.add(inputFrame);
		buildInputFrame();
	}
	
	public void doSearch() {
		findPrimers.setEnabled(false);
		if(this.currentError != null) {
			mainFrame.remove(this.currentError);
			revalidate();
			repaint();
			this.currentError = null;
		}
		if(this.resultFrame != null) {
			mainFrame.remove(this.resultFrame);
			revalidate();
			repaint();
			this.resultFrame = new JPanel();
			this.forwardData = new Object[0][0];
			this.reverseData = new Object[0][0];
		}
		//DNA dna = new DNA(enterSequence.getText());
		int minPrim;
		int maxPrim;
		int minTmFun;
		int maxTmFun;
		int minGcFun;
		int maxGcFun;
		int minBpFun;
		int maxBpFun;
		if (minPrimerBp.getText().length() > 0 && maxPrimerBp.getText().length() > 0) {
			minPrim = Integer.parseInt(minPrimerBp.getText());
			maxPrim = Integer.parseInt(maxPrimerBp.getText());
		} else {
			this.currentError = buildErrorResultsFrame("Check your primer lengths");
			mainFrame.add(currentError);
			findPrimers.setEnabled(true);
			revalidate();
			repaint();
			return;
		}
		
		if (minTm.getText().length() > 0 && maxTm.getText().length() > 0) {
			minTmFun = Integer.parseInt(minTm.getText());
			maxTmFun = Integer.parseInt(maxTm.getText());
		} else {
			this.currentError = buildErrorResultsFrame("Check your Tm lengths");
			mainFrame.add(currentError);
			findPrimers.setEnabled(true);
			revalidate();
			repaint();
			return;
		}
		
		if (minGc.getText().length() > 0 && maxGc.getText().length() > 0) {
			minGcFun = Integer.parseInt(minGc.getText());
			maxGcFun = Integer.parseInt(maxGc.getText());
		} else {
			this.currentError = buildErrorResultsFrame("Check your Gc lengths");
			mainFrame.add(currentError);
			findPrimers.setEnabled(true);
			revalidate();
			repaint();
			return;
		}
		if (minBp.getText().length() > 0 && maxBp.getText().length() > 0) {
			minBpFun = Integer.parseInt(minBp.getText());
			maxBpFun = Integer.parseInt(maxBp.getText());
		} else {
			this.currentError = buildErrorResultsFrame("Check your Bp lengths");
			mainFrame.add(currentError);
			findPrimers.setEnabled(true);
			revalidate();
			repaint();
			return;
		}
		
		if (enterSequence.getText().length() < 50) {
			this.currentError = buildErrorResultsFrame("You must enter a sequence of at least 50bp");
			mainFrame.add(currentError);
			findPrimers.setEnabled(true);
			revalidate();
			repaint();
			return;
		}
		System.out.println("sdfsdfsdf");
		SearchThread searchThread = new SearchThread(UIPass,minPrim, maxPrim, minGcFun, maxGcFun, minTmFun, maxTmFun, minBpFun, maxBpFun);
		new Thread(searchThread).start();
	}
	
	public void buildInputFrame() {
		inputFrame.setLayout(new GridLayout(16,0));
		minmaxLabelFrame.setLayout(new GridLayout(0,3));
		minmaxLabelFrame.add(new JLabel("Min."));
		minmaxLabelFrame.add(new JLabel("Max."));
		minmaxLabelFrame2.setLayout(new GridLayout(0,3));
		minmaxLabelFrame2.add(new JLabel("Min."));
		minmaxLabelFrame2.add(new JLabel("Max."));
		minmaxLabelFrame3.setLayout(new GridLayout(0,3));
		minmaxLabelFrame3.add(new JLabel("Min."));
		minmaxLabelFrame3.add(new JLabel("Max."));
		minmaxLabelFrame4.setLayout(new GridLayout(0,3));
		minmaxLabelFrame4.add(new JLabel("Min."));
		minmaxLabelFrame4.add(new JLabel("Max."));
		tmInputFrame.setLayout(new GridLayout(0,3));
		tmInputFrame.add(minTm);
		tmInputFrame.add(maxTm);
		gcInputFrame.setLayout(new GridLayout(0,3));
		gcInputFrame.add(minGc);
		gcInputFrame.add(maxGc);
		bpInputFrame.setLayout(new GridLayout(0,3));
		bpInputFrame.add(minBp);
		bpInputFrame.add(maxBp);
		primerBpInputFrame.setLayout(new GridLayout(0,3));
		primerBpInputFrame.add(minPrimerBp);
		primerBpInputFrame.add(maxPrimerBp);
		

		
		inputFrame.add(meltingTempLabel);
		inputFrame.add(minmaxLabelFrame);
		inputFrame.add(tmInputFrame);
		inputFrame.add(gcLabel);
		inputFrame.add(minmaxLabelFrame2);
		inputFrame.add(gcInputFrame);
		inputFrame.add(ampliconRangeLabel);
		inputFrame.add(minmaxLabelFrame3);
		inputFrame.add(bpInputFrame);
		inputFrame.add(primerRangeLabel);
		inputFrame.add(minmaxLabelFrame4);
		inputFrame.add(primerBpInputFrame);
		inputFrame.add(findPrimers);
		findPrimers.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				{
					doSearch();
			
				}
			}
		});
		
	}
	public JPanel buildErrorResultsFrame(String error) {
		JPanel errorResultFrame = new JPanel();
		errorResultFrame.setLayout(new GridLayout(6,0));
		Border blackline = BorderFactory.createLineBorder(Color.black);
		errorResultFrame.setBorder(blackline);
		errorResultFrame.add(resultsLabel);
		
		JLabel errorMainLabel = new JLabel("There were errors");
		JLabel errorLabel = new JLabel(error);
		errorResultFrame.add(errorMainLabel);
		errorResultFrame.add(errorLabel);
		return errorResultFrame;
	}
	public void buildResultsFrame() {
		resultFrame.setLayout(new GridLayout(6,0));
		Border blackline = BorderFactory.createLineBorder(Color.black);
		resultFrame.setBorder(blackline);
		resultFrame.add(resultsLabel);
		
		JLabel forwardPrimerLabel = new JLabel("Forward Primers");
		String[] columnForwardNames = {"Index","Sequence",
                "Length",
                "Start Position",
                "Tm",
                "GC Content"};
		JScrollPane scrollPane1 = new JScrollPane();
		JTable forwardResultTable = new JTable(forwardData,columnForwardNames);	
		scrollPane1.setViewportView(forwardResultTable);
		JLabel reversePrimerLabel = new JLabel("Reverse Primers");
		String[] columnReverseNames = {"Index","Sequence",
                "Length",
                "Start Position",
                "Tm",
                "GC Content"};
		JScrollPane scrollPane2 = new JScrollPane();
		JTable reverseResultTable = new JTable(reverseData,columnReverseNames);
		scrollPane2.setViewportView(reverseResultTable);
		resultFrame.add(forwardPrimerLabel);
		resultFrame.add(scrollPane1);
		resultFrame.add(reversePrimerLabel);
		resultFrame.add(scrollPane2);
	//	resultFrame.add(blastPrimers);
		mainFrame.add(resultFrame);
		revalidate();
		repaint();
		
	}
}
