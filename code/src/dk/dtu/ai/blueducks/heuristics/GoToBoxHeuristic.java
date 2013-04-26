package dk.dtu.ai.blueducks.heuristics;

import dk.dtu.ai.blueducks.goals.GoToBoxGoal;
import dk.dtu.ai.blueducks.map.State;

public class GoToBoxHeuristic implements Heuristic<State, GoToBoxGoal> {

	private float distance;

	@Override
	public float getHeuristicValue(State state, GoToBoxGoal goal) {
		distance = Math.abs(goal.getTo().x - state.getAgentAt().x)
				+ Math.abs(goal.getTo().y - state.getAgentAt().y);
		return distance;
	}

}
