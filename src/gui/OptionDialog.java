package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class OptionDialog extends JDialog {

	JSpinner srcAirpSpinner, dstAirpSpinner, airpGeneSpinner, arcSpinner;
	
	JPanel srcAirpColorPanel, dstAirpColorPanel, airpGeneColorPanel;
	JPanel srcColorChooserPanel, dstColorChooserPanel, geneColorChooserPanel, arcColorChooserPanel;
	
	private MouseAdapter colorListener = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			JPanel p = (JPanel) e.getSource();
			Color c = p.getBackground();
			c = JColorChooser.showDialog(null, "Select a color", c);
			if(p.equals(srcColorChooserPanel)) srcColorChooserPanel.setBackground(c);
			else if(p.equals(dstColorChooserPanel)) dstColorChooserPanel.setBackground(c);
			else if(p.equals(geneColorChooserPanel)) geneColorChooserPanel.setBackground(c);
			else if(p.equals(arcColorChooserPanel)) arcColorChooserPanel.setBackground(c);
		}
	};
	
	public OptionDialog(JFrame parent, boolean modal) {
		super(parent, modal);
		this.setTitle("Graphical Options");
		this.setSize(700,200);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JPanel airportsPanel = new JPanel();
		airportsPanel.setLayout(new BoxLayout(airportsPanel, BoxLayout.X_AXIS));
		
		
		/* Création du panel des src */
		/* ***************************************** */
		JPanel srcAirpPanel =  new JPanel();
		srcAirpPanel.setLayout(new BoxLayout(srcAirpPanel, BoxLayout.Y_AXIS));
		srcColorChooserPanel = createColorSample(Color.YELLOW);
		
		srcAirpColorPanel = new JPanel();
		srcAirpColorPanel.setLayout(new BoxLayout(srcAirpColorPanel, BoxLayout.X_AXIS));
		srcAirpColorPanel.add(srcColorChooserPanel);
		srcAirpColorPanel.add(Box.createHorizontalStrut(10));
		srcAirpColorPanel.add(new JLabel("Departing airports color"));
		
		srcAirpPanel.add(srcAirpColorPanel);
		srcAirpPanel.add(Box.createVerticalGlue());
		
		srcAirpSpinner = new JSpinner(new SpinnerNumberModel(2, 1, 10, 0.5));
		JPanel srcAirpSizePanel = new JPanel();
		srcAirpSizePanel.setLayout(new BoxLayout(srcAirpSizePanel, BoxLayout.X_AXIS));

		srcAirpSizePanel.add(srcAirpSpinner);
		srcAirpSizePanel.add(Box.createVerticalGlue());
		srcAirpSizePanel.add(new JLabel("Change departing airports size"));
		
		srcAirpPanel.add(srcAirpSizePanel);
		
		srcAirpPanel.setBorder(BorderFactory.createTitledBorder("Departing airports options"));
		airportsPanel.add(srcAirpPanel);
		

		/* Création du panel des dst */
		/* ***************************************** */
		JPanel dstAirpPanel =  new JPanel();
		dstAirpPanel.setLayout(new BoxLayout(dstAirpPanel, BoxLayout.Y_AXIS));
		dstColorChooserPanel = createColorSample(Color.YELLOW);
		
		dstAirpColorPanel = new JPanel();
		dstAirpColorPanel.setLayout(new BoxLayout(dstAirpColorPanel, BoxLayout.X_AXIS));
		dstAirpColorPanel.add(dstColorChooserPanel);
		dstAirpColorPanel.add(Box.createHorizontalStrut(10));
		dstAirpColorPanel.add(new JLabel("Arriving airports color"));
		
		dstAirpPanel.add(dstAirpColorPanel);
		dstAirpPanel.add(Box.createVerticalGlue());
		
		dstAirpSpinner = new JSpinner(new SpinnerNumberModel(2, 1, 10, 0.5));
		JPanel dstAirpSizePanel = new JPanel();
		dstAirpSizePanel.setLayout(new BoxLayout(dstAirpSizePanel, BoxLayout.X_AXIS));

		dstAirpSizePanel.add(dstAirpSpinner);
		dstAirpSizePanel.add(Box.createHorizontalStrut(5));
		dstAirpSizePanel.add(new JLabel("Change arriving airports size"));
		
		dstAirpPanel.add(dstAirpSizePanel);
		
		dstAirpPanel.setBorder(BorderFactory.createTitledBorder("Arriving airports options"));
		airportsPanel.add(dstAirpPanel);

		
		/* Création du panel des générales */
		/* ***************************************** */
		JPanel airpGenePanel =  new JPanel();
		airpGenePanel.setLayout(new BoxLayout(airpGenePanel, BoxLayout.Y_AXIS));
		geneColorChooserPanel = createColorSample(Color.GREEN);
		
		airpGeneColorPanel = new JPanel();
		airpGeneColorPanel.setLayout(new BoxLayout(airpGeneColorPanel, BoxLayout.X_AXIS));
		airpGeneColorPanel.add(geneColorChooserPanel);
		airpGeneColorPanel.add(Box.createHorizontalStrut(10));
		airpGeneColorPanel.add(new JLabel("Departing airports color"));
		
		airpGenePanel.add(airpGeneColorPanel);
		airpGenePanel.add(Box.createVerticalGlue());
		
		airpGeneSpinner = new JSpinner(new SpinnerNumberModel(2, 1, 10, 0.5));
		JPanel airpGeneSizePanel = new JPanel();
		airpGeneSizePanel.setLayout(new BoxLayout(airpGeneSizePanel, BoxLayout.X_AXIS));

		airpGeneSizePanel.add(airpGeneSpinner);
		airpGeneSizePanel.add(Box.createHorizontalStrut(5));
		airpGeneSizePanel.add(new JLabel("Change departing airports size"));
		
		airpGenePanel.add(airpGeneSizePanel);
		
		airpGenePanel.setBorder(BorderFactory.createTitledBorder("Departing airports options"));
		airportsPanel.add(airpGenePanel);	
		
		/* Création du panel des arcs des routes */
		/* ***************************************** */
		
		JPanel arcOptionPanel = new JPanel();
		arcOptionPanel.setLayout(new BoxLayout(arcOptionPanel, BoxLayout.Y_AXIS));
		arcColorChooserPanel = createColorSample(Color.RED);
		
		JPanel arcColorPanel = new JPanel();
		arcColorPanel.setLayout(new BoxLayout(arcColorPanel, BoxLayout.X_AXIS));
		arcColorPanel.add(arcColorChooserPanel);
		arcColorPanel.add(Box.createHorizontalStrut(10));
		arcColorPanel.add(new JLabel("Flights color"));
		
		arcOptionPanel.add(arcColorPanel);
		
		
		arcSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 0.5));
		
		JPanel arcSizePanel = new JPanel();
		arcSizePanel.setLayout(new BoxLayout(arcSizePanel, BoxLayout.X_AXIS));
