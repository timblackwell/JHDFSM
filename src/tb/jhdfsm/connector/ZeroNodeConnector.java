package tb.jhdfsm.connector;

import tb.jhdfsm.figure.NodeFigure;
import CH.ifa.draw.framework.Connector;


public class ZeroNodeConnector extends NodeConnector {
	
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
