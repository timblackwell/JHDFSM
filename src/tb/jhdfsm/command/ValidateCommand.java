package tb.jhdfsm.command;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tb.jhdfsm.connector.StartNodeConnector;
import tb.jhdfsm.connector.StateLineConnector;
import tb.jhdfsm.figure.StateFigure;
import tb.jhdfsm.figure.StartNode;
import tb.jhdfsm.figure.UserIOFigure;
import tb.jhdfsm.figure.UserIOFigure.ValidationError;
import CH.ifa.draw.command.Command;
import CH.ifa.draw.figure.TextFigure;
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
		
		List<StartNode> startNodes = new ArrayList<StartNode>();
		List<StartNodeConnector> startNodeConnectors = new ArrayList<StartNodeConnector>();

		List<StateFigure> nodes = new ArrayList<StateFigure>();
		List<StateLineConnector> nodeConnectors = new ArrayList<StateLineConnector>();
		
		Figure figure;
		UserIOFigure errorsFigure = null;
		
		while (figures.hasMoreElements()) {
			figure = figures.nextElement();
			if (figure instanceof StartNode) {
				startNodes.add((StartNode)figure);
			} else if (figure instanceof StateFigure) {
				nodes.add((StateFigure)figure);
			} else if (figure instanceof StateLineConnector) {
				nodeConnectors.add((StateLineConnector)figure);
			} else if (figure instanceof StartNodeConnector) {
				startNodeConnectors.add((StartNodeConnector)figure);
			} else if (figure instanceof UserIOFigure) {
				errorsFigure = (UserIOFigure)figure;
			}
		}
		
		if (errorsFigure == null) {
			errorsFigure = new UserIOFigure();
			fView.add(errorsFigure);
		}
		
		errorsFigure.clearErrors();
				
		switch (startNodes.size()) {
			case 0 : {
				errorsFigure.addError(ValidationError.MissingStartNode);
				break;
			}
			case 1 : {
					switch (startNodeConnectors.size()) {
						case 0 : {
							if (nodes.size() > 0) {
								errorsFigure.addError(ValidationError.MissingStartNodeConnection);
							}
							break;
						}
						case 1 : {
							break;
							}
						default : {
							errorsFigure.addError(ValidationError.ExtraStartNodeConnection);
							break;
						}
					}
				break;
			}
			default : {
				errorsFigure.addError(ValidationError.ExtraStartNode);
				break;
			}
		}
		
		Map<StateFigure,ArrayList<StateLineConnector>> nodeConnectorHash = new HashMap<StateFigure,ArrayList<StateLineConnector>>();
		
		for (StateFigure node : nodes) {
			nodeConnectorHash.put(node, new ArrayList<StateLineConnector>());
			for (StateLineConnector nodeConnector : nodeConnectors) {
				if (nodeConnector.startFigure().equals(node)){
					nodeConnectorHash.get(node).add(nodeConnector);
				}
			}
		}
		
		for (StateFigure node : nodes) {
			switch (nodeConnectorHash.get(node).size()) {
				case 0 : {
					errorsFigure.addError(ValidationError.MissingNodeConnection);
					break;
				}
				case 1 : {
					errorsFigure.addError(ValidationError.MissingNodeConnection);
					break;
				}
				case 2 : {
					break;
				}
				default: {
					errorsFigure.addError(ValidationError.ExtraNodeConnection);
					break;
				}
			}
		}
		
		for (StateLineConnector nodeConnector : nodeConnectors) {
			TextFigure text = (TextFigure)nodeConnector.getTextFigure();
			if (!text.getText().equals("0") && !text.getText().equals("1")) {
				errorsFigure.addError(ValidationError.LableNotInAlphabet);
			}
		}
		
		errorsFigure.updateText();
		fView.checkDamage();
//		System.out.println(errors);
	}

}
