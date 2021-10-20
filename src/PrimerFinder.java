import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import java.awt.*;

public class PrimerFinder extends JFrame {
	private static final long serialVersionUID = -8723649827349837249L;
	private JButton findPrimers = new JButton("Search for Primers");
	private JButton blastPrimers = new JButton("Blast Selected Primer");
	private JLabel enterSequenceLabel = new JLabel("<html><u><bold><b1>Sequence</u></bold></b1></html>");
	private JLabel meltingTempLabel = new JLabel("<html><u><bold><b1>Melting Temperature Range (Tm)</u></bold></b1></html>");
	private JLabel gcLabel = new JLabel("<html><u><bold><b1>GC Content Range (Gc)</u></bold></b1></html>");
	private JLabel ampliconRangeLabel = new JLabel("<html><u><bold><b1>Amplicon Length Range (bp)</u></bold></b1></html>");
	private JLabel primerRangeLabel = new JLabel("<html><u><bold><b1>Primer Length Range (bp)</u></bold></b1></html>");
	private JLabel resultsLabel = new JLabel("Below are the results of your search:");
	private JTextArea enterSequence = new JTextArea();
	private JTextField minTm = new JTextField();
	private JTextField maxTm = new JTextField();
	private JTextField minBp = new JTextField();
	private JTextField maxBp = new JTextField();
	private JTextField minGc = new JTextField();
	private JTextField maxGc = new JTextField();
	private JTextField minPrimerBp = new JTextField();
	private JTextField maxPrimerBp = new JTextField();
	private JPanel mainFrame = new JPanel();
	private JPanel resultFrame = new JPanel();
	private JPanel inputFrame = new JPanel();
	private JPanel minmaxLabelFrame = new JPanel();
	private JPanel minmaxLabelFrame2 = new JPanel();
	private JPanel minmaxLabelFrame3 = new JPanel();
	private JPanel minmaxLabelFrame4 = new JPanel();
	private JPanel tmInputFrame = new JPanel();
	private JPanel gcInputFrame = new JPanel();
	private JPanel bpInputFrame = new JPanel();
	private JPanel primerBpInputFrame = new JPanel();
	
	
	
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
		new PrimerFinder("Primer Finder v0.01");
	}
	
	public void buildMainFrame() {
		mainFrame.setLayout(new GridLayout(2,0));
		mainFrame.add(inputFrame);
		mainFrame.add(resultFrame);
		buildInputFrame();
		buildResultsFrame();
		
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
		
		inputFrame.add(enterSequenceLabel);
		enterSequence.setSize(5,4);
		inputFrame.add(enterSequence);
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
		
	}
	public void buildResultsFrame() {
		resultFrame.setLayout(new GridLayout(6,0));
		Border blackline = BorderFactory.createLineBorder(Color.black);
		resultFrame.setBorder(blackline);
		resultFrame.add(resultsLabel);
		
		JLabel forwardPrimerLabel = new JLabel("Forward Primers");
		String[] columnForwardNames = {"Sequence",
                "Length",
                "Tm",
                "GC Content"};
		JTable forwardResultTable = new JTable(getForwardTableData(),columnForwardNames);		
		JLabel reversePrimerLabel = new JLabel("Reverse Primers");
		String[] columnReverseNames = {"Sequence",
                "Length",
                "Tm",
                "GC Content"};
		JTable reverseResultTable = new JTable(getReverseTableData(),columnReverseNames);
		resultFrame.add(forwardPrimerLabel);
		resultFrame.add(forwardResultTable);
		resultFrame.add(reversePrimerLabel);
		resultFrame.add(reverseResultTable);
		resultFrame.add(blastPrimers);
		
		
	}
	public Object[][] getForwardTableData() {
		Object[][] forwardData = {
			    {"ACGTGTCGAG", "20",
			     "69.7", "60.2"},
			    {"ATCGTTGAGGC", "20",
			     "54.4", "60.2"},
			    {"ATGCTGCTGA", "190",
			     "45.5", "60.2"},
			    {"GGTATCCA", "12",
			     "65.4", "60.2"},
			    {"GGTAACCCATTA", "7",
			     "65.5", "60.2"}
			};
		return forwardData;
	}
	public Object[][] getReverseTableData() {
		Object[][] reverseData = {
			    {"ACGTGTCGAG", "20",
			     "69.7", "60.2"},
			    {"ATCGTTGAGGC", "20",
			     "54.4", "60.2"},
			    {"ATGCTGCTGA", "190",
			     "45.5", "60.2"},
			    {"GGTATCCA", "12",
			     "65.4", "60.2"},
			    {"GGTAACCCATTA", "7",
			     "65.5", "60.2"}
			};
		return reverseData;
	}
}
