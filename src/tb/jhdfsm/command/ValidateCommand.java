package tb.jhdfsm.command;

import java.util.Enumeration;

import tb.jhdfsm.figure.StartNode;
import CH.ifa.draw.command.Command;
import CH.ifa.draw.framework.Drawing;
import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;

public class ValidateCommand extends Command {
	
private DrawingView fView;

	public ValidateCommand(String name, DrawingView view) {
		super(name);
        fView = view;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		Drawing fDrawing = fView.drawing();
		Enumeration<Figure> figures = fDrawing.figures();
		boolean startNodePresent = false;
		
		while (figures.hasMoreElements()) {
			if (figures.nextElement() instanceof StartNode) {
				if (startNodePresent) {
					System.out.println("Too many start nodes");
				} else {
					startNodePresent = true;
				}
			}
		}
		
		if (!startNodePresent) {
			System.out.println("Missing start node");
		}
	}

}
