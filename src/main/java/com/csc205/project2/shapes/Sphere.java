
 /*
* AI GENERATION DOCUMENTATION
* ===========================
* AI Tool Used: Claude 4.6
* Generation Date: 02/25/26
*
* Original Prompt:
* Create shape classes: Sphere with property radius. Each class should extend Shape3D,
* implement the abstract methods from ThreeDimensionalShape, include proper constructors
* with validation, override toString() with shape-specific formatting, and add any
* shape-specific methods if needed
*
* Follow-up Prompts (if any):
* when I run the test I got this error — NaN validation not throwing exception
*
* Manual Modifications:
* - Fixed package declaration to com.csc205.project2.shapes
* - Updated setRadius() validation from (radius <= 0) to (Double.isNaN(radius) || radius <= 0)
*   because Java returns false for NaN <= 0, which let NaN slip through without throwing —
*   caught this when the nanRadiusThrows JUnit test failed
*
* Formula Verification:
* - Volume formula (4/3 * pi * r^3) verified against: Wolfram MathWorld
*   https://mathworld.wolfram.com/Sphere.html — confirmed with r=5, expected 523.5988
* - Surface area formula (4 * pi * r^2) verified against: calculator.net 3D geometry calculator
*   confirmed with r=5, expected 314.1593
*/


package com.csc205.project2.shapes;

/**
 * Represents a three-dimensional Sphere.
 *
 * <p>Formulas used:
 * <ul>
 *   <li>Surface Area = 4 &pi; r&sup2;</li>
 *   <li>Volume = (4/3) &pi; r&sup3;</li>
 * </ul>
 * </p>
 *
 * @author  CSC205
 * @version 1.0
 */
public class Sphere extends Shape3D {

    // ---------------------------------------------------------------
    // Fields
    // ---------------------------------------------------------------

    /** The radius of the sphere; must be greater than zero. */
    private double radius;

    // ---------------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------------

    /**
     * Constructs a Sphere with the specified radius and color.
     *
     * @param radius the radius of the sphere; must be &gt; 0
     * @param color  the color of the sphere; must not be null or blank
     * @throws IllegalArgumentException if {@code radius} is not positive
     */
    public Sphere(double radius, String color) {
        super("Sphere", color);
        setRadius(radius);
    }

    // ---------------------------------------------------------------
    // ThreeDimensionalShape implementations
    // ---------------------------------------------------------------

    /**
     * Calculates the surface area of the sphere.
     * Formula: 4 &pi; r&sup2;
     *
     * @return the surface area as a {@code double}
     */
    @Override
    public double getSurfaceArea() {
        return 4 * Math.PI * radius * radius;
    }

    /**
     * Calculates the volume of the sphere.
     * Formula: (4/3) &pi; r&sup3;
     *
     * @return the volume as a {@code double}
     */
    @Override
    public double getVolume() {
        return (4.0 / 3.0) * Math.PI * Math.pow(radius, 3);
    }

    // ---------------------------------------------------------------
    // Shape-specific method
    // ---------------------------------------------------------------

    /**
     * Calculates the diameter of the sphere.
     *
     * @return diameter (2 * radius)
     */
    public double getDiameter() {
        return 2 * radius;
    }

    // ---------------------------------------------------------------
    // Getter & Setter
    // ---------------------------------------------------------------

    /**
     * Returns the radius of this sphere.
     *
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Sets the radius of this sphere.
     *
     * @param radius the new radius; must be &gt; 0
     * @throws IllegalArgumentException if {@code radius} is not positive
     */
    public void setRadius(double radius) {
        if (Double.isNaN(radius) || radius <= 0) {
            throw new IllegalArgumentException("Sphere radius must be greater than zero. Received: " + radius);
        }
        this.radius = radius;
    }

    // ---------------------------------------------------------------
    // toString
    // ---------------------------------------------------------------

    /**
     * Returns a formatted string representation of this Sphere.
     *
     * @return shape details including radius, diameter, surface area, and volume
     */
    @Override
    public String toString() {
        return super.toString() + String.format(
                "%nRadius       : %.2f" +
                        "%nDiameter     : %.2f",
                radius,
                getDiameter()
        );
    }
}