package com.zendesk.zccucsc.zendeskticketviewer;

import com.zendesk.zccucsc.zendeskticketviewer.controller.TicketViewerController;
import com.zendesk.zccucsc.zendeskticketviewer.entity.Ticket;
import com.zendesk.zccucsc.zendeskticketviewer.entity.TicketDetailEntity;
import com.zendesk.zccucsc.zendeskticketviewer.entity.TicketViewerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Driver implements CommandLineRunner {
    @Autowired
    TicketViewerController ticketViewerController;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Welcome to Zendesk Ticket Viewer Service. Please select an option from the menu below:");
        Scanner scanner = new Scanner(System.in);
        String choice = "";
        do {
            System.out.println("1. View all tickets");
            System.out.println("2. Select ticket by id");
            choice = scanner.nextLine();
            switch(choice) {
                case "1":
                    System.out.println("ID Subject");
                    TicketViewerEntity ticketViewerEntity = ticketViewerController.getTickets();
                    if(ticketViewerEntity != null && ticketViewerEntity.getTickets() != null) {
                        for(Ticket ticket : ticketViewerEntity.getTickets()) {
                            System.out.println(ticket.id + " " + ticket.subject);
                        }
                    }
                    break;
                case "2":
                    System.out.print("Enter ticket id: ");
                    String ticketId = scanner.nextLine();
                    TicketDetailEntity ticketDetails = ticketViewerController.getTicketById(ticketId);
                    System.out.println(ticketDetails.getTicket().id + "---" + ticketDetails.getTicket().subject);
                    break;
                default:
                    System.out.println("Please input a valid choice (1, 2 or 3)");
            }

        } while (true);
    }

}
