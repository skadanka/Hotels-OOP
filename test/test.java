import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;



public class test {


    Model m;

    @BeforeEach
    public void init() {
        m = new Model();
    }

    @Test
    public void constraint1Success1() {
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Beer Sheva", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        Group group2 = new Group(2);
        m.addObjectToModel(group2);
        Hotel hotel2 = new Hotel("Beer Sheva", "Atika", 2);
        m.addObjectToModel(hotel2);
        m.create_link_group_hotel(hotel1, group1);
        m.create_link_group_hotel(hotel2, group2);
        assertTrue(m.checkModelConstraints(), "Failed check 2 different group in the same city.");
    }

    @Test
    public void constraint1Success2() {
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Beer Sheva", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        Hotel hotel2 = new Hotel("Tel Aviv", "Leonardo", 2);
        m.addObjectToModel(hotel2);
        m.create_link_group_hotel(hotel1, group1);
        m.create_link_group_hotel(hotel2, group1);
        assertTrue(m.checkModelConstraints(), "Failed check 2 different city's in the same group.");
    }

    @Test
    public void constraint1Failed() {
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Tel Aviv", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        Hotel hotel2 = new Hotel("Tel Aviv", "Dan", 2);
        m.addObjectToModel(hotel2);
        m.create_link_group_hotel(hotel1, group1);
        m.create_link_group_hotel(hotel2, group1);
        assertFalse(m.checkModelConstraints(), "Failed 2 hotels from the same group in the same city.");
    }

    @Test
    public void constraint2Success() {

        Date or1 = Model.getDateFromString("22-04-2022");
        Date rq1 = Model.getDateFromString("24-04-2022");
        Date or2 = Model.getDateFromString("22-05-2022");
        Date rq2 = Model.getDateFromString("24-05-2022");
        Date or3 = Model.getDateFromString("22-06-2021");
        Date rq3 = Model.getDateFromString("24-06-2021");
        Date or4 = Model.getDateFromString("22-03-2020");
        Date rq4 = Model.getDateFromString("24-03-2020");
        Date or5 = Model.getDateFromString("28-05-2021");
        Date rq5 = Model.getDateFromString("30-05-2021");

        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Tel Aviv", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);
        Client c = new Client(1, 20, "Shay", "Haifa");
        m.addObjectToModel(c);
        ReservationSet rs = new ReservationSet();
        m.addObjectToModel(rs);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        RoomCategory rc1 = new RoomCategory(100, RoomCategory.RoomType.VIP);
        RoomCategory rc2 = new RoomCategory(50, RoomCategory.RoomType.BASIC);

        m.addObjectToModel(rc1);
        Reservation r1 = new Reservation(or1, rq1, 1);
        r1.addRoomCategory(rc1);
        r1.setReservationSet(rs);
        m.create_link_reservation_roomCategory(r1, rc1);
        m.create_link_reservationSet_reservation(rs, r1);
        Reservation r2 = new Reservation(or2, rq2, 2);
        r2.addRoomCategory(rc1);
        r2.setReservationSet(rs);
        m.create_link_reservation_roomCategory(r2, rc2);
        m.create_link_reservationSet_reservation(rs, r2);
        Reservation r3 = new Reservation(or3, rq3, 3);
        r3.addRoomCategory(rc1);
        r3.setReservationSet(rs);
        m.create_link_reservation_roomCategory(r3, rc2);
        m.create_link_reservationSet_reservation(rs, r3);
        Reservation r4 = new Reservation(or4, rq4, 4);
        r4.setReservationSet(rs);
        r4.addRoomCategory(rc1);
        m.create_link_reservation_roomCategory(r4, rc2);
        m.create_link_reservationSet_reservation(rs, r4);
        Reservation r5 = new Reservation(or5, rq5, 5);
        r5.setReservationSet(rs);
        r5.addRoomCategory(rc1);
        m.create_link_reservation_roomCategory(r5, rc2);
        m.create_link_reservationSet_reservation(rs, r5);
        m.create_link_reservationSet_reservation(rs, r1);
        m.create_link_reservationSet_reservation(rs, r2);
        m.create_link_reservationSet_reservation(rs, r3);
        m.create_link_reservationSet_reservation(rs, r4);
        m.create_link_reservationSet_reservation(rs, r5);


        Room room1 = new Room(1);
        hotel1.addRoom(1, room1);
        room1.asignRoomCategory(rc1);
        m.addObjectToModel(room1);
        Booking b = new Booking(rq1, room1);
        m.addObjectToModel(b);
        room1.addBooking(b, rq1);
        b.assignRoom(room1);
        m.create_link_reservation_booking(b, r1);
        b.addReservation(r1);
        m.addObjectToModel(r1);
        m.create_link_room_Booking(room1, b);
        m.create_link_room_roomCategory(room1, rc1);
        m.create_link_hotel_room(room1, hotel1);


        for (int i = 0; i < 15; i++) {
            Room room = new Room(i + 10);
            m.addObjectToModel(room);
            hotel1.addRoom(i + 10, room);
            room.asignRoomCategory(rc2);
            room.setHotel(hotel1);
            m.create_link_hotel_room(room, hotel1);

        }

        b.addReservation(r1);
        assertTrue(m.checkModelConstraints(), "Failed - client didnt got vip room in non of its visits....");

    }

    @Test
    public void constraint2Failed() {


        Date or1 = Model.getDateFromString("22-04-2022");
        Date rq1 = Model.getDateFromString("24-04-2022");
        Date or2 = Model.getDateFromString("22-05-2022");
        Date rq2 = Model.getDateFromString("24-05-2022");
        Date or3 = Model.getDateFromString("22-06-2021");
        Date rq3 = Model.getDateFromString("24-06-2021");
        Date or4 = Model.getDateFromString("22-03-2020");
        Date rq4 = Model.getDateFromString("24-03-2020");
        Date or5 = Model.getDateFromString("28-05-2021");
        Date rq5 = Model.getDateFromString("30-05-2021");

        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Tel Aviv", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);
        Client c = new Client(1, 20, "Shay", "Haifa");
        m.addObjectToModel(c);
        ReservationSet rs = new ReservationSet();
        m.addObjectToModel(rs);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        RoomCategory rc1 = new RoomCategory(100, RoomCategory.RoomType.SUITE);
        RoomCategory rc2 = new RoomCategory(50, RoomCategory.RoomType.BASIC);

        m.addObjectToModel(rc1);
        Reservation r1 = new Reservation(or1, rq1, 1);
        r1.addRoomCategory(rc1);
        r1.setReservationSet(rs);
        m.create_link_reservation_roomCategory(r1, rc1);
        m.create_link_reservationSet_reservation(rs, r1);
        Reservation r2 = new Reservation(or2, rq2, 2);
        r2.addRoomCategory(rc1);
        r2.setReservationSet(rs);
        m.create_link_reservation_roomCategory(r2, rc2);
        m.create_link_reservationSet_reservation(rs, r2);
        Reservation r3 = new Reservation(or3, rq3, 3);
        r3.addRoomCategory(rc1);
        r3.setReservationSet(rs);
        m.create_link_reservation_roomCategory(r3, rc2);
        m.create_link_reservationSet_reservation(rs, r3);
        Reservation r4 = new Reservation(or4, rq4, 4);
        r4.setReservationSet(rs);
        r4.addRoomCategory(rc1);
        m.create_link_reservation_roomCategory(r4, rc2);
        m.create_link_reservationSet_reservation(rs, r4);
        Reservation r5 = new Reservation(or5, rq5, 5);
        r5.setReservationSet(rs);
        r5.addRoomCategory(rc1);
        m.create_link_reservation_roomCategory(r5, rc2);
        m.create_link_reservationSet_reservation(rs, r5);
        m.create_link_reservationSet_reservation(rs, r1);
        m.create_link_reservationSet_reservation(rs, r2);
        m.create_link_reservationSet_reservation(rs, r3);
        m.create_link_reservationSet_reservation(rs, r4);
        m.create_link_reservationSet_reservation(rs, r5);


        Room room1 = new Room(1);
        hotel1.addRoom(1, room1);
        room1.asignRoomCategory(rc1);
        m.addObjectToModel(room1);
        Booking b = new Booking(rq1, room1);
        m.addObjectToModel(b);
        room1.addBooking(b, rq1);
        m.create_link_reservation_booking(b, r1);
        b.addReservation(r1);
        m.create_link_hotel_room(room1, hotel1);
        m.addObjectToModel(r1);

        for (int i = 0; i < 15; i++) {
            Room room = new Room(i + 10);
            m.addObjectToModel(room);
            hotel1.addRoom(i + 10, room);
            room.asignRoomCategory(rc2);
            room.setHotel(hotel1);
        }

        b.addReservation(r1);

        assertFalse(m.checkModelConstraints(), "Failed - client didnt got vip room in non of its visits....");

    }

