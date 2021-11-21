package com.zendesk.zccucsc.zendeskticketviewer;

import com.zendesk.zccucsc.zendeskticketviewer.controller.TicketViewerController;
import com.zendesk.zccucsc.zendeskticketviewer.entity.Ticket;
import com.zendesk.zccucsc.zendeskticketviewer.entity.TicketDetailEntity;
import com.zendesk.zccucsc.zendeskticketviewer.entity.TicketViewerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Driver implements CommandLineRunner {
    @Autowired
    TicketViewerController ticketViewerController;
    @Autowired
    private ConfigurableApplicationContext context;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Welcome to Zendesk Ticket Viewer Service. Please select an option from the menu below. At any point, if you would like to see the menu options again, just type 'menu'. ");
        System.out.println("1. View all tickets (25 tickets per page)");
        System.out.println("2. Select ticket by id");
        System.out.println("3. Exit");
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("\nEnter option (1, 2, 3 or 'menu'): ");
            String choice = scanner.nextLine();
            switch(choice) {
                case "1":
                    System.out.println("ID Subject");
                    TicketViewerEntity ticketViewerEntity = ticketViewerController.getTickets();
                    if(ticketViewerEntity != null && ticketViewerEntity.getTickets() != null) {
                        for(Ticket ticket : ticketViewerEntity.getTickets()) {
                            System.out.println(ticket.id + " " + ticket.subject);
                        }
                        if(!ticketViewerEntity.getMeta().getHas_more()) {
                            System.out.println("No more tickets to display.");
                        }
                    }
                    break;
                case "2":
                    System.out.print("Enter ticket id: ");
                    String ticketId = scanner.nextLine();
                    TicketDetailEntity ticketDetails = ticketViewerController.getTicketById(ticketId);
                    System.out.println(ticketDetails.getTicket().id + "---" + ticketDetails.getTicket().subject);
                    break;
                case "3":
                    System.out.println("Exiting application. Thank you for using Zendesk Ticket Viewer");
                    System.exit(SpringApplication.exit(context));
                case "menu":
                    System.out.println("1. View all tickets (25 tickets per page)");
                    System.out.println("2. Select ticket by id");
                    System.out.println("3. Exit");
                    break;
                default:
                    System.out.println("Please input a valid choice (1, 2 or 3)");
            }
        } while (true);
    }

}
