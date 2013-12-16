package tb.jhdfsm.tool;

import tb.jhdfsm.figure.NodeFigure;
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
		if (figure instanceof NodeFigure)
		{
			((NodeFigure) figure).endNode = !((NodeFigure) figure).endNode;
		}
	}

}
