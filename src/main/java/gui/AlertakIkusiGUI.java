package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import domain.Alerta;
import domain.AlertaEgoera;
import domain.Traveler;

import java.util.Date;
import java.util.List;

import java.awt.*;

public class AlertakIkusiGUI extends JFrame {

	private JPanel contentPane;

	public AlertakIkusiGUI(Traveler t) {
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));

		JScrollPane panelAurkitua = new JScrollPane();
		contentPane.add(panelAurkitua);
		
		JPanel listaAurkitua = new JPanel();
		listaAurkitua.setLayout(new BoxLayout(listaAurkitua, BoxLayout.Y_AXIS));
		listaAurkitua.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelAurkitua.setViewportView(listaAurkitua);
		
		JScrollPane panelZain = new JScrollPane();
		contentPane.add(panelZain);
		
		JPanel listaZain = new JPanel();
		listaZain.setLayout(new BoxLayout(listaZain, BoxLayout.Y_AXIS));
		listaZain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelZain.setViewportView(listaZain);
		
		List<Alerta> aList = WelcomeGUI.getBusinessLogic().lortuAlertak(t.getEmail());
		for(Alerta a:aList) {
			if(a.getEgoera().equals(AlertaEgoera.AURKITUA)) {
				listaAurkitua.add(sortuElementua(a.getFrom(),a.getTo(),a.getDate()));
			} else if(a.getEgoera().equals(AlertaEgoera.ZAIN)) {
				listaZain.add(sortuElementua(a.getFrom(),a.getTo(),a.getDate()));
			}
		}
	}

	private JPanel sortuElementua(String titulo, String descripcion, Date date) {
	    JPanel panel = new JPanel(new BorderLayout());
	    panel.setBackground(new Color(230, 240, 255));
	    panel.setBorder(BorderFactory.createCompoundBorder(
	        BorderFactory.createLineBorder(Color.GRAY, 1),
	        BorderFactory.createEmptyBorder(5, 10, 5, 10)
	    ));
	    panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

	    // Panel con los dos textos
	    JPanel panelTextos = new JPanel();
	    panelTextos.setLayout(new BoxLayout(panelTextos, BoxLayout.Y_AXIS));
	    panelTextos.setOpaque(false);

	    JLabel lblTitulo = new JLabel(titulo);
	    lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 14));

	    JLabel lblDescripcion = new JLabel(descripcion);
	    lblDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 12));

	    panelTextos.add(lblTitulo);
	    panelTextos.add(lblDescripcion);

	    JButton boton = new JButton("a");
	    boton.setFont(new Font("SansSerif", Font.PLAIN, 12));

	    panel.add(panelTextos, BorderLayout.CENTER);
	    panel.add(boton, BorderLayout.EAST);

	    return panel;
	}


}
