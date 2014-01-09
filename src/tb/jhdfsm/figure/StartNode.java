package tb.jhdfsm.figure;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;

import tb.jhdfsm.connector.StartNodeConnector;
import CH.ifa.draw.framework.ConnectionFigure;
import CH.ifa.draw.framework.Handle;
import CH.ifa.draw.handle.ConnectionHandle;
import CH.ifa.draw.locator.RelativeLocator;

public class StartNode extends CircleFigure implements Validatable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8363466797940835974L;

    public boolean endNode;
    private boolean canConnect = true;
    private Vector<StartNodeConnector> startNodeConnectors;
    private boolean active;

	public StartNode() {
		super();
		this.setAttribute("FillColor", Color.BLACK);
		this.setAttribute("FrameColor", Color.BLACK);
		startNodeConnectors = new Vector<StartNodeConnector>();
		active = false;
	}


	@Override
	public boolean canConnect() {
		return canConnect;
	}
	
	public void connected(boolean figureConnected) {
		this.canConnect = !figureConnected;
	}
	
	@Override
	public void basicDisplayBox(Point origin, Point corner) {
    	centerPoint = origin;
    	int maxXY = 5;
    	updateDisplayBox(maxXY);    
	}
	
	@Override
	public void drawBackground(Graphics g) {
        Rectangle r = displayBox();
        g.fillOval(r.x, r.y, r.width, r.height);
        
        if (!isValid()) {
        	g.setColor(Color.RED);
            g.drawOval(r.x, r.y, r.width, r.height);
        }
        
        if (endNode) {        	
        	int grow = (int) (r.height*0.15);
            r.grow(grow, grow);
            
            g.setColor(getFrameColor());
            g.drawOval(r.x, r.y, r.width, r.height);
        }
        
        if (active) {        	
        	int grow = (int) (r.height*0.15);
            r.grow(grow, grow);
            
            g.setColor(getFrameColor());
            g.drawOval(r.x, r.y, r.width, r.height);
        }
	}
	
	@Override
	public Vector<Handle> handles() {
        Vector<Handle> handles = new Vector<Handle>();
        ConnectionFigure prototype = new StartNodeConnector();
        handles.addElement(new ConnectionHandle(this, RelativeLocator.center(), prototype));
        return handles;
    }

	public void addConnector(StartNodeConnector connector) {
		startNodeConnectors.add(connector);
	}
	
	public void removeConnector(StartNodeConnector connector) {
		startNodeConnectors.remove(connector);
	}
	
	@Override
	public boolean isValid() {
		return startNodeConnectors.size() == 1;
	}
}
