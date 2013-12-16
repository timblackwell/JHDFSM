package tb.jhdsfm.application;

import javax.swing.JMenuBar;
import javax.swing.JPanel;

import tb.jhdfsm.figure.CircleFigure;
import CH.ifa.draw.application.DrawApplication;
import CH.ifa.draw.framework.Tool;
import CH.ifa.draw.tool.CreationTool;

public class FSMApplication extends DrawApplication {
	
	private static final long serialVersionUID = -7286367658180495935L;

	public static void main(String[] args) {
		FSMApplication orrery = new FSMApplication();
		orrery.open();
    }

	FSMApplication() {
        super("Finite state machine");
    }
	
	protected void createMenus(JMenuBar mb) {
		super.createMenus(mb);
	}
	
	@Override
	protected void createTools(JPanel palette) {
        super.createTools(palette);

        Tool tool;
        tool = new CreationTool(view(), new CircleFigure());
		palette.add(createToolButton(IMAGES + "ELLIPSE", "Node", tool));
	}
	
	public void destroy() {
		super.destroy();
	}
}