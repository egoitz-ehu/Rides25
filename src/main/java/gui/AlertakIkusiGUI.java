package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import domain.Alerta;
import domain.AlertaEgoera;
import domain.Traveler;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlertakIkusiGUI extends JFrame {

	private JPanel contentPane;
	List<Alerta> aList;
	
	JScrollPane panelAurkitua;
	JPanel listaAurkitua;
	JScrollPane panelZain;
	JPanel listaZain;
	
	private Traveler t;

	public AlertakIkusiGUI(Traveler tr) {
		t=tr;
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));

		panelAurkitua = new JScrollPane();
		contentPane.add(panelAurkitua);
		
		listaAurkitua = new JPanel();
		listaAurkitua.setLayout(new BoxLayout(listaAurkitua, BoxLayout.Y_AXIS));
		listaAurkitua.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelAurkitua.setViewportView(listaAurkitua);
		
		panelZain = new JScrollPane();
		contentPane.add(panelZain);
		
		listaZain = new JPanel();
		listaZain.setLayout(new BoxLayout(listaZain, BoxLayout.Y_AXIS));
		listaZain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelZain.setViewportView(listaZain);
		
		datuakLortu(t.getEmail());
	}
	
	private void datuakLortu(String email) {
		aList = WelcomeGUI.getBusinessLogic().lortuAlertak(email);
		for(int i=0;i<aList.size();i++) {
			final int index = i;
			Alerta a = aList.get(i);
			if(a.getEgoera().equals(AlertaEgoera.AURKITUA)) {
				listaAurkitua.add(sortuElementua(a.getFrom(),a.getTo(),a.getDate(),index, true));
				listaAurkitua.add(Box.createVerticalStrut(10));
			} else if(a.getEgoera().equals(AlertaEgoera.ZAIN)) {
				listaZain.add(sortuElementua(a.getFrom(),a.getTo(),a.getDate(),index, false));
				listaZain.add(Box.createVerticalStrut(10));
			}
		}
	}

	private JPanel sortuElementua(String from, String to, Date date, int index, boolean aurkitua) {
	    JPanel panel = new JPanel(new BorderLayout());
	    panel.setBackground(new Color(230, 240, 255));
	    panel.setBorder(BorderFactory.createCompoundBorder(
	        BorderFactory.createLineBorder(Color.GRAY, 1),
	        BorderFactory.createEmptyBorder(5, 10, 5, 10)
	    ));
	    panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

	    JPanel panelTextos = new JPanel();
	    panelTextos.setLayout(new BoxLayout(panelTextos, BoxLayout.Y_AXIS));
	    panelTextos.setOpaque(false);

	    JLabel lblLekuak = new JLabel(from+"-"+to);
	    lblLekuak.setFont(new Font("SansSerif", Font.BOLD, 14));

	    JLabel lblDescripcion = new JLabel(date.toString());
	    lblDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 12));

	    panelTextos.add(lblLekuak);
	    panelTextos.add(lblDescripcion);
	    
	    JPanel panelBtn = new JPanel();
	    panelBtn.setLayout(new BoxLayout(panelBtn, BoxLayout.X_AXIS));
	    panelBtn.setOpaque(false);

	    JButton boton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AlertaIkusiGUI.Delete"));
	    boton.setFont(new Font("SansSerif", Font.PLAIN, 12));

	    boton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            int id = aList.get(index).getId();
	            WelcomeGUI.getBusinessLogic().ezabatuAlerta(id);
	            
	            Component panelToRemove = boton.getParent().getParent();
	            Container parent = panelToRemove.getParent();
	            parent.remove(panelToRemove);
	            int idx = -1;
	            Component[] comps = parent.getComponents();
	            for (int i = 0; i < comps.length; i++) {
	            	if (comps[i] == panelToRemove) {
	            		idx = i;
	            		break;
	            	}
	            }
	            if (idx != -1 && idx + 1 < comps.length && comps[idx + 1] instanceof Box.Filler) {
	            	parent.remove(comps[idx + 1]);
	            }
	            parent.revalidate();
	            parent.repaint();
	        }
	    });

	    
	    JButton btnQuery = null;
	    if(aurkitua) {
	    	btnQuery = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AlertaIkusiGUI.See"));
		    btnQuery.setFont(new Font("SansSerif", Font.PLAIN, 12));
		    btnQuery.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		        	ErreserbaEskaeraGUI ee = new ErreserbaEskaeraGUI(t, from, to, date);
		        	ee.setVisible(true);
		        }
		    });
	    }
	    
	    if(btnQuery!=null) {
		    panelBtn.add(btnQuery);
	    }
	    panelBtn.add(boton);


	    panel.add(panelTextos, BorderLayout.CENTER);
	    panel.add(panelBtn, BorderLayout.EAST);

	    return panel;
	}


}
