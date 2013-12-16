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

import CH.ifa.draw.connector.LocatorConnector;
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
    private Vector<LocatorConnector>      fConnectors;
    private boolean     fConnectorsVisible;

    public NodeFigure() {
        initialize();
        fConnectors = null;
    }

    /**
     */
    @Override
	public Connector connectorAt(int x, int y) {
        return findConnector(x, y);
    }

    /**
     */
    private Vector<LocatorConnector> connectors() {
        if (fConnectors == null)
            createConnectors();
        return fConnectors;
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

    private void createConnectors() {
        fConnectors = new Vector<LocatorConnector>(4);
        fConnectors.addElement(new LocatorConnector(this, RelativeLocator.north()) );
        fConnectors.addElement(new LocatorConnector(this, RelativeLocator.south()) );
        fConnectors.addElement(new LocatorConnector(this, RelativeLocator.west()) );
        fConnectors.addElement(new LocatorConnector(this, RelativeLocator.east()) );
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
        drawConnectors(g);
        super.draw(g);
    }

    @Override
    public void drawBackground(Graphics g) {    	
    	Rectangle r = displayBox();
    	g.setColor(Color.WHITE);
        g.fillOval(r.x, r.y, r.width, r.height);
    }

    private void drawConnectors(Graphics g) {
        if (fConnectorsVisible) {
            Enumeration<LocatorConnector> e = connectors().elements();
            while (e.hasMoreElements())
                e.nextElement().draw(g);
        }
    }

    private Connector findConnector(int x, int y) {
        // return closest connector
        long min = Long.MAX_VALUE;
        Connector closest = null;
        Enumeration<LocatorConnector> e = connectors().elements();
        while (e.hasMoreElements()) {
            Connector c = e.nextElement();
            Point p2 = Geom.center(c.displayBox());
            long d = Geom.length2(x, y, p2.x, p2.y);
            if (d < min) {
                min = d;
                closest = c;
            }
        }
        return closest;
    }

    @Override
	public Vector<Handle> handles() {
        ConnectionFigure prototype = new LineConnection();
        Vector<Handle> handles = new Vector<Handle>();
        handles.addElement(new ConnectionHandle(this, RelativeLocator.east(), prototype));
        handles.addElement(new ConnectionHandle(this, RelativeLocator.west(), prototype));
        handles.addElement(new ConnectionHandle(this, RelativeLocator.south(), prototype));
        handles.addElement(new ConnectionHandle(this, RelativeLocator.north(), prototype));

        handles.addElement(new NullHandle(this, RelativeLocator.southEast()));
        handles.addElement(new NullHandle(this, RelativeLocator.southWest()));
        handles.addElement(new NullHandle(this, RelativeLocator.northEast()));
        handles.addElement(new NullHandle(this, RelativeLocator.northWest()));
        return handles;
    }

    private void initialize() {
        setText("S1");
        Font fb = new Font("Helvetica", Font.BOLD, 12);
        setFont(fb);
        createConnectors();
    }
}
