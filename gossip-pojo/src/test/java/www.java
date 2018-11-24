import java.util.Date;

/**
 * @author zjl
 * @create 2018-10-10 16:56
 **/
public class www {

    public static void main(String[] args) {
        Date date = new Date(2018,10,10,7,0,0);
        long time = date.getTime();

        time = time-(1000*60*60*8);
        date.setTime(time);

        System.out.println(date);
    }
}
