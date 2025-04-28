package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JEditorPane;

public class CreateReview extends JFrame {

	private JPanel contentPane;
	
	private JLabel[] stars = new JLabel[5];
    private int rating = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateReview frame = new CreateReview();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CreateReview() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		GridBagConstraints gbcStar = new GridBagConstraints();
		gbcStar.gridy = 0; // todas en la misma fila
		gbcStar.gridy=0;
		gbcStar.insets = new Insets(3, 3, 3, 3); // margen entre estrellas
		
		for (int i = 0; i < stars.length; i++) {
            final int starIndex = i;
            stars[i] = new JLabel("☆"); // Estrella vacía
            stars[i].setFont(new Font("SansSerif", Font.PLAIN, 50));
            stars[i].setForeground(Color.ORANGE);
            
            // Añadir eventos
            stars[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    setRating(starIndex + 1);
                }
                
                @Override
                public void mouseEntered(MouseEvent e) {
                    highlightStars(starIndex + 1);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    highlightStars(rating);
                }
            });

            gbcStar.gridx=i;
            contentPane.add(stars[i],gbcStar);
        }
	}
	
	private void setRating(int rating) {
        this.rating = rating;
        highlightStars(rating);
        System.out.println("Rating seleccionado: " + rating);
    }
	
	 private void highlightStars(int numStars) {
	        for (int i = 0; i < stars.length; i++) {
	            if (i < numStars) {
	                stars[i].setText("★"); // Estrella llena
	            } else {
	                stars[i].setText("☆"); // Estrella vacía
	            }
	        }
	    }

}