    @Test
    public void constraint3Success() {
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Beer Sheva", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);

        Hotel hotel2 = new Hotel("Tel Aviv", "Leonardo", 2);
        m.addObjectToModel(hotel2);
        m.create_link_group_hotel(hotel2, group1);

        RoomCategory rc1 = new RoomCategory(50, RoomCategory.RoomType.BASIC);
        m.addObjectToModel(rc1);
        Client c = new Client(1, 20, "Shay", "Haifa");
        m.addObjectToModel(c);
        ReservationSet rs = new ReservationSet();
        m.addObjectToModel(rs);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        Room room1 = new Room(1);
        hotel1.addRoom(1, room1);
        room1.asignRoomCategory(rc1);
        room1.setHotel(hotel1);
        m.addObjectToModel(room1);

        Room room2 = new Room(1);
        hotel2.addRoom(1, room2);
        room2.asignRoomCategory(rc1);
        room2.setHotel(hotel2);
        m.addObjectToModel(room1);
        m.create_link_hotel_room(room2, hotel2);
        m.create_link_room_roomCategory(room2, rc1);

        Date or1 = Model.getDateFromString("22-04-2022");
        Date rq1 = Model.getDateFromString("24-04-2022");
        Reservation r1 = new Reservation(or1, rq1, 1);
        r1.addRoomCategory(rc1);
        r1.setReservationSet(rs);
        m.create_link_reservation_roomCategory(r1, rc1);
        m.create_link_reservationSet_reservation(rs, r1);
        Booking b = new Booking(rq1, room1);
        m.addObjectToModel(b);
        m.create_link_reservation_booking(b, r1);
        b.addReservation(r1);
        b.assignRoom(room1);
        m.create_link_room_Booking(room1, b);

        assertTrue(m.checkModelConstraints(), "Failed check that room belong to hotel.");
    }

    @Test
    public void constraint3Failed() {
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Beer Sheva", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);

        Hotel hotel2 = new Hotel("Tel Aviv", "Leonardo", 2);
        m.addObjectToModel(hotel2);
        m.create_link_group_hotel(hotel2, group1);

        RoomCategory rc1 = new RoomCategory(50, RoomCategory.RoomType.BASIC);
        m.addObjectToModel(rc1);
        Client c = new Client(1, 20, "Shay", "Haifa");
        m.addObjectToModel(c);
        ReservationSet rs = new ReservationSet();
        m.addObjectToModel(rs);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        Room room1 = new Room(1);
        hotel1.addRoom(1, room1);
        room1.asignRoomCategory(rc1);
        room1.setHotel(hotel1);
        m.addObjectToModel(room1);

        Room room2 = new Room(1);
        hotel2.addRoom(1, room2);
        room2.asignRoomCategory(rc1);
        room2.setHotel(hotel2);
        m.addObjectToModel(room1);
        m.create_link_hotel_room(room2, hotel2);
        m.create_link_room_roomCategory(room2, rc1);

        Date or1 = Model.getDateFromString("22-04-2022");
        Date rq1 = Model.getDateFromString("24-04-2022");
        Reservation r1 = new Reservation(or1, rq1, 1);
        r1.addRoomCategory(rc1);
        r1.setReservationSet(rs);
        m.create_link_reservation_roomCategory(r1, rc1);
        m.create_link_reservationSet_reservation(rs, r1);
        Booking b = new Booking(rq1, room1);
        m.addObjectToModel(b);
        m.create_link_reservation_booking(b, r1);
        b.addReservation(r1);
        b.assignRoom(room1);
        m.create_link_room_Booking(room2, b);

        assertFalse(m.checkModelConstraints(), "Failed check that room belong to hotel.");
    }


    @Test
    public void constraint4Success() {
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Beer Sheva", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);
        Hotel hotel2 = new Hotel("Tel Aviv", "Leonardo", 2);
        m.addObjectToModel(hotel2);
        m.create_link_group_hotel(hotel2, group1);
        Client c = new Client(1, 20, "Shay", "Haifa");
        m.addObjectToModel(c);
        ReservationSet rs = new ReservationSet();
        m.addObjectToModel(rs);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        Service cs = new CommunityService("Service 1");
        HotelService hs1 = new HotelService(150, 3);
        hotel1.addService(cs, hs1);
        hs1.assignService(cs);
        m.create_link_hotel_service_hotelService(hotel1, cs, hs1);
        HotelService hs2 = new HotelService(100, 3);
        hotel2.addService(cs, hs2);
        m.create_link_hotel_service_hotelService(hotel2, cs, hs2);

        assertTrue(m.checkModelConstraints(), "Failed - same facilities at hotel.");
    }

    @Test
    public void constraint4Failed() {
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Beer Sheva", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);
        Hotel hotel2 = new Hotel("Tel Aviv", "Leonardo", 2);
        m.addObjectToModel(hotel2);
        m.create_link_group_hotel(hotel2, group1);
        Client c = new Client(1, 20, "Shay", "Haifa");
        m.addObjectToModel(c);
        ReservationSet rs = new ReservationSet();
        m.addObjectToModel(rs);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        Service cs = new CommunityService("Service 1");
        HotelService hs1 = new HotelService(150, 3);
        hotel1.addService(cs, hs1);
        hs1.assignService(cs);
        m.create_link_hotel_service_hotelService(hotel1, cs, hs1);

        assertFalse(m.checkModelConstraints(), "Failed - different facilities at hotel.");
    }

    @Test
    public void constrain5Success() {
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Beer Sheva", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);
        Client c = new Client(1, 20, "Shay", "Haifa");
        m.addObjectToModel(c);
        ReservationSet rs = new ReservationSet();
        m.addObjectToModel(rs);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        // reservation
        Date or1 = Model.getDateFromString("22-04-2022");
        Date rq1 = Model.getDateFromString("24-04-2022");
        RoomCategory rc = new RoomCategory(2000, RoomCategory.RoomType.VIP);
        m.addObjectToModel(rc);
        Reservation r = new Reservation(or1, rq1, 1);
        m.addObjectToModel(r);
        r.setReservationSet(rs);
        r.addRoomCategory(rc);
        rc.addReservation(r);
        m.create_link_reservation_roomCategory(r, rc);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        HotelService hs = new HotelService(1000, 5);
        m.addObjectToModel(hs);
        Service s = new VipService("VIP SERVICE");
        m.addObjectToModel(s);
        hs.assignService(s);
        hs.setHotel(hotel1);
        s.addHotel(hotel1, hs);
        hotel1.addReservationSet(c, rs);
        hotel1.addService(s, hs);
        m.create_link_hotel_service_hotelService(hotel1, s, hs);
        /// room
        Room room = new Room(1);
        m.addObjectToModel(room);
        room.setHotel(hotel1);
        room.asignRoomCategory(rc);
        hotel1.addRoom(1, room);
        rc.addReservation(r);
        rc.addRoom(room);
        m.create_link_room_roomCategory(room, rc);
        m.create_link_hotel_room(room, hotel1);
        ///
        Booking b = new Booking(rq1, room);
        m.addObjectToModel(b);
        b.addReservation(r);
        b.addService(hs);
        b.assignRoom(room);
        r.addBooking(b);
        hs.addBooking(b);
        room.addBooking(b, rq1);
        m.create_link_reservation_booking(b, r);
        m.create_link_room_Booking(room, b);
        m.create_link_hotelService_booking(hs, b);
        hotel1.addReservationSet(c, rs);
        rs.addReservation(r);
        RoomCategory roomCategory3 = new RoomCategory(100, RoomCategory.RoomType.SUITE);
        for (int i = 2; i < 30; i++) {
            Room room4 = new Room(i);
            room4.asignRoomCategory(roomCategory3);
            room4.setHotel(hotel1);
            roomCategory3.addRoom(room4);
            hotel1.addRoom(i, room4);
            m.addObjectToModel(room4);
            m.create_link_room_roomCategory(room4, roomCategory3);
            m.create_link_hotel_room(room4, hotel1);
        }
        // Review
        Date reviewDate = Model.getDateFromString("27-04-2022");

        Review review = new Review(10, "Good Hotel", reviewDate);
        review.addBooking(b);
        b.addReview(review);
        m.addObjectToModel(review);
        m.create_link_booking_review(b, review);
        assertTrue(m.checkModelConstraints(), "Failed - booking not uses vip facilities.");
    }

