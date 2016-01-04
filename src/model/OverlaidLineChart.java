package model;

import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.util.Pair;

public class OverlaidLineChart extends LineChart<Number,Number>
{
	   public OverlaidLineChart(Axis<Number> xAxis, Axis<Number> yAxis) {
	       super(xAxis, yAxis);
	   }
	   /**
	    * Add vertical value marker. The marker's X value is used to plot a
	    * horizontal line across the plot area, its Y value is ignored.
	    * 
	    * @param marker must not be null.
	    */
//	   public void addVerticalValueMarker(Data<Number, Number> marker, Color c, double strokeWid) {
//		   addVerticalValueMarker(marker, c, strokeWid, null);
//	   }
//	   public void addVerticalValueMarker(Data<Number, Number> marker, Color c, double strokeWid, StrokeType strokeType) {
//	       Objects.requireNonNull(marker, "the marker must not be null");
////	       if (horizontalMarkers.contains(marker)) return;
////	       Line line = new Line();
//	
//	       ObjectProperty<Number> x = marker.XValueProperty();
////	       Number xn = x.getValue();
////	       double d = xn.doubleValue();
////	       double xP =  getXAxis().getDisplayPosition(marker.getXValue());
////	       double wid = widthProperty().getValue().doubleValue();
//	       NumberAxis xAxis = (NumberAxis) getXAxis();
////	       double min = xAxis.getLowerBound();
////	       double max = xAxis.getUpperBound();
////	       double disp = getXAxis().getDisplayPosition(xn);
////	       line.setStartX(disp);
////	       line.setEndX(disp);
//	       Line line = new Line();
//	       marker.setNode(line );
////       line.startXProperty().bind(getXAxis().(x));
////	       line.endXProperty().bind(x);
////	       
//	       line.startYProperty().bind(heightProperty());
//	       line.setEndY(0);
//	       line.setStroke(c);
//	       line.setStrokeWidth(strokeWid);
//	       if (strokeType != null)
//	    	   line.setStrokeType(strokeType);
//	       marker.setNode(line );
//	       getPlotChildren().add(line);
////	       horizontalMarkers.add(marker);
//	   }
	   private ObservableList<Data<Number, Number>> verticalMarkers = FXCollections.observableArrayList();
	   private ObservableList<Peak> bellCurveMarkers = FXCollections.observableArrayList();


	   /**
	    * Add Vertical value marker. The marker's X value is used to plot a
	    * Vertical line across the plot area, its Y value is ignored.
	    * 
	    * @param marker must not be null.
	    */
	   public void addVerticalValueMarker(Data<Number, Number> marker, Color c, double strokeWid) {
	       Objects.requireNonNull(marker, "the marker must not be null");
	       if (verticalMarkers.contains(marker)) return;
	       Line line = new Line();
	       line.setStroke(c);
	       line.setStrokeWidth(strokeWid);
	       marker.setNode(line );
	       getPlotChildren().add(line);
	       verticalMarkers.add(marker);
	   }

	   /**
	    * Remove Vertical value marker.
	    * 
	    * @param VerticalMarker must not be null
	    */
	   public void removeVerticalValueMarker(Data<Number, Number> marker) {
	       Objects.requireNonNull(marker, "the marker must not be null");
	       if (marker.getNode() != null) {
	           getPlotChildren().remove(marker.getNode());
	           marker.setNode(null);
	       }
	       verticalMarkers.remove(marker);
	   }
	   
	   /**------------------------------------------------------------------------------
	    * Add BellCurve  marker. 
	    * 
	    * @param peak must not be null.
	    */
	   public void addBellCurveMarker(Peak peak, Color c, double strokeWid) {
	       Objects.requireNonNull(peak, "the peak must not be null");
	       if (bellCurveMarkers.contains(peak)) return;
	       Path line = peak.getPath();
	       line.setStroke(c);
	       line.setStrokeWidth(strokeWid);
	       peak.setNode(line);
	       getPlotChildren().add(line);
	       bellCurveMarkers.add(peak);
	   }

	   /**
	    * Remove BellCurve value marker.
	    * 
	    * @param BellCurve must not be null
	    */
	   public void removeBellCurveMarker(Data<Number, Number> marker) {
	       Objects.requireNonNull(marker, "the marker must not be null");
	       if (marker.getNode() != null) {
	           getPlotChildren().remove(marker.getNode());
	           marker.setNode(null);
	       }
	       bellCurveMarkers.remove(marker);
	   }
	      
