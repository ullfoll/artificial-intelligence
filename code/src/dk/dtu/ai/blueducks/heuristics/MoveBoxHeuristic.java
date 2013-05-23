package dk.dtu.ai.blueducks.heuristics;

import java.util.logging.Level;
import java.util.logging.Logger;

import dk.dtu.ai.blueducks.actions.PullAction;
import dk.dtu.ai.blueducks.actions.PushAction;
import dk.dtu.ai.blueducks.goals.MoveBoxGoal;
import dk.dtu.ai.blueducks.map.Cell;
import dk.dtu.ai.blueducks.map.LevelMap;
import dk.dtu.ai.blueducks.map.State;

public class MoveBoxHeuristic implements Heuristic<State, MoveBoxGoal> {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(LevelMap.class.getSimpleName());
	
	@Override
	public float getHeuristicValue(State state, MoveBoxGoal goal, State prevState) {
		float h;
		
		// betweenness in [0, 1]
		float agentCellBetweenness = LevelMap.getInstance().getBetweenessCentrality().get(state.getAgentCell()).floatValue();
		float boxCellBetweenness = LevelMap.getInstance().getBetweenessCentrality().get(state.getCellForBox(goal.getWhat())).floatValue();
		float goalCellBetweenness = LevelMap.getInstance().getBetweenessCentrality().get(goal.getTo()).floatValue();
		
		// distance on a map
		float distance = LevelMap.getInstance().getDistance(state.getAgentCell(), goal.getTo());
		
		h = agentCellBetweenness + boxCellBetweenness + goalCellBetweenness + distance;
		
		// penalize undoing goals
		if (prevState != null && state.getEdgeFromPrevNode() instanceof PullAction) {
			PullAction pullAction = (PullAction) state.getEdgeFromPrevNode();
			Cell boxCell = prevState.getCellForBox(pullAction.getBox());
			if (LevelMap.getInstance().getLockedCells().contains(boxCell)) {
				h += Heuristic.PENALTY_UNDO_GOAL;
			}
		} else if (prevState != null && state.getEdgeFromPrevNode() instanceof PushAction) {
			PushAction pushAction = (PushAction) state.getEdgeFromPrevNode();
			Cell boxCell = prevState.getCellForBox(pushAction.getBox());
			if (LevelMap.getInstance().getLockedCells().contains(boxCell)) {
				h += Heuristic.PENALTY_UNDO_GOAL;
			}
		}
		
		return h;
	}
	
//	@Override
//	public float getHeuristicValue(State state, MoveBoxGoal goal, State prevState) {
//		float h;
//		int penaltyForUndoingGoals = 100000;
//			
//		//betweenness in [0,1]
//		float betweenness = LevelMap.getInstance().getBetweenessCentrality().get(state.getAgentCell()).floatValue();
//		//actual distance on map
//		float distance = LevelMap.getInstance().getDistance(state.getAgentCell(), goal.getTo());
//		//betweenness in [0,1]
//		float goalCellBetweenness = LevelMap.getInstance().getBetweenessCentrality().get(goal.getTo()).floatValue();
//		
//		
//		h = betweenness + distance + goalCellBetweenness;
//		
//		
//		if (prevState != null && state.getEdgeFromPrevNode() instanceof PullAction) {
//			PullAction pullAction = (PullAction) state.getEdgeFromPrevNode();
//			Cell cell = prevState.getCellForBox(pullAction.getBox());
//			//TODO: Why is this different from the one one GoToBox?
//			if (LevelMap.getInstance().getLockedCells().contains(cell)) {
//				h += penaltyForUndoingGoals;
//			}
//		}
		
//		if (prevState != null) {
//			if (state.getBoxesInGoalCells().size() < prevState.getBoxesInGoalCells().size()) {
//				h += penaltyForUndoingGoals;
//			}
//		}
//		return h;
//		
//	}

}
