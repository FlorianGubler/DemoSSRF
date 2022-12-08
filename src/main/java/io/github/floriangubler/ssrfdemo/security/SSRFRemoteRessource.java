package io.github.floriangubler.ssrfdemo.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URL;
import java.util.List;

public class SSRFRemoteRessource {
    private String allowedHost;
    private List<Integer> allowedPorts;
    private List<String> allowedProtocols;
    private List<String> allowedPaths;
    private boolean allowNoPort;

    public boolean checkURL(URL url){
        System.out.println("Path: " + this.getAllowedPaths().contains(url.getPath()));
        System.out.println("Host: " + url.getHost().endsWith(this.getAllowedHost()));
        System.out.println("Protocol: " + this.getAllowedProtocols().contains(url.getProtocol()));
        System.out.println("Port: " + (this.isAllowNoPort() || this.getAllowedPorts().contains(url.getPort())));

        return url.getHost().endsWith(this.getAllowedHost())
                && this.getAllowedProtocols().contains(url.getProtocol())
                && this.getAllowedPaths().contains(url.getPath())
                && (this.isAllowNoPort() || this.getAllowedPorts().contains(url.getPort()));
    }

    public boolean isAllowNoPort() {
        return allowNoPort;
    }

    public void setAllowNoPort(boolean allowNoPort) {
        this.allowNoPort = allowNoPort;
    }
    public String getAllowedHost() {
        return allowedHost;
    }

    public void setAllowedHost(String allowedHost) {
        this.allowedHost = allowedHost;
    }

    public List<Integer> getAllowedPorts() {
        return allowedPorts;
    }

    public void setAllowedPorts(List<Integer> allowedPorts) {
        this.allowedPorts = allowedPorts;
    }

    public List<String> getAllowedProtocols() {
        return allowedProtocols;
    }

    public void setAllowedProtocols(List<String> allowedProtocols) {
        this.allowedProtocols = allowedProtocols;
    }

    public List<String> getAllowedPaths() {
        return allowedPaths;
    }

    public void setAllowedPaths(List<String> allowedPaths) {
        this.allowedPaths = allowedPaths;
    }
}