    @Test
    public void constrain5Failed() {
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Beer Sheva", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);
        Client c = new Client(1, 20, "Shay", "Haifa");
        m.addObjectToModel(c);
        ReservationSet rs = new ReservationSet();
        m.addObjectToModel(rs);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        // reservation
        Date or1 = Model.getDateFromString("22-04-2022");
        Date rq1 = Model.getDateFromString("24-04-2022");
        RoomCategory rc = new RoomCategory(2000, RoomCategory.RoomType.VIP);
        m.addObjectToModel(rc);
        Reservation r = new Reservation(or1, rq1, 1);
        m.addObjectToModel(r);
        r.setReservationSet(rs);
        r.addRoomCategory(rc);
        rc.addReservation(r);
        m.create_link_reservation_roomCategory(r, rc);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        HotelService hs = new HotelService(1000, 5);
        m.addObjectToModel(hs);
        Service s = new RegularService("REGULAR SERVICE");
        m.addObjectToModel(s);
        hs.assignService(s);
        hs.setHotel(hotel1);
        s.addHotel(hotel1, hs);
        hotel1.addReservationSet(c, rs);
        hotel1.addService(s, hs);
        m.create_link_hotel_service_hotelService(hotel1, s, hs);
        /// room
        Room room = new Room(1);
        m.addObjectToModel(room);
        room.setHotel(hotel1);
        room.asignRoomCategory(rc);
        hotel1.addRoom(1, room);
        rc.addReservation(r);
        rc.addRoom(room);
        m.create_link_room_roomCategory(room, rc);
        m.create_link_hotel_room(room, hotel1);
        ///
        Booking b = new Booking(rq1, room);
        m.addObjectToModel(b);
        b.addReservation(r);
        b.addService(hs);
        b.assignRoom(room);
        r.addBooking(b);
        hs.addBooking(b);
        room.addBooking(b, rq1);
        m.create_link_reservation_booking(b, r);
        m.create_link_room_Booking(room, b);
        m.create_link_hotelService_booking(hs, b);
        hotel1.addReservationSet(c, rs);
        rs.addReservation(r);
        RoomCategory roomCategory3 = new RoomCategory(100, RoomCategory.RoomType.SUITE);
        for (int i = 2; i < 30; i++) {
            Room room4 = new Room(i);
            room4.asignRoomCategory(roomCategory3);
            room4.setHotel(hotel1);
            roomCategory3.addRoom(room4);
            hotel1.addRoom(i, room4);
            m.addObjectToModel(room4);
            m.create_link_room_roomCategory(room4, roomCategory3);
            m.create_link_hotel_room(room4, hotel1);
        }
        // Review
        Date reviewDate = Model.getDateFromString("27-04-2022");

        Review review = new Review(10, "Good Hotel", reviewDate);
        review.addBooking(b);
        b.addReview(review);
        m.addObjectToModel(review);
        m.create_link_booking_review(b, review);
        assertFalse(m.checkModelConstraints(), "Failed - booking not uses vip facilities.");
    }

    @Test
    public void constrain6Success() {
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Beer Sheva", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);

        // room category
        RoomCategory roomCategory1 = new RoomCategory(100, RoomCategory.RoomType.BASIC);
        RoomCategory roomCategory2 = new RoomCategory(100, RoomCategory.RoomType.VIP);
        m.addObjectToModel(roomCategory1);
        m.addObjectToModel(roomCategory2);
        ///

        Room room1 = new Room(1);
        room1.asignRoomCategory(roomCategory2);
        room1.setHotel(hotel1);
        roomCategory2.addRoom(room1);
        hotel1.addRoom(1, room1);
        m.addObjectToModel(room1);
        m.create_link_hotel_room(room1, hotel1);
        m.create_link_room_roomCategory(room1, roomCategory2);
        ///

        for (int i = 2; i < 22; i++) {
            Room room = new Room(i);
            room.asignRoomCategory(roomCategory1);
            room.setHotel(hotel1);
            roomCategory1.addRoom(room);
            hotel1.addRoom(i, room);
            m.addObjectToModel(room);
            m.create_link_room_roomCategory(room, roomCategory1);
            m.create_link_hotel_room(room, hotel1);
        }

        assertTrue(m.checkModelConstraints(), "Failed - vip 10% from all rooms.");

    }

    @Test
    public void constrain6Failed() {
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Beer Sheva", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);

        // room category
        RoomCategory roomCategory1 = new RoomCategory(100, RoomCategory.RoomType.BASIC);
        RoomCategory roomCategory2 = new RoomCategory(100, RoomCategory.RoomType.VIP);
        m.addObjectToModel(roomCategory1);
        m.addObjectToModel(roomCategory2);
        ///

        Room room1 = new Room(1);
        room1.asignRoomCategory(roomCategory2);
        room1.setHotel(hotel1);
        roomCategory2.addRoom(room1);
        hotel1.addRoom(1, room1);
        m.addObjectToModel(room1);
        m.create_link_hotel_room(room1, hotel1);
        m.create_link_room_roomCategory(room1, roomCategory2);
        ///

        assertFalse(m.checkModelConstraints(), "Failed - vip 10% from all rooms.");

    }

    @Test
    public void constrain7Success() {
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("LAS VEGAS", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);
        /// client
        Client c = new Client(1, 25, "Shay ", "BeerSheva");
        m.addObjectToModel(c);
        /// resrvation set
        ReservationSet rs = new ReservationSet();
        rs.setClient(c);
        rs.setHotel(hotel1);
        m.addObjectToModel(rs);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        // room category
        RoomCategory roomCategory1 = new RoomCategory(100, RoomCategory.RoomType.BASIC);
        m.addObjectToModel(roomCategory1);
        ///

        // resrvation
        Date or1 = Model.getDateFromString("22-04-2022");
        Date rq1 = Model.getDateFromString("24-04-2022");

        Reservation r = new Reservation(or1, rq1, 1);
        r.addRoomCategory(roomCategory1);
        r.setReservationSet(rs);
        m.addObjectToModel(r);
        m.create_link_reservationSet_reservation(rs, r);
        m.create_link_reservation_roomCategory(r, roomCategory1);

        ///

        assertTrue(m.checkModelConstraints(), "Failed - LAS VEGAS - AGE.");

    }

    @Test
    public void constrain7Failed() {
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("LAS VEGAS", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);
        /// client
        Client c = new Client(1, 20, "Shay ", "BeerSheva");
        m.addObjectToModel(c);
        /// resrvation set
        ReservationSet rs = new ReservationSet();
        rs.setClient(c);
        rs.setHotel(hotel1);
        m.addObjectToModel(rs);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        // room category
        RoomCategory roomCategory1 = new RoomCategory(100, RoomCategory.RoomType.BASIC);
        m.addObjectToModel(roomCategory1);
        ///

        // resrvation
        Date or1 = Model.getDateFromString("22-04-2022");
        Date rq1 = Model.getDateFromString("24-04-2022");

        Reservation r = new Reservation(or1, rq1, 1);
        r.addRoomCategory(roomCategory1);
        r.setReservationSet(rs);
        m.addObjectToModel(r);
        m.create_link_reservationSet_reservation(rs, r);
        m.create_link_reservation_roomCategory(r, roomCategory1);

        ///

        assertFalse(m.checkModelConstraints(), "Failed - LAS VEGAS - AGE.");

    }

    @Test
    public void constrain8Success1() {
        // success when:
        // basic -> basic, vip, suite
        // suite -> suite, vip
        // vip -> vip
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Beer Sheva", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);
        Client c = new Client(1, 20, "Shay", "Haifa");
        m.addObjectToModel(c);
        ReservationSet rs = new ReservationSet();
        m.addObjectToModel(rs);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        // reservation
        Date or1 = Model.getDateFromString("22-04-2022");
        Date rq1 = Model.getDateFromString("24-04-2022");
        RoomCategory rc = new RoomCategory(2000, RoomCategory.RoomType.BASIC);
        m.addObjectToModel(rc);
        Reservation r = new Reservation(or1, rq1, 1);
        m.addObjectToModel(r);
        r.setReservationSet(rs);
        r.addRoomCategory(rc);
        rc.addReservation(r);
        m.create_link_reservation_roomCategory(r, rc);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);

        /// room
        Room room1 = new Room(1);
        m.addObjectToModel(room1);
        room1.setHotel(hotel1);
        room1.asignRoomCategory(rc);
        hotel1.addRoom(1, room1);
        rc.addReservation(r);
        rc.addRoom(room1);
        m.create_link_room_roomCategory(room1, rc);
        m.create_link_hotel_room(room1, hotel1);

        // room 2 - fancy
        RoomCategory rc2 = new RoomCategory(2000, RoomCategory.RoomType.SUITE);
        m.addObjectToModel(rc2);
        Room room2 = new Room(2);
        m.addObjectToModel(room2);
        room1.setHotel(hotel1);
        room1.asignRoomCategory(rc2);
        hotel1.addRoom(2, room2);
        rc.addReservation(r);
        rc.addRoom(room2);
        m.create_link_room_roomCategory(room2, rc2);
        m.create_link_hotel_room(room2, hotel1);

        ///
        Booking b = new Booking(rq1, room2);
        m.addObjectToModel(b);
        b.addReservation(r);
        b.assignRoom(room2);
        room2.addBooking(b, rq1);
        m.create_link_reservation_booking(b, r);
        m.create_link_room_Booking(room2, b);

