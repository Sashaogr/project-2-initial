package com.csc205.project2;

import com.csc205.project2.shapes.*;

import java.util.*;

/**
 * Sophisticated driver demonstrating polymorphism, comparative analysis,
 * interactive shape creation, performance timing, and formatted output.
 *
 * @author  CSC205
 * @version 2.0
 */
public class ShapeDriver {

    // ---------------------------------------------------------------
    // Console styling constants
    // ---------------------------------------------------------------
    private static final String RESET  = "\u001B[0m";
    private static final String BOLD   = "\u001B[1m";
    private static final String CYAN   = "\u001B[36m";
    private static final String GREEN  = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED    = "\u001B[31m";
    private static final String PURPLE = "\u001B[35m";
    private static final String BLUE   = "\u001B[34m";

    private static final String DIVIDER     = "=".repeat(60);
    private static final String SUB_DIVIDER = "-".repeat(60);

    private static final Scanner scanner = new Scanner(System.in);

    // ---------------------------------------------------------------
    // Entry point
    // ---------------------------------------------------------------

    public static void main(String[] args) {
        printBanner();

        // ── Demo pre-loaded shapes ─────────────────────────────────
        List<Shape3D> shapes = buildDemoShapes();
        printAllShapes(shapes);
        printAnalysis(shapes);
        printVolumeRanking(shapes);
        printEfficiencyTable(shapes);

        // ── Interactive section ────────────────────────────────────
        if (promptYesNo("\nWould you like to add a custom shape? (y/n): ")) {
            Shape3D custom = buildCustomShape();
            if (custom != null) {
                shapes.add(custom);
                printSectionHeader("Newly Added Shape");
                printShapeCard(shapes.size(), custom);

                printSectionHeader("Updated Analysis (All Shapes)");
                printAnalysis(shapes);
            }
        }

        // ── Performance timing demo ────────────────────────────────
        printPerformanceTiming(shapes);

        printFooter();
        scanner.close();
    }

    // ===============================================================
    // DEMO DATA
    // ===============================================================

    /**
     * Builds a pre-loaded list of diverse Shape3D instances to demonstrate
     * polymorphic behaviour via a shared List<Shape3D> reference.
     */
    private static List<Shape3D> buildDemoShapes() {
        List<Shape3D> list = new ArrayList<>();

        // Each concrete type stored as Shape3D — polymorphism in action
        list.add(new Sphere(5.0,                    "red"));
        list.add(new Cube(4.0,                      "blue"));
        list.add(new Cylinder(3.0, 7.0,             "green"));
        list.add(new RectangularPrism(6.0, 4.0, 3.0,"yellow"));
        list.add(new Pyramid(5.0, 8.0,              "orange"));

        return list;
    }

    // ===============================================================
    // DISPLAY: SHAPE CARDS
    // ===============================================================

    private static void printAllShapes(List<Shape3D> shapes) {
        printSectionHeader("Created Shapes");
        for (int i = 0; i < shapes.size(); i++) {
            printShapeCard(i + 1, shapes.get(i));
        }
    }

