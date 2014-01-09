package tb.jhdfsm.figure;

public interface ProcessInput {
	
    public ProcessInput nextState(int input);
	
	public void activate();
	
	public void deactivate();

	public void reset();

	public boolean isActive();
	
	public void setStartNode(boolean startNode);

}