	   /**
	    * Overridden to layout the value markers.
	    */
	   @Override
	   protected void layoutPlotChildren() {
	       super.layoutPlotChildren();

//	       if (verticalMarkers.size() > 0)	return;
//System.out.println("nMarkers = " + verticalMarkers.size());
	       for (Data<Number, Number> verticalMarker : verticalMarkers) {
	           double lower = ((NumberAxis) getYAxis()).getLowerBound();
	           Number lowerY = getYAxis().toRealValue(lower);
	           double upper = ((NumberAxis) getYAxis()).getUpperBound();
	           Number upperY = getYAxis().toRealValue(upper);
	           Line line = (Line) verticalMarker.getNode();
	           line.setStartY(getYAxis().getDisplayPosition(lowerY));
	           line.setEndY(getYAxis().getDisplayPosition(upperY));
	           line.setStartX(getXAxis().getDisplayPosition(verticalMarker.getXValue()));
	           line.setEndX(line.getStartX());
	           
	           
	       }
	       boolean showRightEdge = true;
	       boolean showLefEdge = true;
	       for (Peak curve : bellCurveMarkers) 
	       {
			double startBin = curve.getMin();
			double endBin = curve.getMax();
//			double lowerx = ((NumberAxis) getXAxis()).getLowerBound();
//			Number lowerX = getXAxis().toRealValue(lowerx);
			// double upperx = ((NumberAxis) getXAxis()).getUpperBound();
			// Number upperX = getXAxis().toRealValue(upperx);
			// double xRange = upperx - lowerx;

			double lowery = ((NumberAxis) getYAxis()).getLowerBound();
			// Number lowerY = getYAxis().toRealValue(lowery);
			double uppery = ((NumberAxis) getYAxis()).getUpperBound();
			// Number upperY = getYAxis().toRealValue(uppery);
			// double yRange = uppery - lowery;

			Histogram1D histo = curve.getHistogram();
			double y0 = getYAxis().getDisplayPosition(getYAxis().toRealValue(lowery));
			Point2D left = new Point2D(getXAxis().getDisplayPosition(histo.binToVal((int) startBin)), y0);
			Point2D right = new Point2D(getXAxis().getDisplayPosition(histo.binToVal((int) endBin)), y0);

			
			double x = histo.binToVal((int) curve.getMean());
			double h = getXAxis().getDisplayPosition(x);
			double y = histo.getValue((int) curve.getMean()) / histo.getArea();	//2.5 * curve.getUnitAmplitude();			// HACK
			double v = getYAxis().getDisplayPosition(y);
			
			double altV = curve.get(curve.getMean());
			double altV2 = getYAxis().getDisplayPosition(altV);
			
			Point2D center = new Point2D(h, v);
			double ytemp;
			// fill 7 points around the center, in the order:  4, 2, 1, 3, 6, 5, 7
			
			int xNegBin = (int) ((startBin + curve.getMean()) / 2);
			double x2 = histo.binToVal(xNegBin);
			double x2Neg = getXAxis().getDisplayPosition(x2);
			ytemp = curve.get(x2);   // histo.getValue(xNegBin) / histo.getArea();				// usually near 0
			double y2Neg= getYAxis().getDisplayPosition(ytemp);
			
			int xNegBin1 = (int) ((startBin + xNegBin )/ 2);
			double x1 = histo.binToVal(xNegBin1);
			double x1Neg = getXAxis().getDisplayPosition(x1);
			ytemp = curve.get(x1);   // histo.getValue(xNegBin1) / histo.getArea();				
			double y1Neg = getYAxis().getDisplayPosition(ytemp);

			int xNegBin3 = (int) ((curve.getMean() + xNegBin)/ 2);
			double x3 = histo.binToVal(xNegBin3);
			double x3Neg = getXAxis().getDisplayPosition(x3);
			ytemp = curve.get(x3);   // histo.getValue(xNegBin3) / histo.getArea();				
			double y3Neg = getYAxis().getDisplayPosition(ytemp);

			
			int x6PosBin= (int) ((endBin + curve.getMean()) / 2);
			double x6 = histo.binToVal(x6PosBin	);
			double xPos = getXAxis().getDisplayPosition(x6);
			ytemp = curve.get(x6);   // histo.getValue(xPosBin) / histo.getArea();
			double y6Pos = getYAxis().getDisplayPosition(ytemp);

			int xPosBin5 = (int) ((x6PosBin + curve.getMean()) / 2);
			double x5 = histo.binToVal(xPosBin5);
			double x5Pos = getXAxis().getDisplayPosition(x5);
			ytemp = curve.get(x5);   // histo.getValue(xPosBin5) / histo.getArea();
			double y5Pos = getYAxis().getDisplayPosition(ytemp);

			int xPosBin7 = (int) ((endBin + x6PosBin) / 2);
			double x7 = histo.binToVal(xPosBin7);
			double x7Pos = getXAxis().getDisplayPosition(x7);
			ytemp = curve.get(x7);   // histo.getValue(xPosBin7) / histo.getArea();			
			double y7Pos = getYAxis().getDisplayPosition(ytemp);
			
				
		     Point2D[] dataPoints = new Point2D[] 
    			 { 	left, 		new Point2D(x1Neg, y1Neg),   	new Point2D(x2Neg, y2Neg), 	 new Point2D(x3Neg, y3Neg), 
    				 center,  	new Point2D(x5Pos, y5Pos), 		new Point2D(xPos, y6Pos), 	 new Point2D(x7Pos, y7Pos), 
    				 right};

		    Path path = curve.getPath();
			path.getElements().clear();
			if (showLefEdge)
				path.getElements().addAll(new MoveTo(left.getX(), uppery), new LineTo(left.getX(), y0)); // draw the bounds

			path.getElements().add(new MoveTo(left.getX(), left.getY()));
			for (int i = 1; i < dataPoints.length; i++) 
			{
		      	path.getElements().add(new LineTo(dataPoints[i].getX(), dataPoints[i].getY()));
		      	System.out.println("" +  String.format("%.2f", dataPoints[i].getY()));
			}
				
			
		    if (showRightEdge)
		    	path.getElements().addAll(new MoveTo(right.getX(), y0), new LineTo(right.getX(), uppery));
	       
			path.setStroke(Color.BLUE);
			path.setStrokeWidth(1);
	
					
//					
//					
//
//				     Pair<Point2D[], Point2D[]> result = calcCurveControlPoints(dataPoints);
//				     Point2D[] pts1 = result.getKey();
//				     Point2D[] pts2 = result.getValue();
//				     
//						path.getElements().add(new MoveTo(left.getX(), left.getY()));
//					       for (int i = 1; i < dataPoints.length; i++) 
//					           	path.getElements().add(makeCubicCurveTo(pts1[i-1], pts2[i-1], dataPoints[i]));
//							path.getElements().addAll(new MoveTo(right.getX(), y0), new LineTo(right.getX(), uppery));
//			       

//			path.getElements().add(new MoveTo(left.getX(), left.getY()));
//			path.getElements().add(new LineTo(xNeg, yNeg));			// 0
//			path.getElements().add(new LineTo(center.getX(), center.getY()));
//			path.getElements().add(new LineTo(xPos, yPos));			// 0
//			path.getElements().add(new LineTo(right.getX(), right.getY()));
			// for (int seriesIndex=0; seriesIndex < getDataSize();
			// seriesIndex++) {
			// final XYChart.Series<Number, Number> series =
			// getData().get(seriesIndex);
			// final Path seriesLine = (Path)(series.getNode());
			// ObservableList<PathElement> fillElements =
			// FXCollections.observableArrayList(seriesLine.getElements());
			// smooth(seriesLine.getElements(), fillElements);
			// }
		   }

	     }
   
	   
  
