package business_logic;

import java.io.IOException;

import data_access.DataAccess;

public class BLFactoryLocal implements IBLFactory {

	@Override
	public BLFacade createBL() throws Exception{
		DataAccess da;
		da = new DataAccess();
		return new BLFacadeImplementation(da);
	}

}
