package kodeinc;

public class Move {

    public static void main(String[] args) {
      //  run("1a",2,3);
     //   System.out.println( run("1a",2,3));
        System.out.println( run("4c",6,3));
    }

    public static String run(String startPosition, int R, int C) {
        /*
         * Write your code below; return type and arguments should be according to the problem's requirements
         */
        int rows[] = {1,2,3,4,5,6,7,8};
        char columns[] = {'a','b','c','d','e','f','g','h'};
        int rowStart =  Integer.parseInt( startPosition.split("")[0]);
        char colummStart =(char) startPosition.charAt(1);

        int RElement =0;
        char CElement ='a';
      //  System.out.println(startPosition);

        //rws
        for(int index =0; index < rows.length; index ++) {

            if (rows[index] == rowStart) {
                int RowMovevs = rows[index] + R;

                if (RowMovevs > rows[rows.length]) {
                    RowMovevs = RowMovevs - rows[rows.length];
                    RElement = rows[rows.length] - RowMovevs;
                } else {
                    RElement = rows[RowMovevs];
                }
            }

        }

            for(int index =0; index < columns.length; index ++){

                if (rows[index] == 0) {
                    int RowMovevs = rows[index] + R;

                    if (RowMovevs > rows[rows.length]) {
                        RowMovevs = RowMovevs - rows[rows.length];
                        RElement = rows[rows.length] - RowMovevs;
                    } else {
                        RElement = rows[RowMovevs];
                    }
                }

            }




        String endPosition = ""+RElement+CElement;
        return endPosition;
    }
}
