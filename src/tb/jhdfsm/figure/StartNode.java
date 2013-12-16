package tb.jhdfsm.figure;

import java.awt.Color;
import java.awt.Point;
import java.util.Vector;

import tb.jhdfsm.connector.StartConnector;
import CH.ifa.draw.figure.connection.LineConnection;
import CH.ifa.draw.framework.ConnectionFigure;
import CH.ifa.draw.framework.Handle;
import CH.ifa.draw.handle.ConnectionHandle;
import CH.ifa.draw.locator.RelativeLocator;

public class StartNode extends CircleFigure{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8363466797940835974L;

	public StartNode() {
		super();
		this.setAttribute("FillColor", Color.BLACK);
	}
    
    
	@Override
	public void basicDisplayBox(Point origin, Point corner) {
    	centerPoint = origin;
    	int maxXY = 10;
    	updateDisplayBox(maxXY);    
	}
	
	 @Override
	public Vector<Handle> handles() {
        Vector<Handle> handles = new Vector<Handle>();
        ConnectionFigure prototype = new StartConnector();
        handles.addElement(new ConnectionHandle(this, RelativeLocator.center(), prototype));
        return handles;
    }
}
