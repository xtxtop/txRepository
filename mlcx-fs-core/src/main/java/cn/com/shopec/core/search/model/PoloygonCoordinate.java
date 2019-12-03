package cn.com.shopec.core.search.model;

import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

import cn.com.shopec.core.car.model.LongitudeAndLatitude;

/**
 * 计算多边形几何中心点
 * @author machao
 *
 */
public class PoloygonCoordinate {
	public static LongitudeAndLatitude getPolygonCoordinate(List<LongitudeAndLatitude> points) {
		GeometryFactory geometryFactory = new GeometryFactory();
		if(points!=null&&points.size()>0){
			Coordinate[] coordinates = new Coordinate[points.size()+1];
			for(int i=0;i<points.size();i++){
				coordinates[i] = new Coordinate(points.get(i).getLongitude(), points.get(i).getLatitude());
			}
			coordinates[coordinates.length-1] = new Coordinate(points.get(0).getLongitude(), points.get(0).getLatitude());
			try {
				Polygon polygon = geometryFactory.createPolygon(coordinates);
				Point centroid = polygon.getCentroid();
				Coordinate c = centroid.getCoordinate();
				LongitudeAndLatitude longitudeAndLatitude = new LongitudeAndLatitude(c.getOrdinate(0), c.getOrdinate(1));
				System.out.println(c.getOrdinate(0) + "," + c.getOrdinate(1));
				return longitudeAndLatitude;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
		}
		return null;

	}
}
