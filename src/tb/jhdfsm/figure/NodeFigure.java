/*
 * @(#)NodeFigure.java 5.1
 *
 */

package tb.jhdfsm.figure;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Vector;

import tb.jhdfsm.connector.OffBitConnector;
import tb.jhdfsm.connector.OnBitConnector;
import tb.jhdfsm.connector.StartConnector;
import CH.ifa.draw.connector.ChopEllipseConnector;
import CH.ifa.draw.connector.LocatorConnector;
import CH.ifa.draw.connector.ShortestDistanceConnector;
import CH.ifa.draw.figure.TextFigure;
import CH.ifa.draw.figure.connection.LineConnection;
import CH.ifa.draw.framework.ConnectionFigure;
import CH.ifa.draw.framework.Connector;
import CH.ifa.draw.framework.Handle;
import CH.ifa.draw.handle.ConnectionHandle;
import CH.ifa.draw.handle.NullHandle;
import CH.ifa.draw.locator.RelativeLocator;
import CH.ifa.draw.util.Geom;


public class NodeFigure extends TextFigure {

	private static final long serialVersionUID = -2615020318489739223L;
	private static final int BORDER = 5;
    private boolean fConnectorsVisible;
    public boolean endNode;

    public NodeFigure() {
        initialize();
    }

    /**
     */
    @Override
	public Connector connectorAt(int x, int y) {
        return new ChopEllipseConnector(this);
    }

    /**
     */
    @Override
	public void connectorVisibility(boolean isVisible) {
        fConnectorsVisible = isVisible;
        invalidate();
    }

    @Override
	public boolean containsPoint(int x, int y) {
        // add slop for connectors
        if (fConnectorsVisible) {
            Rectangle r = displayBox();
            int d = LocatorConnector.SIZE/2;
            r.grow(d, d);
            return r.contains(x, y);
        }
        return super.containsPoint(x, y);
    }

    @Override
	public Rectangle displayBox() {
    	  Rectangle rec = super.displayBox();
          int d = BORDER;
          rec.grow(d, d);
          int maxdim = Math.max(rec.height, rec.width);
          
          Rectangle box = new Rectangle((int) (rec.x + (0.5*rec.width) - (0.5*maxdim)), (int) (rec.y + (0.5*rec.height) - (0.5*maxdim)), maxdim, maxdim);
          return box;
    }

    @Override
	public void draw(Graphics g) {
    	this.drawBackground(g);
        super.draw(g);
    }

    @Override
    public void drawBackground(Graphics g) {    	
    	Rectangle r = displayBox();
    	if (endNode) {
        	Rectangle rBig = displayBox();
        	
            int grow = (int) (r.height*0.15);
            rBig.grow(grow, grow);
        	
            g.fillOval(rBig.x, rBig.y, rBig.width, rBig.height);
            
            g.setColor(getFrameColor());
            g.drawOval(rBig.x, rBig.y, rBig.width, rBig.height);
    	} else {
        g.fillOval(r.x, r.y, r.width, r.height);
    	}
    	
    	g.setColor(getFrameColor());
        g.drawOval(r.x, r.y, r.width, r.height);
        
    }

    @Override
	public Vector<Handle> handles() {
        Vector<Handle> handles = new Vector<Handle>();
        return handles;
    }

    private void initialize() {
        this.setAttribute("FillColor", Color.WHITE);
        this.setAttribute("FrameColor", Color.BLACK);
        setText("S1");
        Font fb = new Font("Helvetica", Font.BOLD, 12);
        setFont(fb);
        endNode = false;
    }
}
