# Search Visualizer (Work in Progress)

**Search Visualizer** is a simple application made in Java that visualizes how a searching algorithm is performed. This project utilizes the Java Swing utilities to create an intuitive GUI.

# Usage

When running the main class, **Application.java**, it will create a JFrame that consists of **two** JPanels. The left panel, **GridPanel.java**, is used to customize the starting, goal, and obstacles for the searching algorithm. The right panel, **ActionPanel.java**, is used to begin or reset the GridPanel.

# Node Legend (Not yet implemented in GUI)

Green: The starting node<br>
Red: The goal node<br>
Light Gray: Nodes that can be traversed by the search algorithm.<br>
Dark Gray: Nodes that **cannot** be traversed by the search algorithm.

## Post-Search Legend

Gray: Nodes that were visited by the search algorithm.<br>
Cyan: The final path determined by the search algorithm.
