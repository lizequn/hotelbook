package uk.ac.ncl.cs.zequnli.test;

import static org.junit.Assert.assertNotNull;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import uk.ac.ncl.cs.zequnli.model.Hotel;
import uk.ac.ncl.cs.zequnli.service.HotelRegistration;
import uk.ac.ncl.cs.zequnli.util.Resources;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class FlightRestServiceTest {
    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Hotel.class, HotelRegistration.class, Resources.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                        // Deploy our test datasource
                .addAsWebInfResource("test-ds.xml");
    }

    @Inject
    HotelRegistration hotelRegistration;

    @Inject
    Logger log;

//    @Test
//    public void testRegister() throws Exception {
//        Hotel newFlight = new Hotel();
//        newFlight.setCurrent_num(11);
//        newFlight.setDate("1991-11-11");
//        //newFlight.set
//        hotelRegistration.register(newFlight);
//        assertNotNull(newFlight.getId());
//        log.info(newFlight.getFlight_no() + " was persisted with id " + newFlight.getId());
//    }

}
