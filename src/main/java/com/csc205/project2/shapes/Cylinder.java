/*
AI GENERATION DOCUMENTATION
* ===========================
* AI Tool Used: Claude 4.6
* Generation Date: 02/25/26
*
* Original Prompt:
* Create shape classes: Cylinder with properties radius and height. Each class should
* extend Shape3D, implement the abstract methods from ThreeDimensionalShape, include
* proper constructors with validation, override toString() with shape-specific formatting,
* and add any shape-specific methods if needed
*
* Follow-up Prompts (if any):
* when I run the test I got this error — NaN validation not throwing exception

* Manual Modifications:
* - Fixed package declaration to com.csc205.project2.shapes
* - Updated both setRadius() and setHeight() to include Double.isNaN() guard —
*   same NaN issue as the other shape classes, NaN passes the <= 0 check silently
* - Confirmed that getLateralSurfaceArea() was a good addition since it's useful
*   for real-world problems (like painting just the side of a cylinder, not the caps)
*
* Formula Verification:
* - Volume formula (pi * r^2 * h) verified against: Wolfram MathWorld
*   https://mathworld.wolfram.com/Cylinder.html — confirmed with r=3, h=7, expected 197.9203
* - Total surface area (2*pi*r^2 + 2*pi*r*h) verified against: calculator.net
*   confirmed with r=3, h=7, expected 188.4956
* - Cross-checked: total SA = lateral SA + 2 base circles, which matched manually
*/
package com.csc205.project2.shapes;

/**
 * Represents a three-dimensional Cylinder.
 *
 * <p>Formulas used:
 * <ul>
 *   <li>Surface Area = 2 &pi; r&sup2; + 2 &pi; r h</li>
 *   <li>Volume = &pi; r&sup2; h</li>
 * </ul>
 * </p>
 *
 * @author  CSC205
 * @version 1.0
 */
public class Cylinder extends Shape3D {

    // ---------------------------------------------------------------
    // Fields
    // ---------------------------------------------------------------

    /** The radius of the circular base; must be greater than zero. */
    private double radius;

    /** The height of the cylinder; must be greater than zero. */
    private double height;

    // ---------------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------------

    /**
     * Constructs a Cylinder with the specified radius, height, and color.
     *
     * @param radius the radius of the base; must be &gt; 0
     * @param height the height of the cylinder; must be &gt; 0
     * @param color  the color of the cylinder; must not be null or blank
     * @throws IllegalArgumentException if {@code radius} or {@code height} is not positive
     */
    public Cylinder(double radius, double height, String color) {
        super("Cylinder", color);
        setRadius(radius);
        setHeight(height);
    }

    // ---------------------------------------------------------------
    // ThreeDimensionalShape implementations
    // ---------------------------------------------------------------

    /**
     * Calculates the total surface area of the cylinder (both caps + lateral face).
     * Formula: 2 &pi; r&sup2; + 2 &pi; r h
     *
     * @return the surface area as a {@code double}
     */
    @Override
    public double getSurfaceArea() {
        return (2 * Math.PI * radius * radius) + (2 * Math.PI * radius * height);
    }

    /**
     * Calculates the volume of the cylinder.
     * Formula: &pi; r&sup2; h
     *
     * @return the volume as a {@code double}
     */
    @Override
    public double getVolume() {
        return Math.PI * radius * radius * height;
    }

    // ---------------------------------------------------------------
    // Shape-specific method
    // ---------------------------------------------------------------

    /**
     * Calculates the lateral (side) surface area only, excluding the two circular caps.
     * Formula: 2 &pi; r h
     *
     * @return the lateral surface area as a {@code double}
     */
    public double getLateralSurfaceArea() {
        return 2 * Math.PI * radius * height;
    }

    // ---------------------------------------------------------------
    // Getters & Setters
    // ---------------------------------------------------------------

    /**
     * Returns the radius of this cylinder.
     *
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Sets the radius of this cylinder.
     *
     * @param radius the new radius; must be &gt; 0
     * @throws IllegalArgumentException if {@code radius} is not positive
     */
    public void setRadius(double radius) {
        if (Double.isNaN(radius) || radius <= 0) {
            throw new IllegalArgumentException("Cylinder radius must be greater than zero. Received: " + radius);
        }
        this.radius = radius;
    }

    /**
     * Returns the height of this cylinder.
     *
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the height of this cylinder.
     *
     * @param height the new height; must be &gt; 0
     * @throws IllegalArgumentException if {@code height} is not positive
     */
    public void setHeight(double height) {
        if (Double.isNaN(height) || height <= 0) {
            throw new IllegalArgumentException("Cylinder height must be greater than zero. Received: " + height);
        }
        this.height = height;
    }

    // ---------------------------------------------------------------
    // toString
    // ---------------------------------------------------------------

    /**
     * Returns a formatted string representation of this Cylinder.
     *
     * @return shape details including radius, height, lateral surface area, total surface area, and volume
     */
    @Override
    public String toString() {
        return super.toString() + String.format(
                "%nRadius        : %.2f" +
                        "%nHeight        : %.2f" +
                        "%nLateral SA    : %.2f",
                radius,
                height,
                getLateralSurfaceArea()
        );
    }
}