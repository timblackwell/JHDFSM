package tb.jhdfsm.figure;

import java.awt.Point;
import java.util.Vector;

import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.framework.Handle;
import CH.ifa.draw.framework.Locator;
import CH.ifa.draw.handle.LocatorHandle;
import CH.ifa.draw.locator.RelativeLocator;

public class CircleHandleKit {

	/**
     * Fills the given Vector with handles at each corner of a
     * figure.
     */
    static public void addCornerHandles(Figure f, Vector<Handle> handles) {
        handles.addElement(new RadiusHandel(f, RelativeLocator.northEast()));
        handles.addElement(new RadiusHandel(f, RelativeLocator.southEast()));
        handles.addElement(new RadiusHandel(f, RelativeLocator.southWest()));
        handles.addElement(new RadiusHandel(f, RelativeLocator.northWest()));
    }

    /**
     * Fills the given Vector with handles at each corner
     * and the north, south, east, and west of the figure.
     */
    static public void addHandles(Figure f, Vector<Handle> handles) {
        addCornerHandles(f, handles);
        handles.addElement(new RadiusHandel(f, RelativeLocator.north()));
        handles.addElement(new RadiusHandel(f, RelativeLocator.east()));
        handles.addElement(new RadiusHandel(f, RelativeLocator.south()));
        handles.addElement(new RadiusHandel(f, RelativeLocator.west()));
    }

}

class RadiusHandel extends LocatorHandle {
	RadiusHandel(Figure owner, Locator locator) {
        super(owner, locator);
    }

    @Override
	public void invokeStep (int x, int y, int anchorX, int anchorY, DrawingView view) {
    	Point center = owner().center();
        owner().displayBox(
            new Point(center.x, center.y),
            new Point(x, y)
        );
    }
}
