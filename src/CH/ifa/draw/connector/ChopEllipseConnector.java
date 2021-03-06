/*
 * @(#)ChopEllipseConnector.java 5.1
 *
 */

package CH.ifa.draw.connector;

import java.awt.Point;
import java.awt.Rectangle;

import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.util.Geom;

/**
 * A ChopEllipseConnector locates a connection point by
 * chopping the connection at the ellipse defined by the
 * figure's display box.
 */
public class ChopEllipseConnector extends ChopBoxConnector {

    /*
     * Serialization support.
     */
    private static final long serialVersionUID = -3165091511154766610L;

    public ChopEllipseConnector() {
    }

    public ChopEllipseConnector(Figure owner) {
        super(owner);
    }

    @Override
	protected Point chop(Figure target, Point from) {
        Rectangle r = target.displayBox();
        double angle = Geom.pointToAngle(r, from);
	    return Geom.ovalAngleToPoint(r, angle);
    }
}

