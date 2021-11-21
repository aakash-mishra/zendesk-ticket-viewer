package com.zendesk.zccucsc.zendeskticketviewer.gateway;

import com.zendesk.zccucsc.zendeskticketviewer.config.Constants;
import com.zendesk.zccucsc.zendeskticketviewer.config.Util;
import com.zendesk.zccucsc.zendeskticketviewer.entity.Ticket;
import com.zendesk.zccucsc.zendeskticketviewer.entity.TicketDetailEntity;
import com.zendesk.zccucsc.zendeskticketviewer.entity.TicketViewerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import static com.zendesk.zccucsc.zendeskticketviewer.config.Constants.*;

@Component
public class TicketViewerGatewayImpl implements TicketViewerGateway {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Util util;
    @Value("${api.token}")
    private String apiToken;
    @Value("${api.owner.username}")
    private String username;

    private static final Logger LOG = LogManager.getLogger(TicketViewerGatewayImpl.class);
    private final String BASE_CLASS = "TicketViewerGatewayImpl";

    @Override
    public TicketViewerEntity getTickets(String url) {
        ResponseEntity<TicketViewerEntity> response = null;
        try {
            LOG.info("Entered " + " " + BASE_CLASS + "-> getTicketPage");
            response = restTemplate.exchange(url, HttpMethod.GET, setHeaders(), TicketViewerEntity.class);
            TicketViewerEntity ticketViewerEntity = response.getBody();
            return ticketViewerEntity;
        }
        catch (Exception e) {
            LOG.error("Exception occurred while fetching ticket list.");
            //TODO - add exception
            return null;
        }
    }

    @Override
    public TicketDetailEntity getTicketById(String ticketId) {
        ResponseEntity<TicketDetailEntity> response = null;
        try {
            LOG.info("Entered " + " " + BASE_CLASS + "-> getTicketById");
            String url = TICKETS_BASE_URL + "/" + ticketId;
            response = restTemplate.exchange(url, HttpMethod.GET, setHeaders(), TicketDetailEntity.class);TicketDetailEntity ticketViewerEntity = response.getBody();
            return ticketViewerEntity;
        }
        catch (Exception e) {
            LOG.error("Exception occurred while fetching ticket by id.");
            //TODO - add exception
            return null;
        }
    }

    private HttpEntity<String> setHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        String credentials = username + ":" + apiToken;
        httpHeaders.add(AUTH_HEADER_KEY, "Basic " + util.getBase64EncodedString(credentials));
        httpHeaders.add(ACCEPT_HEADER_KEY, ACCEPT_HEADER_VALUE);
        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        return request;
    }





}
