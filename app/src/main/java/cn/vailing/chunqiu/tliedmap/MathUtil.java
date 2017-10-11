package cn.vailing.chunqiu.tliedmap;

/**
 * Created by dream on 2017/6/19.
 */

public class MathUtil {

    public static int judgeArea(float angle) {
        if (angle < 0) {
            while (angle < 0) {
                angle += 360;
            }
        }
        int area = 1;
        angle = angle % 360;
        if (angle >= 0 && angle < 90) {
            area = 1;
        } else if (angle >= 90 && angle < 180) {
            area = 2;
        } else if (angle >= 180 && angle < 270) {
            area = 3;
        } else if (angle >= 270 && angle < 360) {
            area = 4;
        }
        return area;
    }


    public static int addAllRect(int[][] location, int i, int columnIndex, int rowIndex) {
        int count = 0;
        switch (i) {
            case 1:
//                （i,j)
                count = (location[columnIndex][rowIndex] +
                        location[columnIndex + 1][rowIndex] +
                        location[columnIndex][rowIndex + 1] +
                        location[columnIndex + 1][rowIndex + 1]);
                break;
            case 2:
//                (i+1,j)
                count = (location[columnIndex + 2][rowIndex] +
                        location[columnIndex + 3][rowIndex] +
                        location[columnIndex + 2][rowIndex + 1] +
                        location[columnIndex + 3][rowIndex + 1]);
                break;
            case 3:
//                (i,j+1)
                count = (location[columnIndex][rowIndex + 2] +
                        location[columnIndex + 1][rowIndex + 2] +
                        location[columnIndex][rowIndex + 3] +
                        location[columnIndex + 1][rowIndex + 3]);
                break;
            case 4:
//                (i+1,j+1)
                count = (location[columnIndex + 2][rowIndex + 2] +
                        location[columnIndex + 3][rowIndex + 2] +
                        location[columnIndex + 2][rowIndex + 3] +
                        location[columnIndex + 3][rowIndex + 3]);
                break;
        }
        return count;
    }
}
