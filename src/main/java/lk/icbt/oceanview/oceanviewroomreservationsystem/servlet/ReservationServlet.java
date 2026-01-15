package lk.icbt.oceanview.oceanviewroomreservationsystem.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.icbt.oceanview.oceanviewroomreservationsystem.factory.ServiceFactory;
import lk.icbt.oceanview.oceanviewroomreservationsystem.model.Reservation;
import lk.icbt.oceanview.oceanviewroomreservationsystem.model.RoomType;
import lk.icbt.oceanview.oceanviewroomreservationsystem.model.User;
import lk.icbt.oceanview.oceanviewroomreservationsystem.service.ReservationService;
import lk.icbt.oceanview.oceanviewroomreservationsystem.service.RoomTypeService;
import lk.icbt.oceanview.oceanviewroomreservationsystem.util.DateValidator;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@WebServlet("/reservation")
public class ReservationServlet extends HttpServlet {

    private ReservationService reservationService;
    private RoomTypeService roomTypeService;

    @Override
    public void init() throws ServletException {
        reservationService = ServiceFactory.getReservationService();
        roomTypeService = ServiceFactory.getRoomTypeService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.equals("new") || action.equals("add")) {
            showAddForm(request, response);
        } else if (action.equals("search") || action.equals("view")) {
            showReservationList(request, response);
        } else if (action.equals("detail")) {
            showReservationDetail(request, response);
        } else if (action.equals("cancel")) {
            cancelReservation(request, response);
        } else {
            showReservationList(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String action = request.getParameter("action");

        if ("cancel".equals(action)) {
            cancelReservation(request, response);
            return;
        }

        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            String guestName = request.getParameter("guestName");
            String address = request.getParameter("address");
            String contactNumber = request.getParameter("contactNumber");
            String email = request.getParameter("email");
            String roomTypeIdStr = request.getParameter("roomTypeId");
            String checkInDateStr = request.getParameter("checkInDate");
            String checkOutDateStr = request.getParameter("checkOutDate");
            String numberOfGuestsStr = request.getParameter("numberOfGuests");
            String specialRequests = request.getParameter("specialRequests");

            LocalDate checkInDate = DateValidator.parseDate(checkInDateStr);
            LocalDate checkOutDate = DateValidator.parseDate(checkOutDateStr);

            Reservation reservation = new Reservation();
            reservation.setGuestName(guestName);
            reservation.setAddress(address);
            reservation.setContactNumber(contactNumber);
            reservation.setEmail(email);
            reservation.setRoomTypeId(Integer.parseInt(roomTypeIdStr));
            reservation.setCheckInDate(checkInDate);
            reservation.setCheckOutDate(checkOutDate);
            reservation.setNumberOfGuests(Integer.parseInt(numberOfGuestsStr));
            reservation.setSpecialRequests(specialRequests);
            reservation.setStatus("CONFIRMED");

            Reservation createdReservation = reservationService.createReservation(reservation, user.getUserId());


            response.sendRedirect(request.getContextPath() + "/reservation?action=search&reservationNumber=" +
                    createdReservation.getReservationNumber() + "&success=true");

        } catch (Exception e) {
            request.setAttribute("error", "Failed to create reservation: " + e.getMessage());
            showAddForm(request, response);
        }
    }


    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<RoomType> roomTypes = roomTypeService.getAllAvailableRoomTypes();
        request.setAttribute("roomTypes", roomTypes);

        request.getRequestDispatcher("/WEB-INF/views/add-reservation.jsp").forward(request, response);
    }


    private void showReservationList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String reservationNumber = request.getParameter("reservationNumber");
        String searchQuery = request.getParameter("search");


        if (reservationNumber != null && !reservationNumber.trim().isEmpty()) {
            Reservation reservation = reservationService.getReservationByNumber(reservationNumber.trim());
            if (reservation != null) {
                request.setAttribute("reservation", reservation);
            } else {
                request.setAttribute("error", "No Reservation Found");
                request.setAttribute("errorDetails", "The reservation number \"" + reservationNumber + "\" does not exist in our system.");
            }
        } else if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            List<Reservation> reservations = reservationService.searchByGuestName(searchQuery);
            request.setAttribute("reservations", reservations);
        }

        request.getRequestDispatcher("/WEB-INF/views/view-reservation.jsp").forward(request, response);
    }


    private void showReservationDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");

        if (idStr != null) {
            int reservationId = Integer.parseInt(idStr);
            Reservation reservation = reservationService.getReservationById(reservationId);
            request.setAttribute("reservation", reservation);
        }

        request.getRequestDispatcher("/WEB-INF/views/reservation-detail.jsp").forward(request, response);
    }


    private void cancelReservation(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("reservationId");

        if (idStr != null) {
            int reservationId = Integer.parseInt(idStr);

            Reservation reservation = reservationService.getReservationById(reservationId);
            boolean success = reservationService.cancelReservation(reservationId);

            if (success && reservation != null) {
                response.sendRedirect(request.getContextPath() + "/reservation?action=search&reservationNumber=" +
                        reservation.getReservationNumber() + "&cancelled=true");
            } else {
                response.sendRedirect(request.getContextPath() + "/reservation?action=view&error=cancel_failed");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/reservation?action=view");
        }
    }
}
