package tb.jhdfsm.command;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tb.jhdfsm.connector.NodeConnector;
import tb.jhdfsm.connector.StartNodeConnector;
import tb.jhdfsm.figure.NodeFigure;
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
		
		List<StartNode> startNodes = new ArrayList<StartNode>();
		List<StartNodeConnector> startNodeConnectors = new ArrayList<StartNodeConnector>();

		List<NodeFigure> nodes = new ArrayList<NodeFigure>();
		List<NodeConnector> nodeConnectors = new ArrayList<NodeConnector>();
		
		Figure figure;
		
		while (figures.hasMoreElements()) {
			figure = figures.nextElement();
			if (figure instanceof StartNode) {
				startNodes.add((StartNode)figure);
			} else if (figure instanceof NodeFigure) {
				nodes.add((NodeFigure)figure);
			} else if (figure instanceof NodeConnector) {
				nodeConnectors.add((NodeConnector)figure);
			} else if (figure instanceof StartNodeConnector) {
				startNodeConnectors.add((StartNodeConnector)figure);
			}
		}
		
		String errors = "";
		
		switch (startNodes.size()) {
			case 0 : {
				errors += "Missing start node";
				break;
			}
			case 1 : {
					switch (startNodeConnectors.size()) {
						case 0 : {
							errors += "Start node must be connected one node";
							break;
						}
						case 1 : {
							break;
							}
						default : {
							errors += "Start node connected to too many nodes.";
							break;
						}
					}
				break;
			}
			default : {
				errors += "Too many start nodes";
				break;
			}
		}
		
		Map<NodeFigure,ArrayList<NodeConnector>> nodeConnectorHash = new HashMap<NodeFigure,ArrayList<NodeConnector>>();
		
		for (NodeFigure node : nodes) {
			nodeConnectorHash.put(node, new ArrayList<NodeConnector>());
			for (NodeConnector nodeConnector : nodeConnectors) {
				if (nodeConnector.startFigure().equals(node)){
					nodeConnectorHash.get(node).add(nodeConnector);
				}
			}
		}
		
		for (NodeFigure node : nodes) {
			switch (nodeConnectorHash.get(node).size()) {
				case 0 : {
					errors += "Missing node connections";
					break;
				}
				case 1 : {
					errors += "Missing node connections";
					break;
				}
				case 2 : {
					break;
				}
				default: {
					errors += "Too many node connections";
					break;
				}
			}
		}
		
		if (errors.isEmpty()) {
			errors += "Valid";
		}
		
		System.out.println(errors);
	}

}
