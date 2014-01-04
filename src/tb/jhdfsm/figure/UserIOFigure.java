/*
 * @(#)PertFigure.java 5.1
 *
 */

package tb.jhdfsm.figure;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import CH.ifa.draw.figure.CompositeFigure;
import CH.ifa.draw.figure.NumberTextFigure;
import CH.ifa.draw.figure.TextFigure;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.framework.FigureChangeEvent;
import CH.ifa.draw.framework.Handle;
import CH.ifa.draw.handle.NullHandle;
import CH.ifa.draw.locator.RelativeLocator;
import CH.ifa.draw.storable.Storable;
import CH.ifa.draw.storable.StorableInput;
import CH.ifa.draw.storable.StorableOutput;


public class UserIOFigure extends CompositeFigure {
    private static final int BORDER = 3;
    private Rectangle fDisplayBox;
    private List<ValidationError> errors = new ArrayList<ValidationError>();

    public enum ValidationError {
    	MissingStartNode, ExtraStartNode, MissingStartNodeConnection, ExtraStartNodeConnection,
    	MissingNodeConnection, ExtraNodeConnection,
    	LableNotInAlphabet
    }
    
    /*
     * Serialization support.
     */
    private static final long serialVersionUID = -7877776240236946511L;
    @SuppressWarnings("unused")
	private int pertFigureSerializedDataVersion = 1;

    public UserIOFigure() {
        initialize();
    }
    
    
    public void clearErrors() {
    	errors.clear();
    	Vector<Figure> extras = this.fFigures;
    	extras.remove(0);
    	this.removeAll();
    	initialize();
    }
    
    public void addError(ValidationError error) {
    	errors.add(error);
    	
    	Font f = new Font("Helvetica", Font.PLAIN, 12);
    	
    	TextFigure end = new TextFigure();
    	end.setText(error.name());
    	end.setFont(f);
    	end.setReadOnly(true);
    	add(end);
    }
    

//    public int start() {
//        int start = 0;
//        return start;
//    }
//
//    public int end() {
//        return asInt(2);
//    }
//
//    public int duration() {
//        return asInt(1);
//    }
//
//    public void setEnd(int value) {
//        setInt(2, value);
//    }

//    public void addPreTask(UserIOFigure figure) {
//        if (!fPreTasks.contains(figure)) {
//            fPreTasks.addElement(figure);
//        }
//    }
//
//    public void addPostTask(UserIOFigure figure) {
//        if (!fPostTasks.contains(figure)) {
//            fPostTasks.addElement(figure);
//        }
//    }
//
//    public void removePreTask(UserIOFigure figure) {
//        fPreTasks.removeElement(figure);
//    }
//
//    public void removePostTask(UserIOFigure figure) {
//        fPostTasks.removeElement(figure);
//    }

//    private int asInt(int figureIndex) {
//        NumberTextFigure t = (NumberTextFigure)figureAt(figureIndex);
//        return t.getValue();
//    }

//    @SuppressWarnings("unused")
//	private String taskName() {
//        TextFigure t = (TextFigure)figureAt(0);
//        return t.getText();
//    }

//    private void setInt(int figureIndex, int value) {
//        NumberTextFigure t = (NumberTextFigure)figureAt(figureIndex);
//        t.setValue(value);
//    }

    protected void basicMoveBy(int x, int y) {
	    fDisplayBox.translate(x, y);
	    super.basicMoveBy(x, y);
	}

    public Rectangle displayBox() {
        return new Rectangle(
            fDisplayBox.x,
            fDisplayBox.y,
            fDisplayBox.width,
            fDisplayBox.height);
    }

    public void basicDisplayBox(Point origin, Point corner) {
        fDisplayBox = new Rectangle(origin);
        fDisplayBox.add(corner);
        layout();
    }

    private void drawBorder(Graphics g) {
        super.draw(g);

        Rectangle r = displayBox();

        Figure f = figureAt(0);
        Rectangle rf = f.displayBox();
        g.setColor(Color.gray);
        g.drawLine(r.x, r.y+rf.height+2, r.x+r.width, r.y + rf.height+2);
        g.setColor(Color.white);
        g.drawLine(r.x, r.y+rf.height+3, r.x+r.width, r.y + rf.height+3);

        g.setColor(Color.white);
        g.drawLine(r.x, r.y, r.x, r.y + r.height);
        g.drawLine(r.x, r.y, r.x + r.width, r.y);
        g.setColor(Color.gray);
        g.drawLine(r.x + r.width, r.y, r.x + r.width, r.y + r.height);
        g.drawLine(r.x , r.y + r.height, r.x + r.width, r.y + r.height);
    }

