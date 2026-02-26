/*
* AI GENERATION DOCUMENTATION
* ===========================
* AI Tool Used: Claude 4.6
* Generation Date: 02/25/26
*
* Original Prompt:
* Create shape classes: Pyramid . Each class should extend Shape3D,
* implement the abstract methods from ThreeDimensionalShape, include proper constructors
* with validation, override toString() with shape-specific formatting, and add any
* shape-specific methods if needed
*
* Follow-up Prompts (if any):
*when I run the test I got this error — NaN validation not throwing exception
*
* Manual Modifications:
* - Fixed package declaration to com.csc205.project2.shapes
* - Updated setBaseLength() and setHeight() to include Double.isNaN() checks
* - I noticed the field was originally named 'side' and renamed it to 'baseLength'
*   to make it clearer it only refers to the square base edge, not a face edge
* - Verified that getSurfaceArea() correctly calls getSlantHeight() internally
*   rather than re-calculating the slant height formula inline — kept that structure
*   because it avoids duplicating the formula
*
* Formula Verification:
* - Slant height formula (sqrt(h^2 + (s/2)^2)) verified against:
*   omnicalculator.com pyramid calculator — confirmed with base=5, h=8, expected 8.3815
* - Surface area (s^2 + 2*s*slantHeight) verified against same calculator, expected 108.8148
* - Volume formula (1/3 * s^2 * h) verified against: Wolfram MathWorld
*   https://mathworld.wolfram.com/SquarePyramid.html — confirmed with base=3, h=4, expected 12.0
*/
package com.csc205.project2.shapes;

/**
 * Represents a three-dimensional square-base Pyramid.
 *
 * <p>The pyramid has a square base with equal side lengths and a vertical height
 * measured from the center of the base to the apex.</p>
 *
 * <p>Formulas used:
 * <ul>
 *   <li>Slant Height  = &radic;(h&sup2; + (s/2)&sup2;)</li>
 *   <li>Surface Area  = s&sup2; + 2 * s * slantHeight</li>
 *   <li>Volume        = (1/3) * s&sup2; * h</li>
 * </ul>
 * </p>
 *
 * @author  CSC205
 * @version 1.0
 */
public class Pyramid extends Shape3D {

    // ---------------------------------------------------------------
    // Fields
    // ---------------------------------------------------------------

    /** The side length of the square base; must be greater than zero. */
    private double baseLength;

    /** The vertical height from base center to apex; must be greater than zero. */
    private double height;

    // ---------------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------------

    /**
     * Constructs a Pyramid with the specified base length, height, and color.
     *
     * @param baseLength the side length of the square base; must be &gt; 0
     * @param height     the vertical height of the pyramid; must be &gt; 0
     * @param color      the color of the pyramid; must not be null or blank
     * @throws IllegalArgumentException if {@code baseLength} or {@code height} is not positive
     */
    public Pyramid(double baseLength, double height, String color) {
        super("Pyramid", color);
        setBaseLength(baseLength);
        setHeight(height);
    }

    // ---------------------------------------------------------------
    // ThreeDimensionalShape implementations
    // ---------------------------------------------------------------

    /**
     * Calculates the total surface area of the pyramid (base + 4 triangular faces).
     * Formula: s&sup2; + 2 * s * slantHeight
     *
     * @return the surface area as a {@code double}
     */
    @Override
    public double getSurfaceArea() {
        return (baseLength * baseLength) + (2 * baseLength * getSlantHeight());
    }

    /**
     * Calculates the volume of the pyramid.
     * Formula: (1/3) * s&sup2; * h
     *
     * @return the volume as a {@code double}
     */
    @Override
    public double getVolume() {
        return (1.0 / 3.0) * (baseLength * baseLength) * height;
    }

    // ---------------------------------------------------------------
    // Shape-specific method
    // ---------------------------------------------------------------

    /**
     * Calculates the slant height of the pyramid — the distance from the midpoint
     * of a base edge up to the apex along a triangular face.
     * Formula: &radic;(h&sup2; + (s/2)&sup2;)
     *
     * @return the slant height as a {@code double}
     */
    public double getSlantHeight() {
        return Math.sqrt((height * height) + Math.pow(baseLength / 2.0, 2));
    }

    // ---------------------------------------------------------------
    // Getters & Setters
    // ---------------------------------------------------------------

    /**
     * Returns the base side length of this pyramid.
     *
     * @return the base length
     */
    public double getBaseLength() {
        return baseLength;
    }

    /**
     * Sets the base side length of this pyramid.
     *
     * @param baseLength must be &gt; 0
     * @throws IllegalArgumentException if {@code baseLength} is not positive
     */
    public void setBaseLength(double baseLength) {
        if (Double.isNaN(baseLength) || baseLength <= 0) {
            throw new IllegalArgumentException("Pyramid base length must be greater than zero. Received: " + baseLength);
        }
        this.baseLength = baseLength;
    }

    /**
     * Returns the vertical height of this pyramid.
     *
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the vertical height of this pyramid.
     *
     * @param height must be &gt; 0
     * @throws IllegalArgumentException if {@code height} is not positive
     */
    public void setHeight(double height) {
        if (Double.isNaN(height) || height <= 0) {
            throw new IllegalArgumentException("Pyramid height must be greater than zero. Received: " + height);
        }
        this.height = height;
    }

    // ---------------------------------------------------------------
    // toString
    // ---------------------------------------------------------------

    /**
     * Returns a formatted string representation of this Pyramid.
     *
     * @return shape details including base length, height, slant height, surface area, and volume
     */
    @Override
    public String toString() {
        return super.toString() + String.format(
                "%nBase Length   : %.2f" +
                        "%nHeight        : %.2f" +
                        "%nSlant Height  : %.2f",
                baseLength,
                height,
                getSlantHeight()
        );
    }
}