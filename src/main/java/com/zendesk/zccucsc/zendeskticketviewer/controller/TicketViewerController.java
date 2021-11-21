package com.zendesk.zccucsc.zendeskticketviewer.controller;

import com.zendesk.zccucsc.zendeskticketviewer.entity.TicketDetailEntity;
import com.zendesk.zccucsc.zendeskticketviewer.entity.TicketViewerEntity;
import com.zendesk.zccucsc.zendeskticketviewer.service.TicketViewerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class TicketViewerController {
    private static final Logger LOG = LogManager.getLogger(TicketViewerController.class);
    @Value("${api.token}")
    private String apiToken;
    private final String BASE_CLASS = "TicketViewerController";
    @Autowired
    TicketViewerService ticketViewerService;

    @GetMapping("/tickets")
    @ResponseStatus( HttpStatus.OK )
    public TicketViewerEntity getTickets() {
        LOG.info("Inside " + BASE_CLASS + " -> getTickets");
        TicketViewerEntity entity = ticketViewerService.getAllTickets();
        return entity;
    }

    @RequestMapping("/tickets/{id}")
    public TicketDetailEntity getTicketById(@PathVariable("id") String id) {
        LOG.info("Inside " + BASE_CLASS + " -> getTicketById");
        TicketDetailEntity entity = ticketViewerService.getTicketById(id);
        return entity;

    }



}
