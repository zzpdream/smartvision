package com.mws.web.net;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by ranfi on 2/23/16.
 */
public class BaseClientTest {

    protected <T> T get(String url, Class<T> cls) {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(JacksonFeature.class);
        Client client = ClientBuilder.newClient(clientConfig);
        WebTarget webTarget = client.target(url);
        Invocation.Builder request = webTarget.request(MediaType.APPLICATION_JSON);
        return request.get().readEntity(cls);
    }

    protected <T> T post(String url, Object data, Class<T> cls) {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(JacksonFeature.class);
        Client client = ClientBuilder.newClient(clientConfig);
        WebTarget webTarget = client.target(url);
        Invocation.Builder request = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = request.post(Entity.entity(data, MediaType.APPLICATION_JSON));
        return response.readEntity(cls);
    }

    protected <T> T postXml(String url, Object data, Class<T> cls) {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(JacksonFeature.class);
        Client client = ClientBuilder.newClient(clientConfig);
        WebTarget webTarget = client.target(url);
        Invocation.Builder request = webTarget.request(MediaType.APPLICATION_XML);
        Response response = null;
        try {
            response = request.post(Entity.entity(data, MediaType.APPLICATION_XML));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return response.readEntity(cls);
    }

}

