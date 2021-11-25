package com.zendesk.zccucsc.zendeskticketviewer;

import com.zendesk.zccucsc.zendeskticketviewer.entity.Ticket;
import com.zendesk.zccucsc.zendeskticketviewer.entity.TicketDetailEntity;
import com.zendesk.zccucsc.zendeskticketviewer.entity.TicketViewerEntity;
import com.zendesk.zccucsc.zendeskticketviewer.service.TicketViewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CLIRunner implements CommandLineRunner {
    @Autowired
    private TicketViewerService ticketViewerService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Welcome to Zendesk Ticket Viewer Service. Please select an option from the menu below. At any point, if you would like to see the menu options again, just type 'menu'. ");
        System.out.println("1. View all tickets (25 tickets per page)");
        System.out.println("2. Select ticket by id");
        System.out.println("3. Exit");
        Scanner scanner = new Scanner(System.in);
        boolean shouldContinue = true;
        while(shouldContinue) {
            System.out.print("\nEnter option (1, 2, 3 or 'menu'): ");
            String choice = scanner.nextLine();
            switch(choice) {
                case "1":
                    System.out.println("\tTicket ID \tSubject \t\t\tDate Created");
                    System.out.println("\t----------------------------------------------------------");
                    try {
                        TicketViewerEntity ticketViewerEntity = ticketViewerService.getAllTickets();
                        if (!CollectionUtils.isEmpty(ticketViewerEntity.getTickets())) {
                            for (Ticket ticket : ticketViewerEntity.getTickets()) {
                                System.out.println("\t" + ticket.id + " \t" + ticket.subject + " \t" + ticket.created_at);
                            }
                            if (!ticketViewerEntity.getMeta().getHas_more()) {
                                System.out.println("No more tickets to display. Select '1' to go back to the first page");
                            }
                        }
                        else {
                            System.out.println("No tickets were returned by the server.");
                        }
                        break;
                    }
                    catch (HttpServerErrorException | HttpClientErrorException e) {
                        System.out.println("Oops. Something has gone wrong with our services. Please try again after some time.");
                        shouldContinue = false;
                        break;
                    }

                case "2":
                    System.out.print("Enter ticket id: ");
                    String ticketId = scanner.nextLine();
                    if(!validateTicketId(ticketId)) {
                        System.out.println("Please enter a valid ticket id.");
                        continue;
                    }
                    try {
                        TicketDetailEntity ticketDetails = ticketViewerService.getTicketById(ticketId);
                        //assumption: ticketViewerService will either return a ticket or throw an exception (it will never be null)
                        Ticket ticket = ticketDetails.getTicket();
                        System.out.println("\tTicket ID is: " + ticket.id);
                        System.out.println("\tTicket subject is: " + ticket.subject);
                        System.out.println("\tTicket description is: " + ticket.description);
                        System.out.println("\tTicket was created on: " + ticket.created_at);
                        System.out.println("\tCurrent status of ticket is: " + ticket.status);
                        break;
                    }
                    catch (HttpClientErrorException e) {
                        System.out.println("The ticket ID you've entered is invalid or no longer exists. Please input a valid ticket ID.");
                        continue;
                    }
                    catch (HttpServerErrorException e) {
                        System.out.println("Oops. Something has gone wrong with our services. Please try again after some time.");
                        shouldContinue = false;
                        break;
                    }
                case "3":
                    shouldContinue = false;
                    break;

                case "menu":
                    System.out.println("1. View all tickets (25 tickets per page)");
                    System.out.println("2. Select ticket by id");
                    System.out.println("3. Exit");
                    break;
                default:
                    System.out.println("Please input a valid choice (1, 2 or 3). Type 'menu' to see the menu options again");
                }
        }
        System.out.println("Thank you for using Zendesk Ticket Viewer.");
    }

    private boolean validateTicketId(String ticketId) {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = pattern.matcher(ticketId);
        return matcher.find();
        }
    }


