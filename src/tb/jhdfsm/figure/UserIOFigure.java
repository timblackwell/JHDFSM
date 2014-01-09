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
    	
    	fFigures = new Vector<>(fFigures.subList(0, 3));
//    	for(int i = 3; i < fFigures.size(); i++) {
//    		remove(fFigures.elementAt(i));
//    	}
    	this.changed();
//    	removeAll();
//    	initialize();
    }
    
    public void addError(ValidationError error) {
    	errors.add(error);
    	
    	Font f = new Font("Helvetica", Font.PLAIN, 12);
    	
    	TextFigure end = new TextFigure();
    	end.setText(error.name());
    	end.setFont(f);
    	end.setAttribute("TextColor", Color.RED);
    	end.setReadOnly(true);
    	add(end);
    }

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

        Figure f = figureAt(1);
        Rectangle rf = f.displayBox();
        g.setColor(Color.gray);
        g.drawLine(r.x, rf.y+rf.height, r.x+r.width, rf.y + rf.height);
        g.setColor(Color.white);
        g.drawLine(r.x, rf.y+rf.height+1, r.x+r.width, rf.y + rf.height+1);

        g.setColor(Color.white);
        g.drawLine(r.x, r.y, r.x, r.y + r.height);
        g.drawLine(r.x, r.y, r.x + r.width, r.y);
        g.setColor(Color.gray);
        g.drawLine(r.x + r.width, r.y, r.x + r.width, r.y + r.height);
        g.drawLine(r.x , r.y + r.height, r.x + r.width, r.y + r.height);
    }

    public void draw(Graphics g) {
        drawBorder(g);
        TextFigure inputString = (TextFigure) fFigures.elementAt(1);
        if (!inputString.getText().matches("[01]*")) {
        	inputString.setAttribute("TextColor", Color.RED);
        } else {
        	inputString.setAttribute("TextColor", Color.BLACK);
        }
        
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
        fDisplayBox = new Rectangle(0, 0, 0, 0);

        Font f = new Font("Helvetica", Font.PLAIN, 12);
        Font fb = new Font("Helvetica", Font.BOLD, 12);

        TextFigure inputStringLable = new TextFigure();
        inputStringLable.setFont(fb);
        inputStringLable.setText("Input string:");
        inputStringLable.setReadOnly(true);
        add(inputStringLable);
        
        TextFigure inputString = new TextFigure();
        inputString.setFont(f);
        inputString.setText("Enter input string");
        inputString.setReadOnly(false);
        add(inputString);

        TextFigure errorsLable = new TextFigure();
        errorsLable.setFont(fb);
        errorsLable.setText("Validation errors: " + errors.size());
        errorsLable.setReadOnly(true);
        errorsLable.moveBy(BORDER, BORDER);
        add(errorsLable);
        
//        fDisplayBox.grow(2*BORDER, 2*BORDER);
    }

    private void layout() {
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

    public void updateText() {
//      int newEnd = start()+duration();

    	TextFigure errorsLable = (TextFigure)fFigures.get(2);
        errorsLable.setText("Validation errors: " + errors.size());
        
        
//        System.out.println("update");
//    	notifyPostTasks();
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
