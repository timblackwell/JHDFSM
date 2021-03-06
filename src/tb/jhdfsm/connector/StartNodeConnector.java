package tb.jhdfsm.connector;

import tb.jhdfsm.figure.ProcessInput;
import tb.jhdfsm.figure.StateFigure;
import tb.jhdfsm.figure.StartNode;
import CH.ifa.draw.figure.ArrowTip;
import CH.ifa.draw.figure.connection.LineConnection;
import CH.ifa.draw.framework.Figure;

public class StartNodeConnector extends LineConnection {
	
	private boolean connected = false;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7684104213783845083L;
	
	public StartNodeConnector () {
		super();
		setEndDecoration(new ArrowTip());
		setStartDecoration(null);
	}
	
	/**
	 * Tests whether two figures can be connected.
	 */
	@Override
	public boolean canConnect(Figure start, Figure end) {
		return start.canConnect() && start instanceof StartNode && end instanceof ProcessInput;
	}


	/**
	 * Handles the connection of a connection. Override this method to handle
	 * this event.
	 */
	@Override
	protected void handleConnect(Figure start, Figure end) {
		((StartNode)start).connected(true);
		((StartNode)start).addConnector(this);
		connected = true;
		((ProcessInput)end).setStartNode(true);

	}

	/**
	 * Handles the disconnection of a connection. Override this method to handle
	 * this event.	 */
	@Override
	protected void handleDisconnect(Figure start, Figure end) {
		if (connected) {
			((StartNode)start).connected(false);	
		((StartNode)start).removeConnector(this);
		if (end instanceof ProcessInput) {
			((ProcessInput)end).setStartNode(false);
		}
		}
	}
}
