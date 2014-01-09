/*
 * @(#)NodeFigure.java 5.1
 *
 */

package tb.jhdfsm.figure;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

import tb.jhdfsm.connector.OneNodeConnector;
import tb.jhdfsm.connector.ZeroNodeConnector;
import CH.ifa.draw.connector.ChopEllipseConnector;
import CH.ifa.draw.connector.LocatorConnector;
import CH.ifa.draw.figure.TextFigure;
import CH.ifa.draw.figure.connection.LineConnection;
import CH.ifa.draw.framework.Connector;
import CH.ifa.draw.framework.Handle;


public class StateFigure extends TextFigure implements Transisional, ProcessInput {

	private static final long serialVersionUID = -2615020318489739223L;
	private static final int BORDER = 5;
    private boolean fConnectorsVisible;
    private Vector<ZeroNodeConnector> zeroNodeConnectons;
    private Vector<OneNodeConnector> oneNodeConnectons;
    private boolean active;
    private boolean startNode;
    
    public StateFigure() {
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
        g.fillOval(r.x, r.y, r.width, r.height);
        
        if (active) {
        	g.setColor(Color.GREEN);
        } else if (isValid()) {
        	g.setColor(getFrameColor());
        } else {
        	g.setColor(Color.RED);
        }
        
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
        zeroNodeConnectons = new Vector<ZeroNodeConnector>();
        oneNodeConnectons = new Vector<OneNodeConnector>();
        active = false;
        startNode = false;
    }

	@Override
	public boolean isValid() {
		if (zeroNodeConnectons.size() == 1 && oneNodeConnectons.size() == 1) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public void addConnector(LineConnection stateLineConnector) {
		if (stateLineConnector instanceof ZeroNodeConnector) {
			zeroNodeConnectons.add((ZeroNodeConnector) stateLineConnector);
		}
		if (stateLineConnector instanceof OneNodeConnector) {
			oneNodeConnectons.add((OneNodeConnector) stateLineConnector);
		}
	}
	
	@Override
	public void removeConnector(LineConnection stateLineConnector) {
		if (stateLineConnector instanceof ZeroNodeConnector) {
			zeroNodeConnectons.remove((ZeroNodeConnector) stateLineConnector);
		}
		if (stateLineConnector instanceof OneNodeConnector) {
			oneNodeConnectons.remove((OneNodeConnector) stateLineConnector);
		}
	}

	@Override
	public StateFigure nextState(int input) {
			switch (input) {
				case 0: {
					if (zeroNodeConnectons.size() > 0) {
						return (StateFigure)zeroNodeConnectons.firstElement().endFigure();
					}
					break;
				}
				case 1: {
					if (oneNodeConnectons.size() > 0) {
						return (StateFigure)oneNodeConnectons.firstElement().endFigure();
					}
					break;
				}
			}
		
		return this;
	}
	
	@Override
	public void activate() {
		active = true;
		invalidate();
	}
	
	
	@Override
	public void deactivate() {
		active = false;
		invalidate();
	}

	@Override
	public void reset() {
		active = startNode;	
		invalidate();
	}

	@Override
	public boolean isActive() {
		return active;
	}
	
	@Override
	public void setStartNode(boolean startNode) {
		this.startNode = startNode;
	}	
}