//		arcSizePanel.add(Box.createHorizontalStrut(50));
		arcSizePanel.add(arcSpinner);
		arcSizePanel.add(Box.createHorizontalStrut(5));
		arcSizePanel.add(new JLabel("Change Flights size"));
		
		arcOptionPanel.add(arcSizePanel);
		
		arcOptionPanel.setBorder(BorderFactory.createTitledBorder("Flights Options"));
		
		JPanel buff = new JPanel();
		buff.setLayout(new BoxLayout(buff, BoxLayout.X_AXIS));
		buff.add(Box.createGlue());
		buff.add(arcOptionPanel);
		buff.add(Box.createGlue());
		
		/* Ajout des panels dans le panel principal*/
		/* ***************************************** */
		mainPanel.add(airportsPanel);
		mainPanel.add(Box.createGlue());
		mainPanel.add(new JSeparator());
		mainPanel.add(Box.createGlue());
		mainPanel.add(buff);
		mainPanel.setOpaque(true);
		this.setContentPane(mainPanel);
		this.setVisible(true);
		pack();
		
	}

	
	private JPanel createColorSample(Color c) {
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		p.setOpaque(true);
		p.setBackground(c);
		p.setMaximumSize(new Dimension(500, 150));
		p.addMouseListener(colorListener);
		return p;
	}
}
