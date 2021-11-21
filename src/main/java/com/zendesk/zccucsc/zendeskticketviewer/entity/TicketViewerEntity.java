package com.zendesk.zccucsc.zendeskticketviewer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketViewerEntity {
    Meta meta;
    Links links;

    public List<Ticket> getTickets() {
        return tickets;
    }

    List<Ticket> tickets;
}
