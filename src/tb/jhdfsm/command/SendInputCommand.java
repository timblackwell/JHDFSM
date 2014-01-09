package tb.jhdfsm.command;

import java.util.Enumeration;

import tb.jhdfsm.figure.ProcessInput;
import tb.jhdfsm.figure.StateFigure;
import CH.ifa.draw.command.Command;
import CH.ifa.draw.framework.Drawing;
import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;

public class SendInputCommand extends Command {
	
private DrawingView fView;
private int input;

	public SendInputCommand(String name, int input, DrawingView view) {
		super(name);
        fView = view;
        this.input = input;
	}
	
	@Override
	public void execute() {
		Drawing fDrawing = fView.drawing();
		Enumeration<Figure> figures = fDrawing.figures();
		
		while (figures.hasMoreElements()) {
			Figure fig = figures.nextElement();
			
			if (fig instanceof ProcessInput) {
				ProcessInput node = (ProcessInput)fig;
				
				if (node.isActive()) {
					ProcessInput nextActive = node.nextState(input);
					node.deactivate();
					nextActive.activate();
					fView.repairDamage();
					return;
				}
			}
		}
	}
}