   private int getDataSize() {
       final ObservableList<XYChart.Series<Number, Number>> data = getData();
       return (data!=null) ? data.size() : 0;
   }

   private static void smooth(ObservableList<PathElement> strokeElements, ObservableList<PathElement> fillElements) {
       // as we do not have direct access to the data, first recreate the list of all the data points we have
       final Point2D[] dataPoints = new Point2D[strokeElements.size()];
       for (int i = 0; i < strokeElements.size(); i++) {
           final PathElement element = strokeElements.get(i);
           if (element instanceof MoveTo) {
               final MoveTo move = (MoveTo)element;
               dataPoints[i] = new Point2D(move.getX(), move.getY());
           } else if (element instanceof LineTo) {
               final LineTo line = (LineTo)element;
               final double x = line.getX(), y = line.getY();
               dataPoints[i] = new Point2D(x, y);
           }
       }
       // next we need to know the zero Y value
       final double zeroY = ((MoveTo) strokeElements.get(0)).getY();

       // now clear and rebuild elements
       strokeElements.clear();
       fillElements.clear();
       Pair<Point2D[], Point2D[]> result = calcCurveControlPoints(dataPoints);
       if (result == null) 
    	   return;
       Point2D[] pts1 = result.getKey();
       Point2D[] pts2 = result.getValue();
       // start both paths
       strokeElements.add(new MoveTo(dataPoints[0].getX(),dataPoints[0].getY()));
       fillElements.add(new MoveTo(dataPoints[0].getX(),zeroY));
       fillElements.add(new LineTo(dataPoints[0].getX(),dataPoints[0].getY()));
       // add curves
       for (int i = 1; i < dataPoints.length; i++) {
           strokeElements.add(makeCubicCurveTo(pts1[i-1], pts2[i-1], dataPoints[i]));
             fillElements.add(makeCubicCurveTo(pts1[i-1], pts2[i-1], dataPoints[i]));
       }
       // end the paths
       fillElements.add(new LineTo(dataPoints[dataPoints.length-1].getX(),zeroY));
       fillElements.add(new ClosePath());
   }

   
   