    public void draw(Graphics g) {
        drawBorder(g);
        super.draw(g);
    }

    public Vector<Handle> handles() {
        Vector<Handle> handles = new Vector<Handle>();
        handles.addElement(new NullHandle(this, RelativeLocator.northWest()));
        handles.addElement(new NullHandle(this, RelativeLocator.northEast()));
        handles.addElement(new NullHandle(this, RelativeLocator.southWest()));
        handles.addElement(new NullHandle(this, RelativeLocator.southEast()));
        return handles;
    }

    private void initialize() {
        Font fb = new Font("Helvetica", Font.BOLD, 12);

        TextFigure errorsLable = new TextFigure();
        errorsLable.setFont(fb);
        errorsLable.setText("Validation errors: " + errors.size());
        errorsLable.setReadOnly(true);
        errorsLable.moveBy(BORDER, BORDER);
        add(errorsLable);
        
        fDisplayBox = errorsLable.displayBox();
//        fDisplayBox.grow(2*BORDER, 2*BORDER);
    }

    private void layout() {
    	System.out.println("layout");
	    Point partOrigin = new Point(fDisplayBox.x, fDisplayBox.y);
	    partOrigin.translate(BORDER, BORDER);
	    Dimension extent = new Dimension(0, 0);

        Enumeration<Figure> k = figures();
        
        while (k.hasMoreElements()) {
            Figure f = k.nextElement();

		    Dimension partExtent = f.size();
		    Point corner = new Point(
		                        partOrigin.x+partExtent.width,
		                        partOrigin.y+partExtent.height);
    		f.basicDisplayBox(partOrigin, corner);

		    extent.width = Math.max(extent.width, partExtent.width);
		    extent.height += partExtent.height;
		    partOrigin.y += partExtent.height;
        }        
        
	    fDisplayBox.width = extent.width + 2*BORDER;
	    fDisplayBox.height = extent.height + 2*BORDER;
    }

    private boolean needsLayout() {
	    Dimension extent = new Dimension(0, 0);

        Enumeration<Figure> k = figures();
        while (k.hasMoreElements()) {
            Figure f = k.nextElement();
		    extent.width = Math.max(extent.width, f.size().width);
        }
        int newExtent = extent.width + 2*BORDER;
        return newExtent != fDisplayBox.width;
    }

    public void update(FigureChangeEvent e) {
	        if (needsLayout()) {
	            layout();
	            changed();
	        }
    }

    public void figureChanged(FigureChangeEvent e) {
        update(e);
    }


    public void figureRemoved(FigureChangeEvent e) {
        update(e);
    }

//    public void notifyPostTasks() {
//        Enumeration<UserIOFigure> i = fPostTasks.elements();
//        while (i.hasMoreElements())
//            ((UserIOFigure) i.nextElement()).updateDurations();
//    }

    public void updateText() {
//      int newEnd = start()+duration();

    	TextFigure errorsLable = (TextFigure)fFigures.firstElement();
        errorsLable.setText("Validation errors: " + errors.size());
//    	notifyPostTasks();
    }

    public boolean hasCycle(Figure start) {
//        if (start == this)
//            return true;
//        Enumeration<UserIOFigure> i = fPreTasks.elements();
//        while (i.hasMoreElements()) {
//            if ((i.nextElement()).hasCycle(start))
//                return true;
//        }
        return false;
    }
    
    public Insets connectionInsets() {
        Rectangle r = fDisplayBox;
        int cx = r.width/2;
        int cy = r.height/2;
        return new Insets(cy, cx, cy, cx);
    }
    
    //-- store / load ----------------------------------------------

    public void write(StorableOutput dw) {
        super.write(dw);
        dw.writeInt(fDisplayBox.x);
        dw.writeInt(fDisplayBox.y);
        dw.writeInt(fDisplayBox.width);
        dw.writeInt(fDisplayBox.height);
    }

    public void writeTasks(StorableOutput dw, Vector<UserIOFigure> v) {
        dw.writeInt(v.size());
        Enumeration<UserIOFigure> i = v.elements();
        while (i.hasMoreElements())
            dw.writeStorable((Storable) i.nextElement());
    }

    public void read(StorableInput dr) throws IOException {
        super.read(dr);
        fDisplayBox = new Rectangle(
            dr.readInt(),
            dr.readInt(),
            dr.readInt(),
            dr.readInt());
        layout();
    }

    public Vector<UserIOFigure> readTasks(StorableInput dr) throws IOException {
        int size = dr.readInt();
        Vector<UserIOFigure> v = new Vector<UserIOFigure>(size);
        for (int i=0; i<size; i++)
            v.addElement((UserIOFigure)dr.readStorable());
        return v;
    }
}
