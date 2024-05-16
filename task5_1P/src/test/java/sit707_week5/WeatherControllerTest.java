package sit707_week5;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;

public class WeatherControllerTest {

    private static WeatherController wController;

    @BeforeClass
    public static void setUp() {
        // Initialize the WeatherController instance once
        wController = WeatherController.getInstance();
    }

    @Test
    public void testStudentIdentity() {
        String studentId = "220437608";
        Assert.assertNotNull("Student ID is null", studentId);
    }

    @Test
    public void testStudentName() {
        String studentName = "Gnei Azmah";
        Assert.assertNotNull("Student name is null", studentName);
    }

    @Test
    public void testTemperaturePersist() {
        System.out.println("+++ testTemperaturePersist +++");

        // Arrange
        int hour = 10;
        double temperature = 19.5;

        // Act
        long startTime = System.currentTimeMillis();
        String persistTime = wController.persistTemperature(hour, temperature);
        long endTime = System.currentTimeMillis();

        // Debugging output
        System.out.println("Persist Time: " + persistTime);
        System.out.println("Current Time: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));

        // Allow a slight difference due to processing delay
        long persistTimeInMillis = convertTimeToMillis(persistTime);
        long averageTime = (startTime + endTime) / 2;
        long timeDifference = Math.abs(persistTimeInMillis - averageTime);

        // Assert
        Assert.assertTrue("Persist time should be close to current time", timeDifference < 2000); // Allow 2 seconds difference

        // No need to call close() as it resets the controller, affecting other tests
    }

    private long convertTimeToMillis(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            Date date = sdf.parse(time);

            // Get the current date
            Calendar now = Calendar.getInstance();

            // Create a calendar with the parsed time
            Calendar combined = Calendar.getInstance();
            combined.set(Calendar.YEAR, now.get(Calendar.YEAR));
            combined.set(Calendar.MONTH, now.get(Calendar.MONTH));
            combined.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));

            Calendar timeCalendar = Calendar.getInstance();
            timeCalendar.setTime(date);
            combined.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY));
            combined.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
            combined.set(Calendar.SECOND, timeCalendar.get(Calendar.SECOND));

            return combined.getTimeInMillis();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
