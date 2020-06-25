import java.util.Arrays;

public class BruteCollinearPoints {
    private Point[] points;
    private LineSegmentList lineSegmentList;

    private class LineSegmentList {
        private LineSegment[] lineSegments;
        private int N;

        public LineSegmentList() {
            lineSegments = new LineSegment[1];
            N = 0;
        }

        private void resize(int capacity) {
            LineSegment[] newLineSegments = new LineSegment[capacity];

            for (int i = 0; i < N; i++) {
                newLineSegments[i] = lineSegments[i];
            }

            lineSegments = newLineSegments;
        }

        public void addLineSegment(LineSegment lineSegment) {
            if (N == lineSegments.length) {
                resize(N * 2);
            }

            lineSegments[N++] = lineSegment;
        }

        public LineSegment[] getLineSegmentList() {
            return lineSegments;
        }

        public int size() {
            return N;
        }
    }

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        this.points = points;
        Arrays.sort(this.points);
        createLineSegmentList();
    }

    private void createLineSegmentList() {
        this.lineSegmentList = new LineSegmentList();

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j +  1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        if (points[i] == null || points[j] == null || points[k] == null || points[l] == null) {
                            throw new IllegalArgumentException();
                        }

                        if (points[i] == points[j] || points[i] == points[k] || points[i] == points[l] ||
                            points[j] == points[k] || points[j] == points[l] || points[k] == points[l]) {
                            throw new IllegalArgumentException();
                        }

                        double slope1 = points[i].slopeTo(points[j]);
                        double slope2 = points[j].slopeTo(points[k]);
                        double slope3 = points[k].slopeTo(points[l]);

                        if (slope1 == slope2 && slope2 == slope3) {
                            lineSegmentList.addLineSegment(new LineSegment(points[i], points[l]));
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return lineSegmentList.size();
    }

    public LineSegment[] segments() {
        return lineSegmentList.getLineSegmentList();
    }
}
