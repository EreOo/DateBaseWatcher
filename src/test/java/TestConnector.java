import org.junit.Assert;
import org.junit.Test;
import ru.osdis.datebase.Connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by  Vlaimir on 29.04.2016.
 */
public class TestConnector {

    @Test
    public void testGetDateBase() throws SQLException {
        Connector a = new Connector();
        a.getDateBase("jdbc:mysql://localhost:3306/mydatebase","root","1234");

        Assert.assertFalse(Connector.getConnection().isClosed());
    }

}
