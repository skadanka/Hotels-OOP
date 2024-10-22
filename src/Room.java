import java.util.Date;
import java.util.HashMap;

public class Room implements  ITestable{
    private RoomCategory roomCategory;
    private HashMap<Date,Booking> bookings;
    private int number;
    private Hotel hotel;


    public Room(int number){
        this.number = number;
        bookings = new HashMap<Date,Booking>();
    }

    public void setHotel(Hotel h){ hotel = h; }

    public void asignRoomCategory(RoomCategory roomCategory){
        this.roomCategory = roomCategory;
    }

    public void addBooking(Booking booking, Date date) {
        bookings.put(date, booking);
    }

    public RoomCategory getRoomCategory() {
        return roomCategory;
    }

    public HashMap<Date, Booking> getBookings() {
        return bookings;
    }

    public int getNumber() {
        return number;
    }

    public Hotel getHotel(){ return hotel; }

    @Override
    public boolean checkConstraints() {

        /**
         * Constraint 5
         */

        if (this.roomCategory.getType().equals(RoomCategory.RoomType.VIP))
        {
            for (Booking b : this.getBookings().values())
            {
                if (b.getServices() != null)
                {
                    for (HotelService s : b.getServices())
                    {
                        if (!(s.getService() instanceof VipService))
                            return false;
                    }
                }
            }
        }


        return true;
    }

    public static boolean checkAllIntancesConstraints(Model model){
        return true;
    }
}