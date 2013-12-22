package tb.jhdfsm.figure;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;

import tb.jhdfsm.connector.NodeConnector;
import CH.ifa.draw.framework.ConnectionFigure;
import CH.ifa.draw.framework.Handle;
import CH.ifa.draw.handle.ConnectionHandle;
import CH.ifa.draw.locator.RelativeLocator;

public class StartNode extends CircleFigure{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8363466797940835974L;

    public boolean endNode;

	public StartNode() {
		super();
		this.setAttribute("FillColor", Color.BLACK);
		this.setAttribute("FrameColor", Color.BLACK);
	}
    
    
	@Override
	public void basicDisplayBox(Point origin, Point corner) {
    	centerPoint = origin;
    	int maxXY = 10;
    	updateDisplayBox(maxXY);    
	}
	
	@Override
	public void drawBackground(Graphics g) {
        Rectangle r = displayBox();
        g.fillOval(r.x, r.y, r.width, r.height);
        
        if (endNode) {        	
        	int grow = (int) (r.height*0.15);
            r.grow(grow, grow);
            
            g.setColor(getFrameColor());
            g.drawOval(r.x, r.y, r.width, r.height);
        }
	}
	
	@Override
	public Vector<Handle> handles() {
        Vector<Handle> handles = new Vector<Handle>();
        ConnectionFigure prototype = new NodeConnector();
        handles.addElement(new ConnectionHandle(this, RelativeLocator.center(), prototype));
        return handles;
    }
}
