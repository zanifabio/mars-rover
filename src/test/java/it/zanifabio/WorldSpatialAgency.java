package it.zanifabio;

import java.util.Scanner;

import it.zanifabio.rover.Coordinate;
import it.zanifabio.rover.Direction;
import it.zanifabio.rover.Planet;
import it.zanifabio.rover.Rover;
import it.zanifabio.rover.exceptions.ObstacleEncounteredException;

public class WorldSpatialAgency {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean validInput;

        int width = 0;
        int height = 0;
        Planet planet;

        Rover rover = null;

        System.out.println("Welcome at the World Spatial Agency!");
        System.out.println("Let's input a representation of the planet...");
        System.out.println("First, let's input the limit of it's coordinates:");

        //input width
        do {
            System.out.println("What's the map's width?");
            try {
                width = Integer.parseInt(input.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.err.println("Sorry, it must be an integer value...");
                validInput = false;
            }
        } while (!validInput);

        // input height
        do {
            System.out.println("What's the map's height?");
            try {
                height = Integer.parseInt(input.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.err.println("Sorry, it must be an integer value...");
                validInput = false;
            }
        } while (!validInput);

        try {
            planet = Planet.newInstance(width, height);
        } catch (Exception e) {
            System.err.println("Error while generating planet: " + e.getMessage());
            return;
        }

        boolean addObstacle = false;
        // input obstacles
        do {
            System.out.println("Do you want to add an obstacle? [y/n]");
            try {
                String response = input.nextLine();
                switch (response.toLowerCase()) {
                    case "y":
                        addObstacle = true;
                        try {
                            System.out.println("Insert x and y  (starting from top) coordinate of the obstacle separated by a comma:");
                            String[] obstacle = input.nextLine().split(",");
                            int x = Integer.parseInt(obstacle[0].trim());
                            int y = Integer.parseInt(obstacle[1].trim());
                            planet.placeObstacle(x, y);
                            System.out.printf("Obstacle added at (%d, %d)%n", x, y);
                        } catch (Planet.PlanetPhysicsException e) {
                            throw e;
                        } catch (Exception e) {
                            throw new IllegalArgumentException("Coordinate x and y must be integers separated by a comma");
                        }
                        break;
                    case "n":
                        addObstacle = false;
                        break;
                    default:
                        throw new IllegalArgumentException("Response must be either 'y' or 'n'");
                }
                validInput = true;
            } catch (Exception e) {
                System.err.println(e.getMessage());
                validInput = false;
            }
        } while (!validInput || addObstacle);

        do {
            System.out.println("Do you want to draw the planet map (only for width <= 20)? [y/n]");
            try {
                String response = input.nextLine();
                switch (response.toLowerCase()) {
                    case "y":
                        if (planet.getWidth() > 20) {
                            System.err.println("Sorry, your planet is too big to draw");
                        } else {
                            System.out.println(planet);
                        }
                        break;
                    case "n":
                        break;
                    default:
                        throw new IllegalArgumentException("Response must be either 'y' or 'n'");
                }
                validInput = true;
            } catch (Exception e) {
                System.err.println(e.getMessage());
                validInput = false;
            }
        } while (!validInput);

        System.out.println("\n\nOk, now let's land our rover. Where should it land?\n");

        //input initial rover coordinates and direction
        do {
            System.out.println("Insert x, y, and cardinal direction (N,E,S,W) of the rover separated by a comma (e.g. '1,1,N'): ");
            try {
                String[] obstacle = input.nextLine().split(",");
                int x = Integer.parseInt(obstacle[0].trim());
                int y = Integer.parseInt(obstacle[1].trim());
                Coordinate initialPosition = Coordinate.of(x, y);
                Direction initialFacingDirection = Direction.get(obstacle[2].trim().toUpperCase());
                rover = Rover.newInstance(planet, initialPosition, initialFacingDirection);
                validInput = true;
            } catch (Planet.PlanetPhysicsException e) {
                System.err.println(e.getMessage());
                validInput = false;
            } catch (Exception e) {
                System.err.println("Coordinate x and y must be integers separated by a comma, direction can be one of [N,E,S,W].");
                validInput = false;
            }
        } while (!validInput);

        drawRoverSituation(input, rover);

        boolean inputCommandSequence = false;
        // input command sequence
        do {
            System.out.println("Do you want to input a command sequence for the rover? [y/n]");
            try {
                String response = input.nextLine();
                switch (response.toLowerCase()) {
                    case "y":
                        inputCommandSequence = true;
                        System.out.println("Input a string of consecutive characters (f=forward, b=backwards, l=turnLeft, r=turnRight");
                        String sequence = input.nextLine();
                        rover.move(sequence);
                        System.out.printf("Rover moved correctly. Now it's at (%d,%d), facing %s%n",
                                rover.getCurrentPosition().getX(),
                                rover.getCurrentPosition().getY(),
                                rover.getFacingDirection());
                        drawRoverSituation(input, rover);
                        break;
                    case "n":
                        inputCommandSequence = false;
                        break;
                    default:
                        throw new IllegalArgumentException("Response must be either 'y' or 'n'");
                }
                validInput = true;
            } catch (IllegalStateException e) {
                System.err.println(e.getMessage());
                validInput = false;
                drawRoverSituation(input, rover);
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
                validInput = false;
            }
        } while (!validInput || inputCommandSequence);

        System.out.println("It was a pleasure for us to wrok with you. See you soon.");

    }

    private static void drawRoverSituation(Scanner input, Rover rover) {
        boolean validInput;
        do {
            System.out.println("Do you want to draw the current rover situation (only for planet width <= 20)? [y/n]");
            try {
                String response = input.nextLine();
                switch (response.toLowerCase()) {
                    case "y":
                        if (rover.getPlanet().getWidth() > 20) {
                            System.err.println("Sorry, your planet is too big to draw");
                        } else {
                            System.out.println(rover);
                        }
                        break;
                    case "n":
                        break;
                    default:
                        throw new IllegalArgumentException("Response must be either 'y' or 'n'");
                }
                validInput = true;
            } catch (Exception e) {
                System.err.println(e.getMessage());
                validInput = false;
            }
        } while (!validInput);
    }

}
