package com.zendesk.zccucsc.zendeskticketviewer.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketDetailEntity {
    Ticket ticket;
    public Ticket getTicket() {
        return ticket;
    }
}
