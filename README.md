# Mars Rover

### Problem statement

You’re part of the team that explores Mars by sending remotely controlled vehicles to the surface of the planet. Develop an API that translates the commands sent from earth to instructions that are understood by the rover.

#### Requirements

* You are given the initial starting point (x,y) of a rover and the direction (N,S,E,W) it is facing.
* The rover receives a character array of commands.
* Implement commands that move the rover forward/backward (f,b).
* Implement commands that turn the rover left/right (l,r).
* Implement wrapping from one edge of the grid to another. (planets are spheres after all)
* Implement obstacle detection before each move to a new square. If a given sequence of commands encounters an obstacle, the rover moves up to the last possible point, aborts the sequence and reports the obstacle.

#### Assumptions

* The wrapping from edge to edge follows the _"pacman effect"_ (east <-> west and north <-> south). It is not a realistic sphere. This will be improved in following releases.

#### Instructions

* This is intended to be a JAVA library. It could be built and included as a dependency in a project using gradle
* To test how the library works, there is a simple command line application under the test folder: [WorldSpatialAgency.java](https://github.com/zanifabio/mars-rover/tree/master/src/test/java/it/zanifabio/WorldSpatialAgency.java)

### Documentation

[Javadoc](https://zanifabio.github.io/mars-rover/javadoc)