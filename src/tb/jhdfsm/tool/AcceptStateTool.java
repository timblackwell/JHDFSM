package tb.jhdfsm.tool;

import tb.jhdfsm.figure.AcceptingStateFigure;
import tb.jhdfsm.figure.StartNode;
import tb.jhdfsm.figure.StateFigure;
import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.tool.ActionTool;

public class AcceptStateTool extends ActionTool {

	public AcceptStateTool(DrawingView itsView) {
		super(itsView);
	}

	@Override
	public void action(Figure figure) {		
		if (figure instanceof AcceptingStateFigure) {
			drawing().replace(figure, ((AcceptingStateFigure)figure).peelDecoration());
		}
		if (figure instanceof StateFigure)
		{
			drawing().replace(figure, new AcceptingStateFigure((StateFigure)figure));
//			((StateFigure) figure).endNode = !((StateFigure) figure).endNode;
		}
		
//		if (figure instanceof StartNode)
//		{
//			((StartNode) figure).endNode = !((StartNode) figure).endNode;
//		}
	}

}