   private static PathElement makeCubicCurveTo(Point2D a, Point2D b, Point2D c)
   {
	   return new CubicCurveTo(  a.getX(),a.getY(),  b.getX(),b.getY(), c.getX(),c.getY());	
   }

/** 
    * Calculate open-ended Bezier Spline Control Points.
    * @param dataPoints Input data Bezier spline points.
    */
   public static Pair<Point2D[], Point2D[]> calcCurveControlPoints(Point2D[] dataPoints) {
      if (dataPoints == null || dataPoints.length == 0) return null;
      Point2D[] firstControlPoints;
       Point2D[] secondControlPoints;
       int n = dataPoints.length - 1;
       if (n == 1) { // Special case: Bezier curve should be a straight line.
           firstControlPoints = new Point2D[1];
           // 3P1 = 2P0 + P3
           firstControlPoints[0] = new Point2D(
               (2 * dataPoints[0].getX() + dataPoints[1].getX()) / 3,
               (2 * dataPoints[0].getY() + dataPoints[1].getY()) / 3
           );

           secondControlPoints = new Point2D[1];
           // P2 = 2P1 – P0
           secondControlPoints[0] = new Point2D(
               2 * firstControlPoints[0].getX() - dataPoints[0].getX(),
               2 * firstControlPoints[0].getY() - dataPoints[0].getY()
                   );
           return new Pair<Point2D[], Point2D[]>(firstControlPoints, secondControlPoints);
       }

       // Calculate first Bezier control points
       // Right hand side vector
       double[] rhs = new double[n];

       // Set right hand side X values
       for (int i = 1; i < n - 1; ++i)
               rhs[i] = 4 * dataPoints[i].getX() + 2 * dataPoints[i + 1].getX();
       rhs[0] = dataPoints[0].getX() + 2 * dataPoints[1].getX();
       rhs[n - 1] = (8 * dataPoints[n - 1].getX() + dataPoints[n].getX()) / 2.0;
       // Get first control points X-values
       double[] x = GetFirstControlPoints(rhs);

       // Set right hand side Y values
       for (int i = 1; i < n - 1; ++i)
               rhs[i] = 4 * dataPoints[i].getY() + 2 * dataPoints[i + 1].getY();
       rhs[0] = dataPoints[0].getY() + 2 * dataPoints[1].getY();
       rhs[n - 1] = (8 * dataPoints[n - 1].getY() + dataPoints[n].getY()) / 2.0;
       // Get first control points Y-values
       double[] y = GetFirstControlPoints(rhs);

       // Fill output arrays.
       firstControlPoints = new Point2D[n];
       secondControlPoints = new Point2D[n];
       for (int i = 0; i < n; ++i) {
           // First control point
           firstControlPoints[i] = new Point2D(x[i], y[i]);
           // Second control point
           if (i < n - 1) {
               secondControlPoints[i] = new Point2D(2 * dataPoints
                       [i + 1].getX() - x[i + 1], 2 *
                       dataPoints[i + 1].getY() - y[i + 1]);
           } else {
               secondControlPoints[i] = new Point2D((dataPoints
                       [n].getX() + x[n - 1]) / 2,
                       (dataPoints[n].getY() + y[n - 1]) / 2);
           }
       }
       return new Pair<Point2D[], Point2D[]>(firstControlPoints, secondControlPoints);
   }

   /**
    * Solves a tridiagonal system for one of coordinates (x or y)
    * of first Bezier control points.
    * 
    * @param rhs Right hand side vector.
    * @return Solution vector.
    */
   private static double[] GetFirstControlPoints(double[] rhs) {
       int n = rhs.length;
       double[] x = new double[n]; // Solution vector.
       double[] tmp = new double[n]; // Temp workspace.
       double b = 2.0;
       x[0] = rhs[0] / b;
       for (int i = 1; i < n; i++) {// Decomposition and forward substitution.
               tmp[i] = 1 / b;
               b = (i < n - 1 ? 4.0 : 3.5) - tmp[i];
               x[i] = (rhs[i] - x[i - 1]) / b;
       }
       for (int i = 1; i < n; i++)
               x[n - i - 1] -= tmp[n - i] * x[n - i]; // Backsubstitution.

       return x;
   }

}
