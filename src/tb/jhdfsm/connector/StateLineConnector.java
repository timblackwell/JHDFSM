package tb.jhdfsm.connector;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import tb.jhdfsm.figure.ProcessInput;
import tb.jhdfsm.figure.Transisional;
import CH.ifa.draw.figure.ArrowTip;
import CH.ifa.draw.figure.TextFigure;
import CH.ifa.draw.figure.connection.LineConnection;
import CH.ifa.draw.framework.Figure;

public class StateLineConnector extends LineConnection {
	
	private TextFigure lable = null;
	private int value = 0;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7684104213783845083L;
	
	public StateLineConnector () {
		this(0);
	}
	
	public StateLineConnector (int value) {
		super();
		setEndDecoration(new ArrowTip());
		setStartDecoration(null);
		lable = new TextFigure();
		lable.setAttribute("FillColor", Color.lightGray);
		lable.setText(String.valueOf(value));
		lable.setReadOnly(true);
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public Figure getTextFigure() {
		return lable;
	}
		
	@Override
	public Rectangle displayBox() {
		Rectangle superBox = super.displayBox();
		superBox.add(lable.displayBox());
		return superBox;
	}
	
	private void decorate(Graphics g) {
		if (fStartDecoration != null) {
			Point p1 = fPoints.elementAt(0);
			Point p2 = fPoints.elementAt(1);
			fStartDecoration.draw(g, p1.x, p1.y, p2.x, p2.y);
		}
		if (fEndDecoration != null) {
			Point p3 = fPoints.elementAt(fPoints.size() - 2);
			Point p4 = fPoints.elementAt(fPoints.size() - 1);
			fEndDecoration.draw(g, p4.x, p4.y, p3.x, p3.y);
		}
	}

	@Override
	public void draw(Graphics g) {
		
		if (fStart == null || lable == null || fEnd == null) {
			super.draw(g);
			return;
	    } 
		
		Point start = fStart.findStart(this);
		Point end = fEnd.findEnd(this);
		Point center = lable.center();
				
		if (start.x == end.x && start.y == end.y) {
			g.drawOval(start.x, start.y-10, center.x-start.x , 20);
		} else {
	        Path2D curve = new Path2D.Double();
	        curve.moveTo(start.x, start.y);
	        curve.curveTo(start.x, start.y, center.x, center.y, end.x, end.y);
	
	        Graphics2D g2 = (Graphics2D)g;
	        g2.draw(curve);		
		}
		
        decorate(g);
		lable.draw(g);
	}	
	
	@Override
	public boolean containsPoint(int x, int y) {
		boolean lineContainsPoint = false;
		
		if (fStart != null && lable != null && fEnd != null) {
			Point start = fStart.findStart(this);
			Point end = fEnd.findEnd(this);
			
	        Path2D curve = new Path2D.Double();
	        curve.moveTo(start.x, start.y);
	        curve.curveTo(start.x, start.y, lable.center().x, lable.center().y, end.x, end.y);
	        
		    lineContainsPoint = curve.contains(x, y);
	    } else {
	    	lineContainsPoint = super.containsPoint(x, y);
		}
		return lineContainsPoint || lable.containsPoint(x, y); 
	}
	
	
	/**
	 * Tests whether two figures can be connected.
	 */
	@Override
	public boolean canConnect(Figure start, Figure end) {	
		return start instanceof Transisional && end instanceof Transisional && start instanceof ProcessInput && end instanceof ProcessInput;
	}

	
	/**
	 * Handles the connection of a connection. Override this method to handle
	 * this event.
	 */
	protected void handleConnect(Figure start, Figure end) {
		if (start instanceof Transisional) {
			((Transisional) start).addConnector(this);
		}
	}

	/**
	 * Handles the disconnection of a connection. Override this method to handle
	 * this event.
	 */
	protected void handleDisconnect(Figure start, Figure end) {
		if (start instanceof Transisional) {
			((Transisional) start).removeConnector(this);
		}
	}

	/**
	 * Updates the connection.
	 */
	@Override
	public void updateConnection() {
		 int x = 0, y = 0;
		if (fStart != null) {
			Point start = fStart.findStart(this);
			startPoint(start.x, start.y);

			x = start.x;
			y = start.y;
		}
		if (fEnd != null) {
			Point end = fEnd.findEnd(this);
			endPoint(end.x, end.y);

			x += (end.x - x)/2;
			y += (end.y - y)/2;
		}
		
		if (fStart != null && fEnd != null) {
			y = y - (lable.displayBox().height/2);
			x = x - (lable.displayBox().width/2);
			
			if (fStart.owner() == fEnd.owner()) {
				 x += 15;
			}
			
			Point center = fStart.owner().center();
			
			double[] pt = {x, y};
			double roationAngle = 20;
			
			if (value == 1) {
				roationAngle = 0 - roationAngle;
			}
			
			AffineTransform.getRotateInstance(Math.toRadians(roationAngle), center.x, center.y)
			  .transform(pt, 0, pt, 0, 1); // specifying to use this double[] to hold coords
			double newX = pt[0];
			double newY = pt[1];
			
			x = (int) newX;
			y = (int) newY;
			
			Point lableCenter = new Point(x, y);
			lable.displayBox(lableCenter, lableCenter);			
		}
		
	}

}
