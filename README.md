## Search Visualizer (Work in Progress)

**Search Visualizer** is a simple application made in Java that visualizes how a searching algorithm is performed. This project utilizes the Java Swing utilities to create an intuitive GUI.<br><br>

### Current Features:
<ul>
<li>Performs A* search based on user defined grid.</li>
<li>Delays the search to allow user to view current progress.</li>
</ul>

### Planned Features:
<ul>
<li>Implement more search algorithms.</li>
<li>Allow user-defined searching speed.</li>
<li>Step searching; allow the user to perform the search one iteration at a time.</li>
<li>Generate mazes.</li>
<li>User-defined grid and node size.</li>
<li>Allow user to define a path and compare it to the final path discovered by the algorithm.</li>
</ul><br><br>

## Usage

When running the main class, **Application.java**, it will create a JFrame that consists of **two** JPanels. The left panel, **GridPanel.java**, is used to customize the starting, goal, and obstacles for the searching algorithm. The right panel, **ActionPanel.java**, is used to begin or reset the GridPanel.<br><br>

### GridPanel Action List
<table>
	<tr>
	<td>Left-Click</td>
	<td>Places or removes start/goal node.</td>
	</tr>
	<tr>
	<td>Right-Click</td>
	<td>Places or removes blocked (non-traversable) nodes.</td>
	</tr>
</table><br>

### ActionPanel Buttons
<table>
	<tr>
	<td>Start</td>
	<td>Begins the search. <i>The start AND goal node must be placed prior to starting.</i></td>
	</tr>
	<tr>
	<td>Reset</td>
	<td>Resets the grid to its initial state.</td>
	</tr>
</table><br><br>

## Node Legend (Not yet implemented in GUI)

### Pre-search Colors
<table>
	<tr>
	<td>Green</td>
	<td>The <b>start node</b>; where the search algorithm will begin.</td>
	</tr>
	<tr>
	<td>Red</td>
	<td>The <b>goal/target</b> node; where the search algorithm will end.</td>
	</tr>
	<tr>
	<td>Light Gray</td>
	<td>Nodes that can be traversed by the search algorithm. <br><i>All nodes are initialized to this color/type.</i></td>
	</tr>
	<tr>
	<td>Dark Gray</td>
	<td>Nodes that **cannot** be traversed by the search algorithm.</td>
	</tr>
</table><br>

### Post-search Colors
<table>
	<tr>
	<td>Gray</td>
	<td>Nodes that were visited by the search algorithm.</td>
	</tr>
	<tr>
	<td>Red</td>
	<td>The final path determined by the search algorithm.</td>
	</tr>
</table><br><br>

## References
### Ben Johnson: Learn Code by Gaming <br>
*Note:* Since I was new to creating user interfaces with Java, I watched a tutorial by <b>Ben Johnson</b> that helped me get started with creating a simple GUI for my application.<br>
&emsp;&emsp;Referenced video: https://www.youtube.com/watch?v=PJLLDpaLjds
&emsp;&emsp;Github link: https://github.com/learncodebygaming <br><br>

### Daniel McCluskey<br>
*Note:* While I have my own implementation of the A* search algorithm on github, I felt that it was too inefficient and verbose; which ultimately made it difficult to recreate for my application. I took the liberty of watching a tutorial by <b>Daniel McCluskey</b>, who provided a much more concise script for performing the A* search algorithm.<br>
&emsp;&emsp;Referenced video: https://www.youtube.com/watch?v=AKKpPmxx07w
&emsp;&emsp;Github link: https://github.com/danielmccluskey <br><br>

### Various stackoverflow links:
*Note:* I forgot to save the links that helped me create this application. Many various users on
https://stackoverflow.com/ gave me insight on how to manipulate JFrame/Panel objects and how to allow communication between different classes in Java.