        assertTrue(m.checkModelConstraints(), "Failed - booking BASIC -> SUITE.");
    }

    @Test
    public void constrain8Success2() {
        // success when:
        // basic -> basic, vip, suite
        // suite -> suite, vip
        // vip -> vip
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Beer Sheva", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);
        Client c = new Client(1, 20, "Shay", "Haifa");
        m.addObjectToModel(c);
        ReservationSet rs = new ReservationSet();
        m.addObjectToModel(rs);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        // reservation
        Date or1 = Model.getDateFromString("22-04-2022");
        Date rq1 = Model.getDateFromString("24-04-2022");
        RoomCategory rc = new RoomCategory(2000, RoomCategory.RoomType.BASIC);
        m.addObjectToModel(rc);
        Reservation r = new Reservation(or1, rq1, 1);
        m.addObjectToModel(r);
        r.setReservationSet(rs);
        r.addRoomCategory(rc);
        rc.addReservation(r);
        m.create_link_reservation_roomCategory(r, rc);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);

        /// room
        Room room1 = new Room(1);
        m.addObjectToModel(room1);
        room1.setHotel(hotel1);
        room1.asignRoomCategory(rc);
        hotel1.addRoom(1, room1);
        rc.addReservation(r);
        rc.addRoom(room1);
        m.create_link_room_roomCategory(room1, rc);
        m.create_link_hotel_room(room1, hotel1);

        // room 2 - fancy
        RoomCategory rc2 = new RoomCategory(2000, RoomCategory.RoomType.BASIC);
        m.addObjectToModel(rc2);
        Room room2 = new Room(2);
        m.addObjectToModel(room2);
        room1.setHotel(hotel1);
        room1.asignRoomCategory(rc2);
        hotel1.addRoom(2, room2);
        rc.addReservation(r);
        rc.addRoom(room2);
        m.create_link_room_roomCategory(room2, rc2);
        m.create_link_hotel_room(room2, hotel1);

        ///
        Booking b = new Booking(rq1, room2);
        m.addObjectToModel(b);
        b.addReservation(r);
        b.assignRoom(room2);
        room2.addBooking(b, rq1);
        m.create_link_reservation_booking(b, r);
        m.create_link_room_Booking(room2, b);

        assertTrue(m.checkModelConstraints(), "Failed - booking BASIC -> Basic.");
    }

    @Test
    public void constrain8Success3() {
        // success when:
        // basic -> basic, vip, suite
        // suite -> suite, vip
        // vip -> vip
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Beer Sheva", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);
        Client c = new Client(1, 20, "Shay", "Haifa");
        m.addObjectToModel(c);
        ReservationSet rs = new ReservationSet();
        m.addObjectToModel(rs);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        // reservation
        Date or1 = Model.getDateFromString("22-04-2022");
        Date rq1 = Model.getDateFromString("24-04-2022");
        RoomCategory rc = new RoomCategory(2000, RoomCategory.RoomType.BASIC);
        m.addObjectToModel(rc);
        Reservation r = new Reservation(or1, rq1, 1);
        m.addObjectToModel(r);
        r.setReservationSet(rs);
        r.addRoomCategory(rc);
        rc.addReservation(r);
        m.create_link_reservation_roomCategory(r, rc);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);

        /// room
        Room room1 = new Room(1);
        m.addObjectToModel(room1);
        room1.setHotel(hotel1);
        room1.asignRoomCategory(rc);
        hotel1.addRoom(1, room1);
        rc.addReservation(r);
        rc.addRoom(room1);
        m.create_link_room_roomCategory(room1, rc);
        m.create_link_hotel_room(room1, hotel1);

        // room 2 - fancy
        RoomCategory rc2 = new RoomCategory(2000, RoomCategory.RoomType.VIP);
        m.addObjectToModel(rc2);
        Room room2 = new Room(2);
        m.addObjectToModel(room2);
        room1.setHotel(hotel1);
        room1.asignRoomCategory(rc2);
        hotel1.addRoom(2, room2);
        rc.addReservation(r);
        rc.addRoom(room2);
        m.create_link_room_roomCategory(room2, rc2);
        m.create_link_hotel_room(room2, hotel1);

        ///
        Booking b = new Booking(rq1, room2);
        m.addObjectToModel(b);
        b.addReservation(r);
        b.assignRoom(room2);
        room2.addBooking(b, rq1);
        m.create_link_reservation_booking(b, r);
        m.create_link_room_Booking(room2, b);

        RoomCategory roomCategory3 = new RoomCategory(100, RoomCategory.RoomType.SUITE);
        for (int i = 2; i < 30; i++) {
            Room room = new Room(i);
            room.asignRoomCategory(roomCategory3);
            room.setHotel(hotel1);
            roomCategory3.addRoom(room);
            hotel1.addRoom(i, room);
            m.addObjectToModel(room);
            m.create_link_room_roomCategory(room, roomCategory3);
            m.create_link_hotel_room(room1, hotel1);
        }

        assertTrue(m.checkModelConstraints(), "Failed - booking BASIC -> VIP.");
    }

    @Test
    public void constrain8Success4() {
        // success when:
        // basic -> basic, vip, suite
        // suite -> suite, vip
        // vip -> vip
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Beer Sheva", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);
        Client c = new Client(1, 20, "Shay", "Haifa");
        m.addObjectToModel(c);
        ReservationSet rs = new ReservationSet();
        m.addObjectToModel(rs);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        // reservation
        Date or1 = Model.getDateFromString("22-04-2022");
        Date rq1 = Model.getDateFromString("24-04-2022");
        RoomCategory rc = new RoomCategory(2000, RoomCategory.RoomType.SUITE);
        m.addObjectToModel(rc);
        Reservation r = new Reservation(or1, rq1, 1);
        m.addObjectToModel(r);
        r.setReservationSet(rs);
        r.addRoomCategory(rc);
        rc.addReservation(r);
        m.create_link_reservation_roomCategory(r, rc);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);

        /// room
        Room room1 = new Room(1);
        m.addObjectToModel(room1);
        room1.setHotel(hotel1);
        room1.asignRoomCategory(rc);
        hotel1.addRoom(1, room1);
        rc.addReservation(r);
        rc.addRoom(room1);
        m.create_link_room_roomCategory(room1, rc);
        m.create_link_hotel_room(room1, hotel1);

        // room 2 - fancy
        RoomCategory rc2 = new RoomCategory(2000, RoomCategory.RoomType.SUITE);
        m.addObjectToModel(rc2);
        Room room2 = new Room(2);
        m.addObjectToModel(room2);
        room1.setHotel(hotel1);
        room1.asignRoomCategory(rc2);
        hotel1.addRoom(2, room2);
        rc.addReservation(r);
        rc.addRoom(room2);
        m.create_link_room_roomCategory(room2, rc2);
        m.create_link_hotel_room(room2, hotel1);

        ///
        Booking b = new Booking(rq1, room2);
        m.addObjectToModel(b);
        b.addReservation(r);
        b.assignRoom(room2);
        room2.addBooking(b, rq1);
        m.create_link_reservation_booking(b, r);
        m.create_link_room_Booking(room2, b);

        assertTrue(m.checkModelConstraints(), "Failed - booking SUITE -> SUITE.");
    }

    @Test
    public void constrain8Success5() {
        // success when:
        // basic -> basic, vip, suite
        // suite -> suite, vip
        // vip -> vip
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Beer Sheva", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);
        Client c = new Client(1, 20, "Shay", "Haifa");
        m.addObjectToModel(c);
        ReservationSet rs = new ReservationSet();
        m.addObjectToModel(rs);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        // reservation
        Date or1 = Model.getDateFromString("22-04-2022");
        Date rq1 = Model.getDateFromString("24-04-2022");
        RoomCategory rc = new RoomCategory(2000, RoomCategory.RoomType.BASIC);
        m.addObjectToModel(rc);
        Reservation r = new Reservation(or1, rq1, 1);
        m.addObjectToModel(r);
        r.setReservationSet(rs);
        r.addRoomCategory(rc);
        rc.addReservation(r);
        m.create_link_reservation_roomCategory(r, rc);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);

        /// room
        Room room1 = new Room(1);
        m.addObjectToModel(room1);
        room1.setHotel(hotel1);
        room1.asignRoomCategory(rc);
        hotel1.addRoom(1, room1);
        rc.addReservation(r);
        rc.addRoom(room1);
        m.create_link_room_roomCategory(room1, rc);
        m.create_link_hotel_room(room1, hotel1);

        // room 2 - fancy
        RoomCategory rc2 = new RoomCategory(2000, RoomCategory.RoomType.VIP);
        m.addObjectToModel(rc2);
        Room room2 = new Room(2);
        m.addObjectToModel(room2);
        room1.setHotel(hotel1);
        room1.asignRoomCategory(rc2);
        hotel1.addRoom(2, room2);
        rc.addReservation(r);
        rc.addRoom(room2);
        m.create_link_room_roomCategory(room2, rc2);
        m.create_link_hotel_room(room2, hotel1);

        ///
        Booking b = new Booking(rq1, room2);
        m.addObjectToModel(b);
        b.addReservation(r);
        b.assignRoom(room2);
        room2.addBooking(b, rq1);
        m.create_link_reservation_booking(b, r);
        m.create_link_room_Booking(room2, b);
        RoomCategory roomCategory3 = new RoomCategory(100, RoomCategory.RoomType.SUITE);
        for (int i = 2; i < 30; i++) {
            Room room = new Room(i);
            room.asignRoomCategory(roomCategory3);
            room.setHotel(hotel1);
            roomCategory3.addRoom(room);
            hotel1.addRoom(i, room);
            m.addObjectToModel(room);
            m.create_link_room_roomCategory(room, roomCategory3);
            m.create_link_hotel_room(room1, hotel1);
        }
        assertTrue(m.checkModelConstraints(), "Failed - booking BASIC -> VIP.");
    }

    @Test
    public void constrain8Success6() {
        // success when:
        // basic -> basic, vip, suite
        // suite -> suite, vip
        // vip -> vip
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Beer Sheva", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);
        Client c = new Client(1, 20, "Shay", "Haifa");
        m.addObjectToModel(c);
        ReservationSet rs = new ReservationSet();
        m.addObjectToModel(rs);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        // reservation
        Date or1 = Model.getDateFromString("22-04-2022");
        Date rq1 = Model.getDateFromString("24-04-2022");
        RoomCategory rc = new RoomCategory(2000, RoomCategory.RoomType.VIP);
        m.addObjectToModel(rc);
        Reservation r = new Reservation(or1, rq1, 1);
        m.addObjectToModel(r);
        r.setReservationSet(rs);
        r.addRoomCategory(rc);
        rc.addReservation(r);
        m.create_link_reservation_roomCategory(r, rc);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);

        /// room
        Room room1 = new Room(1);
        m.addObjectToModel(room1);
        room1.setHotel(hotel1);
        room1.asignRoomCategory(rc);
        hotel1.addRoom(1, room1);
        rc.addReservation(r);
        rc.addRoom(room1);
        m.create_link_room_roomCategory(room1, rc);
        m.create_link_hotel_room(room1, hotel1);

        // room 2 - fancy
        RoomCategory rc2 = new RoomCategory(2000, RoomCategory.RoomType.VIP);
        m.addObjectToModel(rc2);
        Room room2 = new Room(2);
        m.addObjectToModel(room2);
        room1.setHotel(hotel1);
        room1.asignRoomCategory(rc2);
        hotel1.addRoom(2, room2);
        rc.addReservation(r);
        rc.addRoom(room2);
        m.create_link_room_roomCategory(room2, rc2);
        m.create_link_hotel_room(room2, hotel1);

        ///
        Booking b = new Booking(rq1, room2);
        m.addObjectToModel(b);
        b.addReservation(r);
        b.assignRoom(room2);
        room2.addBooking(b, rq1);
        m.create_link_reservation_booking(b, r);
        m.create_link_room_Booking(room2, b);
        RoomCategory roomCategory3 = new RoomCategory(100, RoomCategory.RoomType.SUITE);
        for (int i = 2; i < 30; i++) {
            Room room = new Room(i);
            room.asignRoomCategory(roomCategory3);
            room.setHotel(hotel1);
            roomCategory3.addRoom(room);
            hotel1.addRoom(i, room);
            m.addObjectToModel(room);
            m.create_link_room_roomCategory(room, roomCategory3);
            m.create_link_hotel_room(room1, hotel1);
        }
        assertTrue(m.checkModelConstraints(), "Failed - booking VIP -> VIP.");
    }

    @Test
    public void constrain8Failed1() {
        // success when:
        // basic -> basic, vip, suite
        // suite -> suite, vip
        // vip -> vip
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Beer Sheva", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);
        Client c = new Client(1, 20, "Shay", "Haifa");
        m.addObjectToModel(c);
        ReservationSet rs = new ReservationSet();
        m.addObjectToModel(rs);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        // reservation
        Date or1 = Model.getDateFromString("22-04-2022");
        Date rq1 = Model.getDateFromString("24-04-2022");
        RoomCategory rc = new RoomCategory(2000, RoomCategory.RoomType.VIP);
        m.addObjectToModel(rc);
        Reservation r = new Reservation(or1, rq1, 1);
        m.addObjectToModel(r);
        r.setReservationSet(rs);
        r.addRoomCategory(rc);
        rc.addReservation(r);
        m.create_link_reservation_roomCategory(r, rc);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);

        /// room
        Room room1 = new Room(1);
        m.addObjectToModel(room1);
        room1.setHotel(hotel1);
        room1.asignRoomCategory(rc);
        hotel1.addRoom(1, room1);
        rc.addReservation(r);
        rc.addRoom(room1);
        m.create_link_room_roomCategory(room1, rc);
        m.create_link_hotel_room(room1, hotel1);

        // room 2 - fancy
        RoomCategory rc2 = new RoomCategory(2000, RoomCategory.RoomType.SUITE);
        m.addObjectToModel(rc2);
        Room room2 = new Room(2);
        m.addObjectToModel(room2);
        room1.setHotel(hotel1);
        room1.asignRoomCategory(rc2);
        hotel1.addRoom(2, room2);
        rc.addReservation(r);
        rc.addRoom(room2);
        m.create_link_room_roomCategory(room2, rc2);
        m.create_link_hotel_room(room2, hotel1);

        ///
        Booking b = new Booking(rq1, room2);
        m.addObjectToModel(b);
        b.addReservation(r);
        b.assignRoom(room2);
        room2.addBooking(b, rq1);
        m.create_link_reservation_booking(b, r);
        m.create_link_room_Booking(room2, b);
        RoomCategory roomCategory3 = new RoomCategory(100, RoomCategory.RoomType.SUITE);
        for (int i = 2; i < 30; i++) {
            Room room = new Room(i);
            room.asignRoomCategory(roomCategory3);
            room.setHotel(hotel1);
            roomCategory3.addRoom(room);
            hotel1.addRoom(i, room);
            m.addObjectToModel(room);
            m.create_link_room_roomCategory(room, roomCategory3);
            m.create_link_hotel_room(room1, hotel1);
        }
        assertFalse(m.checkModelConstraints(), "Failed - booking VIP -> SUITE.");
    }

    @Test
    public void constrain8Failed2() {
        // success when:
        // basic -> basic, vip, suite
        // suite -> suite, vip
        // vip -> vip
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Beer Sheva", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);
        Client c = new Client(1, 20, "Shay", "Haifa");
        m.addObjectToModel(c);
        ReservationSet rs = new ReservationSet();
        m.addObjectToModel(rs);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        // reservation
        Date or1 = Model.getDateFromString("22-04-2022");
        Date rq1 = Model.getDateFromString("24-04-2022");
        RoomCategory rc = new RoomCategory(2000, RoomCategory.RoomType.VIP);
        m.addObjectToModel(rc);
        Reservation r = new Reservation(or1, rq1, 1);
        m.addObjectToModel(r);
        r.setReservationSet(rs);
        r.addRoomCategory(rc);
        rc.addReservation(r);
        m.create_link_reservation_roomCategory(r, rc);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);

        /// room
        Room room1 = new Room(1);
        m.addObjectToModel(room1);
        room1.setHotel(hotel1);
        room1.asignRoomCategory(rc);
        hotel1.addRoom(1, room1);
        rc.addReservation(r);
        rc.addRoom(room1);
        m.create_link_room_roomCategory(room1, rc);
        m.create_link_hotel_room(room1, hotel1);

        // room 2 - fancy
        RoomCategory rc2 = new RoomCategory(2000, RoomCategory.RoomType.BASIC);
        m.addObjectToModel(rc2);
        Room room2 = new Room(2);
        m.addObjectToModel(room2);
        room1.setHotel(hotel1);
        room1.asignRoomCategory(rc2);
        hotel1.addRoom(2, room2);
        rc.addReservation(r);
        rc.addRoom(room2);
        m.create_link_room_roomCategory(room2, rc2);
        m.create_link_hotel_room(room2, hotel1);

        ///
        Booking b = new Booking(rq1, room2);
        m.addObjectToModel(b);
        b.addReservation(r);
        b.assignRoom(room2);
        room2.addBooking(b, rq1);
        m.create_link_reservation_booking(b, r);
        m.create_link_room_Booking(room2, b);
        RoomCategory roomCategory3 = new RoomCategory(100, RoomCategory.RoomType.SUITE);
        for (int i = 2; i < 30; i++) {
            Room room = new Room(i);
            room.asignRoomCategory(roomCategory3);
            room.setHotel(hotel1);
            roomCategory3.addRoom(room);
            hotel1.addRoom(i, room);
            m.addObjectToModel(room);
            m.create_link_room_roomCategory(room, roomCategory3);
            m.create_link_hotel_room(room1, hotel1);
        }
        assertFalse(m.checkModelConstraints(), "Failed - booking VIP -> Basic.");
    }

    @Test
    public void constrain8Failed3() {
        // success when:
        // basic -> basic, vip, suite
        // suite -> suite, vip
        // vip -> vip
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Beer Sheva", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);
        Client c = new Client(1, 20, "Shay", "Haifa");
        m.addObjectToModel(c);
        ReservationSet rs = new ReservationSet();
        m.addObjectToModel(rs);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        // reservation
        Date or1 = Model.getDateFromString("22-04-2022");
        Date rq1 = Model.getDateFromString("24-04-2022");
        RoomCategory rc = new RoomCategory(2000, RoomCategory.RoomType.SUITE);
        m.addObjectToModel(rc);
        Reservation r = new Reservation(or1, rq1, 1);
        m.addObjectToModel(r);
        r.setReservationSet(rs);
        r.addRoomCategory(rc);
        rc.addReservation(r);
        m.create_link_reservation_roomCategory(r, rc);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);

        /// room
        Room room1 = new Room(1);
        m.addObjectToModel(room1);
        room1.setHotel(hotel1);
        room1.asignRoomCategory(rc);
        hotel1.addRoom(1, room1);
        rc.addReservation(r);
        rc.addRoom(room1);
        m.create_link_room_roomCategory(room1, rc);
        m.create_link_hotel_room(room1, hotel1);

        // room 2 - fancy
        RoomCategory rc2 = new RoomCategory(2000, RoomCategory.RoomType.BASIC);
        m.addObjectToModel(rc2);
        Room room2 = new Room(2);
        m.addObjectToModel(room2);
        room1.setHotel(hotel1);
        room1.asignRoomCategory(rc2);
        hotel1.addRoom(2, room2);
        rc.addReservation(r);
        rc.addRoom(room2);
        m.create_link_room_roomCategory(room2, rc2);
        m.create_link_hotel_room(room2, hotel1);

        ///
        Booking b = new Booking(rq1, room2);
        m.addObjectToModel(b);
        b.addReservation(r);
        b.assignRoom(room2);
        room2.addBooking(b, rq1);
        m.create_link_reservation_booking(b, r);
        m.create_link_room_Booking(room2, b);

        assertFalse(m.checkModelConstraints(), "Failed - booking SUITE -> BASIC.");
    }

    @Test
    public void constrain9Success() {
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Beer Sheva", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);
        Client c = new Client(1, 27, "Shay", "Haifa");
        m.addObjectToModel(c);
        ReservationSet rs = new ReservationSet();
        m.addObjectToModel(rs);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        // reservation
        Date or1 = Model.getDateFromString("22-04-2022");
        Date rq1 = Model.getDateFromString("24-04-2022");
        RoomCategory rc = new RoomCategory(2000, RoomCategory.RoomType.BASIC);
        m.addObjectToModel(rc);
        Reservation r = new Reservation(or1, rq1, 1);
        m.addObjectToModel(r);
        r.setReservationSet(rs);
        r.addRoomCategory(rc);
        rc.addReservation(r);
        m.create_link_reservation_roomCategory(r, rc);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        HotelService hs = new HotelService(1000, 5);
        m.addObjectToModel(hs);
        Service s = new VipService("VIP SERVICE");
        m.addObjectToModel(s);
        hs.assignService(s);
        hs.setHotel(hotel1);
        s.addHotel(hotel1, hs);
        hotel1.addReservationSet(c, rs);
        hotel1.addService(s, hs);
        m.create_link_hotel_service_hotelService(hotel1, s, hs);
        /// room
        Room room = new Room(1);
        m.addObjectToModel(room);
        room.setHotel(hotel1);
        room.asignRoomCategory(rc);
        hotel1.addRoom(1, room);
        rc.addReservation(r);
        rc.addRoom(room);
        m.create_link_room_roomCategory(room, rc);
        m.create_link_hotel_room(room, hotel1);
        ///
        Booking b = new Booking(rq1, room);
        m.addObjectToModel(b);
        b.addReservation(r);
        b.addService(hs);
        b.assignRoom(room);
        r.addBooking(b);
        hs.addBooking(b);
        room.addBooking(b, rq1);
        m.create_link_reservation_booking(b, r);
        m.create_link_room_Booking(room, b);
        m.create_link_hotelService_booking(hs, b);

        hotel1.addReservationSet(c, rs);
        rs.addReservation(r);

        // Review
        Date reviewDate = Model.getDateFromString("27-04-2022");

        Review review = new Review(10, "Good Hotel", reviewDate);
        review.addBooking(b);
        b.addReview(review);
        m.addObjectToModel(review);
        m.create_link_booking_review(b, review);


        assertTrue(m.checkModelConstraints(), "Failed - missing review.");
    }

    @Test
    public void constrain9Failed() {
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Beer Sheva", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);
        Client c = new Client(1, 27, "Shay", "Haifa");
        m.addObjectToModel(c);
        ReservationSet rs = new ReservationSet();
        m.addObjectToModel(rs);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        // reservation
        Date or1 = Model.getDateFromString("22-04-2022");
        Date rq1 = Model.getDateFromString("24-04-2022");
        RoomCategory rc = new RoomCategory(2000, RoomCategory.RoomType.BASIC);
        m.addObjectToModel(rc);
        Reservation r = new Reservation(or1, rq1, 1);
        m.addObjectToModel(r);
        r.setReservationSet(rs);
        r.addRoomCategory(rc);
        rc.addReservation(r);
        m.create_link_reservation_roomCategory(r, rc);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        HotelService hs = new HotelService(1000, 5);
        m.addObjectToModel(hs);
        Service s = new VipService("VIP SERVICE");
        m.addObjectToModel(s);
        hs.assignService(s);
        hs.setHotel(hotel1);
        s.addHotel(hotel1, hs);
        hotel1.addReservationSet(c, rs);
        hotel1.addService(s, hs);
        m.create_link_hotel_service_hotelService(hotel1, s, hs);
        /// room
        Room room = new Room(1);
        m.addObjectToModel(room);
        room.setHotel(hotel1);
        room.asignRoomCategory(rc);
        hotel1.addRoom(1, room);
        rc.addReservation(r);
        rc.addRoom(room);
        m.create_link_room_roomCategory(room, rc);
        m.create_link_hotel_room(room, hotel1);
        ///
        Booking b = new Booking(rq1, room);
        m.addObjectToModel(b);
        b.addReservation(r);
        b.addService(hs);
        b.assignRoom(room);
        r.addBooking(b);
        hs.addBooking(b);
        room.addBooking(b, rq1);
        m.create_link_reservation_booking(b, r);
        m.create_link_room_Booking(room, b);
        m.create_link_hotelService_booking(hs, b);

        hotel1.addReservationSet(c, rs);
        rs.addReservation(r);

        // Review
        Date reviewDate = Model.getDateFromString("27-04-2022");


        assertFalse(m.checkModelConstraints(), "Failed - missing review.");
    }

    @Test
    public void constrain10Success() {
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Beer Sheva", "Leonardo", 5);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);
        Client c = new Client(1, 27, "Shay", "Haifa");
        m.addObjectToModel(c);
        ReservationSet rs = new ReservationSet();
        m.addObjectToModel(rs);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        // reservation
        Date or1 = Model.getDateFromString("22-04-2022");
        Date rq1 = Model.getDateFromString("24-04-2022");
        RoomCategory rc = new RoomCategory(2000, RoomCategory.RoomType.BASIC);
        m.addObjectToModel(rc);
        Reservation r = new Reservation(or1, rq1, 1);
        m.addObjectToModel(r);
        r.setReservationSet(rs);
        r.addRoomCategory(rc);
        rc.addReservation(r);
        m.create_link_reservation_roomCategory(r, rc);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        HotelService hs = new HotelService(1000, 5);
        m.addObjectToModel(hs);
        Service s = new VipService("VIP SERVICE");
        m.addObjectToModel(s);
        hs.assignService(s);
        hs.setHotel(hotel1);
        s.addHotel(hotel1, hs);
        hotel1.addReservationSet(c, rs);
        hotel1.addService(s, hs);
        m.create_link_hotel_service_hotelService(hotel1, s, hs);
        /// room
        Room room = new Room(1);
        m.addObjectToModel(room);
        room.setHotel(hotel1);
        room.asignRoomCategory(rc);
        hotel1.addRoom(1, room);
        rc.addReservation(r);
        rc.addRoom(room);
        m.create_link_room_roomCategory(room, rc);
        m.create_link_hotel_room(room, hotel1);
        ///
        Booking b = new Booking(rq1, room);
        m.addObjectToModel(b);
        b.addReservation(r);
        b.addService(hs);
        b.assignRoom(room);
        r.addBooking(b);
        hs.addBooking(b);
        room.addBooking(b, rq1);
        m.create_link_reservation_booking(b, r);
        m.create_link_room_Booking(room, b);
        m.create_link_hotelService_booking(hs, b);

        hotel1.addReservationSet(c, rs);
        rs.addReservation(r);

        // Review
        Date reviewDate = Model.getDateFromString("27-04-2022");

        Review review = new Review(10, "Good Hotel", reviewDate);
        review.addBooking(b);
        b.addReview(review);
        m.addObjectToModel(review);
        m.create_link_booking_review(b, review);
        assertTrue(m.checkModelConstraints(), "Success - rank average > 7.5.");
    }

    @Test
    public void constrain10Failed1() {
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Beer Sheva", "Leonardo", 5);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);
        Client c = new Client(1, 27, "Shay", "Haifa");
        m.addObjectToModel(c);
        ReservationSet rs = new ReservationSet();
        m.addObjectToModel(rs);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        // reservation
        Date or1 = Model.getDateFromString("22-04-2022");
        Date rq1 = Model.getDateFromString("24-04-2022");
        RoomCategory rc = new RoomCategory(2000, RoomCategory.RoomType.BASIC);
        m.addObjectToModel(rc);
        Reservation r = new Reservation(or1, rq1, 1);
        m.addObjectToModel(r);
        r.setReservationSet(rs);
        r.addRoomCategory(rc);
        rc.addReservation(r);
        m.create_link_reservation_roomCategory(r, rc);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        HotelService hs = new HotelService(1000, 5);
        m.addObjectToModel(hs);
        Service s = new VipService("VIP SERVICE");
        m.addObjectToModel(s);
        hs.assignService(s);
        hs.setHotel(hotel1);
        s.addHotel(hotel1, hs);
        hotel1.addReservationSet(c, rs);
        hotel1.addService(s, hs);
        m.create_link_hotel_service_hotelService(hotel1, s, hs);
        /// room
        Room room = new Room(1);
        m.addObjectToModel(room);
        room.setHotel(hotel1);
        room.asignRoomCategory(rc);
        hotel1.addRoom(1, room);
        rc.addReservation(r);
        rc.addRoom(room);
        m.create_link_room_roomCategory(room, rc);
        m.create_link_hotel_room(room, hotel1);
        ///
        Booking b = new Booking(rq1, room);
        m.addObjectToModel(b);
        b.addReservation(r);
        b.addService(hs);
        b.assignRoom(room);
        r.addBooking(b);
        hs.addBooking(b);
        room.addBooking(b, rq1);
        m.create_link_reservation_booking(b, r);
        m.create_link_room_Booking(room, b);
        m.create_link_hotelService_booking(hs, b);

        hotel1.addReservationSet(c, rs);
        rs.addReservation(r);

        // Review
        Date reviewDate = Model.getDateFromString("27-04-2022");

        Review review = new Review(1, "Good Hotel", reviewDate);
        review.addBooking(b);
        b.addReview(review);
        m.addObjectToModel(review);
        m.create_link_booking_review(b, review);
        assertFalse(m.checkModelConstraints(), "Success - rank average < 7.5.");
    }

    @Test
    public void constrain10Failed2() {
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Beer Sheva", "Leonardo", 5);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);
        Client c = new Client(1, 27, "Shay", "Haifa");
        m.addObjectToModel(c);
        ReservationSet rs = new ReservationSet();
        m.addObjectToModel(rs);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        // reservation
        Date or1 = Model.getDateFromString("22-04-2022");
        Date rq1 = Model.getDateFromString("24-04-2022");
        RoomCategory rc = new RoomCategory(2000, RoomCategory.RoomType.BASIC);
        m.addObjectToModel(rc);
        Reservation r = new Reservation(or1, rq1, 1);
        m.addObjectToModel(r);
        r.setReservationSet(rs);
        r.addRoomCategory(rc);
        rc.addReservation(r);
        m.create_link_reservation_roomCategory(r, rc);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        hotel1.addReservationSet(c, rs);
        /// room
        Room room = new Room(1);
        m.addObjectToModel(room);
        room.setHotel(hotel1);
        room.asignRoomCategory(rc);
        hotel1.addRoom(1, room);
        rc.addReservation(r);
        rc.addRoom(room);
        m.create_link_room_roomCategory(room, rc);
        m.create_link_hotel_room(room, hotel1);
        ///
        Booking b = new Booking(rq1, room);
        m.addObjectToModel(b);
        b.addReservation(r);
        b.assignRoom(room);
        r.addBooking(b);
        room.addBooking(b, rq1);
        m.create_link_reservation_booking(b, r);
        m.create_link_room_Booking(room, b);

        hotel1.addReservationSet(c, rs);
        rs.addReservation(r);

        // Review
        Date reviewDate = Model.getDateFromString("27-04-2022");

        Review review = new Review(1, "Good Hotel", reviewDate);
        review.addBooking(b);
        b.addReview(review);
        m.addObjectToModel(review);
        m.create_link_booking_review(b, review);

        // reservation
        Date or2 = Model.getDateFromString("22-02-2022");
        Date rq2 = Model.getDateFromString("24-02-2022");
        m.addObjectToModel(rc);
        Reservation r2 = new Reservation(or2, rq2, 2);
        m.addObjectToModel(r2);
        r2.setReservationSet(rs);
        r2.addRoomCategory(rc);
        rc.addReservation(r2);
        m.create_link_reservation_roomCategory(r2, rc);
        ///
        Booking b2 = new Booking(rq2, room);
        m.addObjectToModel(b2);
        b2.addReservation(r2);
        b2.assignRoom(room);
        r2.addBooking(b2);
        room.addBooking(b2, rq2);
        m.create_link_reservation_booking(b2, r2);
        m.create_link_room_Booking(room, b2);
        rs.addReservation(r2);

        // Review
        Date reviewDate2 = Model.getDateFromString("27-02-2022");

        Review review2 = new Review(9, "Good Hotel", reviewDate);
        review2.addBooking(b2);
        b2.addReview(review2);
        m.addObjectToModel(review2);
        m.create_link_booking_review(b2, review2);


        assertFalse(m.checkModelConstraints(), "Success - rank average < 7.5.");
    }

    @Test
    public void constrain11Success() {
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Beer Sheva", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);
        Client c = new Client(1, 20, "Shay", "Haifa");
        m.addObjectToModel(c);
        ReservationSet rs = new ReservationSet();
        m.addObjectToModel(rs);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        // reservation
        Date or1 = Model.getDateFromString("22-04-2022");
        Date rq1 = Model.getDateFromString("24-04-2022");
        RoomCategory rc = new RoomCategory(2000, RoomCategory.RoomType.SUITE);
        m.addObjectToModel(rc);
        Reservation r = new Reservation(or1, rq1, 1);
        m.addObjectToModel(r);
        r.setReservationSet(rs);
        r.addRoomCategory(rc);
        rc.addReservation(r);
        m.create_link_reservation_roomCategory(r, rc);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        HotelService hs = new HotelService(1000, 5);
        m.addObjectToModel(hs);
        Service s = new RegularService("VIP SERVICE");
        m.addObjectToModel(s);
        hs.assignService(s);
        hs.setHotel(hotel1);
        s.addHotel(hotel1, hs);
        hotel1.addReservationSet(c, rs);
        hotel1.addService(s, hs);
        m.create_link_hotel_service_hotelService(hotel1, s, hs);
        /// room
        Room room = new Room(1);
        m.addObjectToModel(room);
        room.setHotel(hotel1);
        room.asignRoomCategory(rc);
        hotel1.addRoom(1, room);
        rc.addReservation(r);
        rc.addRoom(room);
        m.create_link_room_roomCategory(room, rc);
        m.create_link_hotel_room(room, hotel1);
        ///
        Booking b = new Booking(rq1, room);
        m.addObjectToModel(b);
        b.addReservation(r);
        b.addService(hs);
        b.assignRoom(room);
        r.addBooking(b);
        hs.addBooking(b);
        room.addBooking(b, rq1);
        m.create_link_reservation_booking(b, r);
        m.create_link_room_Booking(room, b);
        m.create_link_hotelService_booking(hs, b);
        hotel1.addReservationSet(c, rs);
        rs.addReservation(r);

        HotelService hs1 = new HotelService(1000, 5);
        m.addObjectToModel(hs1);
        Service s1 = new RegularService("VIP SERVICE1");
        m.addObjectToModel(s1);
        hs1.assignService(s1);
        hs1.setHotel(hotel1);
        s1.addHotel(hotel1, hs1);
        hotel1.addService(s1, hs1);
        m.create_link_hotel_service_hotelService(hotel1, s1, hs1);
        b.addService(hs1);
        hs1.addBooking(b);
        m.create_link_hotelService_booking(hs1, b);

        assertTrue(m.checkModelConstraints(), "Success - services with different name.");
    }

    @Test
    public void constrain11Failed() {
        Group group1 = new Group(1);
        m.addObjectToModel(group1);
        Hotel hotel1 = new Hotel("Beer Sheva", "Leonardo", 2);
        m.addObjectToModel(hotel1);
        m.create_link_group_hotel(hotel1, group1);
        Client c = new Client(1, 20, "Shay", "Haifa");
        m.addObjectToModel(c);
        ReservationSet rs = new ReservationSet();
        m.addObjectToModel(rs);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        // reservation
        Date or1 = Model.getDateFromString("22-04-2022");
        Date rq1 = Model.getDateFromString("24-04-2022");
        RoomCategory rc = new RoomCategory(2000, RoomCategory.RoomType.SUITE);
        m.addObjectToModel(rc);
        Reservation r = new Reservation(or1, rq1, 1);
        m.addObjectToModel(r);
        r.setReservationSet(rs);
        r.addRoomCategory(rc);
        rc.addReservation(r);
        m.create_link_reservation_roomCategory(r, rc);
        m.create_link_client_hotel_reservationSet(c, hotel1, rs);
        HotelService hs = new HotelService(1000, 5);
        m.addObjectToModel(hs);
        Service s = new RegularService("VIP SERVICE");
        m.addObjectToModel(s);
        hs.assignService(s);
        hs.setHotel(hotel1);
        s.addHotel(hotel1, hs);
        hotel1.addReservationSet(c, rs);
        hotel1.addService(s, hs);
        m.create_link_hotel_service_hotelService(hotel1, s, hs);
        /// room
        Room room = new Room(1);
        m.addObjectToModel(room);
        room.setHotel(hotel1);
        room.asignRoomCategory(rc);
        hotel1.addRoom(1, room);
        rc.addReservation(r);
        rc.addRoom(room);
        m.create_link_room_roomCategory(room, rc);
        m.create_link_hotel_room(room, hotel1);
        ///
        Booking b = new Booking(rq1, room);
        m.addObjectToModel(b);
        b.addReservation(r);
        b.addService(hs);
        b.assignRoom(room);
        r.addBooking(b);
        hs.addBooking(b);
        room.addBooking(b, rq1);
        m.create_link_reservation_booking(b, r);
        m.create_link_room_Booking(room, b);
        m.create_link_hotelService_booking(hs, b);
        hotel1.addReservationSet(c, rs);
        rs.addReservation(r);

        HotelService hs1 = new HotelService(1000, 5);
        m.addObjectToModel(hs1);
        Service s1 = new RegularService("VIP SERVICE");
        m.addObjectToModel(s1);
        hs1.assignService(s1);
        hs1.setHotel(hotel1);
        s1.addHotel(hotel1, hs1);
        hotel1.addService(s1, hs1);
        m.create_link_hotel_service_hotelService(hotel1, s1, hs1);
        b.addService(hs1);
        hs1.addBooking(b);
        m.create_link_hotelService_booking(hs1, b);

        assertFalse(m.checkModelConstraints(), "Success - services with different name.");
    }

    @Test
    public void constrain12Success()
    {
        // create 1 hotel, 1 group
        Client c = new Client(1,23,"Shay", "Tel Aviv");
        m.addObjectToModel(c);
        Hotel h = new Hotel("Beer Sheva", "Leonardo", 3);
        m.addObjectToModel(h);
        Group g = new Group(1);
        m.addObjectToModel(g);
        Date or1 = Model.getDateFromString("22-04-2022");
        Date rq1 = Model.getDateFromString("24-04-2022");
        Date or2 = Model.getDateFromString("22-05-2022");
        Date rq2 = Model.getDateFromString("24-05-2022");
        Date or3 = Model.getDateFromString("22-04-2021");
        Date rq3 = Model.getDateFromString("24-04-2021");
        Date or4 = Model.getDateFromString("22-04-2019");
        Date rq4 = Model.getDateFromString("24-04-2019");
        Reservation r1 = new Reservation(or1, rq1, 1);
        m.addObjectToModel(r1);
        Reservation r2 = new Reservation(or2, rq2, 2);
        m.addObjectToModel(r2);
        Reservation r3 = new Reservation(or3, rq3, 3);
        m.addObjectToModel(r3);
        Reservation r4 = new Reservation(or4, rq4, 4);
        m.addObjectToModel(r4);
        Service serviceE = new RegularService("Expensive");
        m.addObjectToModel(serviceE);
        Service serviceC = new RegularService("Cheap");
        m.addObjectToModel(serviceC);
        RoomCategory roomCategory = new RoomCategory(150, RoomCategory.RoomType.BASIC);
        m.addObjectToModel(roomCategory);
        Room room = new Room(1);
        m.addObjectToModel(room);
        HotelService hotelServiceE = new HotelService(1000, 5);
        m.addObjectToModel(hotelServiceE);
        HotelService hotelServiceC = new HotelService(100, 3);
        m.addObjectToModel(hotelServiceC);
        Booking b1 = new Booking(rq1, room);
        m.addObjectToModel(b1);
        Booking b2 = new Booking(rq2, room);
        m.addObjectToModel(b2);
        Booking b3 = new Booking(rq3, room);
        m.addObjectToModel(b3);
        Booking b4 = new Booking(rq4, room);
        m.addObjectToModel(b4);

        ReservationSet rs = new ReservationSet();
        m.addObjectToModel(rs);
        rs.addReservation(r1);
        rs.addReservation(r2);
        rs.addReservation(r3);
        rs.addReservation(r4);
        rs.setHotel(h);
        rs.setClient(c);
        c.addReservationSet(h, rs);
        h.addReservationSet(c, rs);
        h.addRoom(1, room);
        h.addService(serviceE, hotelServiceE);
        h.addService(serviceC, hotelServiceC);
        h.setGroup(g);
        g.addHotelToGroup(h);
        r1.setReservationSet(rs);
        r1.addRoomCategory(roomCategory);
        r1.addBooking(b1);
        r2.setReservationSet(rs);
        r2.addRoomCategory(roomCategory);
        r2.addBooking(b2);
        r3.setReservationSet(rs);
        r3.addRoomCategory(roomCategory);
        r3.addBooking(b3);
        r4.setReservationSet(rs);
        r4.addRoomCategory(roomCategory);
        r4.addBooking(b4);
        serviceC.addHotel(h, hotelServiceC);
        serviceE.addHotel(h, hotelServiceE);
        room.setHotel(h);
        room.addBooking(b1, rq1);
        room.addBooking(b2, rq2);
        room.addBooking(b3, rq3);
        room.addBooking(b4, rq4);
        hotelServiceC.addBooking(b1);
        hotelServiceE.addBooking(b2);
        hotelServiceE.addBooking(b3);
        hotelServiceC.addBooking(b4);
        hotelServiceE.assignService(serviceE);
        hotelServiceE.setHotel(h);
        hotelServiceE.addBooking(b2);
        hotelServiceE.addBooking(b3);
        hotelServiceC.assignService(serviceC);
        hotelServiceC.addBooking(b1);
        hotelServiceC.addBooking(b4);
        b1.assignRoom(room);
        b2.assignRoom(room);
        b3.assignRoom(room);
        b4.assignRoom(room);
        b1.addService(hotelServiceC);
        b2.addService(hotelServiceE);
        b3.addService(hotelServiceE);
        b4.addService(hotelServiceC);
        b1.addReservation(r1);
        b2.addReservation(r2);
        b3.addReservation(r3);
        b4.addReservation(r4);

        m.create_link_room_Booking(room, b1);
        m.create_link_room_Booking(room, b2);
        m.create_link_room_Booking(room, b3);
        m.create_link_room_Booking(room, b4);
        m.create_link_reservation_booking(b1, r1);
        m.create_link_reservation_booking(b2, r2);
        m.create_link_reservation_booking(b3, r3);
        m.create_link_reservation_booking(b4, r4);
        m.create_link_reservationSet_reservation(rs, r1);
        m.create_link_reservationSet_reservation(rs, r2);
        m.create_link_reservationSet_reservation(rs, r3);
        m.create_link_reservationSet_reservation(rs, r4);
        m.create_link_reservation_roomCategory(r1, roomCategory);
        m.create_link_reservation_roomCategory(r2, roomCategory);
        m.create_link_reservation_roomCategory(r3, roomCategory);
        m.create_link_reservation_roomCategory(r4, roomCategory);
        m.create_link_client_hotel_reservationSet(c, h, rs);
        m.create_link_hotel_room(room, h);
        m.create_link_hotelService_booking(hotelServiceC, b1);
        m.create_link_hotelService_booking(hotelServiceE, b2);
        m.create_link_hotelService_booking(hotelServiceE, b3);
        m.create_link_hotelService_booking(hotelServiceC, b4);
        m.create_link_room_roomCategory(room, roomCategory);
        m.create_link_hotel_service_hotelService(h, serviceE, hotelServiceE);
        m.create_link_hotel_service_hotelService(h, serviceC, hotelServiceC);


        assertTrue(m.checkModelConstraints(), "Success - services with different name.");


    }

    @Test
    public void test()
    {
        Date or1 = Model.getDateFromString("22-04-2022");
        Date rq1 = Model.getDateFromString("24-04-2022");

        Client client1 = new Client(1,23,"Dolev","Tel Aviv");
        Hotel hotel1 = new Hotel("London","BPS",4);
        Group g = new Group(1);
        Room room1 = new Room(404);
        ReservationSet reservationSet1 = new ReservationSet();
        Reservation reservation1 = new Reservation(or1,rq1,100);
        RoomCategory roomCategory1 = new RoomCategory(200, RoomCategory.RoomType.VIP);
        Booking booking = new Booking(rq1,room1);
        Service s1 = new CommunityService("wifi"); // this is the reason for false
        HotelService hs1 = new HotelService(12,10);

        client1.addReservationSet(hotel1, reservationSet1);
        hotel1.setGroup(g);
        hotel1.addRoom(404, room1);
        hotel1.addReservationSet(client1, reservationSet1);
        hotel1.addService(s1, hs1);
        g.addHotelToGroup(hotel1);
        room1.asignRoomCategory(roomCategory1);
        room1.setHotel(hotel1);
        room1.addBooking(booking, rq1);
        reservationSet1.setClient(client1);
        reservationSet1.setHotel(hotel1);
        reservationSet1.addReservation(reservation1);
        roomCategory1.addRoom(room1);
        roomCategory1.addReservation(reservation1);
        booking.addService(hs1);
        booking.assignRoom(room1);
        booking.addReservation(reservation1);
        s1.addHotel(hotel1, hs1);
        hs1.assignService(s1);
        hs1.addBooking(booking);
        hs1.setHotel(hotel1);

        m.addObjectToModel(client1);
        m.addObjectToModel(hotel1);
        m.addObjectToModel(g);
        m.addObjectToModel(room1);
        m.addObjectToModel(reservationSet1);
        m.addObjectToModel(reservation1);
        m.addObjectToModel(booking);
        m.addObjectToModel(roomCategory1);
        m.addObjectToModel(hs1);
        m.addObjectToModel(s1);

        m.create_link_hotelService_booking(hs1, booking);
        m.create_link_hotel_service_hotelService(hotel1, s1, hs1);
        m.create_link_room_Booking(room1, booking);
        m.create_link_hotel_room(room1, hotel1);
        m.create_link_room_roomCategory(room1, roomCategory1);
        m.create_link_client_hotel_reservationSet(client1, hotel1, reservationSet1);
        m.create_link_group_hotel(hotel1, g);
        m.create_link_reservation_booking(booking, reservation1);
        m.create_link_reservationSet_reservation(reservationSet1, reservation1);
        m.create_link_reservation_roomCategory(reservation1, roomCategory1);
        assertTrue(m.checkModelConstraints(), "xxx");
    }


}


