/*
 * AI GENERATION DOCUMENTATION
* ===========================
* AI Tool Used: Claude 4.6
* Generation Date: 02/25/26
*
* Original Prompt:
* "Create shape classes: Cube with property sideLength. Each class should extend Shape3D,
* implement the abstract methods from ThreeDimensionalShape, include proper constructors
* with validation, override toString() with shape-specific formatting, and add any
* shape-specific methods if needed
*
* Follow-up Prompts (if any):
* when I run the test I got this error — NaN validation not throwing exception
*
* Manual Modifications:
* - Fixed package declaration to com.csc205.project2.shapes
* - Updated setSideLength() to add Double.isNaN(sideLength) check before the <= 0 check
*   because the nanSideLengthThrows test was failing — NaN passes any comparison in Java
*   so it needs its own explicit check
*
* Formula Verification:
* - Volume formula (s^3) verified against: Wolfram MathWorld
*   https://mathworld.wolfram.com/Cube.html — confirmed with s=4, expected 64.0
* - Surface area formula (6 * s^2) verified against: omnicalculator.com cube calculator
*   confirmed with s=4, expected 96.0
* - Space diagonal formula (s * sqrt(3)) also verified against omnicalculator.com
*/

package com.csc205.project2.shapes;

/**
 * Represents a three-dimensional Cube.
 *
 * <p>Formulas used:
 * <ul>
 *   <li>Surface Area = 6 s&sup2;</li>
 *   <li>Volume = s&sup3;</li>
 * </ul>
 * </p>
 *
 * @author  CSC205
 * @version 1.0
 */
public class Cube extends Shape3D {

    // ---------------------------------------------------------------
    // Fields
    // ---------------------------------------------------------------

    /** The length of one side of the cube; must be greater than zero. */
    private double sideLength;

    // ---------------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------------

    /**
     * Constructs a Cube with the specified side length and color.
     *
     * @param sideLength the length of each side; must be &gt; 0
     * @param color      the color of the cube; must not be null or blank
     * @throws IllegalArgumentException if {@code sideLength} is not positive
     */
    public Cube(double sideLength, String color) {
        super("Cube", color);
        setSideLength(sideLength);
    }

    // ---------------------------------------------------------------
    // ThreeDimensionalShape implementations
    // ---------------------------------------------------------------

    /**
     * Calculates the surface area of the cube.
     * Formula: 6 s&sup2;
     *
     * @return the surface area as a {@code double}
     */
    @Override
    public double getSurfaceArea() {
        return 6 * sideLength * sideLength;
    }

    /**
     * Calculates the volume of the cube.
     * Formula: s&sup3;
     *
     * @return the volume as a {@code double}
     */
    @Override
    public double getVolume() {
        return Math.pow(sideLength, 3);
    }

    // ---------------------------------------------------------------
    // Shape-specific method
    // ---------------------------------------------------------------

    /**
     * Calculates the length of the space diagonal of the cube
     * (the longest diagonal connecting two opposite vertices).
     * Formula: s * &radic;3
     *
     * @return the space diagonal length
     */
    public double getSpaceDiagonal() {
        return sideLength * Math.sqrt(3);
    }

    // ---------------------------------------------------------------
    // Getter & Setter
    // ---------------------------------------------------------------

    /**
     * Returns the side length of this cube.
     *
     * @return the side length
     */
    public double getSideLength() {
        return sideLength;
    }

    /**
     * Sets the side length of this cube.
     *
     * @param sideLength the new side length; must be &gt; 0
     * @throws IllegalArgumentException if {@code sideLength} is not positive
     */
    public void setSideLength(double sideLength) {
        if (Double.isNaN(sideLength) || sideLength <= 0) {
            throw new IllegalArgumentException("Cube side length must be greater than zero. Received: " + sideLength);
        }
        this.sideLength = sideLength;
    }

    // ---------------------------------------------------------------
    // toString
    // ---------------------------------------------------------------

    /**
     * Returns a formatted string representation of this Cube.
     *
     * @return shape details including side length, space diagonal, surface area, and volume
     */
    @Override
    public String toString() {
        return super.toString() + String.format(
                "%nSide Length   : %.2f" +
                        "%nSpace Diagonal: %.2f",
                sideLength,
                getSpaceDiagonal()
        );
    }
}