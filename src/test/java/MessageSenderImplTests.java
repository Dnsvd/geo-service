import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageSenderImplTests {

    @Test
    void sendRussianMessageTest() {
        GeoService geoService = Mockito.mock(GeoService.class);
        LocalizationServiceImpl localizationService = Mockito.mock(LocalizationServiceImpl.class);

        Mockito.when(geoService.byIp("172.0.32.11")).thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.0.32.11");

        String result = messageSender.send(headers);
        assertEquals("Добро пожаловать", result);
    }

    @Test
    void sendAmericanMessageTest() {
        GeoService geoService = Mockito.mock(GeoService.class);
        LocalizationServiceImpl localizationService = Mockito.mock(LocalizationServiceImpl.class);

        Mockito.when(geoService.byIp("96.44.183.149")).thenReturn(new Location("New York", Country.USA, null, 0));
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.44.183.149");

        String result = messageSender.send(headers);
        assertEquals("Welcome", result);
    }

    @Test
    void sendUnknownIpTest() {
        GeoService geoService = Mockito.mock(GeoService.class);
        LocalizationServiceImpl localizationService = Mockito.mock(LocalizationServiceImpl.class);

        Mockito.when(geoService.byIp("203.0.113.0")).thenReturn(null);
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "203.0.113.0");

        String result = messageSender.send(headers);
        assertEquals("Welcome", result);
    }

    @Test
    void sendEmptyIpTest() {
        GeoService geoService = Mockito.mock(GeoService.class);
        LocalizationServiceImpl localizationService = Mockito.mock(LocalizationServiceImpl.class);

        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<>();

        String result = messageSender.send(headers);
        assertEquals("Welcome", result);
    }
}