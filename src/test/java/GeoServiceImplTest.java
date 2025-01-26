import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GeoServiceImplTest {

    @Test
    void byIpReturnsRussianCity() {
        GeoServiceImpl geoService = new GeoServiceImpl();
        Location location = geoService.byIp("172.0.32.11");

        assertEquals("Moscow", location.getCity());
        assertEquals(Country.RUSSIA, location.getCountry());
    }

    @Test
    void byIpReturnsAmericanCity() {
        GeoServiceImpl geoService = new GeoServiceImpl();
        Location location = geoService.byIp("96.44.183.149");

        assertEquals("New York", location.getCity());
        assertEquals(Country.USA, location.getCountry());
    }

    @Test
    void byIpReturnsNullForUnknownIp() {
        GeoServiceImpl geoService = new GeoServiceImpl();
        Location location = geoService.byIp("203.0.113.0");

        assertNull(location);
    }
}
