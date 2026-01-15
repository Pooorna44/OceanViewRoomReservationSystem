package lk.icbt.oceanview.oceanviewroomreservationsystem.factory;

import lk.icbt.oceanview.oceanviewroomreservationsystem.service.AuthenticationService;
import lk.icbt.oceanview.oceanviewroomreservationsystem.service.BillingService;
import lk.icbt.oceanview.oceanviewroomreservationsystem.service.ReservationService;
import lk.icbt.oceanview.oceanviewroomreservationsystem.service.RoomTypeService;
import lk.icbt.oceanview.oceanviewroomreservationsystem.service.impl.AuthenticationServiceImpl;
import lk.icbt.oceanview.oceanviewroomreservationsystem.service.impl.BillingServiceImpl;
import lk.icbt.oceanview.oceanviewroomreservationsystem.service.impl.ReservationServiceImpl;
import lk.icbt.oceanview.oceanviewroomreservationsystem.service.impl.RoomTypeServiceImpl;

public class ServiceFactory {


    private ServiceFactory() {
        throw new IllegalStateException("Factory class - cannot be instantiated");
    }

    public static AuthenticationService getAuthenticationService() {
        return new AuthenticationServiceImpl();
    }

    public static ReservationService getReservationService() {
        return new ReservationServiceImpl();
    }

    public static BillingService getBillingService() {
        return new BillingServiceImpl();
    }

    public static RoomTypeService getRoomTypeService() {
        return new RoomTypeServiceImpl();
    }

    public static Object getService(ServiceType serviceType) {
        switch (serviceType) {
            case AUTHENTICATION:
                return new AuthenticationServiceImpl();
            case RESERVATION:
                return new ReservationServiceImpl();
            case BILLING:
                return new BillingServiceImpl();
            case ROOM_TYPE:
                return new RoomTypeServiceImpl();
            default:
                throw new IllegalArgumentException("Unknown service type: " + serviceType);
        }
    }


    public enum ServiceType {
        AUTHENTICATION,
        RESERVATION,
        BILLING,
        ROOM_TYPE
    }
}
