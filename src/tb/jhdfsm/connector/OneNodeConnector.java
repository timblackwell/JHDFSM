package tb.jhdfsm.connector;



public class OneNodeConnector extends NodeConnector {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7684104213783845083L;
	
	public OneNodeConnector () {
		super(1);
	}

//	@Override
//	public void connectStart(Connector start) {
//		super.connectStart(start);
//		NodeFigure startFigure = (NodeFigure) start.owner();
//		startFigure.setConnector((OneNodeConnector)this);
//	}
}
