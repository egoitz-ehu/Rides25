package gui;

import business_logic.BLFacade;
import business_logic.BLFactoryImplementation;
import domain.Driver;

public class AdapterNagusia {
	public static void main(String[]	args) throws Exception	{
	//		the	BL	is	local
		boolean isLocal =	true;
		BLFacade	blFacade =	new BLFactoryImplementation().createBL(true);
		Driver	d= blFacade.getDriver("1");
		DriverTable	dt=new	DriverTable(d);
		dt.setVisible(true);
	}
}
