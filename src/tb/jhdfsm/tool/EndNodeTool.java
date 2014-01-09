package tb.jhdfsm.tool;

import tb.jhdfsm.figure.StateFigure;
import tb.jhdfsm.figure.StartNode;
import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.tool.ActionTool;

public class EndNodeTool extends ActionTool {

	public EndNodeTool(DrawingView itsView) {
		super(itsView);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action(Figure figure) {		
		if (figure instanceof StateFigure)
		{
			((StateFigure) figure).endNode = !((StateFigure) figure).endNode;
		}
		
		if (figure instanceof StartNode)
		{
			((StartNode) figure).endNode = !((StartNode) figure).endNode;
		}
	}

}