    /**
     * Prints a formatted card for one shape, dispatching shape-specific
     * detail lines via instanceof — demonstrating runtime polymorphism.
     */
    private static void printShapeCard(int index, Shape3D shape) {
        String label = shapeLabel(shape);

        System.out.printf("%s%s%d. %s  {name='%s', color='%s'%s}%s%n",
                BOLD, CYAN, index, shape.getName(),
                toTitleCase(shape.getColor()) + " " + shape.getName(),
                shape.getColor(), label, RESET);

        System.out.printf("   %s- Surface Area:%s %s%.2f square units%s%n",
                YELLOW, RESET, GREEN, shape.getSurfaceArea(), RESET);
        System.out.printf("   %s- Volume:      %s %s%.2f cubic units%s%n",
                YELLOW, RESET, GREEN, shape.getVolume(), RESET);
        System.out.printf("   %s- Efficiency:  %s %s%.4f  (volume / surface area)%s%n",
                YELLOW, RESET, PURPLE, efficiency(shape), RESET);

        // Shape-specific bonus properties via pattern matching
        if (shape instanceof Sphere s) {
            System.out.printf("   %s- Diameter:    %s %s%.2f units%s%n",
                    YELLOW, RESET, BLUE, s.getDiameter(), RESET);
        } else if (shape instanceof Cube c) {
            System.out.printf("   %s- Space Diag:  %s %s%.2f units%s%n",
                    YELLOW, RESET, BLUE, c.getSpaceDiagonal(), RESET);
        } else if (shape instanceof Cylinder cy) {
            System.out.printf("   %s- Lateral SA:  %s %s%.2f square units%s%n",
                    YELLOW, RESET, BLUE, cy.getLateralSurfaceArea(), RESET);
        } else if (shape instanceof RectangularPrism rp) {
            System.out.printf("   %s- Space Diag:  %s %s%.2f units%s%n",
                    YELLOW, RESET, BLUE, rp.getSpaceDiagonal(), RESET);
        } else if (shape instanceof Pyramid p) {
            System.out.printf("   %s- Slant Height:%s %s%.2f units%s%n",
                    YELLOW, RESET, BLUE, p.getSlantHeight(), RESET);
        }

        System.out.println(SUB_DIVIDER);
    }

    // ===============================================================
    // DISPLAY: ANALYSIS
    // ===============================================================

    private static void printAnalysis(List<Shape3D> shapes) {
        printSectionHeader("Analysis Results");

        Shape3D maxVol = shapes.stream()
                .max(Comparator.comparingDouble(Shape3D::getVolume)).orElseThrow();
        Shape3D maxSA  = shapes.stream()
                .max(Comparator.comparingDouble(Shape3D::getSurfaceArea)).orElseThrow();
        Shape3D maxEff = shapes.stream()
                .max(Comparator.comparingDouble(ShapeDriver::efficiency)).orElseThrow();
        Shape3D minVol = shapes.stream()
                .min(Comparator.comparingDouble(Shape3D::getVolume)).orElseThrow();

        double totalVol = shapes.stream().mapToDouble(Shape3D::getVolume).sum();
        double avgVol   = shapes.stream().mapToDouble(Shape3D::getVolume).average().orElse(0);

        System.out.printf("  %s%-30s%s %s%s (%.2f)%s%n",
                YELLOW, "Largest Volume:",        RESET, GREEN,  displayName(maxVol), maxVol.getVolume(),       RESET);
        System.out.printf("  %s%-30s%s %s%s (%.2f)%s%n",
                YELLOW, "Smallest Volume:",       RESET, RED,    displayName(minVol), minVol.getVolume(),       RESET);
        System.out.printf("  %s%-30s%s %s%s (%.2f)%s%n",
                YELLOW, "Largest Surface Area:",  RESET, GREEN,  displayName(maxSA),  maxSA.getSurfaceArea(),   RESET);
        System.out.printf("  %s%-30s%s %s%s (%.4f)%s%n",
                YELLOW, "Most Efficient (V/SA):", RESET, PURPLE, displayName(maxEff), efficiency(maxEff),       RESET);
        System.out.printf("  %s%-30s%s %s%.2f cubic units%s%n",
                YELLOW, "Total Combined Volume:", RESET, CYAN,   totalVol,                                      RESET);
        System.out.printf("  %s%-30s%s %s%.2f cubic units%s%n",
                YELLOW, "Average Volume:",        RESET, CYAN,   avgVol,                                        RESET);

        System.out.println(SUB_DIVIDER);
    }

    // ===============================================================
    // DISPLAY: VOLUME RANKING TABLE
    // ===============================================================

    private static void printVolumeRanking(List<Shape3D> shapes) {
        printSectionHeader("Volume Ranking (Largest to Smallest)");

        List<Shape3D> ranked = shapes.stream()
                .sorted(Comparator.comparingDouble(Shape3D::getVolume).reversed())
                .toList();

        System.out.printf("  %s%-5s %-22s %-16s %-16s %-10s%s%n",
                BOLD, "Rank", "Shape", "Volume", "Surface Area", "Efficiency", RESET);
        System.out.println("  " + "-".repeat(72));

        String[] medals = {"1st", "2nd", "3rd"};
        for (int i = 0; i < ranked.size(); i++) {
            Shape3D s = ranked.get(i);
            String rank = i < medals.length ? medals[i] : String.format(" %d. ", i + 1);
            System.out.printf("  %-5s %-22s %-16.2f %-16.2f %-10.4f%n",
                    rank,
                    displayName(s),
                    s.getVolume(),
                    s.getSurfaceArea(),
                    efficiency(s));
        }
        System.out.println(SUB_DIVIDER);
    }

