/*
 * @(#)NodeFigure.java 5.1
 *
 */

package tb.jhdfsm.figure;

import java.awt.Graphics;
import java.awt.Rectangle;

import CH.ifa.draw.figure.DecoratorFigure;
import CH.ifa.draw.figure.connection.LineConnection;


public class AcceptingStateFigure extends DecoratorFigure implements Transisional, ProcessInput {

    /**
	 * 
	 */
	private static final long serialVersionUID = -9016367305206324460L;
	private StateFigure figure;
    
    public AcceptingStateFigure(StateFigure figure) {
    	super(figure);
    	this.figure = figure;
    }

   
    @Override
	public Rectangle displayBox() {
    	Rectangle r = super.displayBox();
        r.grow(5, 5);
        return r;
    }

    @Override
	public void draw(Graphics g) {
    	figure.draw(g);
        Rectangle r = super.displayBox();
        r.grow(5, 5);
        g.drawOval(r.x, r.y, r.width, r.height);
    }
    
	@Override
	public boolean isValid() {
		return figure.isValid();
	}
	
	@Override
	public void addConnector(LineConnection stateLineConnector) {
		figure.addConnector(stateLineConnector);
	}
	
	@Override
	public void removeConnector(LineConnection stateLineConnector) {
		figure.removeConnector(stateLineConnector);
	}

	@Override
	public StateFigure nextState(int input) {
		return figure.nextState(input);
	}
	
	@Override
	public void activate() {
		figure.activate();
		invalidate();
	}
	
	@Override
	public void deactivate() {
		figure.deactivate();
		invalidate();
	}

	@Override
	public void reset() {
		figure.reset();
		invalidate();
	}

	@Override
	public boolean isActive() {
		return figure.isActive();
	}
	
	@Override
	public void setStartNode(boolean startNode) {
		figure.setStartNode(startNode);
	}
	
}
