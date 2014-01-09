package tb.jhdfsm.connector;



public class ZeroNodeConnector extends StateLineConnector {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7684104213783845083L;
	
	public ZeroNodeConnector () {
		super(0);
	}

//	@Override
//	public void connectStart(Connector start) {
//	super.connectStart(start);
//		NodeFigure startFigure = (NodeFigure) start.owner();
//		startFigure.setConnector((ZeroNodeConnector)this);
//	}
}
