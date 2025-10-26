package gui;

import java.awt.Color;
import java.net.URL;
import java.util.Locale;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import business_logic.BLFacade;
import business_logic.BLFacadeImplementation;
import business_logic.BLFactoryLocal;
import business_logic.BLFactoryServer;
import business_logic.IBLFactory;
import configuration.ConfigXML;
import data_access.DataAccess;
import domain.Driver;

public class ApplicationLauncher { 
	
	
	
	public static void main(String[] args) {

		ConfigXML c=ConfigXML.getInstance();
		
		Locale.setDefault(new Locale(c.getLocale()));

		
		WelcomeGUI a=new WelcomeGUI();
		


		try {
			
			BLFacade appFacadeInterface;
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			IBLFactory factory;
			if (c.isBusinessLogicLocal()) {
				factory = new BLFactoryLocal();
			}
			
			else { //If remote
				factory = new BLFactoryServer();
				 
			} 
			
			appFacadeInterface = factory.createBL();
			WelcomeGUI.setBusinessLogic(appFacadeInterface);
			
			a.setVisible(true);
		

			
		}catch (Exception e) {	
			System.out.println("Error in ApplicationLauncher: "+e.toString());
		}


	}

}
