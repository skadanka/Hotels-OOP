import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Booking implements  ITestable{
    private Date date;
    private Room room;
    private ArrayList<HotelService> services;
    private Reservation reservation;
    private Review review;


    public Booking(Date a_date, Room a_room){
        date = a_date;
        room = a_room;
        services = new ArrayList<HotelService>();
    }

    public void addService(HotelService s){
        services.add(s);
    }

    public void addReview(Review a_review) {review  = a_review; }

    public void addReservation(Reservation r){
        reservation = r;
    }

    public void assignRoom(Room room){
        this.room = room;
    }


    // getters

    public Date getDate() {
        return date;
    }

    public Room getRoom() {
        return room;
    }

    public ArrayList<HotelService> getServices() {
        return services;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public Review getReview() {
        return review;
    }


    @Override
    public boolean checkConstraints() {
        /**
         * Constraint 3
         */
        if (this.room != null) {
            if (this.room.getHotel() != this.reservation.getReservationSet().getHotel())
                return false;

            /**
             * Constarint 8
             */
            if (this.reservation.getRoomCategory().getType().equals(RoomCategory.RoomType.SUITE))
            {
                if (this.room.getRoomCategory().getType().equals(RoomCategory.RoomType.BASIC))
                    return false;
            } else if (this.reservation.getRoomCategory().getType().equals(RoomCategory.RoomType.VIP)) {
                if (!(this.room.getRoomCategory().getType().equals(RoomCategory.RoomType.VIP)))
                    return false;
            }

        }

        /**
         * Constraint 9
         */
        for (HotelService s : this.getServices()){
            if (s.getService() instanceof VipService)
            {
                if (this.review == null)
                    return false;
            }
        }

        /**
         * Constraint 13
         */

        if (this.room != null)
        {
            for (HotelService hotelService : this.getServices())
            {
                if (!(this.room.getHotel().getServices().containsValue(hotelService)))
                    return false;
            }
        }


//        for (HotelService hs : this.services)
//        {
//            if (!(hs.getHotel().equals(this.reservation.getReservationSet().getHotel())))
//            {
//                return false;
//            }
//        }



        return true;

    }

    public static boolean checkAllIntancesConstraints(Model model){
        return true;
    }
}
