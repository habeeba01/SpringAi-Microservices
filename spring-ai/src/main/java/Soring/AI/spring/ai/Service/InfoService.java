package Soring.AI.spring.ai.Service;

import Soring.AI.spring.ai.Models.Infos;
import Soring.AI.spring.ai.Repo.InfoRepo;
import org.eclipse.serializer.reflect.ClassLoaderProvider;
import org.eclipse.store.storage.embedded.types.EmbeddedStorage;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfoService {
    @Autowired
    private InfoRepo infoRepo;
    @Autowired
    private EmbeddedStorageManager embeddedStorageManager;
    @Autowired
    private Infos infos;

    public InfoService(){
        infos=new Infos();
        embeddedStorageManager= EmbeddedStorage.Foundation().onConnectionFoundation(cf -> cf.setClassLoaderProvider(ClassLoaderProvider.New(Thread.currentThread().getContextClassLoader()))).start(infos);
        //initDemoData();
    }
    public List<Infos> getAll(){
        return infoRepo.findAll();
    }
    /*public BookingDetails getBookingDetails(String bookingNumber, String firstName, String lastName) {
        var booking = findBooking(bookingNumber, firstName, lastName);
        return toBookingDetails(booking);
    }
    public List<BookingDetails> getBookings() {
        return db.getBookings().stream().map(this::toBookingDetails).toList();
    }

    private BookingDetails toBookingDetails(Booking booking){
        return new BookingDetails(
                booking.getBookingNumber(),
                booking.getCustomer().getFirstName(),
                booking.getCustomer().getLastName(),
                booking.getBookingFrom(),
                booking.getBookingTo(),
                booking.getBookingStatus()
        );
    }*/

}
