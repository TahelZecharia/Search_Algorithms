# Search Algorithms 

# Topographic Map Pathfinding Assignment

## Overview

This assignment involves solving the problem of finding the cheapest route on a topographic map from a starting point to an end point using various search algorithms. The map consists of different terrain types with associated movement costs, and the goal is to navigate the map while avoiding cliffs and minimizing the total cost of movement.

### Algorithms Included

The following search algorithms have been implemented for this assignment:

1. **BFS (Breadth-First Search):** A graph traversal algorithm that explores all neighboring nodes before moving deeper into the search space. BFS is complete and guarantees finding the shortest path if all step costs are the same.

2. **DFID (Depth-First Iterative Deepening):** A combination of depth-first search and iterative deepening. It starts with a depth limit and gradually increases it until the goal is found. DFID is complete in finite state spaces and optimal when step costs are identical.

3. **A-star (A-star Search):** A heuristic search algorithm that uses a heuristic function to estimate the cost from the current state to the goal state. It combines the actual cost to reach the current state and the estimated cost to reach the goal. A* is complete and optimal if the heuristic is admissible.

4. **IDA-star (Iterative-Deepening A-star):** A variant of A* that avoids the need for excessive memory usage by employing iterative deepening. It explores nodes incrementally based on the current depth limit.

5. **DFBnB (Depth-First Branch and Bound):** An optimization of depth-first search that guarantees optimality by using a best-first strategy and bounding the search space. DFBnB maintains a record of the best solution found so far and prunes branches that cannot improve it.

### Heuristic Function

In this assignment, we were tasked with designing a heuristic function that is not only efficient but also meets the criteria of admissibility and consistency. The heuristic function plays a crucial role in guiding the search algorithms, specifically A*, IDA*, and DFBnB, to efficiently explore the topographic map while ensuring that the solutions found are both optimal and reliable.

**Admissibility:** An admissible heuristic is one that never overestimates the cost of reaching the goal from the current state. In other words, the heuristic should provide a lower bound on the true cost. 

**Consistency:** A consistent heuristic, also known as a monotonic heuristic, satisfies the triangle inequality property. This means that for any state, the estimated cost to reach the goal from that state, plus the estimated cost from that state to a successor state, should be less than or equal to the estimated cost to reach the successor state. Mathematically: h(n) ≤ c(n, n') + h(n').

For a detailed explanation and proof of the consistency and admissibility of the heuristic function, please refer to the document **"Heuristic Function.pdf"** in this repository.

This document provides in-depth insights into how the heuristic function meets these critical properties, ensuring that the search algorithms operate effectively and reliably in finding the most cost-effective routes through the challenging topographic map.
