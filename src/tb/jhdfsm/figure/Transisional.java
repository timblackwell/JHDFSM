package tb.jhdfsm.figure;

import CH.ifa.draw.figure.connection.LineConnection;


public interface Transisional {
	
	public boolean isValid();

	public void addConnector(LineConnection stateLineConnector);
	
	public void removeConnector(LineConnection stateLineConnector);
}
