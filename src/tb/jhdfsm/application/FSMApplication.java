package tb.jhdfsm.application;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import tb.jhdfsm.command.ValidateCommand;
import tb.jhdfsm.connector.NodeConnector;
import tb.jhdfsm.figure.NodeFigure;
import tb.jhdfsm.figure.StartNode;
import tb.jhdfsm.tool.EndNodeTool;
import tb.jhdfsm.tool.TextTool;
import CH.ifa.draw.application.DrawApplication;
import CH.ifa.draw.command.CommandMenu;
import CH.ifa.draw.framework.Tool;
import CH.ifa.draw.tool.ConnectionTool;
import CH.ifa.draw.tool.CreationTool;

public class FSMApplication extends DrawApplication {
	
	private static final long serialVersionUID = -7286367658180495935L;
	
    public static final String JHDFSMIMAGES = "/tb/jhdfsm/images/";

	public static void main(String[] args) {
		FSMApplication fsm = new FSMApplication();
		fsm.open();
    }

	FSMApplication() {
        super("Finite state machine");
    }
	
	@Override
	protected void createMenus(JMenuBar mb) {
		super.createMenus(mb);
		mb.add(createFSMMenu());
    }
	
	protected JMenu createFSMMenu() {
		CommandMenu menu = new CommandMenu("Finite State Machine");
		menu.add(new ValidateCommand("Validate Finite State Machine", super.view()));
		return menu;
	}
	
	@Override
	protected void createTools(JPanel palette) {
        super.createTools(palette);

        Tool  tool = new TextTool(view(), new NodeFigure());
        palette.add(createToolButton(IMAGES+"TEXT", "Text Tool", tool));
        
        tool = new CreationTool(view(), new StartNode());
        palette.add(createToolButton(JHDFSMIMAGES+"STARTNODE", "Start Node Tool", tool)); 
        
        tool = new CreationTool(view(), new NodeFigure());
        palette.add(createToolButton(IMAGES+"ELLIPSE", "Node Tool", tool));
        
		tool = new EndNodeTool(view());
		palette.add(createToolButton(IMAGES + "ELLIPSE", "End Node Tool", tool));
		
		tool = new ConnectionTool(view(), new NodeConnector());
        palette.add(createToolButton(IMAGES+"CONN", "Node Connector", tool));
	}
	
	public void destroy() {
		super.destroy();
	}
}
