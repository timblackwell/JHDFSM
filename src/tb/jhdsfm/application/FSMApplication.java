package tb.jhdsfm.application;

import java.awt.Point;

import javax.swing.JMenuBar;
import javax.swing.JPanel;

import tb.jhdfsm.connector.OffBitConnector;
import tb.jhdfsm.connector.OnBitConnector;
import tb.jhdfsm.figure.NodeFigure;
import tb.jhdfsm.figure.StartNode;
import tb.jhdfsm.tool.EndNodeTool;
import CH.ifa.draw.application.DrawApplication;
import CH.ifa.draw.framework.Tool;
import CH.ifa.draw.tool.ConnectionTool;
import CH.ifa.draw.tool.TextTool;

public class FSMApplication extends DrawApplication {
	
	private static final long serialVersionUID = -7286367658180495935L;

	public static void main(String[] args) {
		FSMApplication fsm = new FSMApplication();
		fsm.open();
    }

	FSMApplication() {
        super("Finite state machine");
    }
	
	@Override
	public void open() {
		super.open();
		StartNode startNode = new StartNode();
		Point point = new Point(50, 50);
		startNode.displayBox(point, point);
		view().add(startNode);
	}
	
	protected void createMenus(JMenuBar mb) {
		super.createMenus(mb);
	}
	
	@Override
	protected void createTools(JPanel palette) {
        super.createTools(palette);
        
        Tool tool = new TextTool(view(), new NodeFigure());
        palette.add(createToolButton(IMAGES+"ELLIPSE", "Node Tool", tool));
        
		tool = new EndNodeTool(view());
		palette.add(createToolButton(IMAGES + "ELLIPSE", "End Node", tool));
		
		tool = new ConnectionTool(view(), new OnBitConnector());
        palette.add(createToolButton(IMAGES+"CONN", "On Bit Tool", tool));
        
        tool = new ConnectionTool(view(), new OffBitConnector());
        palette.add(createToolButton(IMAGES+"CONN", "Off Bit Tool", tool));
	}
	
	public void destroy() {
		super.destroy();
	}
}
