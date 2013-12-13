package tb.jhdfsm.figure;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Vector;

import CH.ifa.draw.connector.ChopEllipseConnector;
import CH.ifa.draw.figure.AttributeFigure;
import CH.ifa.draw.framework.Connector;
import CH.ifa.draw.framework.Handle;
import CH.ifa.draw.storable.StorableInput;
import CH.ifa.draw.storable.StorableOutput;

public class CircleFigure extends AttributeFigure {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8284875653220127421L;
	
	private Rectangle   fDisplayBox;
	private Point centerPoint;

    @SuppressWarnings("unused")
	private int ellipseFigureSerializedDataVersion = 1;

    public CircleFigure() {
        this(new Point(0,0), new Point(0,0));
    }
    
    public CircleFigure(Point origin, Point corner) {
        basicDisplayBox(origin,corner);
    }
    
    
    @Override
	public void basicDisplayBox(Point origin, Point corner) {
    	centerPoint = origin;
    	int maxXY = getMaxXY(origin, corner);
    	updateDisplayBox(maxXY);    	
    }

    private void updateDisplayBox(int maxXY) {
    	Point topLeft = new Point(centerPoint.x - maxXY, centerPoint.y - maxXY);
    	Point bottomRight = new Point(centerPoint.x + maxXY, centerPoint.y + maxXY);
    	
        fDisplayBox = new Rectangle(topLeft);
        fDisplayBox.add(bottomRight);
	}

	protected int getMaxXY(Point origin, Point corner) {
    	int vectorX = Math.abs(origin.x - corner.x);
    	int vectorY= Math.abs(origin.y - corner.y);
    	int maxXY = Math.max(vectorX, vectorY);
    	
    	return maxXY;
	}

	@Override
	protected void basicMoveBy(int x, int y) {
        fDisplayBox.translate(x,y);
    }

    @Override
	public Insets connectionInsets() {
        Rectangle r = fDisplayBox;
        int cx = r.width/2;
        int cy = r.height/2;
        return new Insets(cy, cx, cy, cx);
    }

    @Override
	public Connector connectorAt(int x, int y) {
        return new ChopEllipseConnector(this);
    }

    @Override
	public Rectangle displayBox() {
        return new Rectangle(
            fDisplayBox.x,
            fDisplayBox.y,
            fDisplayBox.width,
            fDisplayBox.height);
    }

    @Override
	public void drawBackground(Graphics g) {
        Rectangle r = displayBox();
        g.fillOval(r.x, r.y, r.width, r.height);
    }

    @Override
	public void drawFrame(Graphics g) {
        Rectangle r = displayBox();
        g.drawOval(r.x, r.y, r.width-1, r.height-1);
    }

    @Override
	public Vector<Handle> handles() {
        Vector<Handle> handles = new Vector<Handle>();
        CircleHandleKit.addHandles(this, handles);
        return handles;
    }

    @Override
	public void read(StorableInput dr) throws IOException {
        super.read(dr);
        fDisplayBox = new Rectangle(
            dr.readInt(),
            dr.readInt(),
            dr.readInt(),
            dr.readInt());
    }

    @Override
	public void write(StorableOutput dw) {
        super.write(dw);
        dw.writeInt(fDisplayBox.x);
        dw.writeInt(fDisplayBox.y);
        dw.writeInt(fDisplayBox.width);
        dw.writeInt(fDisplayBox.height);
    }
}
