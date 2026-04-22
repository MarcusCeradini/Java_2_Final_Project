# Tile turner
### Marcus Ceradini

## Features
* Custom color selection for visited cells
* Adjustable simulation speed (slider)
* Live step counter
* Toroidal grid (wrap-around edges)
  
## Langton’s Ant rules:

If the ant is on a black square:
Turn right
Flip the color of the square
Move forward
If the ant is on a white square:
Turn left
Flip the color of the square
Move forward

## System Requirments:
* Must have Java 8 or better installed
* A way to run Java (Java visualizer)

## How to run:
* Open in Java visualizer such as VS code or Intellij
* Upload the file or copy paste it in
* Click the run button or enter "javac *.java" then "java AntController" in the terminal