    // ===============================================================
    // DISPLAY: EFFICIENCY TABLE
    // ===============================================================

    private static void printEfficiencyTable(List<Shape3D> shapes) {
        printSectionHeader("Surface Area vs Volume Efficiency Analysis");

        System.out.printf("  %s%-22s %10s   %10s   %8s   %s%s%n",
                BOLD, "Shape", "SA", "Volume", "V/SA", "Bar Chart", RESET);
        System.out.println("  " + "-".repeat(75));

        double maxEff = shapes.stream()
                .mapToDouble(ShapeDriver::efficiency)
                .max().orElse(1);

        for (Shape3D s : shapes) {
            double eff = efficiency(s);
            String bar = buildBar(eff, maxEff, 15);
            System.out.printf("  %-22s %10.2f   %10.2f   %8.4f   %s%s%s%n",
                    displayName(s),
                    s.getSurfaceArea(),
                    s.getVolume(),
                    eff,
                    GREEN, bar, RESET);
        }
        System.out.println(SUB_DIVIDER);
    }

    // ===============================================================
    // PERFORMANCE TIMING
    // ===============================================================

    private static void printPerformanceTiming(List<Shape3D> shapes) {
        printSectionHeader("Performance Timing  (1,000,000 iterations per shape)");

        int iterations = 1_000_000;

        for (Shape3D shape : shapes) {
            long start = System.nanoTime();
            double dummy = 0;
            for (int i = 0; i < iterations; i++) {
                dummy += shape.getVolume() + shape.getSurfaceArea();
            }
            long elapsed = System.nanoTime() - start;

            System.out.printf("  %-24s  %s%,12d ns%s  (~%6.2f ms)   [sum: %.0f]%n",
                    displayName(shape),
                    CYAN,
                    elapsed,
                    RESET,
                    elapsed / 1_000_000.0,
                    dummy % 1_000);   // prevents JIT from optimising away the loop
        }
        System.out.println(SUB_DIVIDER);
    }

    // ===============================================================
    // INTERACTIVE SHAPE BUILDER
    // ===============================================================

    /**
     * Guides the user through building a custom shape interactively,
     * with real-time validation feedback on every input.
     */
    private static Shape3D buildCustomShape() {
        printSectionHeader("Custom Shape Builder");

        System.out.println("  Select a shape type:");
        System.out.println("    1. Sphere");
        System.out.println("    2. Cube");
        System.out.println("    3. Cylinder");
        System.out.println("    4. Rectangular Prism");
        System.out.println("    5. Pyramid");

        int choice = readInt("  Enter choice (1-5): ", 1, 5);
        String color = readString("  Enter a color: ");

        try {
            return switch (choice) {
                case 1 -> {
                    double r = readDouble("  Radius: ", 0.001, 1e9);
                    yield new Sphere(r, color);
                }
                case 2 -> {
                    double s = readDouble("  Side length: ", 0.001, 1e9);
                    yield new Cube(s, color);
                }
                case 3 -> {
                    double r = readDouble("  Radius: ", 0.001, 1e9);
                    double h = readDouble("  Height: ", 0.001, 1e9);
                    yield new Cylinder(r, h, color);
                }
                case 4 -> {
                    double l = readDouble("  Length: ", 0.001, 1e9);
                    double w = readDouble("  Width: ",  0.001, 1e9);
                    double h = readDouble("  Height: ", 0.001, 1e9);
                    yield new RectangularPrism(l, w, h, color);
                }
                case 5 -> {
                    double b = readDouble("  Base length: ", 0.001, 1e9);
                    double h = readDouble("  Height: ",      0.001, 1e9);
                    yield new Pyramid(b, h, color);
                }
                default -> null;
            };
        } catch (IllegalArgumentException e) {
            System.out.println(RED + "  Shape creation failed: " + e.getMessage() + RESET);
            return null;
        }
    }

