package tb.jhdfsm.figure;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class EndNodeFigure extends NodeFigure {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8767403693597922289L;
	
	public EndNodeFigure() {
        super();
    }
	
	@Override
    public void drawBackground(Graphics g) {    	
    	Rectangle r = displayBox();
    	Rectangle rBig = displayBox();
    	
        int grow = (int) (r.height*0.15);
        rBig.grow(grow, grow);
    	
        g.fillOval(rBig.x, rBig.y, rBig.width, rBig.height);
        
        g.setColor(getFrameColor());
        g.drawOval(rBig.x, rBig.y, rBig.width, rBig.height);
        g.drawOval(r.x, r.y, r.width, r.height);
	}

}
