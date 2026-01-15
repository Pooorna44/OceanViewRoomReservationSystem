package lk.icbt.oceanview.oceanviewroomreservationsystem.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.icbt.oceanview.oceanviewroomreservationsystem.factory.ServiceFactory;
import lk.icbt.oceanview.oceanviewroomreservationsystem.model.Reservation;
import lk.icbt.oceanview.oceanviewroomreservationsystem.service.ReservationService;

import java.io.IOException;
import java.util.List;


@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        reservationService = ServiceFactory.getReservationService();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            reservationService.completePastReservations();

            List<Reservation> todayCheckIns = reservationService.getTodayCheckIns();
            List<Reservation> todayCheckOuts = reservationService.getTodayCheckOuts();
            List<Reservation> upcomingReservations = reservationService.getUpcomingReservations(7);
            List<Reservation> recentReservations = reservationService.getAllReservations();


            if (recentReservations != null && recentReservations.size() > 10) {
                recentReservations = recentReservations.subList(0, 10);
            }


            request.setAttribute("todayCheckIns", todayCheckIns != null ? todayCheckIns : List.of());
            request.setAttribute("todayCheckOuts", todayCheckOuts != null ? todayCheckOuts : List.of());
            request.setAttribute("upcomingReservations", upcomingReservations != null ? upcomingReservations : List.of());
            request.setAttribute("recentReservations", recentReservations != null ? recentReservations : List.of());
            request.setAttribute("checkInCount", todayCheckIns != null ? todayCheckIns.size() : 0);
            request.setAttribute("checkOutCount", todayCheckOuts != null ? todayCheckOuts.size() : 0);
            request.setAttribute("upcomingCount", upcomingReservations != null ? upcomingReservations.size() : 0);


            request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("Error in DashboardServlet: " + e.getMessage());
            e.printStackTrace();

            request.setAttribute("todayCheckIns", List.of());
            request.setAttribute("todayCheckOuts", List.of());
            request.setAttribute("upcomingReservations", List.of());
            request.setAttribute("recentReservations", List.of());
            request.setAttribute("checkInCount", 0);
            request.setAttribute("checkOutCount", 0);
            request.setAttribute("upcomingCount", 0);
            request.setAttribute("error", "Unable to load dashboard data: " + e.getMessage());

            request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
        }
    }
}