    // ===============================================================
    // UTILITY HELPERS
    // ===============================================================

    /** Volume / Surface Area ratio — higher means more "enclosed" per unit of surface. */
    private static double efficiency(Shape3D s) {
        return s.getVolume() / s.getSurfaceArea();
    }

    /** Human-friendly name combining colour and shape type. */
    private static String displayName(Shape3D s) {
        return toTitleCase(s.getColor()) + " " + s.getName();
    }

    private static String toTitleCase(String str) {
        if (str == null || str.isEmpty()) return str;
        return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
    }

    /** Returns a compact parameter summary appended to the card header. */
    private static String shapeLabel(Shape3D shape) {
        if (shape instanceof Sphere s)
            return String.format(", radius=%.1f", s.getRadius());
        if (shape instanceof Cube c)
            return String.format(", side=%.1f", c.getSideLength());
        if (shape instanceof Cylinder cy)
            return String.format(", r=%.1f, h=%.1f", cy.getRadius(), cy.getHeight());
        if (shape instanceof RectangularPrism rp)
            return String.format(", l=%.1f, w=%.1f, h=%.1f", rp.getLength(), rp.getWidth(), rp.getHeight());
        if (shape instanceof Pyramid p)
            return String.format(", base=%.1f, h=%.1f", p.getBaseLength(), p.getHeight());
        return "";
    }

    /** Builds a proportional block-character bar for the efficiency chart. */
    private static String buildBar(double value, double max, int width) {
        int filled = (max == 0) ? 0 : (int) Math.round((value / max) * width);
        return "#".repeat(Math.max(0, filled)) + ".".repeat(Math.max(0, width - filled));
    }

    // ===============================================================
    // INPUT HELPERS (with validation loops)
    // ===============================================================

    private static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = Integer.parseInt(scanner.nextLine().trim());
                if (v >= min && v <= max) return v;
                System.out.printf(RED + "  Please enter a number between %d and %d.%n" + RESET, min, max);
            } catch (NumberFormatException e) {
                System.out.println(RED + "  Invalid number — try again." + RESET);
            }
        }
    }

    private static double readDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try {
                double v = Double.parseDouble(scanner.nextLine().trim());
                if (v >= min && v <= max) return v;
                System.out.printf(RED + "  Value must be between %.3f and %.0e — try again.%n" + RESET, min, max);
            } catch (NumberFormatException e) {
                System.out.println(RED + "  Invalid number — try again." + RESET);
            }
        }
    }

    private static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String v = scanner.nextLine().trim();
            if (!v.isBlank()) return v;
            System.out.println(RED + "  Input cannot be blank — try again." + RESET);
        }
    }

    private static boolean promptYesNo(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim().equalsIgnoreCase("y");
    }

    // ===============================================================
    // HEADER / FOOTER
    // ===============================================================

    private static void printBanner() {
        System.out.println();
        System.out.println(BOLD + CYAN + DIVIDER + RESET);
        System.out.println(BOLD + CYAN + "        3D Shape Analysis System  |  CSC 205" + RESET);
        System.out.println(BOLD + CYAN + "   Polymorphism | Analysis | Timing | Interactive" + RESET);
        System.out.println(BOLD + CYAN + DIVIDER + RESET);
        System.out.println();
    }

    private static void printSectionHeader(String title) {
        System.out.println();
        System.out.printf("%s%s=== %s ===%s%n", BOLD, CYAN, title, RESET);
        System.out.println(CYAN + DIVIDER + RESET);
    }

    private static void printFooter() {
        System.out.println();
        System.out.println(BOLD + GREEN + DIVIDER + RESET);
        System.out.println(BOLD + GREEN + "  Analysis complete.  All shapes processed successfully." + RESET);
        System.out.println(BOLD + GREEN + DIVIDER + RESET);
        System.out.println();
    }
}