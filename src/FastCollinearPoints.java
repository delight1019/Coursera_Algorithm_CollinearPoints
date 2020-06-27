import java.util.Arrays;

public class FastCollinearPoints {
    private final Point[] points;

    private LineSegmentList lineSegmentList;

    private class LineSegmentList {
        private LineSegment[] lineSegments;
        private int nSize;

        public LineSegmentList() {
            lineSegments = new LineSegment[1];
            nSize = 0;
        }

        private void resize(int capacity) {
            LineSegment[] newLineSegments = new LineSegment[capacity];

            for (int i = 0; i < nSize; i++) {
                newLineSegments[i] = lineSegments[i];
            }

            lineSegments = newLineSegments;
        }

        public void addLineSegment(LineSegment lineSegment) {
            if (nSize == lineSegments.length) {
                resize(nSize * 2);
            }

            lineSegments[nSize++] = lineSegment;
        }

        public LineSegment[] getLineSegmentList() {
            LineSegment[] segments = new LineSegment[this.lineSegments.length];

            for (int i = 0; i < segments.length; i++) {
                segments[i] = this.lineSegments[i];
            }

            return segments;
        }

        public int size() {
            return nSize;
        }

        public void adjustSize() {
            LineSegment[] adjusted = new LineSegment[nSize];

            for (int i = 0; i < nSize; i++) {
                adjusted[i] = lineSegments[i];
            }

            lineSegments = adjusted;
        }
    }

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        this.points = new Point[points.length];

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }

            this.points[i] = points[i];
        }

        Arrays.sort(this.points);
        checkDuplicates();
        createLineSegmentList();
    }

    private void createLineSegmentList() {
        this.lineSegmentList = new LineSegmentList();

        Point[] originPoints = new Point[points.length];

        for (int i = 0; i < points.length; i++) {
            originPoints[i] = points[i];
        }

        for (int i = 0; i < originPoints.length; i++) {
            Point origin = originPoints[i];
            Arrays.sort(points, origin.slopeOrder());

            Point point1 = origin;
            Point point2 = origin;

            for (int j = 1; j < points.length; j++) {
                if (origin.compareTo(points[j]) > 0) {
                    continue;
                }

                Point point3 = points[j];

                double slope1 = origin.slopeTo(point1);
                double slope2 = origin.slopeTo(point2);
                double slope3 = origin.slopeTo(point3);

                if (slope1 == slope2 && slope1 == slope3) {
                    lineSegmentList.addLineSegment(new LineSegment(origin, getMaxPoint(point1, point2, point3)));
                }

                point1 = point2;
                point2 = point3;
            }
        }

        lineSegmentList.adjustSize();
    }

    private Point getMaxPoint(Point a, Point b, Point c) {
        if (a.compareTo(b) >= 0 && a.compareTo(c) >= 0) {
            return a;
        }
        else if (b.compareTo(a) >= 0 && b.compareTo(c) >= 0) {
            return b;
        }
        else {
            return c;
        }
    }

    private void checkDuplicates() {
        Point prev = null;

        for (int i = 0; i < points.length; i++) {
            if (prev != null && prev.compareTo(points[i]) == 0) {
                throw new IllegalArgumentException();
            }

            prev = points[i];
        }
    }

    public int numberOfSegments() {
        return lineSegmentList.size();

    }

    public LineSegment[] segments() {
        return lineSegmentList.getLineSegmentList();
    }
}
