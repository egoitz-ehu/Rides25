package business_logic;

public interface IBLFactory {
	public BLFacade createBL(boolean isLocal) throws Exception;
}
