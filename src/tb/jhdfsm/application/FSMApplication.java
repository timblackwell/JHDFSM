package tb.jhdfsm.application;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import tb.jhdfsm.command.ResetCommand;
import tb.jhdfsm.command.SendInputCommand;
import tb.jhdfsm.command.ValidateCommand;
import tb.jhdfsm.connector.OneNodeConnector;
import tb.jhdfsm.connector.ZeroNodeConnector;
import tb.jhdfsm.figure.StateFigure;
import tb.jhdfsm.figure.StartNode;
import tb.jhdfsm.tool.EndNodeTool;
import tb.jhdfsm.tool.NodeConnectionTool;
import tb.jhdfsm.tool.TextTool;
import CH.ifa.draw.application.DrawApplication;
import CH.ifa.draw.command.CommandMenu;
import CH.ifa.draw.framework.Tool;
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
	protected void createTools(JPanel palette) {
        super.createTools(palette);

        Tool  tool = new TextTool(view(), new StateFigure());
        palette.add(createToolButton(IMAGES+"TEXT", "Text Tool", tool));
        
        tool = new CreationTool(view(), new StartNode());
        palette.add(createToolButton(JHDFSMIMAGES+"STARTNODE", "Start Node Tool", tool)); 
        
        tool = new CreationTool(view(), new StateFigure());
        palette.add(createToolButton(JHDFSMIMAGES+"STATE", "Node Tool", tool));
        
		tool = new EndNodeTool(view());
		palette.add(createToolButton(JHDFSMIMAGES+"ACCEPT", "End Node Tool", tool));
		
		tool = new NodeConnectionTool(view(), new ZeroNodeConnector());
        palette.add(createToolButton(JHDFSMIMAGES+"ZEROCONN", "Node Connector", tool));

		tool = new NodeConnectionTool(view(), new OneNodeConnector());
        palette.add(createToolButton(JHDFSMIMAGES+"ONECONN", "1 Node Connector", tool));
	}
	
	
	@Override
	protected void createMenus(JMenuBar mb) {
		super.createMenus(mb);
		mb.add(createFSMMenu());
    }
	
	protected JMenu createFSMMenu() {
		CommandMenu menu = new CommandMenu("Finite State Machine");
		menu.add(new ValidateCommand("Validate", super.view()));
		menu.add(new SendInputCommand("Send 0", 0, super.view()));
		menu.add(new SendInputCommand("Send 1", 1, super.view()));
		menu.add(new ResetCommand("Reset", super.view()));
		return menu;
	}
	
	public void destroy() {
		super.destroy();
	}
}
