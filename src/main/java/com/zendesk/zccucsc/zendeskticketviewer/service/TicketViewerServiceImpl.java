package com.zendesk.zccucsc.zendeskticketviewer.service;

import com.zendesk.zccucsc.zendeskticketviewer.entity.TicketViewerEntity;
import com.zendesk.zccucsc.zendeskticketviewer.gateway.TicketViewerGateway;
import com.zendesk.zccucsc.zendeskticketviewer.gateway.TicketViewerGatewayImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.zendesk.zccucsc.zendeskticketviewer.config.Constants;

import java.util.ArrayList;
import java.util.List;

@Component
public class TicketViewerServiceImpl implements TicketViewerService {
    @Autowired
    private TicketViewerGateway ticketViewerGateway;
    private static final Logger LOG = LogManager.getLogger(TicketViewerServiceImpl.class);
    private final String BASE_CLASS = "TicketViewerServiceImpl";

    @Override
    public TicketViewerEntity getAllTickets() {
        LOG.info("Entered " + " " + BASE_CLASS + "-> getAllTickets");
        TicketViewerEntity ticketViewerEntity = ticketViewerGateway.getTicketPage(Constants.PAGE_SIZE);
        return ticketViewerEntity;
    }
}
