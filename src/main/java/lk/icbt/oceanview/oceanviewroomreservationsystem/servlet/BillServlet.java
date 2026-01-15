package lk.icbt.oceanview.oceanviewroomreservationsystem.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.icbt.oceanview.oceanviewroomreservationsystem.factory.ServiceFactory;
import lk.icbt.oceanview.oceanviewroomreservationsystem.model.Bill;
import lk.icbt.oceanview.oceanviewroomreservationsystem.model.Reservation;
import lk.icbt.oceanview.oceanviewroomreservationsystem.model.User;
import lk.icbt.oceanview.oceanviewroomreservationsystem.service.BillingService;
import lk.icbt.oceanview.oceanviewroomreservationsystem.service.ReservationService;

import java.io.IOException;
import java.util.List;


@WebServlet("/bill")
public class BillServlet extends HttpServlet {

    private BillingService billingService;
    private ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        billingService = ServiceFactory.getBillingService();
        reservationService = ServiceFactory.getReservationService();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.equals("generate")) {
            showGenerateForm(request, response);
        } else if (action.equals("view")) {
            showBillDetail(request, response);
        } else if (action.equals("list")) {
            showBillList(request, response);
        } else {
            showGenerateForm(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action.equals("generate")) {
            generateBill(request, response);
        } else if (action.equals("pay")) {
            markBillAsPaid(request, response);
        } else if (action.equals("discount")) {
            applyDiscount(request, response);
        } else {
            showBillList(request, response);
        }
    }


    private void showBillList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Bill> bills = billingService.getAllBills();
        request.setAttribute("bills", bills);
        request.getRequestDispatcher("/WEB-INF/views/bill-list.jsp").forward(request, response);
    }


    private void showBillDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");

        if (idStr != null) {
            int billId = Integer.parseInt(idStr);
            Bill bill = billingService.getBillById(billId);
            request.setAttribute("bill", bill);


            if (bill != null) {
                Reservation reservation = reservationService.getReservationById(bill.getReservationId());
                request.setAttribute("reservation", reservation);
            }
        }

        request.getRequestDispatcher("/WEB-INF/views/bill.jsp").forward(request, response);
    }


    private void showGenerateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Reservation> reservations = reservationService.getActiveReservations();
        request.setAttribute("reservations", reservations);
        request.getRequestDispatcher("/WEB-INF/views/generate-bill.jsp").forward(request, response);
    }


    private void generateBill(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            String reservationIdStr = request.getParameter("reservationId");
            int reservationId = Integer.parseInt(reservationIdStr);

            Reservation reservation = reservationService.getReservationById(reservationId);

            if (reservation != null) {
                Bill bill = billingService.generateBill(reservation, user.getUserId());
                response.sendRedirect(request.getContextPath() + "/bill?action=view&id=" + bill.getBillId());
            } else {
                request.setAttribute("error", "Reservation not found");
                showGenerateForm(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("error", "Failed to generate bill: " + e.getMessage());
            showGenerateForm(request, response);
        }
    }


    private void markBillAsPaid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String billIdStr = request.getParameter("billId");
        String paymentMethod = request.getParameter("paymentMethod");

        if (billIdStr != null && paymentMethod != null) {
            int billId = Integer.parseInt(billIdStr);
            boolean success = billingService.markBillAsPaid(billId, paymentMethod);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/bill?action=view&id=" + billId + "&success=paid");
            } else {
                response.sendRedirect(request.getContextPath() + "/bill?action=view&id=" + billId + "&error=payment_failed");
            }
        }
    }


    private void applyDiscount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String billIdStr = request.getParameter("billId");
        String discountStr = request.getParameter("discountPercentage");

        if (billIdStr != null && discountStr != null) {
            int billId = Integer.parseInt(billIdStr);
            double discount = Double.parseDouble(discountStr);

            Bill bill = billingService.applyDiscount(billId, discount);

            if (bill != null) {
                response.sendRedirect(request.getContextPath() + "/bill?action=view&id=" + billId + "&success=discount");
            } else {
                response.sendRedirect(request.getContextPath() + "/bill?action=view&id=" + billId + "&error=discount_failed");
            }
        }
    }
}
